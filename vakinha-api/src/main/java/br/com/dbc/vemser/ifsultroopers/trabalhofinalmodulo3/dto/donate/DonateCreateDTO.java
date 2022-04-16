package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonateCreateDTO {

    @ApiModelProperty(value = "Nome do Doador")
    @NotEmpty @NotNull
    private String donatorName;

    @Email
    @ApiModelProperty(value = "Email do Doador")
    private String donatorEmail;

    @Size(max = 11)
    @ApiModelProperty(value = "numero de telefone")
    private String phoneNumber;

    @NotNull @Min(1)
    @ApiModelProperty(value = "Valor da doação")
    private Double donateValue;

    @NotEmpty
    @ApiModelProperty(value = "Descrição")
    private String description;


}
