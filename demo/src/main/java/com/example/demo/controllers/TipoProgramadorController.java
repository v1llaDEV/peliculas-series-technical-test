package com.example.demo.controllers;

import com.example.demo.dtos.ExceptionResponseDto;
import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.dtos.responses.TipoProgramadorResponse;
import com.example.demo.services.TipoProgramadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Tag(name = "Tipos programador", description = "API para el manejo del recurso de Tipos de programador")
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api/v1/tiposprogramador")
public class TipoProgramadorController {

    private final TipoProgramadorService tipoProgramadorService;

    @Operation(summary = "Recupera todos los tipos de programador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TipoProgramadorResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen tipos de programador", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping
    public ResponseEntity<List<TipoProgramadorResponse>> getAllTipoProgramador() {
        List<TipoProgramadorResponse> listaTiposProgramador = tipoProgramadorService.getAll();
        if (listaTiposProgramador.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaTiposProgramador, HttpStatus.OK);
    }

    @Operation(summary = "Recupera un tipo de programación a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = TipoProgramadorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe el tipo programador", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/{id}")
    public TipoProgramadorResponse getTipoProgramadorById(@PathVariable Long id) {
        return tipoProgramadorService.getTipoProgramadorById(id);
    }

    @Operation(summary = "Crea un tipo de programador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de programador creado correctamente", content = @Content(schema = @Schema(implementation = TipoProgramadorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TipoProgramadorResponse createTipoProgramador(@RequestBody TipoProgramadorRequest tipoProgramadorDetails) {
        return tipoProgramadorService.createTipoProgramador(tipoProgramadorDetails);
    }

    @Operation(summary = "Actualiza un tipo de programador a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de programador actualizado correctamente", content = @Content(schema = @Schema(implementation = TipoProgramadorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el tipo programador", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping("/{id}")
    public TipoProgramadorResponse updateTipoProgramador(@PathVariable Long id, @RequestBody TipoProgramadorRequest tipoProgramadorDetails) {
        return tipoProgramadorService.updateTipoProgramador(id, tipoProgramadorDetails);
    }

    @Operation(summary = "Elimina un tipo de programador a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de programador eliminado correctamente", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el tipo programador", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTipoProgramador(@PathVariable Long id) {
        tipoProgramadorService.deleteTipoProgramador(id);
    }
}
