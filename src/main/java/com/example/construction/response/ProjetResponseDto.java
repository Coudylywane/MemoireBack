package com.example.construction.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjetResponseDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDateProvisioning;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private Long clientId;
}
