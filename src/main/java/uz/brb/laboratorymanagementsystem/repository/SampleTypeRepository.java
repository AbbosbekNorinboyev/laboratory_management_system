package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.SampleTypeEntity;

@Repository
public interface SampleTypeRepository extends JpaRepository<SampleTypeEntity, Long> {
}
