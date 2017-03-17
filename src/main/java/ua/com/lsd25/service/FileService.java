package ua.com.lsd25.service;

import lombok.NonNull;

import java.io.InputStream;

/**
 * @author vzagnitko
 */
public interface FileService {

    InputStream fastReadFile(@NonNull String fileName) throws ApplicationException;

    void fastSaveFile(@NonNull byte[] buffer, @NonNull String fileName) throws ApplicationException;

}
