package ua.com.lsd25.service;

import lombok.NonNull;

/**
 * @author vzagnitko
 */
public interface FileService {

    byte[] fastReadFile(@NonNull String fileName) throws ApplicationException;

    void fastSaveFile(@NonNull byte[] buffer, @NonNull String fileName) throws ApplicationException;

}
