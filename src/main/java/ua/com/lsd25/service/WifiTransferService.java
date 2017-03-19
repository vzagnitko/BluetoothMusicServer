package ua.com.lsd25.service;

import lombok.NonNull;

/**
 * @author vzagnitko
 */
public interface WifiTransferService {

    void sendMusicStream(@NonNull Long musicId) throws ApplicationException;

}
