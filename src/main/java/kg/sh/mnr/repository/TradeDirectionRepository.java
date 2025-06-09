package kg.sh.mnr.repository;

import kg.sh.mnr.entity.dict.TradeDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDirectionRepository extends JpaRepository<TradeDirection, Long> {
}
