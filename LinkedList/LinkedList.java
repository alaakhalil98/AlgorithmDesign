

import java.io.*;
import java.util.Scanner;

enum Symbols{
	Do,Re,Mi,AND,AT,PERCENT,Asymbolwithareallylongname,DOLLAR,Fa,One,Two,Three;
	
	public String toString() {
		switch(this) {
		case Do:
			return "Do";
		case Re:
			return "Re";
		case Mi:
			return "Mi";
		case AND:
			return "&";
		case AT:
			return "@";
		case PERCENT:
			return "%";
		case Asymbolwithareallylongname:
			return "Asymbolwithareallylongname";
		case DOLLAR:
			return "$";
		case Fa:
			return "Fa";
		case One:
			return "One";
		case Two:
			return "Two";
		case Three:
			return "Three";
		
		}
		return null;
	}
	public double todouble() {
		switch(this) {
		case Do:
			return 0.5;
		case Re:
			return 100.5;
		case Mi:
			return 1000.5;
		case AND:
			return 3.75;
		case AT:
			return 3.25;
		case PERCENT:
			return 1005000.5;
		case Asymbolwithareallylongname:
			return 55.5;
		case DOLLAR:
			return 20.5;
		case Fa:
			return 15.5;
		case One:
			return 103.25;
		case Two:
			return 103.5;
		case Three:
			return 103.75;
		}
		return 0;
	}
}
public class LinkedList{
	private Link head;
	private Link end;
	private Link current;
	private int length=0;
	private Scanner in;
	private FileWriter out;
	private boolean descending =true;
	private boolean first =true;
	private String output;
	
