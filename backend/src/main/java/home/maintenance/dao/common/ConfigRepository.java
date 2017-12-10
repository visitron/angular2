package home.maintenance.dao.common;

import home.maintenance.model.Config;
import home.maintenance.model.ConfigName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface ConfigRepository extends JpaRepository<Config, ConfigName> {}
