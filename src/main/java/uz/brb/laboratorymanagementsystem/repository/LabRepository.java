package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.LabEntity;

@Repository
public interface LabRepository extends JpaRepository<LabEntity, Long> {
}
