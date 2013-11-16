package eu.crembo.test;

import java.util.ArrayList;

import eu.crembo.test.controllers.AirplaneController;
import eu.crembo.test.models.Aeroplane;
import eu.crembo.test.models.types.A1;
import eu.crembo.test.models.types.B1;

public class Main {
	
	public static ArrayList<Aeroplane> airplaneTypes;
	public static ArrayList<Aeroplane> airplanesOnScreen;
	
	public static AirplaneController cntlr;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// start game
		cntlr = new AirplaneController();

		cntlr.addType(new B1());
		cntlr.addType(new A1());
		// now we have all the types, can add more easily
		
		// randomly select airplane type from the list of available airplanes
		Aeroplane toSpawn = cntlr.pickRandomType();
		
		// shows that it is random, try running the program few times
		System.out.println(toSpawn.getSpeed());
		System.out.println(toSpawn);
		
		System.out.println(cntlr.getAirplaneTypes());
		
		// do stuff to actually spawn the airplane
		
		// randomly select airplane type from the list of available airplanes
		toSpawn = cntlr.pickRandomType();
		
		// shows that it is random, try running the program few times
		System.out.println(toSpawn.getSpeed());
		System.out.println(toSpawn);
		System.out.println(cntlr.getAirplaneTypes());
		
	}

}
