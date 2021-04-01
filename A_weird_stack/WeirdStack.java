
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;

public class WeirdStack{
	private Link head;
	private Link current;
	private int length=0;
	private Scanner in;
	private FileWriter out;
	private boolean first =true;
	
	public WeirdStack(String in, String out) {
		current=head=new Link();
		read_And_Write(in,out);
	}
	private void read_And_Write(String input, String output) throws IllegalArgumentException{
		    /*
		     * Opens the input file and reads from it,
		     * then writes to the output based on the given input file
		     */
		try {
			File file= new File(input);
			
			this.in = new Scanner(file);
			
			this.out = new FileWriter(output);
		    
			Pattern pattern = Pattern.compile("push[(]([-0-9.]*)[)]");
			String command;
			while (this.in.hasNextLine()) { 
				command =this.in.nextLine();
				
				Matcher matcher = pattern.matcher(command);
				if(command.equals("top()")) {
					this.top();
				}
				else if (command.equals("pop()")) {
					this.pop();
				}
				
				else if (matcher.matches()) {
					int i;
					try {
						i =Integer.parseInt(matcher.group(1));
						if(i<0) {
							this.write("Imput error.");
							this.out.close();
							this.in.close();
							throw new IllegalArgumentException();
						}
					}
					catch(Exception e) {
						this.write_Error("Imput error.");
						
						throw new IllegalArgumentException();
						
					}
					
					this.push(i);
				}
				else {
					this.write_Error("Input error.");
					
					throw new IllegalArgumentException();
				}
			} 
			
			this.out.close();
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
	
	public boolean isEmpty() {
		//checks if the stack is empty returns boolean
		if (length ==0) {
			return true;
		}
		else return false;
	}
	
	private void add(int value) {
		//used to append items to the front of the stack
		Link temp =new Link(value);
		temp.setPrev(head);
		this.head = temp;
		length ++;
	}
	
	public void push(int value) {
		/* Used to push a value on the the stack
		 * returns void but there are special cases in which 
		 * it will do something else and write to the output file
		 */
		if(value==0 &&length ==0) {
			this.add(value);
		}
		
		else if(value!=0 && value !=666 && value!=13) {
			if(value==3) {
				this.add(7);
			}
			else {
				this.add(value);
			}
		}
		else if(value==666) {
			for(int i=1;i<=3;i++) {
				this.add(value);
				
			}
		}
		else if(value==13) {
			int val;
			while(this.length!=0) {
				val = this.remove();
				this.write(String.valueOf(val));
			
			}
			this.add(13);
		}
	}
	private int remove() {
		//used in pop to remove a element from the list
		
		int removed_val = this.head.item();
		
		this.head = this.head.previous();
		length--;
		return removed_val;
	}
	public int pop() throws Exception {
		/*
		 *  pop writes directly to the output file in this class
		 * 	pop also return the value being popped unless
		 *  the value is a special case value.
		*/
		if (length==0) {
			
			this.write_Error("Error");
			System.exit(0);
			
			
		}
		int top_stack=this.head.item();
		if (top_stack==7) {
			
			this.write("7");
			
			return 7;
		}
		else if(top_stack==42) {
			while(!this.isEmpty()) {
				this.remove();
			}
			this.write("42");
			
			return 42;   
		}
		int check = this.remove();
		if(check==666) {
			if (length!=0) {
				this.remove();
			}
		}
		
		this.write(String.valueOf(check));
		
		return check;
		
	}
	public void top() {
		/*
		 * top writes directly to the output file in this class 
		*/
		if(length==0) {
			
			this.write("null");
	
			return;
		}
		else if(head.item()==666) {
			
			this.write("999");
	
			return;
		
		}
		else if(head.item()==319) {
			
			this.write("666");
		
			return;
		
		}
		else if(head.item()==7) { 
			this.remove();
			return;
		}
		else {
		
			this.write(String.valueOf(head.item()));
			return;
		}
	}
	public void printStack() {
		/*
		 *	this method is used for printing the stack on
		 *   the terminal
		 */
		this.current=this.head;
		for(int i=1;i<=this.length;i++) {
			System.out.print(current.item()+" ");
			this.prev();
		}
		System.out.println();
	}

	
	public static void main(String[] args) {
		WeirdStack test= new WeirdStack(args[0],args[1]);
		
		}
}
		
	

