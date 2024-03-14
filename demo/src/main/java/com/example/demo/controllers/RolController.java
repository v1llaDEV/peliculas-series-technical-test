package com.example.demo.controllers;

import com.example.demo.dtos.ExceptionResponseDto;
import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.dtos.responses.RolResponse;
import com.example.demo.services.RolService;
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
@Tag(name = "Roles", description = "API para el manejo del recurso roles")
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolService rolService;

    @Operation(summary = "Recupera todos los roles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RolResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen roles", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping
    public ResponseEntity<List<RolResponse>> getAllRoles() {
        List<RolResponse> listaRoles = rolService.getAll();
        if(listaRoles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaRoles, HttpStatus.OK);
    }

    @Operation(summary = "Recupera un rol través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe el rol", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/{id}")
    public RolResponse getRolById(@PathVariable Long id) {
        return rolService.getRolById(id);
    }

    @Operation(summary = "Crea un rol")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente", content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RolResponse createRol(@RequestBody RolRequest rolDetails) {
        return rolService.createRol(rolDetails);
    }

    @Operation(summary = "Actualiza un rol a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente", content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el rol", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping("/{id}")
    public RolResponse updateRol(@PathVariable Long id, @RequestBody RolRequest rolDetails) {
        return rolService.updateRol(id, rolDetails);
    }

    @Operation(summary = "Elimina un rol a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el rol", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
    }
}
