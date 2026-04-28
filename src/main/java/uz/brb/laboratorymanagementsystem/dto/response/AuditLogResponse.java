package uz.brb.laboratorymanagementsystem.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private String occurredAt;
    private Long actorUserId;
    private String actorNameCache;
    private String requestId;
    private String sourceSystem;
    private String ipAddress;
    private String entityType;
    private Long entityId;
    private String actionCode;
    private String comment;
    private Object oldValueJson;
    private Object newValueJson;
    private Object diffJson;
    private ActorRef actor;
    private PlantRef plant;

    public record ActorRef(Long id, String email, String fullName) {
    }

    public record PlantRef(Long id, String code, String name) {
    }
}
