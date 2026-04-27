package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.PlantEntity;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {
}
