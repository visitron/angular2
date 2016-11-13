package home.maintenance.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Buibi on 08.11.2016.
 */
@Entity
@Table(name = "ITEM_ADVANCED")
@PrimaryKeyJoinColumn(name = "ITEM")
public class AdvancedItem extends Item {
    /**
     * Original size has to be 256x256 pixels
     */
    @Column(name = "IMAGE")
    @Lob
    private byte[] image;
    /**
     * Has to be not null if the product is a part of another more complicated one
     */
    @Transient
    private Item parent;
    /**
     * In case of complex product it will contain all children products (which has parent)
     */
    @Transient
    private List<Item> children = new ArrayList<>();
    /**
     * Embeddable entity which holds information about specialist required for a maintenance
     */
    @Embedded
    private Specialist specialist;
    /**
     * In case of complex product it can consist of smaller pieces
     */
    @OneToMany(mappedBy = "owner")
    private List<AdditionalDetail> additionalDetails = new ArrayList<>();

    public AdvancedItem() {};

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public List<AdditionalDetail> getAdditionalDetails() {
        return Collections.unmodifiableList(additionalDetails);
    }

    public void addAdditionalDetail(AdditionalDetail detail) {
        this.additionalDetails.add(detail);
    }

    @Embeddable
    public static class Specialist {
        /**
         * Name of the company
         */
        @Column(name = "SPECIALIST_COMPANY", length = 32)
        private String company;
        /**
         * Email pattern is <code>login@x.y</code>
         */
        @Column(name = "SPECIALIST_email", length = 32)
        private String email;
        /**
         * Phone number pattern is 971234567 or 441234567, i.e. contains of 19 digits
         */
        @Column(name = "SPECIALIST_PHONE")
        private int phone;
        /**
         * Represents cost in cents, i.e. usual amount on money multiplied by 100. Has to be within the limit 0-10.000
         */
        @Min(0)
        @Column(name = "SPECIALIST_COST")
        private int cost;

        public Specialist() {};

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }

    @Entity
    @Table(name = "ADDITIONAL_DETAIL")
    public static class AdditionalDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
        @SequenceGenerator(name = "generator", sequenceName = "SEQ_ADDITIONAL_DETAIL", allocationSize = 10)
        @Column(name = "ID")
        private int id;


        @ManyToOne
        @JoinColumn(name = "ITEM_ADVANCED")
        private AdvancedItem owner;

        /**
         * Detail name
         */
        @Column(name = "NAME", length = 32)
        private String name;
        /**
         * Holds a short description of the auxiliary detail
         */
        @Column(name = "DESCRIPTION", length = 128)
        private String description;
        /**
         * Represents cost in cents, i.e. usual amount on money multiplied by 100. Has to be within the limit 0-10.000
         */
        @Min(0)
        @Column(name = "COST")
        private int cost;

        public AdditionalDetail() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }

}
