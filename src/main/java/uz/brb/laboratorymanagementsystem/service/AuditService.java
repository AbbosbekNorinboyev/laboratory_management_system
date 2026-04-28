package uz.brb.laboratorymanagementsystem.service;

import uz.brb.laboratorymanagementsystem.dto.response.AuditLogResponse;

import java.util.List;
import java.util.Map;

public interface AuditService {
    void saveLog(String entityType, Long entityId, String actionCode, Long plantId,
                 Map<String, Object> oldValue, Map<String, Object> newValue, String comment);

    List<AuditLogResponse> listLogs(String entityType, Long entityId, Long actorUserId,
                                    String requestId, String actionCode, int limit);
}
