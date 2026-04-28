package uz.brb.laboratorymanagementsystem.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.brb.laboratorymanagementsystem.entity.AuditLogEntity;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
    @Query(value = """
            SELECT al
            FROM AuditLogEntity as al
            WHERE (:entityType IS NULL OR al.entityType = :entityType)
              AND (:entityId IS NULL OR al.entityId = :entityId)
              AND (:actorUserId IS NULL OR al.actorUserId = :actorUserId)
              AND (:requestId IS NULL OR al.requestId = :requestId)
              AND (:actionCode IS NULL OR al.actionCode = :actionCode)
            ORDER BY al.occurredAt DESC
            """)
    List<AuditLogEntity> findByFilters(
            @Param("entityType") String entityType,
            @Param("entityId") Long entityId,
            @Param("actorUserId") Long actorUserId,
            @Param("requestId") String requestId,
            @Param("actionCode") String actionCode,
            Pageable pageable
    );
}
