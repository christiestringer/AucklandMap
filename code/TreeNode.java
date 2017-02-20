import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private TreeNode parent; 
	private List<TreeNode> children;
	private boolean isWord; 
	public final char character; 
	
	public TreeNode(TreeNode parent, char character, boolean isWord){
		this.parent = parent;
		this.character = character;
		this.isWord = isWord;
		children = new ArrayList<TreeNode>();
	}
	
	public TreeNode addChild(TreeNode child){
		for(TreeNode s: children){
			if(child.character == s.character){
				return s;
			}
		}
		children.add(child);
		return child;
	}
	public TreeNode getNode(char c){
		for(TreeNode s: children){
			if(c == s.character){
				return s;
			}
		}
		return null;
	}
	public boolean contains(char c){
		for(TreeNode s: children){
			if(c == s.character){
				return true;
			}
		}
		return false;
	}
	public void findResults(List<String> results){
		if(isWord){
			results.add(RoadName());
		}
		for(TreeNode s: children){
			s.findResults(results);
		}
	}
	public String RoadName(){
		TreeNode currentNode = this;
		String result = "";
		while(currentNode != null){
			result+= currentNode.character;
			currentNode = currentNode.parent;
		}
		return new StringBuilder(result).reverse().toString();
		
	}
}
