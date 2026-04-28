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
@Table(name = "test_order")
public class TestOrderEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", insertable = false, updatable = false)
    private PlantEntity plant;

    @Column(name = "test_definition_id")
    private String testDefinitionId;

    @Column(name = "method_version_id")
    private String methodVersionId;

    @Column(name = "specification_item_id")
    private String specificationItemId;

    @Column(name = "priority_id")
    private String priorityId;

    @Column(name = "assigned_lab_id")
    private String assignedLabId;

    @Column(name = "assigned_user_id")
    private String assignedUserId;

    @Column(name = "created_by_user_id")
    private String createdByUserId;

    @Column(name = "due_at")
    private Instant dueAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "archived_at")
    private Instant archivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    private StatusDictionaryEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id", insertable = false, updatable = false)
    private SampleEntity sample;
}

