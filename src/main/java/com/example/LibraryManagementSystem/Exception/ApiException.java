package com.example.LibraryManagementSystem.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
public class ApiException {
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;


}
