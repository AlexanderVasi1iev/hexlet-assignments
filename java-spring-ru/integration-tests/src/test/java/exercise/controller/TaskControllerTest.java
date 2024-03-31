package exercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.IntToLongFunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.ResultMatcher;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testPost() throws Exception {
       var data = new HashMap<>();
        data.put("description", "BigTask");
        data.put("title", "VeryBigTask");

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        var result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        Task task = taskRepository.findByTitle("VeryBigTask").get();
        assertThat(task.getTitle()).isEqualTo(("VeryBigTask"));

    }

    @Test
    public void testUpdate() throws Exception {
        var task = new Task();
        task.setId(1);
        task.setTitle("task");
        task.setDescription("description");

        var request = put("/tasks/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(task));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var actualTask = taskRepository.findById(task.getId()).get();

        assertThat(actualTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(actualTask.getDescription()).isEqualTo(task.getDescription());
    }
    @Test
    public void testShow() throws Exception {
        var task = new Task();
        task.setId(1);
        task.setTitle("VeryBigTask");
        task.setDescription("BigTask");
        mockMvc.perform(get("/tasks/{id}", task.getId()))
                .andExpect(status().isOk());
        var actualTask = taskRepository.findById(task.getId()).get();

        assertThat(actualTask.getTitle()).isEqualTo(task.getTitle());
        assertThat(actualTask.getDescription()).isEqualTo(task.getDescription());

    }
    @Test
    public void testDelete() throws Exception {
        var task = new Task();
        task.setTitle("task");
        task.setDescription("description");

        mockMvc.perform(delete("/tasks/{id}", task.getId()))
                .andExpect(status().isOk());

//        assertThat(taskRepository.findAll()).isEmpty();
    }

    // END
}
