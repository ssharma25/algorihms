package com;

import java.io.BufferedReader;
import java.io.*;
//import java.io.IOException;
import java.util.*;

public class maxflow {

	static int maxFlow = 0;
	
	public static int[][] buildGraph(ArrayList<String> input, int vertices, int edges) {
		int[][] graph = new int[vertices][vertices];
		for (int i = 0; i <= edges - 1; i++) {
			String[] str = input.get(i).split("\\s+");// reading input and
														// bulding the adjacency
														// matrix
			int one = Integer.parseInt(str[0]);
			int two = Integer.parseInt(str[1]);
			int weight = Integer.parseInt(str[2]);
			graph[one][two] = weight;
			graph[two][one] = 0;
		}
		return graph;
	}

	public static ArrayList<String> printEdges(int[][] graph, ArrayList<String> input)
	{
		ArrayList<String> output = new ArrayList<String>();
		int weight;
		for(int i=0; i<input.size(); i++)
		{
			String[] str = input.get(i).split("\\s+");
			int one = Integer.parseInt(str[0]);
			int two = Integer.parseInt(str[1]);
			weight = graph[two][one];
			String line = Integer.toString(one)+" "+Integer.toString(two)+" "+Integer.toString(weight);
			output.add(line);
		}
		return output;
	}
	
	public static ArrayList<Nodes> bFS(int[][] graph, int vertices) {
		Nodes vertex;
		Queue<Nodes> queue = new LinkedList();
		// Nodes source = new Nodes(0);
		ArrayList<Nodes> path = new ArrayList<Nodes>();

		for (int i = 0; i < vertices; i++) {
			path.add(new Nodes(i));//creating a collection of nodes in the graph
		}

		queue.add(path.get(0));//add source to the queue
		// path.add(source);

		while (!queue.isEmpty()) {
			vertex = queue.remove();// removes one vertex from the queue and
									// processes it
			for (int i = 0; i < vertices; i++) {
				if ((graph[vertex.index][i] != 0) && !(path.get(i).visited))// finding adjacent edges that have not been visited
			     {
					path.get(i).parent = vertex.index;
					if (i == 1)// if sink vertex is reached add it to path and break			
					{
						return path;//return the collection of nodes that gives the current path
					}
					queue.add(path.get(i));
				}
			}
			vertex.visited = true;
		}
		return null;
	}

	public static int[][] changeGraph(int[][] graph, ArrayList<Nodes> path)
	{
		Integer one = 1;
		int critical = 9999;
		Integer parentNode = path.get(one).parent;
		while (parentNode != null && parentNode >= 0)
		{
			if (critical > graph[path.get(one).parent][one])
			{
				critical = graph[path.get(one).parent][one];	
			}
			one = path.get(one).parent;
			parentNode = path.get(one).parent;
		}
		maxFlow += critical;
		one = 1;
		parentNode = path.get(one).parent;

		while (parentNode != null && parentNode >= 0) {

			graph[path.get(one).parent][one] -= critical;
			graph[one][path.get(one).parent] += critical;

			one = path.get(one).parent;

			parentNode = path.get(one).parent;

		}

		return graph;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> input = new ArrayList<String>();
		String com2;
		int vertices;
		int edges;
		int[][] graph;
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));

		ArrayList<Nodes> path = new ArrayList<Nodes>();
		BufferedReader br = new BufferedReader(new FileReader("input_f3.txt"));

		while ((com2 = br.readLine()) != null) {
			input.add(com2);
		}
		br.close();
		String[] str = input.get(0).split("\\s+");
		vertices = Integer.parseInt(str[0]);
		edges = Integer.parseInt(str[1]);
		input.remove(0);
		graph = buildGraph(input, vertices, edges);

		path = bFS(graph, vertices);// Passing the adjacency matrix to the bfs
									// method that returns a bfs path from
									// source to sink

		while (path != null) {
			graph = changeGraph(graph, path);
			
			/*for (int i = 0; i < vertices; i++) {
				for (int j = 0; j < vertices; j++) {
					System.out.print(graph[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();*/
			path = bFS(graph, vertices);
		}

		System.out. println(maxFlow);
		ArrayList<String> output = printEdges(graph, input);
		for(String i:output)
		{
			System.out.println(i);
		}
		
		/*
		 * for(int i=0; i<vertices; i++) { for(int j=0; j<vertices; j++) {
		 * System.out.print(graph[i][j]+ "\t"); } System.out.println(); }
		 */
	}
}

class Nodes {
	Nodes() {
	}
	Nodes(int index) {
		this.index = index;

		this.parent = null;
	}
	int index;
	Integer parent;
	boolean visited;
}
