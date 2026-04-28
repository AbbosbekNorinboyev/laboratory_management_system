package uz.brb.laboratorymanagementsystem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.brb.laboratorymanagementsystem.dto.response.AuditLogResponse;
import uz.brb.laboratorymanagementsystem.service.AuditService;
import uz.brb.laboratorymanagementsystem.utils.validator.RequirePermissions;

import java.util.List;

@Tag(name = "Audit", description = "Audit log inspection")
@RestController
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
@Validated
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    @RequirePermissions({"audit.read"})
    public List<AuditLogResponse> listAuditLogs(
            @RequestParam(name = "entityType", required = false) String entityType,
            @RequestParam(name = "entityId", required = false) String entityId,
            @RequestParam(name = "actorUserId", required = false) String actorUserId,
            @RequestParam(name = "requestId", required = false) String requestId,
            @RequestParam(name = "actionCode", required = false) String actionCode,
            @RequestParam(name = "limit", required = false, defaultValue = "50")
            @Min(1) @Max(200) int limit
    ) {
        return auditService.listLogs(entityType, entityId, actorUserId, requestId, actionCode, limit);
    }
}
