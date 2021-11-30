package ru.pcs.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pcs.tasktracker.model.Task;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
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

    @NotBlank
    private String shortDescription;
    private String fullDescription;

//    private User author;
//    private User assignee;
//
    // TODO ? Правильно ли я здесь сделал, что в DTO использовал строковые ID, а в service привел к ссылкам?
    // так сделал для того, чтобы в контроллере не получать из базы пользователей, а сделать это в сервисе
    private String assigneeEmail;
    private String authorEmail;

    private Timestamp created;
    private Timestamp finished;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .shortDescription(task.getShortDescription())
                .fullDescription(task.getFullDescription())
                .authorEmail(task.getAuthor().getEmail())
                .assigneeEmail(task.getAssignee().getEmail())
                .created(task.getCreated())
                .finished(task.getFinished())
                .build();
    }

    public static List<TaskDto> from(List<Task> tasks) {
        return tasks.stream().map(TaskDto::from).collect(Collectors.toList());
    }
}
