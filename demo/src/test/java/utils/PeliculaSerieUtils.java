package utils;

import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.entities.PeliculaSerie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PeliculaSerieUtils {

    public static PeliculaSerie getPeliculaSerie(){
        PeliculaSerie peliculaSerie = PeliculaSerie.builder().id(1L).titulo("test").director("test").anio(2024).build();
        return peliculaSerie;
    }

    public static PeliculaSerieRequest getPeliculaSerieRequest(){
        PeliculaSerieRequest peliculaSerie = PeliculaSerieRequest.builder().titulo("test").director("test")
                .anio(2024).genero("test").temporadas(1).notaMedia(5.0)
                .empleadoImplantacion(EmpleadoUtils.getEmpleado()).empleadoProposicion(EmpleadoUtils.getEmpleado()).build();
        return peliculaSerie;
    }

    public static PeliculaSerieResponse getPeliculaSerieResponse(){
        PeliculaSerieResponse peliculaSerie = PeliculaSerieResponse.builder().id(1L).titulo("test").director("test")
                .anio(2024).genero("test").temporadas(1).duracion(LocalTime.now()).notaMedia(5.0)
                .empleadoImplantacion(EmpleadoUtils.getEmpleado()).empleadoProposicion(EmpleadoUtils.getEmpleado()).build();
        return peliculaSerie;
    }

    public static Page<PeliculaSerie> getPeliculaSeriePage(){
        List<PeliculaSerie> listaPeliculaSeriesEsperada = new ArrayList<>();
        listaPeliculaSeriesEsperada.add(getPeliculaSerie());
        Page<PeliculaSerie> page = new PageImpl<>(listaPeliculaSeriesEsperada);
        return page;
    }

    public static List<PeliculaSerieResponse> getPeliculaSerieResponseList(){
        List<PeliculaSerieResponse> listaPeliculaSeriesResponse = new ArrayList<>();
        listaPeliculaSeriesResponse.add(getPeliculaSerieResponse());
        return listaPeliculaSeriesResponse;
    }


}
