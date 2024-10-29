package kg.core.mnr.repository;

import kg.core.mnr.models.entity.dict.TradeDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDirectionRepository extends JpaRepository<TradeDirection, Long> {
}
