package uz.brb.laboratorymanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "approval_step")
public class ApprovalStepEntity extends BaseEntity {

    @Column(name = "workflow_id")
    private String workflowId;

    @Column(name = "step_no")
    private Integer stepNo;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "approver_user_id")
    private String approverUserId;

    @Column(name = "status_id")
    private String statusId;

    @Column(name = "is_current")
    private Boolean isCurrent;

    @Column(name = "acted_by_user_id")
    private String actedByUserId;

    @Column(name = "acted_at")
    private Instant actedAt;

    @Column(name = "comment")
    private String comment;

    @Column(name = "template_step_id")
    private String templateStepId;
}

