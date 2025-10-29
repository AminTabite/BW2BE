package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {


    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByEmail(String email);

    @Query(
            """
            SELECT c FROM Cliente c
            WHERE (:nomeContatto IS NULL OR LOWER(c.nomeContatto) LIKE LOWER(CONCAT('%', :nomeContatto, '%')))
              AND (:minFatturato IS NULL OR c.fatturatoAnnuale >= :minFatturato)
              AND (:maxFatturato IS NULL OR c.fatturatoAnnuale <= :maxFatturato)
              AND (:dataInserimentoFrom IS NULL OR c.dataInserimento >= :dataInserimentoFrom)
              AND (:dataInserimentoTo IS NULL OR c.dataInserimento <= :dataInserimentoTo)
              AND (:dataUltimoContattoFrom IS NULL OR c.dataUltimoContatto >= :dataUltimoContattoFrom)
              AND (:dataUltimoContattoTo IS NULL OR c.dataUltimoContatto <= :dataUltimoContattoTo)
            """
    )

    Page<Cliente> findByFilters(
            @Param("nomeContatto") String name,
            @Param("minFatturato") BigDecimal minFatturato,
            @Param("maxFatturato") BigDecimal maxFatturato,
            @Param("dataInserimentoFrom") LocalDate dataInserimentoFrom,
            @Param("dataInserimentoTo") LocalDate dataInserimentoTo,
            @Param("dataUltimoContattoFrom") LocalDate dataUltimoContattoFrom,
            @Param("dataUltimoContattoTo") LocalDate dataUltimoContattoTo,
            Pageable pageable
    );
}
