package home.maintenance.service;

import java.io.IOException;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface ImageRepository {
    void save(byte[] image, long id) throws IOException;
    void delete(long id) throws IOException;
}
