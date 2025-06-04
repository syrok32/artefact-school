package ru.hogwarts.artefact.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.artefact.school.controller.FacultyController;
import ru.hogwarts.artefact.school.controller.StudentController;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.repositories.FacultyRepository;
import ru.hogwarts.artefact.school.services.FacultyService;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void saveFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Gryffindor";
        String color = "Red";

        JSONObject facultyJson = new JSONObject();
        facultyJson.put("name", name);
        facultyJson.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getAllFacultyTest() throws Exception {
        Faculty f1 = new Faculty();
        f1.setId(1L);
        f1.setName("Gryffindor");
        f1.setColor("Red");

        Faculty f2 = new Faculty();
        f2.setId(2L);
        f2.setName("Slytherin");
        f2.setColor("Green");

        when(facultyService.getAllFaculty()).thenReturn(List.of(f1, f2));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[1].name").value("Slytherin"));
    }

    @Test
    void getFacultyInfoTest() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        when(facultyService.findFaculty(id)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }


    @Test
    void editFacultySuccessTest() throws Exception {
        Long id = 1L;
        String newName = "Hufflepuff";
        String newColor = "Yellow";

        JSONObject facultyJson = new JSONObject();
        facultyJson.put("id", id);
        facultyJson.put("name", newName);
        facultyJson.put("color", newColor);

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(id);
        updatedFaculty.setName(newName);
        updatedFaculty.setColor(newColor);

        when(facultyService.editFaculty(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(put("/faculty")
                        .content(facultyJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }


    @Test
    void deleteFacultyTest() throws Exception {
        Long id = 1L;

        doNothing().when(facultyService).deleteFaculty(id);

        mockMvc.perform(delete("/faculty/{id}", id))
                .andExpect(status().isOk());
    }


}
