package utils;

import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoUtils {

    public static Empleado getEmpleado(){
        Empleado empleado = Empleado.builder().id(1L).nombre("test").email("test@test.com").build();
        return empleado;
    }

    public static Optional<Empleado> getEmpleadoOptional(){
        Optional<Empleado> empleado = Optional.of(Empleado.builder().id(1L).nombre("test").email("test@test.com").build());
        return empleado;
    }

    public static EmpleadoRequest getEmpleadoRequest(){
        EmpleadoRequest empleadoRequest = EmpleadoRequest.builder().nombre("test").email("test@test.com")
                .password("test").fechaNacimiento(LocalDate.now()).tipoProgramador(TipoProgramadorUtils.getTipoProgramador())
                .rol(RolUtils.getRol()).build();
        return empleadoRequest;
    }

    public static EmpleadoResponse getEmpleadoResponse(){
        EmpleadoResponse empleadoResponse = EmpleadoResponse.builder().id(1L).nombre("test").email("test@test.com").build();
        return empleadoResponse;
    }

    public static Page<Empleado> getEmpleadoPage(){
        List<Empleado> listaEmpleadosEsperada = new ArrayList<>();
        listaEmpleadosEsperada.add(getEmpleado());
        Page<Empleado> page = new PageImpl<>(listaEmpleadosEsperada);
        return page;
    }

    public static List<EmpleadoResponse> getEmpleadoResponseList(){
        List<EmpleadoResponse> listaEmpleadosResponse = new ArrayList<>();
        listaEmpleadosResponse.add(getEmpleadoResponse());
        return listaEmpleadosResponse;
    }


}
