package entity.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import business_model.Name;
import model.Demonstrator;

public interface IDemonstrator {
	int addDemonstrator(Demonstrator d) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Demonstrator findDemonstrator(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	ArrayList<Demonstrator> allDemonstrator() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	void addDemonstrators(Demonstrator[] ds);
	Integer demonstratorByName(Name n)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
