package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.TestOrderEntity;

@Repository
public interface TestOrderRepository extends JpaRepository<TestOrderEntity, Long> {
}
