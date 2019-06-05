package entity.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Lecture;
import model.Subject;

public interface ISubject {
	//void addStudent(Student s);
		int addSubject(Subject s) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		Subject findSubject(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		ArrayList<Subject> allSubject() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		void addSubjects(Subject[] ss) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		ArrayList<HashMap<String, String>> allSubjectByDeId(Integer id)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		int createLecture(Lecture lecture)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		int attendLecture(Integer st_id, Integer sj_id)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		ArrayList<HashMap<String, String>> allSubjectByStId(Integer id)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
		Integer attendOnClass(Integer st_id, Integer le_id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
