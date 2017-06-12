package com.arun.student;


import java.util.List;

public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String grade;
    private Integer age;
    private List<Integer> marks;
    private String gender;
    private Boolean isDayScholar;
    private Boolean hasScholarship;

    public Student(Long id, String firstName, String lastName, String email, String grade, Integer age, List<Integer> marks, String gender, Boolean isDayScholar, Boolean hasScholarship) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.age = age;
        this.marks = marks;
        this.gender = gender;
        this.isDayScholar = isDayScholar;
        this.hasScholarship = hasScholarship;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", grade='" + grade + '\'' +
                ", age=" + age +
                ", marks=" + marks +
                ", gender='" + gender + '\'' +
                ", isDayScholar=" + isDayScholar +
                ", hasScholarship=" + hasScholarship +
                '}';
    }
}
