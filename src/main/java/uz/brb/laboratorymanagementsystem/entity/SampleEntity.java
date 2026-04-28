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
@Table(name = "sample")
public class SampleEntity extends BaseEntity {

    @Column(name = "sample_no", nullable = false)
    private String sampleNo;

    @Column(name = "lab_id", nullable = false)
    private String labId;

    @Column(name = "workshop_id")
    private String workshopId;

    @Column(name = "section_id")
    private String sectionId;

    @Column(name = "sampling_point_id")
    private String samplingPointId;

    @Column(name = "shift_id")
    private String shiftId;

    @Column(name = "batch_id")
    private String batchId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "sample_type_id", nullable = false)
    private String sampleTypeId;

    @Column(name = "sample_source_id", nullable = false)
    private String sampleSourceId;

    @Column(name = "storage_location_id")
    private String storageLocationId;

    @Column(name = "priority_id", nullable = false)
    private String priorityId;

    @Column(name = "status_id", nullable = false)
    private String statusId;

    @Column(name = "taken_at", nullable = false)
    private Instant takenAt;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "due_at")
    private Instant dueAt;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "storage_condition")
    private String storageCondition;

    @Column(name = "retention_until")
    private Instant retentionUntil;

    @Column(name = "retention_note")
    private String retentionNote;

    @Column(name = "disposed_at")
    private Instant disposedAt;

    @Column(name = "disposed_by_user_id")
    private String disposedByUserId;

    @Column(name = "disposal_note")
    private String disposalNote;

    @Column(name = "label_code")
    private String labelCode;

    @Column(name = "label_printed_at")
    private Instant labelPrintedAt;

    @Column(name = "collected_by_user_id")
    private String collectedByUserId;

    @Column(name = "registered_by_user_id")
    private String registeredByUserId;

    @Column(name = "description")
    private String description;

    @Column(name = "external_ref")
    private String externalRef;

    @Column(name = "archived_at")
    private Instant archivedAt;

    @Column(name = "control_type", nullable = false)
    private String controlType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", insertable = false, updatable = false)
    private PlantEntity plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id", insertable = false, updatable = false)
    private LabEntity lab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private StatusDictionaryEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", insertable = false, updatable = false)
    private PriorityEntity priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id", insertable = false, updatable = false)
    private WorkshopEntity workshop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_type_id", insertable = false, updatable = false)
    private SampleTypeEntity sampleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", insertable = false, updatable = false)
    private BatchEntity batch;
}

