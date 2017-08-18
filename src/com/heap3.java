package com;

import java.io.*;
import java.util.*;


class heap3
{
	
  int parent(int n)
   {
      n=n+2;
	  if(n>0)
	  {
      n=(int)(Math.ceil(n/3));
	  n=n-1;
      return n;
	  }
	  else
	  {
	  return 0;
	  }
   }
  
   ArrayList<Integer> child(ArrayList<Integer> a, int n)
    {
      ArrayList<Integer> children= new ArrayList<Integer>();
      n=n+1;
	  int maxleaf = a.size()-1;
		  if(3*n <= maxleaf)
		  {
			 children.add(3*n-2);
      		 children.add(3*n-1);
		     children.add(3*n);
		  }			
		  else if(3*n-1==maxleaf)
		  {
			 children.add(3*n-2);
			 children.add(3*n-1);
		  }
		  else if(3*n-2 == maxleaf)
		  {
			 children.add(3*n-1);
		  }
        return children;
	  }
    
   void up(ArrayList<Integer> a, int s)
    {
	 while(s>0)
	 {
      if(a.get(s)<a.get(parent(s)))
        {
          int temp= a.get(s);
          a.set(s, a.get(parent(s)));
          a.set(parent(s), temp);
        }
		s=parent(s);
		up(a, s);
	 }
	}
   void down(ArrayList<Integer> a, int i)
    {
		while(i<(a.size()-1)/3)
	  {
	  int minindex=0;
	  ArrayList<Integer> b= new ArrayList<Integer>();
	  b=child(a, i);
	  int no= b.size();
	  if(no == 3)
	  {
	   if((a.get(b.get(0))<a.get(b.get(1))) && (a.get(b.get(0))<a.get(b.get(2))))
	    {
		 minindex=b.get(0);
	    }
	    else if ((a.get(b.get(1))<a.get(b.get(0))) && (a.get(b.get(1))<a.get(b.get(2))))
	    {
		 minindex= b.get(1);
	    }
	    else
	    {
	    	minindex=b.get(2);
	   }}
	  else if(no == 2)
	  {
		  if((a.get(b.get(0))<a.get(b.get(1))))
		  {
			  minindex=b.get(0);
		  }
		  else
		  {
			  minindex=b.get(1);
		  }
	  }
	  else if(no == 1)
	  {  minindex=b.get(0);
	  }
	  //else{
		//  return; 
	  //}
	   
		  //System.out.println(minindex);
	      if(a.get(i)>a.get(minindex))
		  {
		    int temp= a.get(i);
          a.set(i, a.get(minindex));
          a.set(minindex, temp);
		  i= minindex;
		  down(a, minindex );
		  }
	  }
	}   
   void insert(ArrayList<Integer> a, int x)
    {
      a.add(x);
      int i= a.size()-1;
	  up(a, i);
	  System.out.println(x);
    } 
   int removemin(ArrayList<Integer> a)
   {
      int min =a.get(0);
	  a.set(0, a.get(a.size()-1));
	  a.remove(a.size()-1);
	  down(a, 0);
	  return min;
   }
  
   public static void main(String []args) throws IOException
   {
	   heap3 th= new heap3();
    ArrayList<Integer> a= new ArrayList<Integer>();
     //Scanner sc=new Scanner(System.in);
	//System.out.println("give the name of file with path");
	//String s= sc.nextLine();
	ArrayList<String> methodNames= new ArrayList<String>();
	String com2;
	
    //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	BufferedReader br = new BufferedReader(new FileReader("input_h1000000.txt"));
	     
	while ((com2=br.readLine())!= null) 
	  {
	     methodNames.add(com2);  
	  }
	
	
	for(int i=0; i<=methodNames.size()-1; i++)
	{
	  String ss= methodNames.get(i);
	  String[] str= ss.split("\\s+");
	 
	  if(str[0].equals("remove"))
	  {
	    System.out.println(th.removemin(a));
			    System.out.println("removed");
	  }
	  else
	  {
		int x= Integer.parseInt(str[1]);
	    th.insert(a, x);
	  }
	}
   }
} 
