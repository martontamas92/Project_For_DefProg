package entity.interfaces;
import model.Student;
public interface IStudent {

	void addStudent(Student s);
	Student findStudent(Integer id);
	Student[] allStudent();
	void addStudents(Student[] ss);

}
