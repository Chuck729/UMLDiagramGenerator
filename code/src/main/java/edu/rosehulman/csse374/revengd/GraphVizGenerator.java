package edu.rosehulman.csse374.revengd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GraphVizGenerator implements IGraphVizGenorator {
	
	private List<List<String>> code;
	private List<IComponents> classes;
	
	public GraphVizGenerator() {
		this.code = new LinkedList<>();
		this.classes = new LinkedList<>();
	}

	@Override
	public void generateCode(List<IClassContent> classes) {
		for (IClassContent c : classes) {
			this.classes.add(new GraphVizComponents(c));
		}
	}

	public void write(String file) throws IOException {
		for(IComponents c : this.classes) {
			this.format((GraphVizComponents) c);
		}
		String code = concatCode();
		System.out.println(code);
		OutputStream out = new FileOutputStream(file);
		byte[] b = code.getBytes();
		out.write(b);
	}
	
	private String concatCode() {
		String text = "digraph uml { rankdir=BT; ";
		for (List<String> block : code) {
			for (String s : block) {
				text = text + s + " ";
			}
		}
		return text + " }";
	}
	
	private void format(GraphVizComponents classComponent) {
		List<String> list = new LinkedList<String>();
		
		// add class name
		list.add(classComponent.getName());
		list.add("[");
		
		// make shape
		list.add("shape");
		list.add("=");
		list.add(classComponent.getShape());
		list.add(",");
		
		// make label
		list.add("label");
		list.add("=");
		list.add("\"{");
		
		// Name
		// TODO check if it is an interface or abstract class
		if (classComponent.isInterface()) {
			list.add("\\<\\<Interface\\>\\>\\l");
		}
		if (classComponent.isAbstract()) {
			//list.add("<I>");
			list.add("\\<\\<Abstract\\>\\>\\l");
		}
		list.add(escape(classComponent.getName()));
		if (classComponent.isAbstract()) {
			//list.add("</I>");
		}
		list.add("|");
		
		// fields
		for (String field : classComponent.getFields()) {
			list.add(escape(field));
			list.add("\\l");
		}
		list.add("|");
		
		// methods
		for (String method : classComponent.getMethods()) {
			list.add(escape(method));
			list.add("\\l");
		}
		list.add("}\",");
		
		// TODO options
		
		list.add("];");
		
		//relationships and dependency arrows
		for(String e : classComponent.getEdges()) {
			list.add(e);
		}
		/*for (String assoc : classContent.getAssociation()){
			list.add(escape(classContent.getName()));
			list.add("->");
			list.add(escape(assoc));
			list.add("[arrowhead=\"vee\", style=\"solid\"];");
		}
		for (String dep : classContent.getDependency()){
			list.add(escape(classContent.getName()));
			list.add("->");
			list.add(escape(dep));
			list.add("[arrowhead=\"vee\", style=\"dashed\"];");
		}*/
		
		this.code.add(list);
		
	}
	
	private String escape(String in){
		in = in.replace(">", "\\>");
		in = in.replace("<", "\\<");
		in = in.replace("$", "");
		String[] split = in.split("\\.");
		return split[split.length-1];
		
	}

	@Override
	public IComponents getClasses() {
		return (IComponents) this.classes;
	}

}
