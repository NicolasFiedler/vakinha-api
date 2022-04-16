package com.dbc.vakinhadashboard.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonateDashBoardEntity {
    private Integer idDonate;
    private Integer idRequest;
    private String donatorEmail;
    private Double donateValue;
    private String category;
}
