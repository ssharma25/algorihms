package com;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class allpairs {
	static LinkedHashMap<String, Integer> path = new LinkedHashMap<String, Integer>();

	public static void buildGraph(String[] vertices, int[][] distance, int[][] graph) {
		int n = vertices.length;
		graph = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				graph[i][j] = comparing(vertices[i], vertices[j]);
				distance[i][j] = graph[i][j];

			}

		}

	}

	public static int comparing(String one, String two) {
		int nodiff = 0;// number of differences between the two strings.
		int diff = 0;
		if (one.length() != two.length()) {
			return 999;
		} else {
			for (int i = 0; i < one.length(); i++) {
				if (one.charAt(i) != two.charAt(i)) {
					diff = Math.abs(two.charAt(i) - one.charAt(i));
					nodiff++;
					if (nodiff >= 2) {
						return 999;
					}
				}
			}
		}
		return diff;
	}

	public static LinkedHashMap<String, Integer> findpath(String one, String two, int numVertex, int[][] distance,
			int[][] graph, HashMap<String, Integer> map, int[][] pathFind, ArrayList<String> input) {

		int k;
		int source = map.get(one);// i is source
		int destination = map.get(two);
		path.put(input.get(source + 1), 1);
		// System.out.print(" "+input.get(source+1));
		k = pathFind[map.get(one)][map.get(two)];
		if (k > 0) {
			findpath(one, input.get(k), numVertex, distance, graph, map, pathFind, input);
			findpath(input.get(k), two, numVertex, distance, graph, map, pathFind, input);

		}

		path.put(input.get(destination + 1), 1);

		// System.out.print(" "+input.get(destination+1));

		return path;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> input = new ArrayList<String>();
		String com2;
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		BufferedReader br = new BufferedReader(new FileReader("input_ap3.txt"));

		while ((com2 = br.readLine()) != null) {
			input.add(com2);
		}
		br.close();
		int numVertex = Integer.parseInt(input.get(0));// getting number of
														// vertices from first
														// value of input

		String[] vertices = new String[numVertex];
		for (int i = 1; i <= numVertex; i++) {
			vertices[i - 1] = input.get(i);
			map.put(input.get(i), i - 1);
		}
		int[][] distance = new int[numVertex][numVertex];
		int[][] graph = new int[numVertex][numVertex];
		buildGraph(vertices, distance, graph); // sending the vertices array to
												// the buildGraph method
		double avgReachable = 0;
		DecimalFormat dec = new DecimalFormat(".##");
		int[][] pathFind = new int[numVertex][numVertex];

		for (int k = 0; k < numVertex; k++) {
			for (int i = 0; i < numVertex; i++) {
				for (int j = 0; j < numVertex; j++) {
					if ((distance[i][k] + distance[k][j]) < distance[i][j]) {
						distance[i][j] = distance[i][k] + distance[k][j];
						pathFind[i][j] = k + 1;
					}
				}
			}
		}

		for (int i = 0; i < numVertex; i++) {

			for (int j = 0; j < numVertex; j++) {
				if (distance[i][j] < 999) {
					avgReachable++;
				}
			}
		}
		avgReachable = avgReachable / numVertex;
		Math.round(avgReachable);
		System.out.println(dec.format(avgReachable));

		/*for (int i = 0; i < numVertex; i++) {
			for (int j = 0; j < numVertex; j++) {

				System.out.print(distance[i][j] + "\t");
			}
			System.out.println();
		}

		System.out.println();

		for (int i = 0; i < numVertex; i++) {
			for (int j = 0; j < numVertex; j++) {

				System.out.print(pathFind[i][j] + "\t");
			}
			System.out.println();
		}*/

		HashMap<String, Integer> path ;

				
		for (int i = numVertex + 2; i < input.size(); i++) {
			String[] str = input.get(i).split("\\s+");
			if (distance[map.get(str[0])][map.get(str[1])] < 999) {
				System.out.print(distance[map.get(str[0])][map.get(str[1])]);
				path = findpath(str[0], str[1], numVertex, distance, graph, map, pathFind, input);
				
				Set keys = path.keySet();
				for (Iterator it = keys.iterator(); it.hasNext();) {
					String key = (String) it.next();
					System.out.print(" " + key);
				}
				path.clear();
				System.out.println();
			} else {
				System.out.println(str[0] + " " + str[1] + " not reachable");
			}
		}

	}
}