package ua.com.lsd25.domain.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class MusicWrapper implements Serializable {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("is_favorite")
    private boolean isFavorite;

//    public MusicWrapper() {
//
//    }

}
