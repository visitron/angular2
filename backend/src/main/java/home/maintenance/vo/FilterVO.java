package home.maintenance.vo;

/**
 * Created by Buibi on 21.01.2017.
 */
public class FilterVO {
    private String filterId;
    private String filterType;
    private String filterName;
    private String field;
    private Object value;

    public FilterVO() {}

    public FilterVO(String filterId, String filterType, String filterName, String field, Object value) {
        this.filterId = filterId;
        this.filterType = filterType;
        this.filterName = filterName;
        this.field = field;
        this.value = value;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
