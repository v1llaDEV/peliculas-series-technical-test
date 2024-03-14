package com.example.demo.controllers;

import com.example.demo.dtos.ExceptionResponseDto;
import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.services.PuntuacionService;
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
@Tag(name = "Puntuaciones", description = "API para el manejo del recurso puntuaciones")
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api/v1/puntuaciones")
public class PuntuacionController {

    private final PuntuacionService puntuacionService;

    @Operation(summary = "Recupera todos las puntuaciones con paginación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PuntuacionResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen puntuaciones", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping
    public ResponseEntity<List<PuntuacionResponse>> getAllPuntuaciones(@RequestParam(defaultValue = "0") int pagina,
                                                                       @RequestParam(defaultValue = "10") int tamanio)
                                                                        throws PageParametersInvalidException {
        List<PuntuacionResponse> listaPuntuaciones = puntuacionService.getAllPuntuacionesPaginado(pagina, tamanio);
        if(listaPuntuaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaPuntuaciones, HttpStatus.OK);
    }

    @Operation(summary = "Recupera una puntuación través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PuntuacionResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe la puntuación", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/{id}")
    public PuntuacionResponse getPuntuacionById(@PathVariable Long id) {
        return puntuacionService.getPuntuacionById(id);
    }

    @Operation(summary = "Recupera la puntuación más alta del empleado establecido en la request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PuntuacionResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe la puntuación", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/pelicula-maxima-puntuacion-por-empleado/{idEmpleado}")
    public PuntuacionResponse getPuntuacionMasAltaPorIdEmpleado(@PathVariable Long idEmpleado) {
         return puntuacionService.getMaxPuntuacionByIdEmpleado(idEmpleado);
    }

    @Operation(summary = "Crea una puntuación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Puntuación creada correctamente", content = @Content(schema = @Schema(implementation = PuntuacionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PuntuacionResponse createPuntuacion(@RequestBody PuntuacionRequest puntuacionRequest) {
        return puntuacionService.createPuntuacion(puntuacionRequest);
    }

    @Operation(summary = "Actualiza una puntuación a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Puntuación actualizada correctamente", content = @Content(schema = @Schema(implementation = PuntuacionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe la puntuación", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping()
    public PuntuacionResponse updatePuntuacion(@RequestBody PuntuacionRequest puntuacionRequest) {
        return puntuacionService.updatePuntuacion(puntuacionRequest);
    }

    @Operation(summary = "Elimina una puntuación a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Puntuación eliminada correctamente", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe la puntuación", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePuntuacion(@PathVariable Long id) {
        puntuacionService.deletePuntuacion(id);
    }
}
