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
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @Column(name = "archived_at")
    private Instant archivedAt;
}