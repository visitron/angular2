package home.maintenance.vo;

import java.util.List;

/**
 * Created by Buibi on 21.01.2017.
 */
public class FilterGroupVO {
    private String groupId;
    private String groupName;
    private List<FilterVO> filters;

    public FilterGroupVO() {}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<FilterVO> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterVO> filters) {
        this.filters = filters;
    }
}
