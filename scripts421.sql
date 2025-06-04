ALTER TABLE student
ADD CONSTRAINT age_constrainty CHECK (age > 16);
ALTER TABLE student
ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT uq_student_code UNIQUE (name);
ALTER TABLE faculty
ADD CONSTRAINT uq_dept_name_location UNIQUE (name, color);
ALTER TABLE student
ALTER COLUMN age SET DEFAULT 1;

