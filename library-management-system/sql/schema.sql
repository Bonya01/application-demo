CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  student_id VARCHAR(100)
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE books (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  isbn VARCHAR(100) NOT NULL UNIQUE,
  total_copies INT NOT NULL,
  available_copies INT NOT NULL,
  description TEXT
);

CREATE TABLE issue_records (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  book_id BIGINT,
  issue_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE,
  fine DOUBLE,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (book_id) REFERENCES books(id)
);
