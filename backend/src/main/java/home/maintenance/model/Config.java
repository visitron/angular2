package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
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

    public Config() {}

    public ConfigName getConfigName() {
        return configName;
    }

    public void setConfigName(ConfigName configName) {
        this.configName = configName;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}

enum ValueType {INTEGER, BOOLEAN, STRING}