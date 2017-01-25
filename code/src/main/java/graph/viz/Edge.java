package graph.viz;

import java.util.HashMap;
import java.util.Map;

public class Edge {
	private String vertex1;
	private String vertex2;
	private Map<String, String> options;
	
	public Edge(String vertex1, String vertex2) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.options = new HashMap<String, String>();
	}
	
	public String getVertex1() {
		return this.vertex1;
	}
	
	public String getVertex2() {
		return this.vertex2;
	}
	
	public Map<String, String> getOptions() {
		return this.options;
	}
	
	public void appendOption(String o, String val) {
		this.options.put(o, val);  
	}
}
