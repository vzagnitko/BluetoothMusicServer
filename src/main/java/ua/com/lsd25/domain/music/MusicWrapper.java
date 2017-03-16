package ua.com.lsd25.domain.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.com.lsd25.domain.Wrapper;

/**
 * @author vzagnitko
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class MusicWrapper implements Wrapper {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    public MusicWrapper() {

    }

    public MusicWrapper(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MusicWrapper(Music music) {
        this(music.getId(), music.getName());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
