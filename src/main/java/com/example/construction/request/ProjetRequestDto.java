package com.example.construction.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ProjetRequestDto {
    private Long projectId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDateProvisioning;
    private LocalDateTime endDate;
    private List<String> validationStatuses;
    //private List<SiteRequestDto> sites;

}
