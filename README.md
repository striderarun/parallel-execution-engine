# parallel-execution-engine
A pure java library which can be used by clients to execute independent methods in parallel.
Uses Java 8 Completable Futures, Reflection and Generics.

* Does not use any third party libraries. Java 8 is a pre-requisite.
* Uses flow style builder pattern to create method signatures.
* Clients can use the library to parallelize methods with different signatures - different return types, method arguments, collections, custom classes etc.
* Clients can also parallelize methods in different classes.
* Since it is a pure java library, it can be used in Spring or other DI based projects as well.
* Clients can leverage java concurrency to parallelize method calls without worrying about the implementation.

Example illustrating usage of the API:

Consider a class StudentService.java which provides some methods to query Student data from a database.

List<Integer> getStudentMarks(Long studentId);
List<Student> getStudentsByFirstNames(List<String> firstNames);
String getRandomLastName();
Long findStudentIdByName(String firstName, String lastName);
Student findStudent(String email, Integer age, Boolean isDayScholar);
void printMapValues(Map<String, Integer> bookSeries);

Another class SchoolService.java which returns a list of School names via the method

List<String> getSchoolNames();

Assuming each method takes 1 second to execute, a client which wants to call all the methods from StudentService and SchoolService 
will need 7 seconds to get the output of all the sequential service calls. 

Using the parallel-execution-engine library, a client can call all the methods simultaneously and get the output in 1 second as follows:

Step 1: Create a list of signatures of all methods of a class to be executed in parallel.

eg:

studentServiceSignatures.add(Signature.method("getStudentMarks")
		.returnType(List.class)
		.argsList(Arrays.asList(1L))
		.argTypes(Arrays.asList(Long.class))
		.build());

studentServiceSignatures.add(Signature.method("getStudentsByFirstNames")
		.returnType(List.class)
		.argsList(Arrays.asList(Arrays.asList("John","Alice")))
		.argTypes(Arrays.asList(List.class))
		.build());
		
studentServiceSignatures.add(Signature.method("findStudent")
		.returnType(Student.class)
		.argsList(Arrays.asList("bob@gmail.com", 14, false))
		.argTypes(Arrays.asList(String.class, Integer.class, Boolean.class))
		.build());
				
schoolServiceSignatures.add(Signature.method("getSchoolNames")
		.returnType(List.class)
		.build());
						
Step 2: Submit the objects and the method signatures to the execution library.

Map<Object, List<Signature>> executionMap = new HashMap<>();
executionMap.put(studentService, studentServiceSignatures);
executionMap.put(schoolService, schoolServiceSignatures);
List<T> result = ParallelProcessor.genericParallelExecutor(executionMap);

