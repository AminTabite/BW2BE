package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
