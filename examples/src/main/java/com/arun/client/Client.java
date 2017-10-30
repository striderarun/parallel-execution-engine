package com.arun.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arun.parallel.ParallelProcessor;
import com.arun.parallel.Signature;
import com.arun.student.SchoolService;
import com.arun.student.SchoolService_;
import com.arun.student.Student;
import com.arun.student.StudentService;
import com.arun.student.StudentService_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	public static final Logger LOGGER = LoggerFactory.getLogger(ParallelProcessor.class);

	public static void serialExecution() {
		long startTime = System.nanoTime();
		StudentService service = new StudentService();
		SchoolService schoolService = new SchoolService();
		Map<String, Integer> bookSeries = new HashMap<>();
		bookSeries.put("A Song of Ice and Fire", 7);
		bookSeries.put("Wheel of Time", 14);
		bookSeries.put("Harry Potter", 7);

		Student student = service.findStudent("john@gmail.com", 11, false);
		List<Integer> marks = service.getStudentMarks(1L);
		List<Student> students = service.getStudentsByFirstNames(Arrays.asList("John","Alice"));
		String randomName = service.getRandomLastName();
		Long studentId = service.findStudentIdByName("Kate", "Williams");
		service.printMapValues(bookSeries);
		List<String> schoolNames = schoolService.getSchoolNames();

		LOGGER.info(student.toString());
		LOGGER.info(marks.toString());
		LOGGER.info(students.toString());
		LOGGER.info(randomName);
		LOGGER.info(studentId.toString());
		LOGGER.info(schoolNames.toString());

		long executionTime = (System.nanoTime() - startTime) / 1000000;
		LOGGER.info(String.format("Total elapsed time is %d\n\n", executionTime));
	}
	
	public static <T> void parallelExecution() {
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
		result.forEach(s -> System.out.println(s));
    	long executionTime = (System.nanoTime() - startTime) / 1000000;
    	System.out.printf("\nTotal elapsed time is %d", executionTime);
    }

	public static void main(String[] args) {
		serialExecution();
//		parallelExecution();
	}
	
}
