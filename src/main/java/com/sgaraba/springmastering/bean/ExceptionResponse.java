package com.sgaraba.springmastering.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sgaraba.springmastering.utils.LocalTimestampSerializer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    @JsonSerialize(using = LocalTimestampSerializer.class)
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String details;

    public ExceptionResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }
}