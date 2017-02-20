import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
	//the segments are drawn after the nodes and roads data has been read, thus these are passed through to it
	public static List<Segment> parseSegments(Map<Integer, Node> nodes, Map<Integer, Road> roads, Map<String, Road> searchRoad, File segFile){
		// Read file line by line
		ArrayList<Segment> segments = new ArrayList<Segment>();
		try {
			//the buffered reader will read the data line by line and process each word as a string
		BufferedReader data = new BufferedReader(new FileReader(segFile));
		String line= data.readLine();
		while(  (line  = data.readLine() ) != null) { 
		// Process each line using split method
			String[] values = line.split("\t");
			//store the road id, length, nodeID1 and nodeID1
			int roadId = Integer.parseInt(values[0]);
			double length = Double.parseDouble(values[1]);
			int nodeID1 = Integer.parseInt(values[2]);
			int nodeID2 = Integer.parseInt(values[3]);
			ArrayList<Location> segLoc = new ArrayList<Location>();
			//Stores the location in an arrayList
			for(int i = 4; i< values.length; i+=2){
				double lat = Double.parseDouble(values[i]);
				double lon = Double.parseDouble(values[i+1]);
				segLoc.add(Location.newFromLatLon(lat, lon));
			}
			//create a new Segment with the above data
			Segment s = new Segment(roadId, length, nodeID1, nodeID2, segLoc);
			segments.add(s);
			Road road = roads.get(roadId);
			//add the segment to the road it is connected to 
			road.addSegment(s);
			//adds the segment to the road which has been searched
			String roadName = roads.get(roadId).streetName;
			Road sRoad = searchRoad.get(roadName);
			sRoad.addSegment(s);
			Node node = nodes.get(nodeID1);
			node.addRoadSegment(s);
			if(!road.oneWay){
				Node node2 = nodes.get(nodeID2);
				node2.addRoadSegment(s);
			}
		}
		data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return segments;
	}
	
	public static Map<Integer, Node> parseNodes(File nodeFile){
		//Create a new hashmap for the nodes that are found in the file
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		try {
			BufferedReader data = new BufferedReader(new FileReader(nodeFile));
			String line;
			while( (line  = data.readLine() ) != null) { 
			// Stores all the values in the data into individual variables
				String[] values = line.split("\t");
				int nodeID = Integer.parseInt(values[0]);
				double latit = Double.parseDouble(values[1]);
				double longi = Double.parseDouble(values[2]);
				Node n = new Node(Location.newFromLatLon(latit, longi), nodeID);
				nodes.put(nodeID, n);
			}
			data.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return nodes; //returns the map of nodes
	}
	
	public static Map<Integer, Road> parseRoads(File roadFile){
		//creates new map of roads that can be found with the key of roadID's
		HashMap<Integer, Road> roads = new HashMap<Integer, Road>();
		try {
			BufferedReader data = new BufferedReader(new FileReader(roadFile));
			String line =  data.readLine();
				while((line  = data.readLine() ) != null) {
			// Process each line using split method
					String[] values = line.split("\t");
					int roadID = Integer.parseInt(values[0]);
//					int type = Integer.parseInt(values[1]);
					String streetName = values[2];
					String suburb = values[3];
					int oneWay = Integer.parseInt(values[4]);
//					int speed = Integer.parseInt(values[5]);
//					int roadClass = Integer.parseInt(values[6]);
//					int notForCar = Integer.parseInt(values[7]);
//					int notForPede = Integer.parseInt(values[8]);
//					int notForBic = Integer.parseInt(values[9]);
					roads.put(roadID, new Road(streetName, roadID, suburb, oneWay == 1));
					//add the road to the map
				}
				data.close();
			}	catch (IOException e) {
				e.printStackTrace();
			}
		return roads;
	}

public static Map<String, Road> parseRoadsResults(File roadFile){
	//Same method as the parseRoads, but with the key as the streetName
	HashMap<String, Road> roadSearch = new HashMap<String, Road>();
	try {
		//File roadFile = new File(ROADS_FILENAME);
		BufferedReader data = new BufferedReader(new FileReader(roadFile));
		String line =  data.readLine();
			while((line  = data.readLine() ) != null) {
				String[] values = line.split("\t");
				int roadID = Integer.parseInt(values[0]);
				String streetName = values[2];
				String suburb = values[3];
				int oneWay = Integer.parseInt(values[4]);
				roadSearch.put(streetName, new Road(streetName, roadID, suburb, oneWay == 1));
			}
			data.close();
		}	catch (IOException e) {
			e.printStackTrace();
		}
	return roadSearch;
	 
}
}
