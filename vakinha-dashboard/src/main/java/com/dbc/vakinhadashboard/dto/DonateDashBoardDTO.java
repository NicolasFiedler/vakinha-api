package com.dbc.vakinhadashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateDashBoardDTO {
    private String category;
    private Integer donates;
    private Double donatesValue;
}
