package home.maintenance.service.impl;

import home.maintenance.service.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Buibi on 22.01.2017.
 */
@Service("imageRepository")
public class ImageRepositoryImpl implements ImageRepository {

    @Value("${spring.resources.static-locations}")
    private String folder;

    @Override
    public void save(byte[] image, long id) throws IOException {
        Files.copy(new ByteArrayInputStream(image), Paths.get("e:/temp/images/" + id + ".jpg"));
    }

    @Override
    public void delete(long id) throws IOException {
        Files.delete(Paths.get(folder + id));
    }
}
