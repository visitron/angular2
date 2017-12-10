package home.maintenance.service.impl;

import home.maintenance.service.ImageRepositoryManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Buibi on 22.01.2017.
 */
@Service("imageRepository")
public class ImageRepositoryImpl implements ImageRepositoryManager {

    @Value("${application.resources.static-locations}")
    private String folder;

    @Override
    public void save(byte[] image, long id) throws IOException {
        Files.copy(new ByteArrayInputStream(image), Paths.get(folder + "images/" + id + ".jpg"));
    }

    @Override
    public void delete(long id) throws IOException {
        Files.delete(Paths.get(folder + id));
    }

    @Override
    public void drop() throws IOException {
        Files.walkFileTree(Paths.get(folder + "images/"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
