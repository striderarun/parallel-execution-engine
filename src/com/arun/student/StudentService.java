package com.arun.student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StudentService {

    private List<Student> studentsDatabase = Arrays.asList(
            new Student(1L, "Bob", "Hopkins", "bob@gmail.com", "8", 14, Arrays.asList(90,80,85), "male", false, true),
            new Student(2L, "Alice", "Grey", "alice@gmail.com", "7", 13, Arrays.asList(70,60,55), "female", false, false),
            new Student(3L, "John", "Hankshaw", "john@gmail.com", "5", 11, Arrays.asList(80,70,75), "male", false, false),
            new Student(4L, "Kate", "Williams", "rob@gmail.com", "9", 15, Arrays.asList(80,90,95), "female", true, true)
            );

    public Boolean executeQueryOne() {
        simulateDelay();
        return false;
    }

    public List<Integer> getStudentMarks(Long id) {
        simulateDelay();
        return studentsDatabase.stream().filter(s -> s.getId().equals(id)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown Student Id")).getMarks();
    }



    private void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
