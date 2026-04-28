package uz.brb.laboratorymanagementsystem.mapper;

import org.springframework.stereotype.Component;
import uz.brb.laboratorymanagementsystem.dto.response.AuditLogResponse;
import uz.brb.laboratorymanagementsystem.entity.AuditLogEntity;

import static uz.brb.laboratorymanagementsystem.utils.Utils.parseJsonNullable;

@Component
public class AuditLogMapper {
    public AuditLogResponse toResponse(AuditLogEntity entity) {
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
}
