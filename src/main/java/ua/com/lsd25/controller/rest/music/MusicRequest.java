package ua.com.lsd25.controller.rest.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@EqualsAndHashCode
@ToString
public class MusicRequest implements Serializable {

    @JsonProperty("music_id")
    @NotNull
    private Long musicId;

    public MusicRequest() {

    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

}
