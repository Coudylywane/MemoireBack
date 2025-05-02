package com.example.construction.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DtoProjet implements Serializable {
    private Long id;
    private Long clientId;
}
