import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class PathFinder {
	//current node
	//start node
	//end node
	//array of came_from nodes
	//came_from.get(start) = null
	//Queue of nodes
	//boolean, visited

	//public double heuristics(a, b)
	//return abs(a.x - b.x) + abs(a.y -b.y)
	
	//Put start node in queue
	//When queue is not empty (loop)
	//Find the smallest length, peek? or remove
	//if current = end, break
	//loop through current's neighbours
	//if next is not in came_from
	//priority = heuristic(goal, next)
	//put next and its priority in the queue
	//the came_from at next is now current
	
	
	private Node startNode;
	private Node endNode;
	private List<RoadTupple> allNodes = new ArrayList<RoadTupple>();
	private Set<Integer> visited = new HashSet<Integer>();
	
	public PathFinder(Node startNode, Node endNode){
		this.startNode = startNode;
		this.endNode = endNode;
	}
	
	public double heuristics(Location goal, Location current){
		return Math.sqrt(Math.pow(Math.abs(goal.x - current.x),2) + Math.pow(Math.abs(goal.y - current.y),2));
	}

	public RoadTupple findPath(){
		//System.out.println(startNode + " " + endNode);
		allNodes.add(new RoadTupple(startNode, 0, null, heuristics(endNode.loc, startNode.loc), null));
		while(!allNodes.isEmpty()){
			RoadTupple bestTup = bestTupple();
			visited.add(bestTup.currentNode.id);
			allNodes.remove(bestTup);
			
			if(bestTup.currentNode == endNode){
				return bestTup;
			}
			else{
				List<Segment>bTRoadSeg = bestTup.currentNode.roadSeg;
				for(Segment s: bTRoadSeg){
					int nID = s.otherNode(bestTup.currentNode.id);
					Node n = AucklandMap.roadNodes.get(nID);
					double h = heuristics(endNode.loc, n.loc);
					if(!visited.contains(nID)){
						RoadTupple nhb = new RoadTupple(n, s.length + bestTup.totalDist, bestTup, h, s );
						allNodes.add(nhb);
					}
					
				}
				
			}
		}
		return null;
	}

public List<Segment> bestRoute(){
	List<Segment> route = new ArrayList<Segment>();
	RoadTupple tup = findPath();
	while(tup != null){
		if(tup.segment != null)
			route.add(tup.segment);
		tup = tup.parentTupple;
	}
	return route;
}

public List<Node> bestNodeRoute(){
	List<Node> route = new ArrayList<Node>();
	RoadTupple tup = findPath();
	while(tup != null){
		if(tup.currentNode != null)
			route.add(tup.currentNode);
		tup = tup.parentTupple;
	}
	return route;
}

	public RoadTupple bestTupple(){
		double min = Integer.MAX_VALUE;
		RoadTupple bestRT = null;
		for(RoadTupple r: allNodes){
			if(r.estTotDist() < min){
				min = r.estTotDist();
				bestRT = r;
			}
		}
		return bestRT;
	}

}

