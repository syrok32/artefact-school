
CREATE TABLE Car (
    car_id INT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);


CREATE TABLE Person (
    person_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    has_license BOOLEAN NOT NULL,
    car_id INT,
    FOREIGN KEY (car_id) REFERENCES Car(car_id)
);