
package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
    Optional<StatoFattura> findByStatoFattura(String statoFattura);
    boolean existsByStatoFattura(String statoFattura);
}