	public LinkedList(String in, String out) {
		current=head=new Link();
		this.output = out;
		read(in);
	}
	private void read(String input_file) throws IllegalArgumentException{
		    /*
		     * Opens the input file and reads from it,
		     * then writes to the output based on the given input file
		     */
		try {
			File file= new File(input_file);
			
			this.in = new Scanner(file);  
			
		    
			//Pattern pattern = Pattern.compile("push[(]([-0-9.]*)[)]");
			String input;
			int i;
			while (this.in.hasNextLine()) { 
				input =this.in.nextLine();
				if(input.matches(".*\\d.*")) {
					if(!input.equals("0") && input.charAt(0)=='0') {
						this.out = new FileWriter(output);
						this.write_Error("Input error.");
						this.out.close();
						this.in.close();
						throw new IllegalArgumentException("number leading with 0's");
					}
					try {
						i =Integer.parseInt(input);
						if(i<0) {
							try {
								this.out = new FileWriter(output);
								this.write_Error("Input error.");
								this.out.close();
								this.in.close();
								}
								catch(Exception e) {
									System.exit(1);
								}
							throw new IllegalArgumentException("number less than 0");
							}
						else {
							this.add(input, i);
						}
						}
						catch(Exception e) {
							this.out = new FileWriter(output);
							this.write_Error("Input error.");
							this.out.close();
							this.in.close();
							throw new IllegalArgumentException("number not valid");
						}
						
					}
				else if(!input.matches(".*\\d.*")) {
					int check =0;
					for(Symbols symbol:Symbols.values()) {
						if(input.equals(symbol.toString())) {  
							this.add(input, symbol.todouble());
							check =1;
						}
						
						}
					if(check==0) {
						try {
							this.out = new FileWriter(output);
							this.write_Error("Input error.");
							this.out.close();
							this.in.close();
							}
							catch(Exception e) {
								System.exit(1);
							}
						throw new IllegalArgumentException("input not valid");
					}
				}			
			}  
		
			this.in.close();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	private void write(String s) {
		/*
		 * used for writing to output file
		 */
		try {
			if(this.first==true) {
				this.out.write(s);
				
				this.first =false;
			}
			else {
				
				this.out.write("\r\n"+s);
			}
			
		}
		catch(Exception e) {
			
		}
	}
	private void write_Error(String s) {
		/*
		 * used for writing error messages and closing files,
		 * since we know program terminates after this is called
		 */
		
		try {
			
			if(this.first==true) {
				this.out.write(s);
				
				this.first =false;
			}
			else {
				
				this.out.write("\r\n"+s);
			}
			
			this.out.close();
			this.in.close();
		}
		catch(Exception e) {
			
		}
	}
	public int size(){
		return length;
	}
	
	private void prev() {
		//moves the current position back
		current=current.previous();
	}
	private void next() {
		current=current.next();
	}
	
	public boolean isEmpty() {
		//checks if the stack is empty returns boolean
		if (length ==0) {
			return true;
		}
		else return false;
	}
	
	private void add(String item,double value) {
		//used to append items to the end of list
		if(length!=0) {
			Link temp =new Link(item,value);
			temp.setNext(null);
			temp.setPrev(end);
			this.end.setNext(temp);
			this.end = temp;
			length ++;
		}
		else if(length==0) {
			Link temp =new Link(item,value);
			this.head =temp;
			this.end = head;
			length++;
		}
	}
	
	public void sortAscending() {
		int i;
		int j;
	
		
		for(i=length;i>0;i--) {
			for(j =1;j<i;j++) {
			
				if(this.get(j).getVal()>this.get(j+1).getVal()) {
					Link temp = new Link(this.get(j)); //temp was being changed because of pass by reference, created new link to counter this
					this.set(j, this.get(j+1));
					this.set(j+1, temp);
					
	
				}
			}
			
		}
	}
	public void sortDescending() {
		int i;
		int j;
	
		
		for(i=length;i>0;i--) {
			for(j =1;j<i;j++) {
			
				if(this.get(j).getVal()<this.get(j+1).getVal()) {
					Link temp = new Link(this.get(j)); //temp was being changed because of pass by reference, created new link to counter this
					this.set(j, this.get(j+1));
					this.set(j+1, temp);
					
	
				}
			}
			
		}
	}
	public void scanAndRemoveSix() {
		this.current=this.head;
	
		int [] sixLoc = new int[500];
		
		int j=0;
		for(int i=1;i<=this.length;i++) {
			if(current.getStr().equals("666")){
				sixLoc[j] =i;
				j++;
			}
			this.next();
		}
		int offset=0;
		for(int k=0;k<j;k++) {
			this.remove(sixLoc[k]-offset);
			offset++;
			
		}
		if(j!=0) {
			this.add("@", Symbols.AT.todouble());
			this.descending=false;
		}
		
	}
	public boolean sortTypeDescending() {
		return this.descending;
	}
	public void remove(int i) { 
		moveToPos(i);
		
		if(current ==head) {
			head = current.next();
			if(head!=null) {
				if(head.previous()!=null) {
					head.previous().setPrev(null);
				}
			}
			this.length--;
			return;
		}
		if(current.next()==null ) {
			current.previous().setNext(null);
			this.end=current.previous();
			this.length--;
		}
		else {
			Link temp = new Link(current);
			current.previous().setNext(current.next());
			current.next().setPrev(temp.previous());
			this.length--;
		}
		
		
	}
	public void set(int i, Link element) {
		moveToPos(i);
		current.setStr(element.getStr());
		current.setVal(element.getVal());
		
	}
	public Link get(int i) {
		moveToPos(i);
		return current;
	}
	public void moveToPos(int pos) throws ArrayIndexOutOfBoundsException{
		if(pos<0 ||pos>length) {
			throw new ArrayIndexOutOfBoundsException();
		}
		current = head;
		for(int i =1;i<pos;i++) {
			this.next();
		}
	}
	public void writeToFile() {
		this.current=this.head;
		try {
			this.out = new FileWriter(output);
			for(int i=1;i<=this.length;i++) {
				if(current ==null) {
					break;
				}
				this.write(current.getStr());
				this.next();
			}
			this.out.close();
			}
			catch(Exception e) {
				System.exit(1);
			}
	}
		
	
	public void printStack() {
		/*
		 *	this method is used for printing the stack on
		 *   the terminal
		 */
		this.current=this.head;
		for(int i=1;i<=this.length;i++) {
			if(current ==null) {
				break;
			}
			System.out.print(current.getStr()+" "+current.getVal());
			System.out.println();
			this.next();
		}
		System.out.println();
	}

	
	public static void main(String[] args) {
		LinkedList test= new LinkedList(args[0],args[1]);
		test.scanAndRemoveSix();
		if(test.sortTypeDescending()) {
			test.sortDescending();
		}
		else {
			test.sortAscending();
		}
		test.writeToFile();
		}
}
	