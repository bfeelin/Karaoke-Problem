import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Karaoke {
	 static int winning = -1;
	public static void main(String[] args) throws IOException
	{
		getInput(args);
	}
	public static void getInput(String[] args) throws IOException
	{
		String line;
		BufferedReader abc = new BufferedReader(new FileReader(args[0]));
		ArrayList<String> list = new ArrayList<String>();
		int noLines = 0;
		
		String nums[];
		
		while((!(line = abc.readLine()).equals("0"))) {			// add all input to array list
		    list.add(line);
		    //System.out.println(line);
		}
		abc.close();
		
		for(int j = 0; j < list.size(); j++)
		{
			
			noLines=  Integer.parseInt(list.get(j));
			int[][] input = new int[noLines][4];
			for(int i = 0; i < noLines; i ++, j++)				//fill array with each set of groups
			{
				nums = list.get(j+1).split(" ");
				input[i][0] = Integer.parseInt(nums[0]);
				input[i][1] = Integer.parseInt(nums[1]);
				input[i][2] = Integer.parseInt(nums[2]);
				input[i][3] = Integer.parseInt(nums[3]);
			}

			findBest(input);
			System.out.println(winning);
			winning = -1;
			 
		}
	}
	 public static void findBest(int[][] input)
	 {
		 
		 for(int i = input.length-1 ; i >= 0; i--)
		 {
			int holding = i;
			int holding2 = -1;
			int holding3 = -1;
			int curTotal = input[holding][3];
			 for(int j = input.length-1 ; j >= 0; j--)
			 {
				 	if(j == holding)			// skip holding group
				 		continue;
				 	if(isComp(input[holding][0], input[holding][1], input[holding][2], input[j][0], input[j][1], input[j][2]) == false)
				 		continue;
		
				 	holding2 = j;  
				 	curTotal += input[holding2][3];
				 	
				 	ArrayList<Integer> set = new ArrayList<Integer>();
				 	for(int k = 0; k < 3; k++)								// add current set to an array list
				 	{
				 		set.add(input[holding][k]);
				 		set.add(input[holding2][k]);
				 	}
				 	
				 	ArrayList<Integer> missing = new ArrayList<Integer>();
				 	Collections.sort(set);
				 	set.add(10);
				 	for(int p = 1; p < 10; p++)								// compare current set (2 holding groups) to 1-10 to find the compatible group
				 	{
				 		if(set.get(p-1) != p)
				 		{
				 			missing.add(p);
				 			set.add(p-1, p);
				 		}	 			
				 	}
				 	
				 	int holding3Val = -1;
				 	
					 	for(int e = input.length-1; e >= 0; e--)
					 	{
					 		if(e == holding || e == holding2)			// skip holding groups
								continue;
						 	if(isMatch(missing.get(0), missing.get(1), missing.get(2), input[e][0], input[e][1], input[e][2]) == false) 
						 		continue;
						 		
							holding3 = e;
							 	
						 	if(input[holding3][3] > holding3Val)					// if this group has a better score than current best
							 	holding3Val = input[holding3][3];			 			// set current group to best
					 	}
					 	
				 	missing.clear();
				 	set.clear();
				 	
				 	if(holding3Val == -1)		// if no matches
				 		curTotal = 0;
				 	else
				 	{			 					 	
				 		curTotal += holding3Val;
						 if(curTotal > winning)			// if better than current winner
						 	winning = curTotal;
						 curTotal = 0;
				 	}	
			 }
		}
	 }
	
	 public static Boolean isComp(int a, int b, int c, int a2, int b2, int c2)
	 {
		 if(a != a2 && a != b2 && a != c2 && b != a2 && b != b2 && b != c2 && c != a2 && c != b2 && c != c2)
			 return true;
		 else return false;
	 }
	 public static Boolean isMatch(int a, int b, int c, int a2, int b2, int c2)
	 {
		 if(a == a2 &  b == b2 & c == c2)
			 return true;
		 else return false;
	 }
	 
}
