package Gruppo4BW2BE.BW2.Repositories;

import Gruppo4BW2BE.BW2.Entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {

}