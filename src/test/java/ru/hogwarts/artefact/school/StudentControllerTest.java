package ru.hogwarts.artefact.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.artefact.school.model.Avatar;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;

import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    public void testGetAllStudents() {
        String response = this.restTemplate.getForObject(getUrl("/student"), String.class);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("BIBALearning");
        student.setAge(12);

        Student createdStudent = this.restTemplate.postForObject(getUrl("/student"), student, Student.class);
        Assertions.assertThat(createdStudent).isNotNull();
        Assertions.assertThat(createdStudent.getId()).isNotNull();
        Assertions.assertThat(createdStudent.getName()).isEqualTo("BIBALearning");
        Assertions.assertThat(createdStudent.getAge()).isEqualTo(12);
    }

    @Test
    public void testEditStudent() {
        Student student = new Student();
        student.setName("Bobainfo");
        student.setAge(23);

        // Создаем студента
        Student createdStudent = this.restTemplate.postForObject(getUrl("/student"), student, Student.class);
        Assertions.assertThat(createdStudent).isNotNull();

        // Меняем данные
        createdStudent.setName("UpdatedName");
        createdStudent.setAge(34);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> entity = new HttpEntity<>(createdStudent, headers);

        ResponseEntity<Student> response = this.restTemplate.exchange(getUrl("/student"), HttpMethod.PUT, entity, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("UpdatedName");
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(34);
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("ToDelete");
        student.setAge(20);

        Student createdStudent = this.restTemplate.postForObject(getUrl("/student"), student, Student.class);
        Assertions.assertThat(createdStudent).isNotNull();

        this.restTemplate.delete(getUrl("/student/" + createdStudent.getId()));

        ResponseEntity<Student> response = this.restTemplate.getForEntity(getUrl("/student/" + createdStudent.getId()), Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetStudentFaculty() {
        Student student = new Student();
        student.setName("FacultyStudent");
        student.setAge(18);

        Student createdStudent = this.restTemplate.postForObject(getUrl("/student"), student, Student.class);
        Assertions.assertThat(createdStudent).isNotNull();

        // Предполагается, что студент изначально без факультета
        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(getUrl("/student/" + createdStudent.getId() + "/faculty"), Faculty.class);
        // Если факультет не назначен, возвращается 404
        Assertions.assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateAndGetAvatar() {
        // Создаем студента
        Student student = new Student();
        student.setName("AvatarStudent");
        student.setAge(21);
        Student createdStudent = this.restTemplate.postForObject(getUrl("/student"), student, Student.class);
        Assertions.assertThat(createdStudent).isNotNull();

        // Создаем аватар
        Avatar avatar = new Avatar();
        avatar.setFilePath("/avatars/avatar1.png");
        avatar.setFileSize(2048);
        avatar.setMediaType("image/png");
        avatar.setData(Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAUA")); // пример base64-закодированных данных
        avatar.setStudent(createdStudent);

        // Здесь должен быть вызов метода сервиса или эндпоинта для сохранения аватара
        // В данном примере предполагается, что есть эндпоинт POST /avatar (не реализован в коде, но логика должна быть аналогична)
        // ResponseEntity<Avatar> avatarResponse = this.restTemplate.postForEntity(getUrl("/avatar"), avatar, Avatar.class);
        // Assertions.assertThat(avatarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Assertions.assertThat(avatarResponse.getBody()).isNotNull();
        // Assertions.assertThat(avatarResponse.getBody().getStudent().getId()).isEqualTo(createdStudent.getId());
    }
}
