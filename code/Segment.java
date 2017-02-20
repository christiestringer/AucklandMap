import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Segment {
	//Stores the roadID it belongs to, its length and the nodes it is connected to 
	public final int roadID;
	double length; 
	int nodeID1;
	int nodeID2;
	private List<Location> sections;
	
	public Segment(int roadID, double length, int nodeID1, int nodeID2, ArrayList<Location> sections) {
		this.roadID = roadID;
		this.length = length;
		this.nodeID1 = nodeID1;
		this.nodeID2 = nodeID2;
		this.sections = sections; 
	}
	public void draw(Graphics g, double scale, int offsetX, int offsetY, Color colour) {
		//draws the section with the locations given from the main class
		g.setColor(colour);
		for(int i= 0; i< sections.size() - 1; i++){
			double x1 = sections.get(i).x;
			double y1 = sections.get(i).y;
			double x2 = sections.get(i+1).x;
			double y2 = sections.get(i+1).y;
			g.drawLine((int)((x1 *scale )+ offsetX), (int)(((y1 *scale )+offsetY)* -1),(int) ((x2 *scale)+offsetX), (int)(((y2 *scale )+offsetY)* -1));
			
			}
		}
	
	public int otherNode(int nodeID){
		if(nodeID1 == nodeID ){
			return nodeID2;
		}
		else{
			return nodeID1;
		}
		
	}
}



