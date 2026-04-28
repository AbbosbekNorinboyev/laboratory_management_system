package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.PriorityEntity;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Long> {
}
