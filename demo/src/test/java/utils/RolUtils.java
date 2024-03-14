package utils;

import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.dtos.responses.RolResponse;
import com.example.demo.entities.Rol;

import java.util.ArrayList;
import java.util.List;

public class RolUtils {

    public static Rol getRol(){
        Rol rol = Rol.builder().id(1L).nombre("test").build();
        return rol;
    }

    public static RolRequest getRolRequest(){
        RolRequest rolRequest = RolRequest.builder().nombre("test").build();
        return rolRequest;
    }

    public static RolResponse getRolResponse(){
        RolResponse rol = RolResponse.builder().id(1L).nombre("test").build();
        return rol;
    }

    public static List<RolResponse> getRolResponseList(){
        List<RolResponse> listaRolResponse = new ArrayList<>();
        listaRolResponse.add(getRolResponse());
        return listaRolResponse;
    }


}
