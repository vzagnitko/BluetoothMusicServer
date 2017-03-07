package ua.com.lsd25.controller.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
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

    @JsonProperty("entity")
    private T entity;

    @JsonProperty("error_field_map")
    private Map<String, String> errorValidationFields;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Map<String, String> getErrorValidationFields() {
        return errorValidationFields;
    }

    public void setErrorValidationFields(Map<String, String> errorValidationFields) {
        this.errorValidationFields = errorValidationFields;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

}
