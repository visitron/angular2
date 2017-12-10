package home.maintenance.service;

import java.io.IOException;

/**
 * Created by Buibi on 22.01.2017.
 */
public interface ImageRepositoryManager extends ImageRepository {
    void drop() throws IOException;
}
