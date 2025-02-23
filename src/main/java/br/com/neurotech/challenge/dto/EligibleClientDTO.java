package br.com.neurotech.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EligibleClientDTO {
    private String name;
    private double income;

}
