package ua.com.lsd25.controller.rest.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Data
public class MusicRequest implements Serializable {

    @JsonProperty("music_id")
    @NotNull
    private Long musicId;

    public MusicRequest() {

    }

}
