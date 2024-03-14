package utils;

import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.dtos.responses.TipoProgramadorResponse;
import com.example.demo.entities.TipoProgramador;

import java.util.ArrayList;
import java.util.List;

public class TipoProgramadorUtils {

    public static TipoProgramador getTipoProgramador(){
        TipoProgramador tipoProgramador = TipoProgramador.builder().id(1L).nombre("test").build();
        return tipoProgramador;
    }

    public static TipoProgramadorRequest getTipoProgramadorRequest(){
        TipoProgramadorRequest tipoProgramadorRequest = TipoProgramadorRequest.builder().nombre("test").build();
        return tipoProgramadorRequest;
    }

    public static TipoProgramadorResponse getTipoProgramadorResponse(){
        TipoProgramadorResponse tipoProgramador = TipoProgramadorResponse.builder().id(1L).nombre("test").build();
        return tipoProgramador;
    }

    public static List<TipoProgramadorResponse> getTipoProgramadorResponseList(){
        List<TipoProgramadorResponse> listaTipoProgramadorResponse = new ArrayList<>();
        listaTipoProgramadorResponse.add(getTipoProgramadorResponse());
        return listaTipoProgramadorResponse;
    }


}
