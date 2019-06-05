package entity.interfaces;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Student;
public interface IStudent {
	//void addStudent(Student s);
	int addStudent(Student s)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Student findStudent(Integer id)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	ArrayList<Student> allStudent()  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	void addStudents(Student[] ss)  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;

}
