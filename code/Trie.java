import java.util.ArrayList;
import java.util.List;

public class Trie {
	//Creates a list of children 
	List<TreeNode> children;
	
	public Trie(){
		children = new ArrayList<TreeNode>();
	}
	//
	public void add(String word){
		//adds a word to the trie
		TreeNode currentNode = null;
		for(int i = 0; i<word.length(); i++){
			boolean isWord = i == word.length()-1;
			char c = word.charAt(i);
			//a new TreeNode is made with where the position is, the next character and if it is a new word or not
			TreeNode newNode = new TreeNode(currentNode, c, isWord);
			if(currentNode == null){
				if(contains(c)){
					newNode = getNode(c);
				}else{
					children.add(newNode);
				}
			}
			else{
				newNode = currentNode.addChild(newNode);
			}
			currentNode = newNode;
		}
		
	}
	public boolean contains(char c){
		//returns true if the word contains c 
		for(TreeNode s: children){
			if(c == s.character){
				return true;
			}
		}
		return false;
	}
	
	public TreeNode getNode(char c){
		//gets the node which the character is apart of
		for(TreeNode s: children){
			if(c == s.character){
				return s;
			}
		}
		return null;
	}
	
	public List<String> autoComplete(String query){
		//the character 'c' is the next character in the word being searched. currentNode is the pointer
		//with a word like T R I E, if the current Node is T then 'c' is R
		List<String> results = new ArrayList<String>();
		TreeNode currentNode = null;
		for(int i = 0; i<query.length(); i++){
		//	boolean isWord = i == query.length()-1;
			char c = query.charAt(i);
			if(currentNode == null){
				if(contains(c)){
					currentNode = getNode(c);
				}else{
					return results;
				}
			}
			else if(currentNode.contains(c)){
				currentNode = currentNode.getNode(c);
			}else{
				return results;
			}
				
			}
		currentNode.findResults(results);
		List<String> induvidual = new ArrayList<String>();
		for(int i = 0; i<results.size(); i++){
			if(results.get(i).equals(query)){
				induvidual.add(results.get(i));
				return induvidual;
			}
		}
		return results;
		
		}
	}

