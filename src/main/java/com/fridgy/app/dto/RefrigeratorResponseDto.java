package com.fridgy.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefrigeratorResponseDto {
    private Long id;
    private String fridgeName;
    private List<Long> userIds;
}
