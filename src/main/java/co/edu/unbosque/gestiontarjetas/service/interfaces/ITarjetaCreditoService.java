package co.edu.unbosque.gestiontarjetas.service.interfaces;

import co.edu.unbosque.gestiontarjetas.model.DTO.TarjetaCreditoDTO;
import java.math.BigDecimal;
import java.util.List;

public interface ITarjetaCreditoService {

    TarjetaCreditoDTO crearTarjeta(TarjetaCreditoDTO dto);

    List<TarjetaCreditoDTO> listarTarjetas();

    TarjetaCreditoDTO modificarCupoTotal(Long id, BigDecimal nuevoCupoTotal);

    void eliminarLogicamente(Long id);
}
