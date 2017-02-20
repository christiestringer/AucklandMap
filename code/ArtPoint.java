import java.util.ArrayList;
import java.util.List;

public class ArtPoint {
	
//	start node
//	list of nodes
//	list of articulation point nodes
//	a subtree which starts at 0
//
//	gets passed in a set of nodes and a start node
//	set all nodes to have a depth of infinity
//	set the start node to have a depth of 0
//
//	finding the articulation points
//	create a list of neighbours
//	create a list of segments of the start node
//	for loop around start nodes segments
//	find the id of the other node connected to start node
//	find that node on the map
//	add it to the neighbours list
//
//	for loop around the neighbours list
//	if the neighbours depth is equal to infinity
//	call the recursive method on it with a itself being the current node, 1 being its depth and start node being its parent
//	increase the subtrees
//
//	if subtrees is more than 1, add the start node to the list of articulation points
//
//	return articulation points list
//
//
//	RECURSIVE method
//	gets passed in a current node, a depth and a came from node
//	set the depth of the node to the passed in depth
//	reach back = depth
//	child reach = infinity
//
//	create list of neighbours
//	create list of segments of the current node
//
//	loop through segments finding the other node
//	adding it to list of neighbours
//
//	looping through neighbours
//	if == cameFrom continue
//	if nodes depth is less than infinity
//	reach back is the minimum of reach back and nodes depth
//	else child reach is recArtPoints(n, depth + 1, currentNode)
//	if child reach is more than or equal to depth
//	add the current node to articulation points
//
//	reach back is the minimum of reach back and child reach.
//
//	return reachback
			
	private Node start;
	private List<Node> nodes = new ArrayList<Node>();
	private List<Node> artPoints = new ArrayList<Node>();
	private int subTree = 0;
	
	public ArtPoint(List<Node> node, Node start){
		this.nodes = node;
		for(Node n: nodes){
			n.depth = Double.POSITIVE_INFINITY;
		}
		this.start = start;
		this.start.depth = 0;
	}
	
	public List<Node> findArtPoints(){
	ArrayList<Node> nhb = new ArrayList<Node>();
	List<Segment> artSeg = start.roadSeg;
	
		for(Segment s: artSeg){
			int nID = s.otherNode(start.id);
			Node n = AucklandMap.roadNodes.get(nID);
			nhb.add(n);
		}
		for(Node n: nhb){
			if(n.depth == Double.POSITIVE_INFINITY){
				recArtPoints(n, 1, start);
				subTree++;
			}
			if(subTree > 1){
				artPoints.add(start);
			}
		}
		return artPoints;
	}
	
	public double recArtPoints(Node node, double depth, Node cameFrom){
		node.depth = depth; 
		double reachBack =  depth;
		double childReach = Double.POSITIVE_INFINITY;
		
		ArrayList<Node> nhb = new ArrayList<Node>();
		List<Segment> artSeg = node.roadSeg;
		
			for(Segment s: artSeg){
				int nID = s.otherNode(node.id);
				Node n = AucklandMap.roadNodes.get(nID);
				nhb.add(n);
			}
		
		for(Node n: nhb){
			if(n == cameFrom){
				continue;
				}
			if(n.depth < Double.POSITIVE_INFINITY){
				reachBack = Math.min(reachBack, n.depth);
			}
			else{
				childReach = recArtPoints(n, depth + 1, node);
				if(childReach >= depth){
					artPoints.add(node);
				}
			}
			reachBack = Math.min(reachBack, childReach);
		}
		return reachBack;
	}
}
