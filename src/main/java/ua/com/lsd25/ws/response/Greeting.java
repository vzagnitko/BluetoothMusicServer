package ua.com.lsd25.ws.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@EqualsAndHashCode
@ToString
public class Greeting implements Serializable {

    @Getter
    @Setter
    private String content;

    public Greeting(String content) {
        this.content = content;
    }

}