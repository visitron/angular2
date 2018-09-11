package home.maintenance.service.impl;

import home.maintenance.service.ImageRepositoryManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(folder + "images/"));
    }

    @Override
    public void save(byte[] image, long id) throws IOException {
        Files.copy(new ByteArrayInputStream(image), Paths.get(buildFileName(id)), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void delete(long id) throws IOException {
        Files.deleteIfExists(Paths.get(buildFileName(id)));
    }

    @Override
    public void drop() throws IOException {
        Files.walkFileTree(Paths.get(folder + "images/"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private String buildFileName(long id) {
        return folder + "images/" + id + ".jpg";
    }
}
