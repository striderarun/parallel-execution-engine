package com.arun.client;


public class Student {

    private String name;
    private Long age;
    private Boolean hasPassed;

    public String getName() {
        return name;
    }

    public Student() {

    }
    public Student(String name, Long age, Boolean hasPassed) {
        this.name = name;
        this.age = age;
        this.hasPassed = hasPassed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Boolean isHasPassed() {
        return hasPassed;
    }

    public void setHasPassed(Boolean hasPassed) {
        this.hasPassed = hasPassed;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hasPassed=" + hasPassed +
                '}';
    }
}
