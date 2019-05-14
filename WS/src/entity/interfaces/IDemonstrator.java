package entity.interfaces;

import java.util.ArrayList;

import model.Demonstrator;

public interface IDemonstrator {
	int addDemonstrator(Demonstrator d);
	Demonstrator findDemonstrator(Integer id);
	ArrayList<Demonstrator> allDemonstrator();
	void addDemonstrators(Demonstrator[] ds);
}
