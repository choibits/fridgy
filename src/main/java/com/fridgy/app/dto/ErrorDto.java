package com.fridgy.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;
    private final OffsetDateTime timestamp = OffsetDateTime.now(); // gives current time at the time of error object creation
}
