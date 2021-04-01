//this is the new updated link class for assignment2
public class Link {
	private Link prev;
	private Link next;
	
	private double item_value;
	private String item;
	
	public Link(String item, double item_value){
		this.item_value=item_value;
		this.item = item;
	}
	public Link(Link e) {
		this.item = e.getStr();
		this.item_value = e.getVal();
		this.prev =e.previous();
		this.next = e.next();
	}
	public Link(){
		
	}
	public void setPrev(Link e) {
		this.prev= e;
	}
	public void setNext(Link e) {
		this.next= e;
	}
	public Link next() {
		return next;
	}
	public Link previous() {
		return prev;
	}
	public String getStr() {
		return item;
	}
	public double getVal() {
		return item_value;
	}
	public void setStr(String str) {
		this.item = str;
	}
	public void setVal(double val) {
		this.item_value =val;
	}
}
