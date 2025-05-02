package com.example.construction.request;

import com.example.construction.models.Utilisateur;
import com.sun.istack.NotNull;
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
    private String status;
    private List<String> validationStatuses;
    @NotNull()
    private Long clientId;
    //private List<SiteRequestDto> sites;

}
