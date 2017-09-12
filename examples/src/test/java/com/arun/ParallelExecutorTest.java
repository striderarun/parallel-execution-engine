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

    @Test(timeout = 7200)
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

        assert(executionTime < 7200);

    }

    @Test(timeout = 1500)
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

        studentServiceSignatures.add(Signature.build(StudentService_.getStudentMarks(1L)));
        studentServiceSignatures.add(Signature.build(StudentService_.getStudentsByFirstNames(Arrays.asList("John","Alice"))));
        studentServiceSignatures.add(Signature.build(StudentService_.getRandomLastName()));
        studentServiceSignatures.add(Signature.build(StudentService_.findStudentIdByName("Kate", "Williams")));
        studentServiceSignatures.add(Signature.build(StudentService_.findStudent("bob@gmail.com", 14, false)));
        studentServiceSignatures.add(Signature.build(StudentService_.printMapValues(bookSeries)));
        schoolServiceSignatures.add(Signature.build(SchoolService_.getSchoolNames()));

        executionMap.put(studentService, studentServiceSignatures);
        executionMap.put(schoolService, schoolServiceSignatures);

        List<T> result = ParallelProcessor.genericParallelExecutor(executionMap);
        long executionTime = (System.nanoTime() - startTime) / 1000000;

        assert(7 == result.size());
        assert(executionTime < 1500);
    }
}
