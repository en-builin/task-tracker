package ru.pcs.tasktracker.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.pcs.tasktracker.SpringSecurityWebAuxTestConfig;
import ru.pcs.tasktracker.dto.TaskDto;
import ru.pcs.tasktracker.model.Project;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.services.TasksService;
import ru.pcs.tasktracker.services.UsersService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 07.12.2021 in project task-tracker
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
@DisplayName("IndexController is working when")
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;
    @MockBean
    private TasksService tasksService;

    @BeforeEach
    void setUp() {

        User user = User.builder()
                .email("user@company.com")
                .state(User.State.ACTIVE)
                .role(User.Role.USER)
                .name("User")
                .build();

        List<TaskDto> taskDtoList = Collections.singletonList(TaskDto.builder()
                .project(Project.builder().title("Project").build())
                .hours(BigDecimal.valueOf(10))
                .author(user)
                .assignee(user)
                .build());

        when(usersService.getUserByEmail("user@company.com")).thenReturn(user);
        when(tasksService.getCurrentTasksByAssignee(user)).thenReturn(taskDtoList);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("getIndexPage()")
    class GetIndexPage {
        @Test
        public void redirect_to_sign_in_if_not_authenticated() throws Exception {
            mockMvc.perform(get("/")).andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrlPattern("**/sign-in"));
        }

        @Test
        @WithUserDetails("user@company.com")
        public void return_index_view_if_authenticated() throws Exception {

            User user = User.builder()
                    .email("user@company.com")
                    .state(User.State.ACTIVE)
                    .role(User.Role.USER)
                    .name("User")
                    .build();

            List<TaskDto> taskDtoList = Collections.singletonList(TaskDto.builder()
                    .project(Project.builder().title("Project").build())
                    .hours(BigDecimal.valueOf(10))
                    .author(user)
                    .assignee(user)
                    .build());

            mockMvc.perform(get("/")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(view().name("index"))
                    .andExpect(model().attribute("currentUser", user))
                    .andExpect(model().attribute("tasks", taskDtoList));
        }

    }
}