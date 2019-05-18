package entity.interfaces;

import java.util.ArrayList;


import model.Subject;

public interface ISubject {
	//void addStudent(Student s);
		int addSubject(Subject s);
		Subject findSubject(Integer id);
		ArrayList<Subject> allSubject();
		void addSubjects(Subject[] ss);
}
