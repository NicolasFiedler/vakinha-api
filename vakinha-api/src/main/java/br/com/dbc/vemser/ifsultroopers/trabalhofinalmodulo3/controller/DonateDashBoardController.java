package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.controller;

import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.dashboard.donate.DonateDashBoardDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateCreateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.dto.donate.DonateDTO;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.entity.Category;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.DonateDashBoardService;
import br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3.service.DonateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/donateDashBoard")
@Validated
@RequiredArgsConstructor
public class DonateDashBoardController {

    private final DonateDashBoardService donateDashBoardService;

//    Admin
    @ApiOperation(value = "Retorna o total de doacoes o valor arrecadado por Categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna as informacoes de arrecadacao por Categoria"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/")
    public ResponseEntity<List<DonateDashBoardDTO>> donatesDashBoard() throws Exception {

        return ResponseEntity.ok(donateDashBoardService.donatesDashBoard());
    }

}
