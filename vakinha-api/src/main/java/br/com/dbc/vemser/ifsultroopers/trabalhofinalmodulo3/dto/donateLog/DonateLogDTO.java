package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donateLog;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateLogDTO {
    private Integer idDonate;
    private Integer idRequest;
    private String donatorEmail;
    private Double donateValue;
    private String category;
    private Integer operation;
}
