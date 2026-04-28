package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.ApprovalStepEntity;

@Repository
public interface ApprovalStepRepository extends JpaRepository<ApprovalStepEntity, Long> {
}
