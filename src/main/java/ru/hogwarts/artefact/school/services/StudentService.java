package ru.hogwarts.artefact.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Collection<Student> findByAgeBetween(Integer startAge, Integer endAge) {
        return studentRepository.findByAgeBetween(startAge, endAge);
    }
    public Faculty getFacultyByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        return student != null ? student.getFaculty() : null;
    }

}
