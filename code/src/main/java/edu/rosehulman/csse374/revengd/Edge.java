package edu.rosehulman.csse374.revengd;

public class Edge {
	private String edge1;
	private String edge2;
	private String arrowType;
	private String options;
	
	public Edge(String edge1, String edge2, String arrowType, String options) {
		this.edge1 = edge1;
		this.edge2 = edge2;
		this.arrowType = arrowType;
		this.options = options;
	}
	
	public String getEdge1() {
		return this.edge1;
	}
	
	public String getEdge2() {
		return this.edge2;
	}
	
	public String getArrowType() {
		return this.arrowType;
	}
	
	public String getOptions() {
		return this.options;
	}
	
	public void setArrowType(String t) {
		this.arrowType = t;
	}
	
	public void appendOption(String o) {
		this.options = this.options + ", " + o;  // TODO this is most likely not right 
	}
}
