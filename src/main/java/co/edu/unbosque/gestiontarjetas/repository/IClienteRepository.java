package co.edu.unbosque.gestiontarjetas.repository;

import co.edu.unbosque.gestiontarjetas.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdentificacion(String identificacion);
}
