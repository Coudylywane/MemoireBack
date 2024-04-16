package com.example.construction.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
@Setter
@AllArgsConstructor
public class InternalServerErrorException extends RuntimeException {
    private String message;
}
