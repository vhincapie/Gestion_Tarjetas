package co.edu.unbosque.gestiontarjetas.repository;

import co.edu.unbosque.gestiontarjetas.model.entity.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {

    boolean existsByNumeroTarjeta(String numeroTarjeta);
}