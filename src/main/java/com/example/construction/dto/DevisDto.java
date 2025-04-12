package com.example.construction.dto;

import com.example.construction.models.enumeration.DevisStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DevisDto implements Serializable {
    private Long id;
    private BigDecimal montant;
    private LocalDate dateCreation;
    private DevisStatus statut ;
}
