package com.xyzcorp;

import redis.clients.jedis.Jedis;

import java.util.List;

public class StudentService {
    private final Jedis jedis;

    public StudentService(String address, Integer port) {
        jedis = new Jedis(address, port);
    }

    public void register(Student student) {
        jedis.lpush("student-roll", student.getCommaName());
    }

    public List<Student> getAllStudents() {
        List<String> list = jedis.lrange("student-roll", 0, 100);

        return list.stream().map(s -> {
            var tokens = s.split(",");
            return new Student(tokens[0], tokens[1]);
        }).toList();
    }
}
