package home.maintenance.dao.common;

import home.maintenance.model.Config;
import home.maintenance.model.ConfigName;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface ConfigRepository extends CrudRepository<Config, ConfigName> {}
