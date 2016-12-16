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
	
	public GraphVizGenerator() {
		this.code = new LinkedList<>();
	}

	@Override
	public void generateCode(List<IClassContent> classes) {
		for (IClassContent c : classes) {
			this.format(c);
		}
	}

	public void write(String file) throws IOException {
		String code = concatCode();
		System.out.println(code);
		/*OutputStream out = new FileOutputStream(file);
		byte[] b = code.getBytes();
		out.write(b);*/
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
	
	private void format(IClassContent classContent) {
		List<String> list = new LinkedList<String>();
		
		// add class name
		list.add(escape(classContent.getName()));
		list.add("[");
		
		// make shape
		list.add("shape");
		list.add("=");
		list.add("\"record\",");
		
		// make label
		list.add("label");
		list.add("=");
		list.add("\"{");
		
		// Name
		// TODO check if it is an interface or abstract class
		if (classContent.isInterface()) {
			list.add("\\<\\<Interface\\>\\>\\l");
		}
		if (classContent.isAbstract()) {
			list.add("<I>");
		}
		list.add(escape(classContent.getName()));
		if (classContent.isAbstract()) {
			list.add("</I>");
		}
		list.add("|");
		
		// fields
		for (String field : classContent.getField()) {
			list.add(escape(field));
			list.add("\\l");
		}
		list.add("|");
		
		// methods
		for (String method : classContent.getMethod()) {
			list.add(escape(method));
			list.add("\\l");
		}
		list.add("}\",");
		
		list.add("];");
		
		//relationships and dependency arrows
		/*list.add(classContent.getName());
		list.add("->");
		if(classContent.getParent() != null){
			list.add(classContent.getParent());
			list.add("[arrowhead=\"onormal\", style=\"solid\"];");
		}
		for (String inter : classContent.getInterfaces()){
			list.add(classContent.getName());
			list.add("->");
			list.add(inter);
			list.add("[arrowhead=\"onormal\", style=\"dashed\"];");
		}*/
		
		
		this.code.add(list);
		
	}
	
	private String escape(String in){
		in = in.replace(">", "\\>");
		in = in.replace("<", "\\<");
		return in;
		
	}

}
