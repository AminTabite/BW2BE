package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {

    Optional<Ruolo> findByNome(String nome);
}
