import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Node {
	//Store the location, the nodeID and the segments that are connected to it
	public final Location loc;
	int id;
	public final List<Segment> roadSeg; 
	public double depth;
	
	public Node(Location l, int id){
		this.loc = l;
		this.id = id;
		roadSeg = new ArrayList<Segment>();
	}
	
	public void addRoadSegment(Segment segment){
		//adding the segments which are connected to this intersection
		roadSeg.add(segment);
	}
	 
	public void draw(Graphics g, double scale, int offsetX, int offsetY, Color colour) {
		//draw the nodes with the color passed in and the locations that were passed in the document
		g.setColor(colour);
		g.fillRect((int)(((loc.x  * scale) + offsetX) ), (int)(((loc.y * scale) + offsetY)* -1) , 2, 2);
	}
	
	public int getLocationDif(int x, int y, double scale, int offsetX, int offsetY){
		//finds the difference between the location clicked on the screen and the nearest node
		int screen_x = (int)((loc.x  * scale) + offsetX);
		int screen_y = (int)(((loc.y * scale) + offsetY)* -1);
		int xDiff = Math.abs((int) (x - screen_x)); 
		int yDiff = Math.abs((int) (y - screen_y)); 
		return xDiff + yDiff;
	}
	
}
