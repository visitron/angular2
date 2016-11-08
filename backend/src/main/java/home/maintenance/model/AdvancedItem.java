package home.maintenance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Buibi on 08.11.2016.
 */
public class AdvancedItem extends Item {
    /**
     * Original size has to be 256x256 pixels
     */
    private byte[] image;
    /**
     * Has to be not null if the product is a part of another more complicated one
     */
    private Item parent;
    /**
     * Embeddable entity which holds information about specialist required for a maintenance
     */
    private Specialist specialist;
    /**
     * In case of complex product it can consist of smaller pieces
     */
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

    public static class Specialist {
        /**
         * Name of the company
         */
        private String company;
        /**
         * Email pattern is <code>login@x.y</code>
         */
        private String email;
        /**
         * Phone number pattern is 971234567 or 441234567, i.e. contains of 19 digits
         */
        private int phone;
        /**
         * Represents cost in cents, i.e. usual amount on money multiplied by 100. Has to be within the limit 0-10.000
         */
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

    public static class AdditionalDetail {
        /**
         * Detail name
         */
        private String name;
        /**
         * Holds a short description of the auxiliary detail
         */
        private String description;
        /**
         * Represents cost in cents, i.e. usual amount on money multiplied by 100. Has to be within the limit 0-10.000
         */
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
