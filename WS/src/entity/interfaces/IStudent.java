package entity.interfaces;
import java.util.ArrayList;

import model.Student;
public interface IStudent {

	int addStudent(Student s);
	Student findStudent(Integer id);
	ArrayList<Student> allStudent();
	void addStudents(Student[] ss);

}
