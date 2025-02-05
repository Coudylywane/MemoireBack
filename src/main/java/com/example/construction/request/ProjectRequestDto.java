package com.example.construction.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProjectRequestDto {
    private String projectId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDateProvisioning;
    private LocalDateTime endDate;
    //private List<SiteRequestDto> sites;

}
