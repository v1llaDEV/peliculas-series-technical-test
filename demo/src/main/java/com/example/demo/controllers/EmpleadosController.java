package com.example.demo.controllers;

import com.example.demo.dtos.ExceptionResponseDto;
import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.services.EmpleadoService;
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
@Tag(name = "Empleados", description = "API para el manejo del recurso empleados")
@RestController
@SecurityRequirement(name="Bearer Authentication")
@RequestMapping("/api/v1/empleados")
public class EmpleadosController {

    private final EmpleadoService empleadoService;

    @Operation(summary = "Recupera todos los empleados con paginación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmpleadoResponse.class)))),
            @ApiResponse(responseCode = "204", description = "No existen naves espaciales", content = @Content(schema = @Schema(implementation = Void.class)))})
    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> getAllEmpleados(@RequestParam(defaultValue = "0") int pagina,
                                                                  @RequestParam(defaultValue = "5") int tamanio)
                                                                    throws PageParametersInvalidException {
        List<EmpleadoResponse> listaEmpleados = empleadoService.getAllEmpleadosPaginado(pagina, tamanio);
        if(listaEmpleados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listaEmpleados, HttpStatus.OK);
    }

    @Operation(summary = "Recupera un empleado a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class))),
            @ApiResponse(responseCode = "404", description = "No existe el empleado", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @GetMapping("/{id}")
    public EmpleadoResponse getEmpleadoById(@PathVariable Long id) {
        return empleadoService.getEmpleadoById(id);
    }

    @Operation(summary = "Crea un empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmpleadoResponse createEmpleado(@RequestBody EmpleadoRequest empleado) {
        return empleadoService.createEmpleado(empleado);
    }

    @Operation(summary = "Actualiza un empleado a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente", content = @Content(schema = @Schema(implementation = EmpleadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el empleado", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @PutMapping("/{id}")
    public EmpleadoResponse updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoRequest empleadoDetails) {
        return empleadoService.updateEmpleado(id, empleadoDetails);
    }

    @Operation(summary = "Elimina un empleado a través del ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación de algún parámetro de la petición", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No existe el empleado", content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
    }
}
