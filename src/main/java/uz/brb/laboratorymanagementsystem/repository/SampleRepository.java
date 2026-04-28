package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.SampleEntity;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
