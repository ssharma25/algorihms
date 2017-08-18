package newPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

//import com.Node;

public class StronglyConnectedComponents {
	public static void main(String args[]) throws IOException {
		String a;

		//nodes.add(null);
		ArrayList<String> arr = new ArrayList<String>();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("input_d1.txt"));

		while ((a = br.readLine()) != null) {
			arr.add(a); // store the input in an array list
		}
		int number = (Integer.parseInt(arr.get(0)));// number of nodes

		Graph g = new Graph(number);
		for (int i = 1; i <= number; i++){
			g.addVertex(arr.get(i));
		}
		for (int i = number + 2; i < arr.size(); i++)// Reading and Storing the
			// edges
			{
			String dependent = arr.get(i);
			String[] str = dependent.split("\\s+");
			String one = str[0];// First node in the edge
			
			String two = str[1];// Second node of the edge given
			g.addEdge(one, two);
			
			}
		g.components();
		br.close();
	}
}

class Vertex{
	String label;
	boolean visited;
	Vertex(String label){
		this.label = label;
		visited = false;
	}
}

class Graph{
	public static ArrayList<String> sccvertices = new ArrayList<String>();
	
	private Vertex vertexList[];
	private int adjMatrix[][];
	private int vertexCount;
	Stack s = new Stack();
	static boolean reverseGraph = false;
	int color[];
	Graph(int size){
		adjMatrix = new int[size][size];
		vertexCount = 0;
		color = new int[size];
		vertexList = new Vertex[size];
		for(int i=0; i<size; i++){
			for (int j = 0; j < size; j++) {
				adjMatrix[i][j] = 0;
			}
		}
	}
	public void addVertex(String label){
		vertexList[vertexCount++] = new Vertex(label);
	}

	public void addEdge(String start, String end){
		int s = findIndex(start);
		int e = findIndex(end);
		//System.out.println(s + "test" + e);
		if(s != -1 && e != -1) {
			adjMatrix[s][e] = 1;
			//adjMatrix[e][s] = 1;
		}
	}
	private int findIndex(String start) {
		// TODO Auto-generated method stub
		for (int i = 0; i < vertexList.length; i++) {
			if(start.equals(vertexList[i].label)){
				return i;
			}
		}
		return -1;
	}
	/*public void displayGraph(){		
		for (int i = 0; i < vertexList.length; i++) {
			for (int j = 0; j < vertexList.length; j++) {
				System.out.println(adjMatrix[i][j]);
			}
			System.out.println();
		}
	}*/

	public void dfs(){
		for (int i=0; i< vertexList.length; i++) {
			color[i] = 0;
		}
		for (int i=0; i< vertexList.length; i++) {
			if(color[i] == 0){
				dfsVisit(adjMatrix, i);
			}
		}
		//System.out.println();
	}
	private void dfsVisit(int [][]adjMatrix, int vertexNumber) {
		// TODO Auto-generated method stub
		color[vertexNumber] = 1;

		//System.out.print(vertexList[vertexNumber].label);
		for (int i=0; i< vertexList.length; i++) {
			if(adjMatrix[vertexNumber][i] == 1){
				if(color[i]== 0){
					dfsVisit(adjMatrix, i);
				}

			}
		}
		//System.out.print(vertexList[vertexNumber].label);
		//if(reverseGraph == false){
			s.push(vertexNumber);
	//	}
		color[vertexNumber] = 2;
	}
	private void dfsVisitReverse(int [][]adjMatrix, int vertexNumber, int[] visited) {
		// TODO Auto-generated method stub
		visited[vertexNumber] = 1;

		//System.out.print(vertexList[vertexNumber].label+" ");
		sccvertices.add(vertexList[vertexNumber].label);
		for (int i=0; i< vertexList.length; i++) {
			if(adjMatrix[vertexNumber][i] == 1){
				if(visited[i]== 0){
					dfsVisitReverse(adjMatrix, i, visited);
				}

			}
		}
		/*//System.out.print(vertexList[vertexNumber].label);
		if(reverseGraph == false){
			s.push(vertexNumber);
		}*/
		visited[vertexNumber] = 1;
	}


	public void components(){
		dfs();
		int[] visited = new int[vertexCount];
		Graph gr = invertGraph();
		reverseGraph = true;
		for (int i = 0; i < vertexCount; i++) {
			visited[i] = 0;
		}
		while (s.empty() == false)
		{
			int v = (int) s.pop();

			// Print Strongly connected component of the popped vertex
			if (visited[v]== 0)
			{
				gr.dfsVisitReverse(gr.adjMatrix,v, visited);
				if(sccvertices.size()>1)
				{
					for(int j=0; j<sccvertices.size(); j++)
					{
						System.out.print(sccvertices.get(j)+" ");
					}
					System.out.println();
				}
				
				sccvertices.clear();
			}
		}
	}
	private Graph invertGraph() {
		Graph gr = new Graph(vertexCount);
		gr.vertexList = vertexList;
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				gr.adjMatrix[j][i] = adjMatrix[i][j];
			}
		}
		return gr;
	}
}