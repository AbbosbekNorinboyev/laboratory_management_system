package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query("select count(au), r.name " +
            "from AuthUser as au " +
            "join au.roles r " +
            "group by r.name")
    List<Object[]> roleStatistics();

    Optional<AuthUser> findByEmail(String email);
}