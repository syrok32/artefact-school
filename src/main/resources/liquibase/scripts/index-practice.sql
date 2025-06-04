-- liquibase formatted sql
-- changeset syrok32:1
CREATE INDEX student_name_index ON student (name);

-- changeset syrok32:2
CREATE INDEX faculty_name_color_index ON faculty (name, color);