package ru.pcs.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 24.11.2021 in project task-tracker
 */
@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    static final Long serialVersionUID = 1L;

    public enum Role {
        USER, ADMIN
    }

    public enum State {
        INVITED, ACTIVE, ARCHIVED
    }

    @Id
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private State state;
    private String name;
    private String password;
    private String inviteToken;
}
