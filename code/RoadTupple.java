
public class RoadTupple {
	public final Node currentNode;
	public final double totalDist;
	public final double estDist;
	public final RoadTupple parentTupple;
	public final Segment segment;
	
	public RoadTupple(Node cNode, double tDist, RoadTupple pTup, double eDist, Segment segment){
		this.currentNode = cNode;
		this.totalDist = tDist;
		this.parentTupple = pTup;
		this.estDist = eDist;
		this.segment = segment;
	}
	
	public double estTotDist(){
		return totalDist + estDist; 
	}
	
}
