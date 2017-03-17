package ua.com.lsd25.service;

import lombok.NonNull;
import ua.com.lsd25.domain.music.Music;

import java.io.InputStream;
import java.util.List;

/**
 * @author vzagnitko
 */
public interface MusicService {

    List<Music> findMusics() throws ApplicationException;

    Music findMusicByName(@NonNull String name) throws ApplicationException;

    Music findMusicById(@NonNull Long id) throws ApplicationException;

    long saveMusic(@NonNull String musicName, @NonNull byte[] musicBytes) throws ApplicationException;

    InputStream getMusicInputStream(@NonNull Long musicId) throws ApplicationException;

}
