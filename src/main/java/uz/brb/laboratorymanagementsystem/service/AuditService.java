package uz.brb.laboratorymanagementsystem.service;

import uz.brb.laboratorymanagementsystem.dto.response.AuditLogResponse;

import java.util.List;

public interface AuditService {
    List<AuditLogResponse> listLogs(String entityType, String entityId, String actorUserId,
                                    String requestId, String actionCode, int limit);
}
