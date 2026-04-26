package uz.brb.laboratorymanagementsystem.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query("select count(*), au.userRole from AuthUser as au group by au.userRole")
    List<Tuple> roleStatistics();

    Optional<AuthUser> findByEmail(String email);
}