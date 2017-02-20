import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Road {
	//stores the list of segments of the road, the name, id, suburb and if it has any children
	private List<Segment> road;
	public final String streetName; 
	public final int roadID; 
	public final String suburb; 
	public final boolean oneWay;
	public List<String> children = null;
	
	
	public Road(String name, int id, String suburb, boolean oneWay ){
		this.streetName = name;
		this.roadID = id;
		this.suburb = suburb;
		this.oneWay = oneWay;
		road = new ArrayList<Segment>();
		
	}
	public void addSegment(Segment segment){
		//add segments when it is passed through
		road.add(segment);
	}
	public void draw(Graphics g, double scale, int offsetX, int offsetY, Color colour) {
		//go through the list of segments and draw each segment 
		for(Segment s: road){
			s.draw(g, scale, offsetX, offsetY, colour);
		}
	}
}
