package com;


//package com.practice;

import java.io.*;
import java.util.*;

public class Dependency2 {

	static HashMap<String, Integer> indeces = new HashMap<String, Integer>();
	static ArrayList<Node> nodes = new ArrayList<Node>(); // array list that links the name of the node with the node object
	static int time;
	
	static ArrayList<String> visited = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		String a;

		//nodes.add(null);
		ArrayList<String> arr = new ArrayList<String>();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("input_d2.txt"));

		while ((a = br.readLine()) != null) {
			arr.add(a); // store the input in an array list
		}
		int number = (Integer.parseInt(arr.get(0)));// number of nodes

		boolean[][] graph = new boolean[number + 1][number + 1];// initialize
																// 2-d array to
																// store edge
																// list
		// int dependencies = Integer.parseInt(arr.get(number));
		for (int i = 1; i <= number; i++) {
			nodes.add(new Node(arr.get(i), i, "white"));
			indeces.put(arr.get(i), i);
		}

		for (int i = number + 2; i < arr.size(); i++)// Reading and Storing the
														// edges
		{
			String dependent = arr.get(i);
			String[] str = dependent.split("\\s+");
			int one = indeces.get(str[0]);// First node in the edge

			int two = indeces.get(str[1]);// Second node of the edge given

			graph[one][two] = true;// mark that edge value in matrix as true
		}

		dfs(number, nodes, arr, graph, false);
		br.close();

		//System.out.println("Finish time = " + nodes.get(1).finish);
		
		Collections.sort(nodes, Node.SortNode);
		/*for (int i = 1; i <= number; i++)
		{System.out.println(nodes.get(i-1).name + nodes.get(i-1).finish);}*/
		for (int i = 1; i <= number; i++)
		{
			nodes.get(i-1).color = "white";
			//nodes.get(i-1).number = i;
		}
		boolean isReverse = true;
		dfs(number, nodes, arr, graph, isReverse);
		ArrayList<String> newVisited = new ArrayList<String>();
		for(int i=visited.size()/2 ; i<visited.size(); i++)
		{newVisited.add(visited.get(i));}
		Collections.sort(newVisited, Collections.reverseOrder());
		for(int i=0 ; i<newVisited.size(); i++)
		{System.out.print(newVisited.get(i)+" ");}
	}

	static void dfs(int number, ArrayList<Node> nodes, ArrayList<String> arr, boolean[][] graph, boolean isReverse) {
		 time = 0;
		 int a = 0;
		for (int i = 1; i <= number; i++) {
			Node node = point(nodes, i - 1, number);//get ith node from the hashmap
						if (node.color.equals("white")) {
				recursiveDFS(node, number, graph, arr, isReverse, a);
				//System.out.println("");
				a = a+1;
			}
		}
	}

	static void recursiveDFS(Node node, int number, boolean[][] graph, ArrayList<String> arr, boolean isReverse, int a) {
		time = time + 1;
		node.discovery = time;
		node.color = "grey";
		int i = 0;;
		for (int j = 1; j <= number; j++) {
			boolean checkEdge = isReverse ? graph[j][node.number] : graph[node.number][j];
			if (checkEdge == true) {
				
				Node node2 = point(nodes, j - 1, number);
				if (node2.color.equals("white")) {
					node2.parent = node.name;
					i = i+ 1;
					recursiveDFS(node2, number, graph, arr, isReverse, a);
					}
				if(node2.color.equals("grey")) 
				{i = i + 1;
				
				}
			}
		}
		node.color = "black";
		if(i>0)
		{visited.add(node.name);}
		time = time + 1;
		node.finish = time;
		
	}
	static Node point(ArrayList<Node> nodes, int index, int number)
	{
		for(int i=0; i< nodes.size(); i++)
		{
			if(nodes.get(i).number == index+1)
			{
				return nodes.get(i);
			}
		}
		return null;
	}
}

class Node {
	String color;
	String name;
	int number;
	int discovery;
	int finish;
	String parent;

	public Node() {

	}

	public Node(String name, int number, String color) {
		this.name = name;
		this.number = number;
		this.color = color;
	}

	public static Comparator<Node> SortNode = new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o2.finish - o1.finish;
		}
	};
}
