package home.maintenance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Buibi on 21.01.2017.
 */
@Entity
@Table
public class Config {
    @Id
    @Column
    private ConfigName configName;
    @Column
    private ValueType valueType;
    @Column
    private String stringValue;
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

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public int getIntValue() {
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