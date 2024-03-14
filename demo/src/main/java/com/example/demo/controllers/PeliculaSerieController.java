package com.example.demo.controllers;

import com.example.demo.dtos.ExceptionResponseDto;
import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.services.PeliculaSerieService;
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
@Tag(name = "Peliculas", description = "API para el manejo del recurso películas")
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api/v1/peliculasseries")
public class PeliculaSerieController {

    private final PeliculaSerieService peliculaSerieService;

    @Operation(summary = "Recupera todos las películas y series con paginación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PeliculaSerieResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen películas y series", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping
    public ResponseEntity<List<PeliculaSerieResponse>> getAllPeliculasSeriesPaginadas(@RequestParam(defaultValue = "0") int pagina,
                                                                            @RequestParam(defaultValue = "5") int tamanio)
            throws PageParametersInvalidException {
        List<PeliculaSerieResponse> listaPeliculasSeries = peliculaSerieService.getAllPeliculasSeriesPaginado(pagina, tamanio);
        if(listaPeliculasSeries.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaPeliculasSeries, HttpStatus.OK);
    }

    @Operation(summary = "Recupera todos las películas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PeliculaSerieResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen películas", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping("/peliculas")
    public ResponseEntity<List<PeliculaSerieResponse>> getAllPeliculas(){
        List<PeliculaSerieResponse> listaPeliculasSeries = peliculaSerieService.getAllPeliculas();
        if(listaPeliculasSeries.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaPeliculasSeries, HttpStatus.OK);
    }

    @Operation(summary = "Recupera todos las series")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PeliculaSerieResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen series", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping("/series")
    public ResponseEntity<List<PeliculaSerieResponse>> getAllSeries() {
        List<PeliculaSerieResponse> listaSeries = peliculaSerieService.getAllSeries();
        if(listaSeries.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaSeries, HttpStatus.OK);
    }

    @Operation(summary = "Recupera una película o serie a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PeliculaSerieResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe la película o serie", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/{id}")
    public PeliculaSerieResponse getPeliculaSerieById(@PathVariable Long id) {
        return peliculaSerieService.getPeliculaSerieById(id);
    }

    @Operation(summary = "Crea una película o serie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Película o serie creada correctamente", content = @Content(schema = @Schema(implementation = PeliculaSerieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PeliculaSerieResponse createPeliculaSerie(@RequestBody PeliculaSerieRequest peliculaSerieRequest) {
        return peliculaSerieService.createPeliculaSerie(peliculaSerieRequest);
    }

    @Operation(summary = "Actualiza una película o serie a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película o serie actualizada correctamente", content = @Content(schema = @Schema(implementation = PeliculaSerieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe la película o serie", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping("/{id}")
    public PeliculaSerieResponse updatePeliculaSerie(@PathVariable Long id, @RequestBody PeliculaSerieRequest peliculaSerieRequest) {
        return peliculaSerieService.updatePeliculaSerie(id, peliculaSerieRequest);
    }

    @Operation(summary = "Elimina una película o serie a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Película o serie eliminada correctamente", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe la película o serie", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePeliculaSerie(@PathVariable Long id) {
        peliculaSerieService.deletePeliculaSerie(id);
    }
}
