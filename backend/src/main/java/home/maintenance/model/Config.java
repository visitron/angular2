package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Config {
    @Id
    @Enumerated(EnumType.STRING)
    @Column
    private ConfigName configName;
    @Enumerated(EnumType.STRING)
    @Column
    private ValueType valueType;
    @Column
    private String stringValue;
    @Type(type = "yes_no")
    @Column
    private Boolean booleanValue;
    @Column
    private Integer intValue;
}

enum ValueType {INTEGER, BOOLEAN, STRING}