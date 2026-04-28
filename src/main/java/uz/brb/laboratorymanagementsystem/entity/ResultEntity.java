package uz.brb.laboratorymanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

import lombok.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "result")
public class ResultEntity extends BaseEntity {

  @Column(name = "test_order_id", nullable = false)
  private String testOrderId;

  @Column(name = "status_id")
  private String statusId;

  @Column(name = "method_version_id")
  private String methodVersionId;

  @Column(name = "specification_item_id")
  private String specificationItemId;

  @Column(name = "operator_user_id")
  private String operatorUserId;

  @Column(name = "instrument_code")
  private String instrumentCode;

  @Column(name = "instrument_name")
  private String instrumentName;

  @Column(name = "value_type")
  private String valueType;

  @Column(name = "numeric_value")
  private BigDecimal numericValue;

  @Column(name = "text_value")
  private String textValue;

  @Column(name = "evaluation_code")
  private String evaluationCode;

  @Column(name = "is_out_of_spec")
  private Boolean isOutOfSpec;

  @Column(name = "comment")
  private String comment;

  @Column(name = "submitted_by_user_id")
  private String submittedByUserId;

  @Column(name = "submitted_at")
  private Instant submittedAt;

  @Column(name = "approved_by_user_id")
  private String approvedByUserId;

  @Column(name = "approved_at")
  private Instant approvedAt;

  @Column(name = "archived_at")
  private Instant archivedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "test_order_id", insertable = false, updatable = false)
  private TestOrderEntity testOrder;
}

