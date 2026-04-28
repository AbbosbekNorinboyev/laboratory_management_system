package uz.brb.laboratorymanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.laboratorymanagementsystem.dto.response.AuditLogResponse;
import uz.brb.laboratorymanagementsystem.entity.AuditLogEntity;
import uz.brb.laboratorymanagementsystem.entity.AuthUser;
import uz.brb.laboratorymanagementsystem.repository.AuditLogRepository;
import uz.brb.laboratorymanagementsystem.security.RequestContext;
import uz.brb.laboratorymanagementsystem.security.RequestContextHolder;
import uz.brb.laboratorymanagementsystem.service.AuditService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static uz.brb.laboratorymanagementsystem.utils.Utils.*;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    @Override
    public void saveLog(
            String entityType,
            Long entityId,
            String actionCode,
            Long plantId,
            Map<String, Object> oldValue,
            Map<String, Object> newValue,
            String comment
    ) {
        RequestContext context = RequestContextHolder.get();
        AuthUser actor = RequestContextHolder.getCurrentUser();
        Map<String, Object> diff = buildDiff(oldValue, newValue);

        AuditLogEntity log = AuditLogEntity.builder()
                .occurredAt(Instant.now())
                .actorUserId(actor != null ? actor.getId() : null)
                .actorNameCache(actor != null ? actor.getFullName() : null)
                .plantId(plantId)
                .entityType(entityType)
                .entityId(entityId)
                .actionCode(actionCode)
                .oldValueJson(toJsonNullable(oldValue))
                .newValueJson(toJsonNullable(newValue))
                .diffJson(toJsonNullable(diff))
                .comment(comment)
                .requestId(context != null ? context.requestId() : null)
                .sourceSystem(context != null ? context.sourceSystem() : null)
                .ipAddress(context != null ? context.ipAddress() : null)
                .build();

        auditLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<AuditLogResponse> listLogs(
            String entityType,
            Long entityId,
            Long actorUserId,
            String requestId,
            String actionCode,
            int limit
    ) {
        int safeLimit = Math.clamp(limit, 1, 200);
        String normalizedEntityType = trimToNull(entityType);
        String normalizedRequestId = trimToNull(requestId);
        String normalizedActionCode = trimToNull(actionCode);

        List<AuditLogEntity> logs = auditLogRepository.findByFilters(
                normalizedEntityType,
                entityId,
                actorUserId,
                normalizedRequestId,
                normalizedActionCode,
                PageRequest.of(0, safeLimit)
        );

        return logs.stream().map(this::toResponse).toList();
    }

    private AuditLogResponse toResponse(AuditLogEntity entity) {
        AuditLogResponse.ActorRef actorRef = null;
        if (entity.getActorUser() != null) {
            var user = entity.getActorUser();
            actorRef = new AuditLogResponse.ActorRef(
                    user.getId(), user.getEmail(), user.getFullName());
        }

        AuditLogResponse.PlantRef plantRef = null;
        if (entity.getPlant() != null) {
            var plant = entity.getPlant();
            plantRef = new AuditLogResponse.PlantRef(
                    plant.getId(), plant.getCode(), plant.getName());
        }

        return AuditLogResponse.builder()
                .id(entity.getId())
                .occurredAt(entity.getOccurredAt() != null ? entity.getOccurredAt().toString() : null)
                .actorUserId(entity.getActorUserId())
                .actorNameCache(entity.getActorNameCache())
                .requestId(entity.getRequestId())
                .sourceSystem(entity.getSourceSystem())
                .ipAddress(entity.getIpAddress())
                .entityType(entity.getEntityType())
                .entityId(entity.getEntityId())
                .actionCode(entity.getActionCode())
                .comment(entity.getComment())
                .oldValueJson(parseJsonNullable(entity.getOldValueJson()))
                .newValueJson(parseJsonNullable(entity.getNewValueJson()))
                .diffJson(parseJsonNullable(entity.getDiffJson()))
                .actor(actorRef)
                .plant(plantRef)
                .build();
    }

    private Map<String, Object> buildDiff(
            Map<String, Object> oldValue,
            Map<String, Object> newValue
    ) {
        if (oldValue == null && newValue == null) {
            return null;
        }

        Map<String, Object> diff = new LinkedHashMap<>();
        List<String> keys = new ArrayList<>();

        if (oldValue != null) {
            keys.addAll(oldValue.keySet());
        }
        if (newValue != null) {
            for (String key : newValue.keySet()) {
                if (!keys.contains(key)) {
                    keys.add(key);
                }
            }
        }

        for (String key : keys) {
            Object previous = oldValue != null ? oldValue.get(key) : null;
            Object next = newValue != null ? newValue.get(key) : null;
            if (!objectEquals(previous, next)) {
                diff.put(key, Map.of("oldValue", previous != null ? previous : "null",
                        "newValue", next != null ? next : "null"));
            }
        }

        return diff.isEmpty() ? null : diff;
    }
}
