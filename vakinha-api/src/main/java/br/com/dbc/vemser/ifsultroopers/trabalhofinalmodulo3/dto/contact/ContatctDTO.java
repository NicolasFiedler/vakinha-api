package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.contact;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatctDTO {
    private String name;
    private String email;
    private String phoneNumber;
}
