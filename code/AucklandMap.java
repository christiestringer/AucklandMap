import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AucklandMap extends GUI {

	public static Map<Integer, Node> roadNodes;
	private Map<Integer, Road> roadRoads;
	private Map<String, Road> roadSearch;
	private double scale = 2;
	private int offsetX = 220;
	private int offsetY = -300;
	private int offset_value = 10;
	private double scale_value = 2.0;
	private Color colour;
	private static Node selectedNode;
	private List<Segment> selectedRoads; 
	private List<Road> roadNames; 
	private List<String> results;
	private Trie roadTrie = new Trie();
	private String roadString = "";
	private Set<String> roadss;
	private boolean isRouting = false;
	private Node endNode;
	private Node startNode;
	private List<Node> nodePath;
	List<Road> roadRoute = new ArrayList<Road>();
	List<Segment> segmentRoute = new ArrayList<Segment>();
	private List<String> roadRouteNames = new ArrayList<String>();
	private ArtPoint artPts;
	private List<Node> artPtsNodes;
	private boolean isArticulate = false;
	private double distance = 0;
	
	@Override
	protected void redraw(Graphics g) {
		getTextOutputArea().setText("");
//		draw(g);
		if(roadNodes != null){
		
			//draws all intersections
			for (Map.Entry<Integer, Node> entry : roadNodes.entrySet()) {
			    Node node = entry.getValue();
			    colour = Color.PINK; 
			    node.draw(g, scale, offsetX, offsetY, colour);
			}
			//draws all roads
			for(Map.Entry<Integer, Road>entry :roadRoads.entrySet()){
				colour = Color.GRAY;
				Road road = entry.getValue();    
				road.draw(g, scale, offsetX, offsetY, colour);
			//	System.out.println(road);
			}
			

			//if someone has selected a road, highlight the roads connected and the intersection selected green
			if(selectedNode!= null){
				colour = Color.GREEN;
				selectedNode.draw(g, scale, offsetX, offsetY, colour);
			}
			if(selectedRoads != null){
				getTextOutputArea().setText("");
				colour = Color.GREEN;
				for(int i = 0; i< selectedRoads.size(); i++){
					Segment selectedRoad = selectedRoads.get(i);
				selectedRoad.draw(g, scale, offsetX, offsetY, colour); 
				
				}
		}
			if(segmentRoute != null){
				colour = Color.ORANGE;
				for(Segment r: segmentRoute){
					Segment routeSeg = r;
					routeSeg.draw(g, scale, offsetX, offsetY, colour);
				}
			}
			
			if(nodePath != null){
				colour = Color.MAGENTA;
				for(Node n: nodePath){
					n.draw(g, scale, offsetX, offsetY, colour);
				}
			}
			if(artPtsNodes != null){
				colour = Color.red;
				for(Node n: artPtsNodes) n.draw(g, scale, offsetX, offsetY, colour);
			}
		//if someone has searched a road, highlight it blue
		if(results != null){
			getTextOutputArea().setText("");
			colour = Color.BLUE;
			Road searchedRoad = null;
			for(String s: results){
				 searchedRoad = roadSearch.get(s);
				 searchedRoad.draw(g, scale, offsetX, offsetY, colour);
			}
		}
		getTextOutputArea().setText(roadString);
		}
	}
	

	@Override
	protected void onClick(MouseEvent e) {
		getTextOutputArea().setText("");
		selectedRoads = null;
		//New arraylist to store all segments of the road that is selected
		selectedRoads = new ArrayList<Segment>();
		 int x = e.getX();
		 int y = e.getY();
		 int diff = Integer.MAX_VALUE;
		 //Get the difference between the point selected and the closest node
		 
		 
		 for(Map.Entry<Integer, Node> entry: roadNodes.entrySet()){
			 Node node = entry.getValue();
			 int dist = node.getLocationDif(x, y, scale, offsetX, offsetY );
			 if(dist < diff && dist < 100){
				 diff = dist;
				 if(isRouting){
					endNode = entry.getValue();
					startNode = selectedNode;
					PathFinder path = new  PathFinder(startNode, endNode);
					segmentRoute = path.bestRoute();
				//	System.out.println(segmentRoute);
					roadRouteNames.clear();
					for(Segment s: segmentRoute){
						int id = s.roadID;
						Road r = roadRoads.get(id);
						roadRouteNames.add(r.streetName);
						System.out.println(s.length);
						distance += s.length;
					}
					for(int i = 1; i < roadRouteNames.size(); i++){
						if(roadRouteNames.get(i-1).equals(roadRouteNames.get(i))){
							roadRouteNames.remove(i);
							i--;
						}
					}
					roadString = "";
					for(String r: roadRouteNames){
						roadString += "\n" + r; 
					}
					}
				 else{
					 selectedNode = entry.getValue(); 
				 }
			 }
		 }
		 if(distance > 0){
			 distance = distance/10.0;
			 roadString += "\n" + (Math.round(distance * 100.0)/100.0) + "km";
		 }
		 if(!isRouting){
		 if(diff == Integer.MAX_VALUE){
			 selectedNode = null;
		 }
		 //Get all segments in the selectedNode roads
		 for(int i = 0; i< selectedNode.roadSeg.size(); i++){
			Segment selectedRoadId = selectedNode.roadSeg.get(i); 
			selectedRoads.add(selectedRoadId);
		 }
		 roadNames = new ArrayList<Road>();
		 roadss = new HashSet<String>();
			for(int i = 0; i< selectedRoads.size(); i++){
				Road r = roadRoads.get( selectedRoads.get(i).roadID);
				roadNames.add(r);
				roadss.add(r.streetName);
		 roadString = "";
		 for(String s: roadss){
				roadString += "\n" + s;
			}
			}
	}
		 
	}

	@Override
	//call the roadTrie with the search box text.
	protected void onSearch() {
		getTextOutputArea().setText("");
		results = roadTrie.autoComplete(getSearchBox().getText());
//		System.out.println(results);
		roadString = "";
		for(String s: results){
			roadString += "\n" + s;
		}
	}

	@Override
	protected void onMove(Move m) {
		//Move whichever way the button has been selected. either X or Y
		if(m == Move.EAST){
			offsetX = offsetX - offset_value;
		}
		else if(m == Move.WEST){
			offsetX = offsetX + offset_value;
		}
		else if(m == Move.NORTH){
			offsetY = offsetY - offset_value;
		}
		else if(m == Move.SOUTH){
			offsetY = offsetY + offset_value;
		}
		else if(m == Move.ZOOM_IN){
			scale = scale * scale_value;
			if(offset_value <= 60)offset_value = offset_value * 2;
		}
		else if(m == Move.ZOOM_OUT){
			scale = scale / scale_value;
			offset_value = offset_value/2;
		}
	}
	
	public void onFind(MouseEvent e1, MouseEvent e2){
		
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		//when the document has been loaded, parse through all the documents provided
		roadNodes = Parser.parseNodes(nodes);
		roadRoads = Parser.parseRoads(roads);
		for(Map.Entry<Integer, Road> entry: roadRoads.entrySet()){
			Road road = entry.getValue();
			roadTrie.add(road.streetName);
		}
		roadSearch = Parser.parseRoadsResults(roads);
		Parser.parseSegments(roadNodes, roadRoads,roadSearch, segments);
		
		if(roadRoads.size() < 1000){
			offsetX = 500;
			offsetY = -100;
			scale = 50;
		}
	}
	public static void main(String[] args) {
		new AucklandMap();
	}


	@Override
	protected void onRoute() {
		isRouting =! isRouting;
		
	}


	@Override
	protected void onArticulate() {
		isArticulate =! isArticulate;
		if(isArticulate){
			 List<Node> allNodes = new ArrayList<Node>(this.roadNodes.values());
				artPts = new ArtPoint(allNodes, allNodes.get(0));
				artPtsNodes = artPts.findArtPoints();
		}
		if(!isArticulate){
			artPts = null;
			artPtsNodes = null;
		}
	}


}
