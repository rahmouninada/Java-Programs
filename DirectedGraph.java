/* Directed Graph Implementation in Java
* Classes: ShortestPathInfo.java, Node.java, Edge.java, DiGraph.java
* Interface: DiGraphInterface.java
*/

package src.DirectedGraph;

/* DIRECTED GRAPH INTERFACE: */

public interface DiGraphInterface {
    boolean addNode(long idNum, String label);
    boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel);
    boolean delNode(String label);
    boolean delEdge(String sLabel, String dLabel);
    long numNodes();
    long numEdges();
}

/* SHORTEST PATH INFORMATION CLASS: */
public class ShortestPathInfo {
/* represents a single shortest path from a source Node to a destination Node */
  private String dest;
  private long totalWeight;
  
  public ShortestPathInfo(String dest, long totalWeight){
    this.dest=dest;
    this.totalWeight=totalWeight;
  }

  public String getDest() { // label of the destination node
    return dest;
  }

  public long getTotalWeight() { // totalWeight = sum of edge weight on the shortest path from source to destination.
    return totalWeight; // if path doesn't exist, totalWeight = -1
  }
  
  public String toString(){
    return "dest: "+dest+"\ttotalWeight: "+totalWeight;
  }
}

/* NODE CLASS: */
import java.util.HashMap;

public class Node {

	private String label;
	private long idNum;
	private int distance;
	private boolean visited;
	public HashMap<String, Edge> sourceMap; // for all sources of node, hashes source label to edge
	public HashMap<String, Edge> destinationMap; // for all destinations of node, hashes destination label to edge

	public Node(long id, String label) {
		this.idNum = id;
		this.label = label;
		this.sourceMap = new HashMap<String, Edge>();
		this.destinationMap = new HashMap<String, Edge>();
		this.distance = Integer.MAX_VALUE;
		this.visited = false;
		// list of next & list of previous
	}

	public String getLabel() {
		return label;
	}

	public long getIdNum() {
		return idNum;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	public void makeVisited() {
		this.visited = true;
	}
}

/* EDGE CLASS: */
public class Edge {
	
	private String label;
	private long idNum;
	private long weight;
	private String destinationLabel;
	private String sourceLabel;
	
	public Edge(long idNum, String source, String destination, long weight, String label) {
		this.idNum = idNum;
		this.sourceLabel = source;
		this.destinationLabel = destination;
		this.label = label;
		this.weight = weight; // default = 1?
	}
	
	public String getLabel() {
		return label;
	}
	
	public long getIdNum() {
		return idNum;
	}
	
	public long getWeight() {
		return weight;
	}
	
	public String getDestinationLabel() {
		return destinationLabel;
	}
	
	public String getSourceLabel () {
		return sourceLabel;
	}
	
	public void setDestinationLabel(String destinationLabel) {
		this.destinationLabel = destinationLabel;
	}
	
	public void setSourceLabel(String sourceLabel) {
		this.sourceLabel = sourceLabel;
	}
}

/* DIRECTED GRAPH CLASS: */
import java.util.HashMap;
import java.util.Set;
import MinimumBinaryHeap.*; // importing own implementation of minimum binary heap (can use java library version)

public class DiGraph implements DiGraphInterface {

	public long numNodes;
	public long numEdges;
	public HashMap<String, Node> nodeMap; // maps node label to node
	public HashMap<Long, Node> idMap; // maps node id number to node
	public HashMap<Long, Edge> edgeMap; // maps edge id number to edge
	public DiGraph() {
		this.nodeMap = new HashMap<String, Node>();
		this.idMap = new HashMap<Long, Node>();
		this.edgeMap = new HashMap<Long, Edge>();
		this.numNodes = 0;
		this.numEdges = 0;
	}

