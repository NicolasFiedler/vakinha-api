package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.request;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String goal;
}
