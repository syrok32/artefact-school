package ru.hogwarts.artefact.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.artefact.school.controller.StudentController;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.services.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void saveStudentTest() throws Exception {
        Long id = 1L;
        String name = "Harry Potter";
        int age = 15;

        JSONObject studentJson = new JSONObject();
        studentJson.put("name", name);
        studentJson.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getAllStudentsTest() throws Exception {
        Student s1 = new Student();
        s1.setId(1L);
        s1.setName("Harry Potter");
        s1.setAge(15);

        Student s2 = new Student();
        s2.setId(2L);
        s2.setName("Hermione Granger");
        s2.setAge(16);

        when(studentService.getAllStudents()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[1].name").value("Hermione Granger"));
    }

    @Test
    void getStudentInfoTest() throws Exception {
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setName("Harry Potter");
        student.setAge(15);

        when(studentService.findStudent(id)).thenReturn(student);

        mockMvc.perform(get("/student/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.age").value(15));
    }

    @Test
    void editStudentSuccessTest() throws Exception {
        Long id = 1L;
        String newName = "Harry P.";
        int newAge = 17;

        JSONObject studentJson = new JSONObject();
        studentJson.put("id", id);
        studentJson.put("name", newName);
        studentJson.put("age", newAge);

        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName(newName);
        updatedStudent.setAge(newAge);

        when(studentService.editStudent(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/student")
                        .content(studentJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.age").value(newAge));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Long id = 1L;

        doNothing().when(studentService).deleteStudent(id);

        mockMvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk());
    }
}
