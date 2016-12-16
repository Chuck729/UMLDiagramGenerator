package edu.rosehulman.csse374.revengd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

	@Override
	public void write(String file) throws IOException {
		String code = concatCode();
		OutputStream out = new FileOutputStream(file);
		byte[] b = code.getBytes();
		out.write(b);
	}
	
	private String concatCode() {
		String text = "";
		for (List<String> block : code) {
			for (String s : block) {
				text = text + s + " ";
			}
		}
		return text;
	}
	
	private void format(IClassContent classContent) {
		List<String> list = new LinkedList<String>();
		
		// add class name
		list.add(classContent.getName());
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
		list.add(classContent.getName());
		list.add("|");
		
		// fields
		for (String field : classContent.getField()) {
			list.add(field);
			list.add("\\l");
		}
		list.add("|");
		
		// methods
		for (String method : classContent.getMethod()) {
			list.add(method);
			list.add("\\l");
		}
		list.add("}\",");
		
		this.code.add(list);
		
	}

}