	public ShortestPathInfo[] shortestPath(String label) {
		ShortestPathInfo[] shortestPathInfo = new ShortestPathInfo[(int) numNodes]; // label of node, distance from source
		MinBinHeap pq = new MinBinHeap();
		Set<String> nodeSet = nodeMap.keySet();
		int i = 0;
		
		if (nodeMap.containsKey(label)) {
			nodeMap.get(label).setDistance(0);
			pq.insert(new EntryPair(label, 0));
			boolean isfirst = true;
			while(pq.size() != 0) {
				if (!isfirst) {
					pq.delMin();
				}
				if (pq.getMin() != null) {
					Node tempSource = nodeMap.get(pq.getMin().getValue());
					if (tempSource != null) {
						if(!tempSource.getVisited()) {
							Set<String> keyset = tempSource.destinationMap.keySet();
							for(String key : keyset) {
								Node tempDestination = nodeMap.get(key);
								if (tempDestination != null) {
									if (!tempDestination.getVisited()) {
										int newDistance = tempSource.getDistance() + 
												(int) tempSource.destinationMap.get(key).getWeight();
										if (newDistance < tempDestination.getDistance()) {
											tempDestination.setDistance(newDistance);
										}
										System.out.println("dk: " + tempDestination.getLabel() + " " + tempDestination.getDistance());
										pq.insert(new EntryPair(tempDestination.getLabel(), tempDestination.getDistance()));
									}
								}
							}
							tempSource.makeVisited();
							shortestPathInfo[i] = new ShortestPathInfo(pq.getMin().getValue(), pq.getMin().getPriority());
							nodeSet.remove(tempSource.getLabel());
							isfirst = false;
						}
					}
					if (tempSource != null && i < numNodes) {
						i++;
					}
				}
			}
		}
		
		// if no path exists:
		if (i != numNodes - 1) {
			for (String tempNode: nodeSet) {
				shortestPathInfo[i] = new ShortestPathInfo(tempNode, -1);
				i++;
			}
		}
		return shortestPathInfo;
	}

	public boolean addNode(long idNum, String label) {
		if (idNum < 0 || label == null || nodeMap.containsKey(label) || idMap.containsKey(idNum)) { // if label exists
			return false;
		}
		nodeMap.put(label, new Node(idNum, label));
		idMap.put(idNum, new Node(idNum, label));
		numNodes++;
		return true;
	}

	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		// don't have to check e label because can be null:
		if (idNum < 0 || edgeMap.containsKey(idNum) || sLabel == null || dLabel == null) {
			return false;
		}

		if (!nodeMap.containsKey(sLabel) || !nodeMap.containsKey(dLabel)) {
			return false;
		}

		if (weight == 0) { // weight default is 1
			weight = 1;
		}

		// if there's already edge between these 2 nodes: can't have 2 edges from N to M
		// so if source label already has this destination label:
		if (nodeMap.get(sLabel).destinationMap.containsKey(dLabel)) {
			return false;
		}

		Edge edge = new Edge(idNum, sLabel, dLabel, weight, eLabel);

		// maps edge id number to edge:
		edgeMap.put(idNum, edge);

		// for source node, maps destination label to edge:
		nodeMap.get(sLabel).destinationMap.put(dLabel, edge);

		// for destination map, maps source label to edge:
		nodeMap.get(dLabel).sourceMap.put(sLabel, edge);

		numEdges++;
		return true;
	}

	public boolean delEdge(String sLabel, String dLabel) {
		if (sLabel == null || dLabel == null || !nodeMap.containsKey(sLabel) || !nodeMap.containsKey(dLabel)) {
			return false;
		}

		// if destination node doesn't exist in source node's destination map:
		if (!nodeMap.get(sLabel).destinationMap.containsKey(dLabel)) {
			return false;
		}

		// delete edge from edgeMap:
		edgeMap.remove(nodeMap.get(sLabel).destinationMap.get(dLabel).getIdNum());

		// delete edge from source node's destination map:
		nodeMap.get(sLabel).destinationMap.remove(dLabel);

		// delete edge from destination node's source map:
		nodeMap.get(dLabel).sourceMap.remove(sLabel);

		numEdges--;
		return true;
	}

	public boolean delNode(String label) {
		if (label == null || !nodeMap.containsKey(label)) { // if label is null or doesn't map to a key
			return false;
		}

		// for all the destination nodes in the source map:
		// removes this node from other node's source list & removes edge from edge map
		for (String key : nodeMap.get(label).sourceMap.keySet()) {
			if (edgeMap.remove(nodeMap.get(key).sourceMap.remove(label).getIdNum()) != null) {
				numEdges--;
			}
		}

		// for all destination nodes in destination map:
		// removes this node from other node's dest. list & removes edge from edge map
		for (String key : nodeMap.get(label).destinationMap.keySet()) {
			if (edgeMap.remove(nodeMap.get(key).destinationMap.remove(label).getIdNum()) != null) {
				numEdges--;
			}
		}
		nodeMap.get(label).sourceMap.clear();
		nodeMap.get(label).destinationMap.clear();
		nodeMap.remove(label);
		numNodes--
		return true;
	}

	public long numNodes() {
		return numNodes;
	}

	public long numEdges() {
		return numEdges;
	}
}
