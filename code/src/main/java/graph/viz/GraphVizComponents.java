package graph.viz;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.csse374.revengd.IClassContent;

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
		this.methods = c.getMethods();
		this.fields = c.getFields();
		
		Edge e;
		
		// parent
		if (c.getParent() != null) {
			e = new Edge(this.label, this.nameToID.get(c.getParent()));
			e.appendOption("arrowhead", "onormal");
			e.appendOption("style", "solid");
			this.edges.add(e);
		}
		
		// interface
		for (String inter : c.getInterfaces()){
			e = new Edge(this.label, this.nameToID.get(inter));
			e.appendOption("arrowhead", "onormal");
			e.appendOption("style", "dashed");
			this.edges.add(e);
		}
		
		// associations
		if (c.getAssociation() != null) {  // TODO needs to be fixed
			for (String ass : c.getAssociation()) {
				if(c.getName().equals("headfirst.factory.pizzaaf.Pizza"))
				System.out.println("+++"+ass.replace("[", "").replace("]", ""));
				e = new Edge(this.label, this.nameToID.get(ass.replace("[", "").replace("]", "")));
				e.appendOption("arrowhead", "vee");
				e.appendOption("style", "solid");
				this.edges.add(e);
			}
		}
		
		// dependency
		if(c.getDependency() != null) {  // TODO needs to be fixed
			for (String dp : c.getDependency()) {
				e = new Edge(this.label, this.nameToID.get(dp));
				e.appendOption("arrowhead", "vee");
				e.appendOption("style", "dashed");
				this.edges.add(e);
			}
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
	
	public String getID(String name){
		return nameToID.get(name);
		
	}
	
	public void addOption(String option, String value) {
		this.options.put(option, value);
	}
	
	public Map<String, String> getOptions() {
		return this.options;
	}
	
	public String getName(String i) {
		for(String n : nameToID.keySet()) {
			if (i.equals(nameToID.get(n))) {
				return n;
			}
		}
		return null;
	}
	
}
