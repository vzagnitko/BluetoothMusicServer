package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.FileService;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author vzagnitko
 */
@Service
@CacheConfig(cacheNames = "musics_bytes")
public class FileServiceImpl implements FileService {

    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);

    @Value("${spring.http.multipart.location}")
    private String path;

    @Override
    @Cacheable
    public byte[] fastReadFile(@NonNull String fileName) throws ApplicationException {
        String filePath = StringUtils.join(path, fileName);
        if (Files.notExists(Paths.get(filePath))) {
            throw new IllegalStateException(String.format("File with name: %s not found in the server!", filePath));
        }
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            try (FileChannel in = raf.getChannel()) {
                in.force(true);
                MappedByteBuffer mbb = in.map(FileChannel.MapMode.READ_ONLY, 0L, in.size());
                mbb = mbb.force();
                byte[] buffer = new byte[(int) in.size()];
                mbb.get(buffer);
                return buffer;
            }
        } catch (Exception exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot read music from the disc, fileName: %s", fileName));
        }
    }

    @Override
    @CacheEvict
    public void fastSaveFile(@NonNull byte[] buffer, @NonNull String fileName) throws ApplicationException {
        String filePath = StringUtils.join(path, fileName);
        try (FileChannel rwChannel = new RandomAccessFile(filePath, "rw").getChannel()) {
            rwChannel.force(true);
            rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length).put(buffer);
        } catch (Exception exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot save music on the disc, fileName: %s", fileName));
        }
    }

}
