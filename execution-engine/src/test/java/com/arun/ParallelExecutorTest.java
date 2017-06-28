package com.arun;


import com.arun.parallel.ParallelProcessor;
import com.arun.parallel.Signature;
import com.arun.student.SchoolService;
import com.arun.student.SchoolService_;
import com.arun.student.Student;
import com.arun.student.StudentService;
import com.arun.student.StudentService_;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParallelExecutorTest {

    @Test(timeout = 7100)
    public void testSerialExecution() {
        long startTime = System.nanoTime();
        StudentService studentService = new StudentService();
        SchoolService schoolService = new SchoolService();
        Map<String, Integer> bookSeries = new HashMap<>();
        bookSeries.put("A Song of Ice and Fire", 7);
        bookSeries.put("Wheel of Time", 14);
        bookSeries.put("Harry Potter", 7);

        Student student = studentService.findStudent("john@gmail.com", 11, false);
        List<Integer> marks = studentService.getStudentMarks(1L);
        List<Student> students = studentService.getStudentsByFirstNames(Arrays.asList("John","Alice"));
        String randomName = studentService.getRandomLastName();
        Long studentId = studentService.findStudentIdByName("Kate", "Williams");
        studentService.printMapValues(bookSeries);
        List<String> schoolNames = schoolService.getSchoolNames();
        long executionTime = (System.nanoTime() - startTime) / 1000000;

        assert(executionTime < 7100);

    }

    @Test(timeout = 1100)
    public <T> void testParallelExecution() {
        long startTime = System.nanoTime();
        StudentService studentService = new StudentService();
        SchoolService schoolService = new SchoolService();
        Map<Object, List<Signature>> executionMap = new HashMap<>();
        List<Signature> studentServiceSignatures = new ArrayList<>();
        List<Signature> schoolServiceSignatures = new ArrayList<>();
        Map<String, Integer> bookSeries = new HashMap<>();
        bookSeries.put("A Song of Ice and Fire", 7);
        bookSeries.put("Wheel of Time", 14);
        bookSeries.put("Middle Earth Legendarium", 5);

        studentServiceSignatures.add(Signature.method(StudentService_.getStudentMarks)
                .returnType(List.class)
                .argsList(Arrays.asList(1L))
                .argTypes(Arrays.asList(Long.class))
                .build());

        studentServiceSignatures.add(Signature.method(StudentService_.getStudentsByFirstNames)
                .returnType(List.class)
                .argsList(Arrays.asList(Arrays.asList("John","Alice")))
                .argTypes(Arrays.asList(List.class))
                .build());

        studentServiceSignatures.add(Signature.method(StudentService_.getRandomLastName)
                .returnType(String.class)
                .build());

        studentServiceSignatures.add(Signature.method(StudentService_.findStudentIdByName)
                .returnType(Long.class)
                .argsList(Arrays.asList("Kate", "Williams"))
                .argTypes(Arrays.asList(String.class, String.class))
                .build());

        studentServiceSignatures.add(Signature.method(StudentService_.findStudent)
                .returnType(Student.class)
                .argsList(Arrays.asList("bob@gmail.com", 14, false))
                .argTypes(Arrays.asList(String.class, Integer.class, Boolean.class))
                .build());

        studentServiceSignatures.add(Signature.method(StudentService_.printMapValues)
                .returnType(Void.class)
                .argsList(Arrays.asList(bookSeries))
                .argTypes(Arrays.asList(Map.class))
                .build());

        schoolServiceSignatures.add(Signature.method(SchoolService_.getSchoolNames)
                .returnType(List.class)
                .build());

        executionMap.put(studentService, studentServiceSignatures);
        executionMap.put(schoolService, schoolServiceSignatures);

        List<T> result = ParallelProcessor.genericParallelExecutor(executionMap);
        long executionTime = (System.nanoTime() - startTime) / 1000000;

        assert(7 == result.size());
        assert(executionTime < 1100);
    }
}
