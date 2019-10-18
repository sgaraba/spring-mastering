package com.sgaraba.springmastering.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.sgaraba.springmastering.utils.LocalDateDeserializer;
import com.sgaraba.springmastering.utils.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int id;
    @NotNull
    private String user;
    @Size(min = 9, message = "Enter atleast 10 Characters.")
    private String desc;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate targetDate;
    private boolean isDone;
}
