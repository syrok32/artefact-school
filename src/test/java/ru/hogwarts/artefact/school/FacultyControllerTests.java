package ru.hogwarts.artefact.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.artefact.school.controller.FacultyController;
import ru.hogwarts.artefact.school.controller.StudentController;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.repositories.FacultyRepository;
import ru.hogwarts.artefact.school.services.FacultyService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testGetAllFaculty() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class)).isNotNull();
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("BIBALearning");
        faculty.setColor("blue");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class)).isNotNull();
    }

    @Test
    public void testEditFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Bobainfo");
        faculty.setColor("yellow");

        Faculty createdFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        createdFaculty.setName("UpdatedFaculty");
        createdFaculty.setColor("Blue");

        ResponseEntity<Faculty> response = this.restTemplate.exchange("http://localhost:" + port + "/faculty", org.springframework.http.HttpMethod.PUT, new org.springframework.http.HttpEntity<>(createdFaculty), Faculty.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("BibaBoba");
        faculty.setColor("purple");

        Faculty createdFaculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + createdFaculty.getId());

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + createdFaculty.getId(), Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}





