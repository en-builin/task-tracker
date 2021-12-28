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
import ru.pcs.tasktracker.projects.ProjectDto;
import ru.pcs.tasktracker.projects.ProjectsService;
import ru.pcs.tasktracker.users.UsersService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 09.12.2021 in project task-tracker
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
@DisplayName("ProjectsController is working when")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;
    @MockBean
    private ProjectsService projectsService;

    @BeforeEach
    void setUp() {

        when(usersService.getUserByEmail("admin@company.com")).thenReturn(TestUsersUtils.getAdminUser());
        when(projectsService.getAllProjects()).thenReturn(getTestProjects());
    }

    private List<ProjectDto> getTestProjects() {

        return List.of(ProjectDto.builder().id(1L).title("Project").build());
    }

    @Nested
    @DisplayName("getProjectsPage()")
    class GetProjectsPage {

        @Test
        void redirect_to_sign_in_if_not_authenticated() throws Exception {

            mockMvc.perform(get("/projects")).andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrlPattern("**/sign-in"));
        }

        @Test
        @WithUserDetails("user@company.com")
        void return_forbidden_for_user() throws Exception {

            mockMvc.perform(get("/projects")).andDo(print())
                    .andExpect(status().is(403));
        }

        @Test
        @WithUserDetails("admin@company.com")
        void return_projects_view_for_admin() throws Exception {

            mockMvc.perform(get("/projects")).andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(view().name("projects"))
                    .andExpect(model().attribute("currentUser", TestUsersUtils.getAdminUser()))
                    .andExpect(model().attribute("projects", getTestProjects()));
        }

    }

    @Nested
    @DisplayName("addProject()")
    class AddProject {

        @Test
        void redirect_to_sign_in_if_not_authenticated() throws Exception {

            mockMvc.perform(post("/projects")
                            .param("title", "New project")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrlPattern("**/sign-in"));
        }

        @Test
        @WithUserDetails("user@company.com")
        void forbidden_for_user() throws Exception {

            mockMvc.perform(post("/projects")
                            .param("title", "New project")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(403));
        }

        @Test
        @WithUserDetails("admin@company.com")
        void accepted_for_admin() throws Exception {

            mockMvc.perform(post("/projects")
                            .param("title", "New project")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(302))
                    .andExpect(redirectedUrl("/projects"));
        }

        @Test
        @WithUserDetails("admin@company.com")
        void rejected_on_blank_title() throws Exception {

            mockMvc.perform(post("/projects")
                            .param("title", "")
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(view().name("projects"))
                    .andExpect(model().errorCount(1))
                    .andExpect(model().attributeHasFieldErrors("projectDto", "title"));
        }
    }
}