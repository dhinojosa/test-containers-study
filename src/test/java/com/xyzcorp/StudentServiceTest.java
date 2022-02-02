package com.xyzcorp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class StudentServiceTest {


    private StudentService studentService;

    @Container
    public GenericContainer redis = new GenericContainer(
        DockerImageName.parse("redis:5.0.3-alpine"))
        .withExposedPorts(6379);

    @BeforeEach
    public void setUp() {
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();

        // Now we have an address and port for Redis, no matter where it is running
        studentService = new StudentService(address, port);
    }

    @Test
    void testThatStudentIsPersisted() {
        Student albert = new Student("Albert", "Einstein");
        studentService.register(albert);
        assertThat(studentService.getAllStudents()).contains(albert);
    }
}
