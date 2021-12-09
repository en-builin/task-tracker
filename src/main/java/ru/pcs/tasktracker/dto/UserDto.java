package ru.pcs.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pcs.tasktracker.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 25.11.2021 in project task-tracker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String name;
    private String inviteToken;
    private User.Role role;
    private User.State state;

    public static UserDto from(User user) {

        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .inviteToken(user.getInviteToken())
                .role(user.getRole())
                .state(user.getState())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
