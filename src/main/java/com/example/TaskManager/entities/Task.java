package com.example.TaskManager.entities;

import com.example.TaskManager.enums.Status;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name="tasks", uniqueConstraints = @UniqueConstraint(columnNames = {"task_id"}, name = "task_id_unique"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @JdbcTypeCode(value = Types.VARCHAR)
    @Column(name="task_id", updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)", length = 36)
    private UUID id;

    @Column(name = "task_title", columnDefinition = "VARCHAR(60)", nullable = false)
    private String title;

    @Column(name = "task_desc", columnDefinition = "VARCHAR(255)", nullable = false)
    private String description;

    @Column(name = "task_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="task_due_date", nullable = false, columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column(name="task_created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp(source = SourceType.VM)
    private LocalDateTime created_at;

    @Column(name="task_updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime updated_at;


    @PrePersist
    private void onPrePersist(){
        this.created_at = localDateTime();
        this.updated_at = localDateTime();
        this.status = Status.PENDING;
    }

    @PostUpdate
    private void onPostUpdate(){
        this.updated_at = localDateTime();
    }

    private LocalDateTime localDateTime(){
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        return now;
    }
}
