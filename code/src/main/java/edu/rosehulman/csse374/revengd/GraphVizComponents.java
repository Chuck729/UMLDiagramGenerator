package edu.rosehulman.csse374.revengd;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphVizComponents {
	private String label;
	private String shape;
	private String name;
	private boolean isInterface;
	private boolean isAbstract;
	List<Edge> edges;
	List<String> methods;
	List<String> fields;
	Map<String, String> options;
	private Map<String, String> nameToID;
	
	public GraphVizComponents(IClassContent c, String label, Map<String, String> nameToID) {
		this.label = label;
		this.edges = new LinkedList<>();
		this.methods = new LinkedList<>();
		this.fields = new LinkedList<>();
		this.options = new HashMap<>();
		this.nameToID = nameToID;
		this.setFields(c);
	}
	
	private void setFields(IClassContent c) {
		this.shape = "\"record\"";
		this.name = c.getName();
		this.isInterface = c.isInterface();
		this.isAbstract = c.isAbstract();
		this.methods = c.getMethod();
		this.fields = c.getField();
		
		// parent
		if (c.getParent() != null) {
			this.edges.add(new Edge(this.label, 
					this.nameToID.get(c.getParent()), 
					"[arrowhead=\"onormal\", style=\"solid\"];"));
		}
		
		// interface
		for (String inter : c.getInterfaces()){
			this.edges.add(new Edge(this.label, 
					this.nameToID.get(inter), 
					"[arrowhead=\"onormal\", style=\"dashed\"];"));
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getShape() {
		return this.shape;
	}
	
	public boolean isInterface() {
		return this.isInterface;
	}
	
	public boolean isAbstract() {
		return this.isAbstract;
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	public List<String> getMethods() {
		return this.methods;
	}
	
	public List<String> getFields() {
		return this.fields;
	}
	
	public void addOption(String option, String value) {
		this.options.put(option, value);
	}
}
