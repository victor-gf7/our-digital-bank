package br.com.joao.ourdigitalbank.controller.response;

import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Accessors(chain = true)
public class ResponseError {

    @NotNull(message="Details cannot be null")
    private LocalDateTime timestamp;

    @NotNull(message="Details cannot be null")
    private String details;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
