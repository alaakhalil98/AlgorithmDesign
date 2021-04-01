import java.io.File;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree {
	private TreeNode root = new TreeNode(1,"root"); //system starts at height = 1;
	private int height = 1; //total height is 1
	
	private String tree = "";
	private int nodes = 1;
	private TreeNode[] matches = new TreeNode[500];
	private int numEntries = 0;
	private TreeNode right;
	private TreeNode left;
	private boolean foundLeft =false;
	
	private Scanner in;
	private FileWriter out;
	private boolean first =true;
	
	public Tree(String in,String out) {
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
	    
		Pattern patternDel = Pattern.compile("Del([L|M|R]{1})[(]([\\d\\w\\S]*)[)]$");
		Pattern patternAdd = Pattern.compile("Add([L|M|R]{1})[(](?=.*,{1})([\\d\\w\\S]*)[)]$");
		Pattern patternExc = Pattern.compile("Exchange[(](?=.*,{1})([\\d\\w\\S]*)[)]$");
		String command;
		while (this.in.hasNextLine()) { 
			command =this.in.nextLine();
			
			
			
			Matcher matcherDel = patternDel.matcher(command);
			Matcher matcherAdd = patternAdd.matcher(command);
			Matcher matcherExc = patternExc.matcher(command);
			if(command.equals("Print()")) {
				this.fillTree();
				write(tree);
		
			}
			else if (matcherDel.matches()) {
				
				//DEL OPERATIONS
				if(matcherDel.group(1).equals("R")) {
					this.delR(matcherDel.group(2));
				}
				else if(matcherDel.group(1).equals("L")) {
					this.delL(matcherDel.group(2));
				}
				else if(matcherDel.group(1).equals("M")) {
					this.delM(matcherDel.group(2));
				}
			}
			
			else if (matcherAdd.matches()) {
				//ADD OPERATIONS
				String a;
				String b;
				String temp = matcherAdd.group(2);
				int lastComma = 0;
				boolean found = false; 
				for(int i =0;i<temp.length();i++) {
					if(found==true && temp.charAt(i)!=',') {
						lastComma = i-1;
						break;
					}
					if(temp.charAt(i)==',') {
						found = true;
					}
				}
				
				if(lastComma!=0 && lastComma+1<temp.length()) {
					
					a = temp.substring(0, lastComma);
					
					b = temp.substring(lastComma+1);
					if(matcherAdd.group(1).equals("R")) {
						
						this.addR(a,b);
					}
					else if(matcherAdd.group(1).equals("L")) {
						
						this.addL(a,b);
					}
					else if(matcherAdd.group(1).equals("M")) {
					
						this.addM(a,b);
					}
				}
				else {
					this.write_Error("Input error.");
					throw new IllegalArgumentException();
				}
			}
			else if (matcherExc.matches()) {
				//Exc OPERATIONS
				String a;
				String b;
				String temp = matcherExc.group(1);
				int lastComma = 0;
				boolean found = false; 
				for(int i =0;i<temp.length();i++) {
					if(found==true && temp.charAt(i)!=',') {
						lastComma = i-1;
						break;
					}
					if(temp.charAt(i)==',') {
						found = true;
					}
				}
				if(lastComma!=0 && lastComma+1<temp.length()) {
					a = temp.substring(0, lastComma);
					b = temp.substring(lastComma+1);
					this.exchange(a,b);
				}
				else {
					this.write_Error("Input error.");
					throw new IllegalArgumentException();
				}
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
	public void searchTreeReplace(TreeNode n, String search, String replacement, boolean append) {
		
		if(n == null) {
			return;
		}
		if(n.getStr().equals(search)) {
			if(append ==true) {
				String temp = n.getStr();
				temp = temp+replacement;
				n.setStr(temp);
			}
			else {
				n.setStr(replacement);
			}
		}
		searchTreeReplace(n.getLeft(), search, replacement,append);
		searchTreeReplace(n.getMid(),search, replacement,append);
		searchTreeReplace(n.getRight(),search, replacement,append);
		return;
		
	}
	public void searchTree(TreeNode n, String search) {
		
		if(n == null) {
			return;
		}
		if(n.getStr().equals(search)) {
			matches[numEntries] = n;
			numEntries++;
		}
		searchTree(n.getLeft(), search);
		searchTree(n.getMid(),search);
		searchTree(n.getRight(),search);
		return;
		
	}
	
	public TreeNode getRoot() {
		return this.root;
	}
	public String getTree() {
		return this.tree;
	}
	public void addL(String search, String insert) {
		searchTree(root,search);
		if(matches[0]==null) {
		
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
	
		getLevelRightValue(root,max,search);
		char firstLetter = insert.charAt(0);
		if(firstLetter == '$') {
			insert = insert.substring(1);
		}
		TreeNode newInsert = new TreeNode(right.getHeight()+1,insert);
		
		
		if(right.getLeft()!=null && firstLetter=='$') {
			right.getLeft().setStr(insert);
		}
		else if(right.getLeft()==null) {
			right.setLeft(newInsert);
			
		}
		else{
			this.write("Add operation not possible.");
		}
			
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
		
	}
	
	public void addM(String search, String insert) {
		searchTree(root,search);
		if(matches[0]==null) {
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
	
		getLevelRightValue(root,max,search);
		char firstLetter = insert.charAt(0);
		if(firstLetter == '$') {
			insert = insert.substring(1);
		}
		TreeNode newInsert = new TreeNode(right.getHeight()+1,insert);
		
		
		if(right.getMid()!=null && firstLetter=='$') {
			right.getMid().setStr(insert);
		}
		else if(right.getMid()==null) {
			right.setMid(newInsert);
		}
		else{
			this.write("Add operation not possible.");
		}
			
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
	}
	public void addR(String search, String insert) {
		searchTree(root,search);
		if(matches[0]==null) {
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
	
		getLevelRightValue(root,max,search);
		char firstLetter = insert.charAt(0);
		if(firstLetter == '$') {
			insert = insert.substring(1);
		}
		TreeNode newInsert = new TreeNode(right.getHeight()+1,insert);
		
		
		if(right.getRight()!=null && firstLetter=='$') {
			right.getRight().setStr(insert);
		}
		else if(right.getRight()==null) {
			right.setRight(newInsert);
		}
		else{
			this.write("Add operation not possible.");
		}
			
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
	}
	
	public void delL(String search) {
		searchTree(root,search);
		if(matches[0]==null) {
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
		foundLeft = false;
		getLevelLeftValue(root,max,search);
		
		if(left.getLeft()!=null) {
			left.setLeft(null);
		
		}
		foundLeft = false;	
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
	}
	
	public void delM(String search) {
		searchTree(root,search);
		if(matches[0]==null) {
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
		foundLeft = false;
		getLevelLeftValue(root,max,search);
		
		if(left.getMid()!=null) {
			left.setMid(null);
		
		}
		foundLeft = false;	
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
	}
	public void delR(String search) {
		searchTree(root,search);
		if(matches[0]==null) {
			return;
		}
		
		int k =0;
		int max = 0;
	
		while(matches[k]!=null) {
			if(matches[k].getHeight()>max) {
				max = matches[k].getHeight();
			}
			k++;
		
		}
		foundLeft = false;
		getLevelLeftValue(root,max,search);
		
		if(left.getRight()!=null) {
			left.setRight(null);
		
		}
		foundLeft = false;	
		numEntries = 0;
		nodes++;
		
		for(int i = 0;i<matches.length;i++) {
			matches[i]=null;
		}
	}
	
	public void exchange(String search, String replacement) {
		char firstLetter = replacement.charAt(0);
		boolean append = false;
		if(firstLetter == '$') {
			replacement = replacement.substring(1);
			append =true;
		}
		
		searchTreeReplace(root,search,replacement,append);
	}
	public void fillTree() {
		int height = height(root);
		tree = "";
		for(int i=1;i<=height;i++) {
			appendLevel(root, i);
			tree = tree.substring(0,tree.length()-3);
			if(i+1<=height) {
				tree = tree + "\n";
			}
		}
		
	}
	public int height(TreeNode root)
	    {
	        if (root == null)
	           return 0;
	        else
	        {
	            /* compute  height of each subtree */
	            int lheight = height(root.getLeft());
	            int mheight = height(root.getMid());
	            int rheight = height(root.getRight());
	            height = 1 + Math.max(Math.max(lheight,mheight),rheight);
	            return height;      
	        }
	    }
	public void appendLevel(TreeNode root, int level) {
		if (root == null)
            return;
        if (level == 1) {
        	tree = tree + root.getStr() + " , ";
        
        }
        else if (level > 1)
        {
            appendLevel(root.getLeft(), level-1);
            appendLevel(root.getMid(), level-1);
            appendLevel(root.getRight(), level-1);
        }
        //return;
	}
	public void getLevelRightValue(TreeNode root, int level, String match) {
		
		if (root == null) {
            return;
		}
        if (level == 1) { 	
        	if(root.getStr().equals(match)) {
        		right = root;
		
        	}
        	
        }
        else if (level > 1)
        {
        	getLevelRightValue(root.getLeft(), level-1,match );
        	getLevelRightValue(root.getMid(), level-1,match);
        	getLevelRightValue(root.getRight(), level-1,match);
        }
        
	}
	public void getLevelLeftValue(TreeNode root, int level, String match) {
		if(foundLeft == true) {
			return;
		}
		if (root == null) {
            return;
		}
        if (level == 1) { 	
        	if(root.getStr().equals(match)) {
        		left = root;
        		foundLeft = true;
        		return;
        	}
        	
        }
        else if (level > 1)
        {
        	
        	getLevelLeftValue(root.getLeft(), level-1,match );
        	if(foundLeft == true) {
    			return;
    		}
        	getLevelLeftValue(root.getMid(), level-1,match);
        	if(foundLeft == true) {
    			return;
    		}
        	getLevelLeftValue(root.getRight(), level-1,match);
        	if(foundLeft == true) {
    			return;
    		}
        }
        
	}
	public static void main(String[] args) {
		Tree test = new Tree(args[0],args[1]);

		
	}

}
