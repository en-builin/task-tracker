package ru.pcs.tasktracker.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.pcs.tasktracker.SpringSecurityWebAuxTestConfig;
import ru.pcs.tasktracker.TestUsersUtils;
import ru.pcs.tasktracker.dto.ProjectDto;
import ru.pcs.tasktracker.dto.TaskDto;
import ru.pcs.tasktracker.dto.UserDto;
import ru.pcs.tasktracker.model.Project;
import ru.pcs.tasktracker.model.User;
import ru.pcs.tasktracker.services.ProjectsService;
import ru.pcs.tasktracker.services.TasksService;
import ru.pcs.tasktracker.services.UsersService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 08.12.2021 in project task-tracker
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
@DisplayName("TasksController is working when")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;
    @MockBean
    private TasksService tasksService;
    @MockBean
    private ProjectsService projectsService;

    @BeforeEach
    void setUp() {

        User user = TestUsersUtils.getBasicUser();
        User admin = TestUsersUtils.getAdminUser();
        TaskDto userTask = getTaskDto(user, 1L);
        TaskDto adminTask = getTaskDto(admin, 2L);

        when(usersService.getUserByEmail("user@company.com")).thenReturn(user);
        when(usersService.getActiveUsers()).thenReturn(List.of(UserDto.from(user), UserDto.from(admin)));
        when(tasksService.getAllTasks()).thenReturn(List.of(userTask, adminTask));
        when(tasksService.getTaskById(1L)).thenReturn(userTask);
        when(tasksService.getTaskById(2L)).thenReturn(adminTask);
        when(projectsService.getAllProjects()).thenReturn(getTestProjects());

        // TODO
        when(tasksService.isModifyAllowed(userTask, "user@company.com")).thenReturn(true);
    }

    private List<ProjectDto> getTestProjects() {

        return List.of(ProjectDto.builder().id(1L).title("Project").build());
    }

    private TaskDto getTaskDto(User user, long id) {

        return TaskDto.builder()
                .assignee(user)
                .author(user)
                .id(id)
                .project(Project.builder().title("Project").id(1L).build())
                .shortDescription("Short description")
                .fullDescription("Full description")
                .hours(BigDecimal.TEN)
                .build();
    }

    @Nested
    @DisplayName("getTasksPage()")
    class GetTasksPage {
        @Test
        void redirect_to_sign_in_if_not_authenticated() throws Exception {

            mockMvc.perform(get("/tasks")).andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrlPattern("**/sign-in"));
        }

        @Test
        @WithUserDetails("user@company.com")
        void return_tasks_view_if_authenticated() throws Exception {

            User user = TestUsersUtils.getBasicUser();
            User admin = TestUsersUtils.getAdminUser();

            TaskDto userTask = getTaskDto(user, 1L);
            TaskDto adminTask = getTaskDto(admin, 2L);

            mockMvc.perform(get("/tasks")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(view().name("tasks"))
                    .andExpect(model().attribute("currentUser", user))
                    .andExpect(model().attribute("tasks", List.of(userTask, adminTask)));
        }

    }

    @Nested
    @DisplayName("getTaskEditPage()")
    class GetTaskEditPage {
        @Test
        void redirect_to_sign_in_if_not_authenticated() throws Exception {

            mockMvc.perform(get("/tasks/1")).andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrlPattern("**/sign-in"));
        }

        @Test
        @WithUserDetails("user@company.com")
        void return_task_view_and_model_if_authenticated() throws Exception {

            mockMvc.perform(get("/tasks/1")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(view().name("task"))
                    .andExpect(model().attributeExists("taskDto"))
                    .andExpect(model().attributeExists("users"))
                    .andExpect(model().attributeExists("projects"))
                    .andExpect(model().attributeExists("returnUrl"));
        }

        @Test
        @WithUserDetails("user@company.com")
        void dispatches_return_url() throws Exception {

            mockMvc.perform(get("/tasks/1?from=tasks"))
                    .andExpect(status().is(200))
                    .andExpect(model().attribute("returnUrl", "/tasks"));
            mockMvc.perform(get("/tasks/1?from=index"))
                    .andExpect(status().is(200))
                    .andExpect(model().attribute("returnUrl", "/"));
            mockMvc.perform(get("/tasks/1"))
                    .andExpect(status().is(200))
                    .andExpect(model().attribute("returnUrl", "/"));
        }

    }

    @Nested
    @DisplayName("saveTask()")
    class saveTask {

        @Test
        @WithUserDetails("user@company.com")
        void return_bad_request_on_wrong_id() throws Exception {

            mockMvc.perform(post("/tasks/1")
                            .param("id", "2")
                            .param("shortDescription", "new short description")
                            .param("finished", "true")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(400));
        }

        // TODO - не передает пользователей в DTO
        @Test
        @WithUserDetails("user@company.com")
        void saves_task_from_assigned_user() throws Exception {

            mockMvc.perform(post("/tasks/1")
                            .param("id", "1")
                            .param("shortDescription", "Short description")
                            .param("fullDescription", "Full description")
                            .param("project", "1")
                            .param("author", "user@company.com")
                            .param("assignee", "user@company.com")
                            .param("hours", "10")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(400));
        }
    }
}