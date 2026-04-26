package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.brb.laboratorymanagementsystem.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
