package com.example.construction.request;

import com.example.construction.models.enumeration.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class TacheDto {
    private Long id;
    private String nom;
    private String description;
    private int dureeEstimee;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TaskStatus status;
    private int pourcentageExecution;
    private List<TacheArticleDto> articles = new ArrayList<>();
}
