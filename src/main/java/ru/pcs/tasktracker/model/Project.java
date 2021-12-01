package ru.pcs.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 01.12.2021 in project task-tracker
 */
@Table(name = "projects")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Id
    private Long id;

    private String title;

    private BigDecimal rate;

    private Boolean archived;
}
