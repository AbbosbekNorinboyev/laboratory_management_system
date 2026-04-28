package uz.brb.laboratorymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "batch")
public class BatchEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", insertable = false, updatable = false)
    private PlantEntity plant;

    @Column(name = "batch_no", nullable = false)
    private String batchNo;

    @Column(name = "status_id", nullable = false)
    private String statusId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "status_changed_at")
    private Instant statusChangedAt;

    @Column(name = "hold_until")
    private Instant holdUntil;

    @Column(name = "control_note")
    private String controlNote;

    @Column(name = "reject_reason_id")
    private String rejectReasonId;

    @Column(name = "rejected_at")
    private Instant rejectedAt;

    @Column(name = "rejected_by_user_id")
    private String rejectedByUserId;

    @Column(name = "reject_comment")
    private String rejectComment;

    @Column(name = "archived_at")
    private Instant archivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private StatusDictionaryEntity status;

    @Column(name = "source_document_no")
    private String sourceDocumentNo;

    @Column(name = "supplier_id")
    private String supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id", insertable = false, updatable = false)
    private WorkshopEntity workshopId;
}

