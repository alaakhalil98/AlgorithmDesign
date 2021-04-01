
public class Link {
	private Link prev;
	
	private int item;
	
	public Link(int val){
		this.item=val;
	}
	public Link() {
	}
	public void setPrev(Link e) {
		this.prev= e;
	}
	public Link previous() {
		return prev;
	}
	public int item() {
		return item;
	}
}
