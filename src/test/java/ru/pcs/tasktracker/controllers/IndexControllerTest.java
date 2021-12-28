package ru.pcs.tasktracker.controllers;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.pcs.tasktracker.SpringSecurityWebAuxTestConfig;
import ru.pcs.tasktracker.TestUsersUtils;
import ru.pcs.tasktracker.tasks.TaskDto;
import ru.pcs.tasktracker.projects.Project;
import ru.pcs.tasktracker.users.User;
import ru.pcs.tasktracker.tasks.TasksService;
import ru.pcs.tasktracker.users.UsersService;

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
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;
    @MockBean
    private TasksService tasksService;

    @BeforeEach
    void setUp() {

        User user = TestUsersUtils.getBasicUser();

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

            User user = TestUsersUtils.getBasicUser();

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

        @Test
        @WithUserDetails("user@company.com")
        public void not_show_restricted_urls_to_users() throws Exception {

            User user = TestUsersUtils.getBasicUser();

            List<TaskDto> taskDtoList = Collections.singletonList(TaskDto.builder()
                    .project(Project.builder().title("Project").build())
                    .hours(BigDecimal.valueOf(10))
                    .author(user)
                    .assignee(user)
                    .build());

            mockMvc.perform(get("/")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(content().string(notContains("/users")))
                    .andExpect(content().string(notContains("/projects")));
        }

        @Test
        @WithUserDetails("admin@company.com")
        public void show_restricted_urls_to_admin() throws Exception {

            User user = TestUsersUtils.getBasicUser();

            List<TaskDto> taskDtoList = Collections.singletonList(TaskDto.builder()
                    .project(Project.builder().title("Project").build())
                    .hours(BigDecimal.valueOf(10))
                    .author(user)
                    .assignee(user)
                    .build());

            mockMvc.perform(get("/")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(content().string(contains("/users")))
                    .andExpect(content().string(contains("/projects")));
        }

    }

    private AssertionMatcher<String> contains(String searchString) {
        return new AssertionMatcher<String>() {
            @Override
            public void assertion(String actual) throws AssertionError {
                if (!actual.contains(searchString)) {
                    throw new AssertionError();
                }
            }
        };
    }

    private AssertionMatcher<String> notContains(String searchString) {
        return new AssertionMatcher<String>() {
            @Override
            public void assertion(String actual) throws AssertionError {
                if (actual.contains(searchString)) {
                    throw new AssertionError();
                }
            }
        };
    }
}