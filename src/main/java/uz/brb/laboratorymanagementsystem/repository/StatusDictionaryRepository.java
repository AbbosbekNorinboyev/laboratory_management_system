package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.StatusDictionaryEntity;

@Repository
public interface StatusDictionaryRepository extends JpaRepository<StatusDictionaryEntity, Long> {
}
