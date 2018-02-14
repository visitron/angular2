package home.maintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Persistable implements org.springframework.data.domain.Persistable<Long> {

    @JsonIgnore
    @Override
    public boolean isNew() {
        return getId() == null;
    }
}
