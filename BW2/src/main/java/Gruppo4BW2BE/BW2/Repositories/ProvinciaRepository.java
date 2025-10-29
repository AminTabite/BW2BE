package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, String> {
    Optional<Provincia> findByNomeIgnoreCase(String nome);
}