package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Fattura;
import Gruppo4BW2BE.BW2.Entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface FatturaRepository extends JpaRepository<Fattura, UUID> {
    @Query(
            //value query principale per recuperare i dati
            //countQuery query per calcolare il numero totale di risultati
            //nativeQuery = true, indica che la query è SQL nativo, non JPQL
            value = """
            SELECT * FROM invoices f
            WHERE (:clienteId IS NULL OR f.client_id = :clienteId)
              AND (:stato IS NULL OR :stato = '' OR f.stato = :stato)
              AND (:data IS NULL OR f.invoice_date = :data)
              AND (:anno IS NULL OR EXTRACT(YEAR FROM f.invoice_date) = :anno)
              AND (:minImporto IS NULL OR f.amount >= :minImporto)
              AND (:maxImporto IS NULL OR f.amount <= :maxImporto)
            """,
            countQuery = """
            SELECT count(*) FROM invoices f
            WHERE (:clienteId IS NULL OR f.client_id = :clienteId)
              AND (:stato IS NULL OR :stato = '' OR f.stato = :stato)
              AND (:data IS NULL OR f.invoice_date = :data)
              AND (:anno IS NULL OR EXTRACT(YEAR FROM f.invoice_date) = :anno)
              AND (:minImporto IS NULL OR f.amount >= :minImporto)
              AND (:maxImporto IS NULL OR f.amount <= :maxImporto)
            """,
            nativeQuery = true
    )
    //Metodo che restituisce una pagina di fatture filtrate secondo i parametri passati
    Page<Fattura> filtraFatture(
            @Param("clienteId") Long clienteId,
            @Param("stato") String stato,
            @Param("data") LocalDate data,
            @Param("anno") Integer anno,
            @Param("minImporto") BigDecimal minImporto,
            @Param("maxImporto") BigDecimal maxImporto,
            Pageable pageable
    );

    Page<Fattura> findByStato(StatoFattura stato, Pageable pageable);


}
