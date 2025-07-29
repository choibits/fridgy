package com.fridgy.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // this will exclude null values from the response
public class AuthResponseDto {
    private boolean success;
    private String message;
    private Long id;
    private String email;
}
