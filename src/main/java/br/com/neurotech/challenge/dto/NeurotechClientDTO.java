package br.com.neurotech.challenge.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeurotechClientDTO {

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotNull(message = "Age is mandatory.")
    @Min(value = 18, message = "The minimum age is 18 years old.")
    private Integer age;

    @NotNull(message = "Income is mandatory.")
    @Min(value = 1, message = "Income must be positive.")
    private Double income;
}