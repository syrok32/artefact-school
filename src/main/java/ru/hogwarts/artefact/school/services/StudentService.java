package ru.hogwarts.artefact.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("create student");
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("find");
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("edit student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("delete student");
        studentRepository.deleteById(id);

    }

    public Collection<Student> getAllStudents() {
        logger.info("all student");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(Integer startAge, Integer endAge) {
        return studentRepository.findByAgeBetween(startAge, endAge);
    }

    public Faculty getFacultyByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        logger.warn("ошибка" + studentId);
        return student != null ? student.getFaculty() : null;
    }

    public Long countAllStudents() {
        return studentRepository.countAllStudents();
    }

    public Double getAverageStudentAge() {
        return studentRepository.getAverageStudentAge();
    }

    public List<Student> findLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }


}
