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
	
	public GraphVizComponents(IClassContent c) {
		this.edges = new LinkedList<>();
		this.methods = new LinkedList<>();
		this.fields = new LinkedList<>();
		this.options = new HashMap<>();
		this.setFields(c);
	}
	
	private void setFields(IClassContent c) {
		this.label = escape(c.getName());  // FIXME should not be name, could cause errors
		this.shape = "\"record\"";
		this.name = escape(c.getName());
		this.isInterface = c.isInterface();
		this.isAbstract = c.isAbstract();
		this.methods = c.getMethod();
		this.fields = c.getField();
		
		// parent
		if (c.getParent() != null) {
			this.edges.add(new Edge(escape(c.getName()), 
					escape(c.getParent()), 
					"[arrowhead=\"onormal\", style=\"solid\"];", 
					""));
		}
		
		// interface
		for (String inter : c.getInterfaces()){
			this.edges.add(new Edge(escape(c.getName()), 
					escape(inter), 
					"[arrowhead=\"onormal\", style=\"dashed\"];", 
					""));
		}
	}
	
	private String escape(String in){
		in = in.replace(">", "\\>");
		in = in.replace("<", "\\<");
		in = in.replace("$", "");
		String[] split = in.split("\\.");  // FIXME this should be left
		return split[split.length-1];
	}
}
