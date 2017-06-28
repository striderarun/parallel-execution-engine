package com.arun.student;

import com.arun.Parallelizable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StudentService {

    private List<Student> studentsDatabase = Arrays.asList(
            new Student(1L, "Bob", "Hopkins", "bob@gmail.com", "8", 14, Arrays.asList(90,80,85), "male", false, true),
            new Student(2L, "Alice", "Grey", "alice@gmail.com", "7", 13, Arrays.asList(70,60,55), "female", false, false),
            new Student(3L, "John", "Hankshaw", "john@gmail.com", "5", 11, Arrays.asList(80,70,75), "male", false, false),
            new Student(4L, "Kate", "Williams", "rob@gmail.com", "9", 15, Arrays.asList(80,90,95), "female", true, true)
            );

    @Parallelizable
    public List<Integer> getStudentMarks(Long id) {
        simulateOneSecondDelay();
        return studentsDatabase.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Student Id"))
                .getMarks();
    }

    @Parallelizable
    public List<Student> getStudentsByFirstNames(List<String> firstNames) {
        List<Student> students = new ArrayList<>();
        simulateOneSecondDelay();
        firstNames.forEach(s -> students.addAll(
                studentsDatabase.stream()
                .filter(p -> p.getFirstName().equals(s))
                .collect(Collectors.toList())
        ));
        return students;
    }

    @Parallelizable
    public String getRandomLastName() {
        simulateOneSecondDelay();
        Random r = new Random();
        int low = 1;
        int high = 4;
        int randomId = r.nextInt(high-low) + low;
        return studentsDatabase.get(randomId).getLastName();
    }

    @Parallelizable
    public Long findStudentIdByName(String firstName, String lastName) {
        simulateOneSecondDelay();
        return studentsDatabase.stream()
                .filter(s -> s.getFirstName().equals(firstName))
                .filter(s -> s.getLastName().equals(lastName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Student Name"))
                .getId();
    }

    @Parallelizable
    public Student findStudent(String email, Integer age, Boolean isDayScholar) {
        simulateOneSecondDelay();
        return studentsDatabase.stream()
                .filter(s -> s.getEmail().equals(email))
                .filter(s -> s.getAge().equals(age))
                .filter(s -> s.getDayScholar().equals(isDayScholar))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    @Parallelizable
    public void printMapValues(Map<String, Integer> bookSeries) {
        simulateOneSecondDelay();
        System.out.println("Printing map contents");
        for(Map.Entry<String, Integer> entry: bookSeries.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private void simulateOneSecondDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
