package ru.pcs.tasktracker.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import ru.pcs.tasktracker.projects.Project;
import ru.pcs.tasktracker.users.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 29.11.2021 in project task-tracker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    private Long id;
    @NotNull
    private Project project;
    @NotBlank
    private String shortDescription;
    private String fullDescription;
    @NotNull
    private User assignee;
    private User author;
    @DateTimeFormat(pattern="dd.MM.YYYY HH:mm")
    private Timestamp createdAt;
    @DateTimeFormat(pattern="dd.MM.YYYY HH:mm")
    private Timestamp finishedAt;
    private Boolean finished;
    @PositiveOrZero
    @NumberFormat(pattern = "#.##")
    private BigDecimal hours;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .project(task.getProject())
                .shortDescription(task.getShortDescription())
                .fullDescription(task.getFullDescription())
                .author(task.getAuthor())
                .assignee(task.getAssignee())
                .createdAt(getTimestampFromInstant(task.getCreatedAt()))
                .finishedAt(getTimestampFromInstant(task.getFinishedAt()))
                .finished(task.getFinishedAt() != null)
                .hours(task.getHours())
                .build();
    }

    public static List<TaskDto> from(List<Task> tasks) {
        return tasks.stream().map(TaskDto::from).collect(Collectors.toList());
    }

    private static Timestamp getTimestampFromInstant(Instant instant) {
        return (instant == null) ? null : Timestamp.from(instant);
    }
}
