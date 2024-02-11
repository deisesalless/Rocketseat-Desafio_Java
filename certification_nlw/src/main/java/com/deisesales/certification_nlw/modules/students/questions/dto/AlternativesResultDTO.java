package com.deisesales.certification_nlw.modules.students.questions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlternativesResultDTO {
    private UUID id;
    private String description;
}
