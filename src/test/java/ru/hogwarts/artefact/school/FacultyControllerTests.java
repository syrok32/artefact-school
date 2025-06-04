package ru.hogwarts.artefact.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.repositories.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    private String getUrl(String path) {
        return "http://localhost:" + port + "/faculty" + path;
    }

    @BeforeEach
    void setUp() {
        // Очищаем базу
        facultyRepository.deleteAll();

        // Наполняем тестовыми данными
        Faculty faculty = new Faculty();
        faculty.setName("Hogwarts");
        faculty.setColor("Green");
        facultyRepository.save(faculty);
    }

    @Test
    void testGetAllFaculty() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(getUrl(""), Faculty[].class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(1);
        Assertions.assertThat(response.getBody()[0].getName()).isEqualTo("Hogwarts");
        Assertions.assertThat(response.getBody()[0].getColor()).isEqualTo("Green");
    }

    @Test
    void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Slytherin");
        faculty.setColor("Silver");

        ResponseEntity<Faculty> response = restTemplate.postForEntity(getUrl(""), faculty, Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Slytherin");
        Assertions.assertThat(response.getBody().getColor()).isEqualTo("Silver");
    }

    @Test
    void testEditFaculty() {
        Faculty faculty = facultyRepository.findAll().get(0); // Получаем существующий факультет
        faculty.setName("UpdatedHogwarts");
        faculty.setColor("Blue");

        ResponseEntity<Faculty> response = restTemplate.exchange(
                getUrl(""), HttpMethod.PUT, new HttpEntity<>(faculty), Faculty.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo("UpdatedHogwarts");
        Assertions.assertThat(response.getBody().getColor()).isEqualTo("Blue");
    }

    @Test
    void testDeleteFaculty() {
        Faculty faculty = facultyRepository.findAll().get(0); // Получаем существующий факультет
        Long id = faculty.getId();

        restTemplate.delete(getUrl("/" + id));

        ResponseEntity<Faculty> response = restTemplate.getForEntity(getUrl("/" + id), Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}