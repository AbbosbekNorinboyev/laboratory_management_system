package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.WorkshopEntity;

@Repository
public interface WorkshopRepository extends JpaRepository<WorkshopEntity, Long> {
}
