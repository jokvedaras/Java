package phone_calls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//Challenge 161 (Hard)
//Split up classes between .java documents

//Java: Implemented a graph using a 2D array with the values being number of 
//available connections between each phone. Used Dijkstra to find the shortest
//path for each phone call and then 'allocate' the resources by reducing the 
//value of the available connections used after each call.

public class PhoneProblem {
	public static void main(String[] args) {
		PhoneNetwork network = new PhoneNetwork();
		network.parseScannerInput(new Scanner(System.in));
		for (Call call : network.calls) {
			network.attemptToPlaceCall(call);
			System.out.println(call.status());
		}
	}
}

class PhoneNetwork {
	private int[][] graph = new int[26][26];
	protected ArrayList<Call> calls = new ArrayList<Call>();
	
	/**
	 * Attempt to place call using dijksta's algo to see if there is a route
	 * @param call
	 */
	public void attemptToPlaceCall(Call call) {
		Dijkstra dijkstra = new Dijkstra(graph);
		List<Integer> bestRouteIndexes = dijkstra.getShortestPath(indexFromPhone(call.caller), indexFromPhone(call.receiver));
		
		if (bestRouteIndexes != null) {
			List<Character> bestRoutePhones = new ArrayList<Character>();
			for (int i : bestRouteIndexes) {
				bestRoutePhones.add(phoneFromIndex(i));
			}
			call.route = bestRoutePhones;
		}
	}
	
	/**
	 * Retrieve index from Phone
	 * @param phone
	 * @return
	 */
	private static int indexFromPhone(char phone) {
		return Character.toUpperCase(phone) - 65;
	}
	
	/**
	 * Retrieve phone from index
	 * @param index
	 * @return
	 */
	private static char phoneFromIndex(int index) {
		return (char) (index + 65);
	}

	/**
	 * Parse input from scanner
	 * @param Scanner
	 */
	public void parseScannerInput(Scanner sc) {
		String[] line = new String[3];
		while (line.length >= 2) {
			line = sc.nextLine().split(" ");
			if (line.length == 3) {
				addPath(line[0].charAt(0), line[1].charAt(0),
						Integer.parseInt(line[2]));
			} else if (line.length == 2) {
				calls.add(new Call(line[0].charAt(0), line[1].charAt(0)));
			}
		}
	}
	
	/**
	 * Add path from two phones to graph. Increase number of connection for that phone by 1
	 * @param phone1
	 * @param phone2
	 * @param noOfConnections
	 */
	private void addPath(char phone1, char phone2, int noOfConnections) {
		graph[indexFromPhone(phone1)][indexFromPhone(phone2)] += noOfConnections;
		graph[indexFromPhone(phone2)][indexFromPhone(phone1)] += noOfConnections;
	}
}

/**
 * Dijkstra Class to handle Djkstra's algorithm
 * @author jokvedaras
 *
 */
class Dijkstra {
	private int[][] graph;
	private int[] pathEstimate, pred;
	private boolean[] tight;

	public Dijkstra(int[][] graph) {
		this.graph = graph;
		this.pathEstimate = new int[graph.length];
		this.tight = new boolean[graph.length];
		this.pred = new int[graph.length];
	}

	/**
	 * Find shortest path from two points
	 * @param origin
	 * @param destination
	 * @return shortest path or null
	 */
	public List<Integer> getShortestPath(int origin, int destination) {
		return generateShortestPath(origin, destination) ? traceRoute(origin, destination) : null;
	}

	/**
	 * Generate shortest path from orgin to destination
	 * @param origin
	 * @param destination
	 * @return True/False depending if its possible
	 */
	private boolean generateShortestPath(int origin, int destination) {
		setUpInitialEstimates(origin);
		int counter = 0;
		while (!tight[destination] && counter < graph.length) {
			int minimalIndex = getMinNonTightElement();
			if (minimalIndex == -1) {
				return false;
			}
			tight[minimalIndex] = true;
			setNewShortestPathsFromElement(minimalIndex);
			counter++;
		}
		return true;
	}

	/**
	 * Estimate to destination
	 * @param destination
	 */
	private void setUpInitialEstimates(int destination) {
		pathEstimate[destination] = 0;
		for (int i = 0; i < graph.length; i++) {
			if (i != destination) {
				pathEstimate[i] = Integer.MAX_VALUE - 1;
			}
		}
	}
	
	private int getMinNonTightElement() {
		int minimalEstimate = Integer.MAX_VALUE - 1;
		int minimalIndex = -1;
		for (int j = 0; j < graph.length; j++) {
			if (!tight[j] && pathEstimate[j] < minimalEstimate) {
				minimalEstimate = pathEstimate[j];
				minimalIndex = j;
			}
		}
		return minimalIndex;
	}
	
	/**
	 * Set new shortest path
	 * @param index
	 */
	private void setNewShortestPathsFromElement(int index) {
		for (int j = 0; j < graph.length; j++) {
			if (graph[index][j] > 0
					&& (pathEstimate[index] + 1 < pathEstimate[j])) {
				pathEstimate[j] = pathEstimate[index] + 1;
				pred[j] = index;
			}
		}
	}
	
	/**
	 * Trace route
	 * @param origin
	 * @param desination
	 * @return List of Integers to get from origin to destination
	 */
	private List<Integer> traceRoute(int origin, int destination) {
		List<Integer> route = new LinkedList<Integer>();
		while (destination != origin) {
			route.add(destination);
			--graph[destination][pred[destination]];
			--graph[pred[destination]][destination];
			destination = pred[destination];
		}
		route.add(origin);
		Collections.reverse(route);
		return route;
	}
}

/**
 * Class to handle all the calls
 * @author jokvedaras
 *
 */
class Call {
	protected char caller, receiver;
	protected List<Character> route;
	
	/**
	 * Initialize Call
	 * @param caller
	 * @param receiver
	 */
	public Call(char caller, char receiver) {
		this.caller = caller;
		this.receiver = receiver;
	}
	
	/**
	 * Find status of a call
	 * @return status
	 */
	public String status() {
		String s = "Call " + caller + " " + receiver + " -- ";
		return s + (route == null ? "failed" : ("placed " + route));
	}
}

/**
 * Input:
 * 
 * A B 2 A C 2 B C 2 B D 2 C E 1 D E 2 D G 1 E F 2 F G 2 A G A G C E G D D E A B
 * A D
 * 
 * 
 * Output:
 * 
 * Call A G -- placed [A, B, D, G] Call A G -- placed [A, C, E, F, G] Call C E
 * -- placed [C, B, D, E] Call G D -- placed [G, F, E, D] Call D E -- failed
 * Call A B -- placed [A, B] Call A D -- failed
 */
