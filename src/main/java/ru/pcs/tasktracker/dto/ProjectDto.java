package ru.pcs.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import ru.pcs.tasktracker.model.Project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 01.12.2021 in project task-tracker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    private Long id;
    @NotBlank
    private String title;
    @PositiveOrZero
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal rate;
    private Boolean archived;

    public static ProjectDto from(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .rate(project.getRate())
                .archived(project.getArchived())
                .build();
    }

    public static List<ProjectDto> from(List<Project> projects) {
        if (projects == null) {
            return Collections.emptyList();
        } else {
            return projects.stream().map(ProjectDto::from).collect(Collectors.toList());
        }
    }
}
