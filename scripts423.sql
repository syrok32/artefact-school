SELECT student.name, student.age, faculty.name AS faculty_name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age, avatar.data AS avatar
FROM student
LEFT JOIN avatar ON avatar.student_id = student.id;

