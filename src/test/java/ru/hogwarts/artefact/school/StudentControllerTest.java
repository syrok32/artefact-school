package ru.hogwarts.artefact.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.repositories.AvatarRepository;
import ru.hogwarts.artefact.school.repositories.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    private String getUrl(String path) {
        return "http://localhost:" + port + "/student" + path;
    }

    @BeforeEach
    void setUp() {
        // Очищаем таблицы в правильном порядке
        avatarRepository.deleteAll();
        studentRepository.deleteAll();

        // Наполняем тестовыми данными
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(15);
        studentRepository.save(student);
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(getUrl(""), Student[].class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(1);
        Assertions.assertThat(response.getBody()[0].getName()).isEqualTo("Harry Potter");
        Assertions.assertThat(response.getBody()[0].getAge()).isEqualTo(15);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Hermione Granger");
        student.setAge(16);

        ResponseEntity<Student> response = restTemplate.postForEntity(getUrl(""), student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Hermione Granger");
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(16);
    }

    @Test
    public void testEditStudent() {
        Student student = studentRepository.findAll().get(0);
        student.setName("Harry Updated");
        student.setAge(16);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> entity = new HttpEntity<>(student, headers);

        ResponseEntity<Student> response = restTemplate.exchange(getUrl(""), HttpMethod.PUT, entity, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Harry Updated");
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(16);
    }

    @Test
    public void testDeleteStudent() {
        Student student = studentRepository.findAll().get(0);
        Long id = student.getId();

        restTemplate.delete(getUrl("/" + id));

        ResponseEntity<Student> response = restTemplate.getForEntity(getUrl("/" + id), Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStudentFaculty() {
        Student student = studentRepository.findAll().get(0);
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getUrl("/" + student.getId() + "/faculty"), Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // Нет факультета
    }
}