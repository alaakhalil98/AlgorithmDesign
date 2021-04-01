//this is the new updated Node class for assignment3
public class TreeNode {
	
	private TreeNode left;
	private TreeNode mid;
	private TreeNode right;
	
		
	private int height;
	private String value;
		
	public TreeNode(int height, String value){
			this.height = height;
			this.value = value;
			
			this.left =null;
			this.right =null;
			this.mid =null;
	}

	public TreeNode(String a){
			this.value =a;
	}
	public TreeNode(){
		
	}
	public void setRight(TreeNode e) {
		this.right= e;
	}
	public void setLeft(TreeNode e) {
		this.left= e;
	}
	public void setMid(TreeNode e) {
		this.mid= e;
	}
	public TreeNode getRight() {
		return this.right;
	}
	public TreeNode getLeft() {
		return this.left;
	}
	public TreeNode getMid() {
		return this.mid;
	}
	public String getStr() {
		return value;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int h) {
		this.height = h;
	}
	public void setStr(String str) {
		this.value =str;
	}
}

