package com;

import java.io.*;
import java.util.ArrayList;

public class kmp {
	public static int kmp1(String haystack,String needle){
		int[] pie = computePrefix(haystack,needle);
		int q = -1;
		for(int i=0; i<haystack.length(); i++){
			while(q>-1 && needle.charAt(q+1)!=haystack.charAt(i)){
				q = pie[q]-1;	
			}
			if(needle.charAt(q+1)==haystack.charAt(i)){
				q++;
			}
			if(q == needle.length()-1){
				return i-needle.length()+1;
			}
		}
		return -1;
	}

	public static int[] computePrefix(String haystack,String needle){
		int searchLength = needle.length();
		int k;
		int[] pie = new int[searchLength];
		pie[0] = 0;
		k = -1;
		for(int i=1; i<needle.length(); i++){
			while(k>-1 && needle.charAt(k+1)!= needle.charAt(i)){
				k = pie[k]-1;
			}
			if(needle.charAt(k+1)== needle.charAt(i)){
				k++;
			}
			pie[i] = k+1;
		}
		return pie;
	}
	public static int naive(String haystack, String needle){
		int index = 0;
		for(int i=0; i<haystack.length(); i++){
			index = i;
			for(int j=0; j<needle.length(); j++){				
				if(haystack.charAt(index) == needle.charAt(j)){
					index++;
					if(j == needle.length()-1){
						return i;
					}
				}
				else{
					break;
				}
			}
		}
		return -1;
	}
	public static void main(String [] args) throws IOException{
		String a;
		long t1, t2;
	    ArrayList<String> arr = new ArrayList<String>();
		if(args.length == 0){
			/*String one = "aaab";
			String two = "ab";*/
			String one = "";
			for(int i=0; i<=50000; i++){
				one = one + "aaaaaaaaa";
			}
			one+="e";
			String two = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaae";
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+naive(one,two));
			t2 = System.currentTimeMillis();
			System.out.println("naive search time: "+(t2-t1));
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+one.indexOf(two));
			t2 = System.currentTimeMillis();
			System.out.println("standard search time: "+(t2-t1));
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+kmp1(one, two));
			t2 = System.currentTimeMillis();
			System.out.println("kmp search time: "+(t2-t1));
		}
		else{
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			for(int i=0; i<2; i++) 
			   {
				a=br.readLine();
				   arr.add(a);  
			   }
			String one = arr.get(0);
			String two = arr.get(1);
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+naive(one,two));
			t2 = System.currentTimeMillis();
			System.out.println("naive search time: "+(t2-t1));
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+one.indexOf(two));
			t2 = System.currentTimeMillis();
			System.out.println("standard search time: "+(t2-t1));
			t1 = System.currentTimeMillis();
			System.out.println("found at: "+kmp1(one, two));
			t2 = System.currentTimeMillis();
			System.out.println("kmp search time: "+(t2-t1));
			   }
			   
			
		
	}
}
