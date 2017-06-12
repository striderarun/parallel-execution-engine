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

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGrade() {
        return grade;
    }

    public Integer getAge() {
        return age;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public String getGender() {
        return gender;
    }

    public Boolean getDayScholar() {
        return isDayScholar;
    }

    public Boolean getHasScholarship() {
        return hasScholarship;
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
