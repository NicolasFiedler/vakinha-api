package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmailDTO extends RequestCreateDTO {
    @ApiModelProperty(value = "Nome do usuário")
    private String username;

    @ApiModelProperty(value = "Email")
    private String ownerEmail;

    @ApiModelProperty(value = "Título")
    private String title;

    @ApiModelProperty(value = "Meta")
    private Double goal;
}
