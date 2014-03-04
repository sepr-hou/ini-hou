package seprini.models;

public final class Airport extends Entity{

	Waypoint runwayEnd = new Waypoint(464, 395, false);
	Waypoint runwayMid = new Waypoint(387, 335, false);
	Waypoint runwayStart = new Waypoint(310, 275, false);
	
	public Waypoint getStart(){
		return runwayStart;
	}
	
	public Waypoint getEnd(){
		return runwayEnd;
	}
	
	public Waypoint getMid(){
		return runwayMid;
	}
	
}
