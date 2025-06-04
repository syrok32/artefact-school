package ru.hogwarts.artefact.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.hogwarts.artefact.school.model.Faculty;
import ru.hogwarts.artefact.school.model.Student;
import ru.hogwarts.artefact.school.services.StudentService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    private synchronized void printStudentName(String name) {
        System.out.println(name);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent(@RequestParam(required = false) Integer startAge, @RequestParam(required = false) Integer endAge) {
        if (startAge != null && endAge != null) {
            return ResponseEntity.ok(studentService.findByAgeBetween(startAge, endAge));

        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        student.setId(null);
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student renameStudent = studentService.editStudent(student);
        if (renameStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(renameStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {

        Faculty faculty = studentService.getFacultyByStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("countAllStudents")
    public Long countAllStudents() {
        return studentService.countAllStudents();
    }

    @GetMapping("getAverageStudentAge")
    public Double getAverageStudentAge() {
        return studentService.getAverageStudentAge();
    }

    @GetMapping("findLastFiveStudents")
    public List<Student> findLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }

    @GetMapping("filter-student")
    public List<Student> filterStudentByA() {
        return studentService.getAllStudents().stream().filter(student -> student.getName().startsWith("A")).collect(Collectors.toList());
    }

    @GetMapping("average-age")
    public double getAverageAge() {
        return studentService.getAllStudents().stream().mapToDouble(Student::getAge).average().orElse(0.0);
    }

    @GetMapping("longest-faculty")
    public String getLongestFacultyName() {
        return studentService.getAllStudents().stream()
                .map(Student::getFaculty)
                .filter(Objects::nonNull)
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }


    @GetMapping("calculate-sum")
    public long calculateSum() {

        return IntStream.rangeClosed(1, 1_000_000).sum();
    }

    @GetMapping("students/print-parallel")
    public String printParallel() {
        List<String> studentNames = studentService.getAllStudents().stream()
                .map(Student::getName)
                .toList();


        Thread thread1 = new Thread(() -> {
            System.out.println(studentNames.get(2));
            System.out.println(studentNames.get(3));
        });


        Thread thread2 = new Thread(() -> {
            System.out.println(studentNames.get(4));
            System.out.println(studentNames.get(5));
        });
        thread1.start();
        thread2.start();

        return "ok";
    }

    @GetMapping("students/print-synchronized")
    public String printStudentsSynchronized() {
        List<String> studentNames = studentService.getAllStudents().stream()
                .map(Student::getName)
                .toList();

        printStudentName(studentNames.get(0));
        printStudentName(studentNames.get(1));
        Thread thread1 = new Thread(() -> {
            printStudentName(studentNames.get(2));
            printStudentName(studentNames.get(3));
        });
        Thread thread2 = new Thread(() -> {
            printStudentName(studentNames.get(4));
            printStudentName(studentNames.get(5));
        });
        thread1.start();
        thread2.start();

        return "ok";
    }
}


