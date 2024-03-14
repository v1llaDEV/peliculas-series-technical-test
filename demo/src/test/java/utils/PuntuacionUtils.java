package utils;

import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.entities.Puntuacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PuntuacionUtils {

    public static Puntuacion getPuntuacion(){
        Puntuacion puntuacion = Puntuacion.builder().id(1L).peliculaSerie(PeliculaSerieUtils
                .getPeliculaSerie()).empleado(EmpleadoUtils.getEmpleado()).puntuacion(7.0).build();
        return puntuacion;
    }

    public static Optional<Puntuacion> getPuntuacionOptional(){
        Optional<Puntuacion> puntuacion = Optional.of(Puntuacion.builder().id(1L).peliculaSerie(PeliculaSerieUtils
                .getPeliculaSerie()).empleado(EmpleadoUtils.getEmpleado()).puntuacion(7.0).build());
        return puntuacion;
    }

    public static PuntuacionRequest getPuntuacionRequest(){
        PuntuacionRequest puntuacionRequest = PuntuacionRequest.builder().peliculaSerie(PeliculaSerieUtils
                .getPeliculaSerie()).empleado(EmpleadoUtils.getEmpleado()).puntuacion(7.0).build();
        return puntuacionRequest;
    }

    public static PuntuacionResponse getPuntuacionResponse(){
        PuntuacionResponse puntuacionResponse = PuntuacionResponse.builder().peliculaSerie(PeliculaSerieUtils
                .getPeliculaSerie()).empleado(EmpleadoUtils.getEmpleado()).puntuacion(7.0).build();
        return puntuacionResponse;
    }

    public static Page<Puntuacion> getPuntuacionPage(){
        List<Puntuacion> listaPuntuacionsEsperada = new ArrayList<>();
        listaPuntuacionsEsperada.add(getPuntuacion());
        Page<Puntuacion> page = new PageImpl<>(listaPuntuacionsEsperada);
        return page;
    }

    public static List<PuntuacionResponse> getPuntuacionResponseList(){
        List<PuntuacionResponse> listaPuntuacionsResponse = new ArrayList<>();
        listaPuntuacionsResponse.add(getPuntuacionResponse());
        return listaPuntuacionsResponse;
    }


}
