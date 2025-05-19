package co.edu.unbosque.gestiontarjetas.service.interfaces;

import co.edu.unbosque.gestiontarjetas.model.DTO.ClienteDTO;
import java.util.List;

public interface IClienteService {

    List<ClienteDTO> listarClientes();
}
