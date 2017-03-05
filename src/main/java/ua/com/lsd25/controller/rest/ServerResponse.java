package ua.com.lsd25.controller.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @author vzagnitko
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class ServerResponse<T> implements Serializable {

    @Getter
    @Setter
    @JsonProperty("entity")
    private T entity;

    @Getter
    @Setter
    @JsonProperty("error_field_map")
    private Map<String, String> errorValidationFields;

    @Getter
    @Setter
    @JsonProperty("message")
    private String message;

    @Getter
    @Setter
    @JsonProperty("status")
    private int status;

    @Getter
    @Setter
    @JsonProperty("ts")
    private long ts;

    public ServerResponse(int status) {
        this.status = status;
        this.ts = System.currentTimeMillis();
    }

    public ServerResponse(T entity, int status) {
        this(status);
        this.entity = entity;
    }

    public ServerResponse(Map<String, String> errorValidationFields, int status) {
        this(status);
        this.errorValidationFields = errorValidationFields;
    }

    public ServerResponse(String message, int status) {
        this(status);
        this.message = message;
    }

}
