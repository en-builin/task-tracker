package ru.pcs.tasktracker.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 25.11.2021 in project task-tracker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteForm {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}
