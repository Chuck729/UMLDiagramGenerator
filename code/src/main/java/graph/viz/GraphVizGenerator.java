package graph.viz;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.csse374.revengd.IClassContent;


public class GraphVizGenerator implements IGraphVizGenorator {
	
	private List<List<String>> code;
	private List<GraphVizComponents> classes;
	private Map<String, String> options;
	private Map<String, String> names;
	
	public GraphVizGenerator() {
		this.code = new LinkedList<>();
		this.classes = new LinkedList<>();
		this.options = new HashMap<>();
		this.names = new HashMap<>();
	}

	@Override
	public void generateCode(List<IClassContent> classes) {
		for (int i = 0; i < classes.size(); i++) {
//			System.out.println("---"+classes.get(i).getName());
			this.names.put(classes.get(i).getName(), Integer.toString(i));
		}
		this.addOption("rankdir", "BT");
		for (IClassContent c : classes) {
			GraphVizComponents g = new GraphVizComponents(c,  this.names.get(c.getName()), this.names);
			for (String o : c.getOptionKeys()) {
				g.addOption(o, c.getOption(o));
			}
			this.classes.add(g);
		}
	}

	public void write(String file) throws IOException {
		for(GraphVizComponents c : this.classes) {
			this.format(c);
		}
		String code = concatCode();
		producePicture(code);
		System.out.println(code);
		OutputStream out = new FileOutputStream(file);
		byte[] b = code.getBytes();
		out.write(b);
	}
	
	private void producePicture(String code2) throws IOException {
		String outFile = "output.gv";
		String outPic = "graph.png";
		PrintWriter write = new PrintWriter(outFile);
		write.print(code2);
		write.close();
		
		Process graphViz = new ProcessBuilder("C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe", "-Tpng", outFile, "-o", outPic).start();
		try{
			graphViz.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (Desktop.isDesktopSupported()){
			Desktop.getDesktop().open(new File(outPic));
		} else {
			Process paintProcess = new ProcessBuilder("mspaint", outPic).start();
		}
	}

	private String concatCode() {
		String text = "digraph uml { ";
		for (String o : this.options.keySet()) {
			text = text + o + "=" + this.options.get(o) + ";";
		}
		
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
		list.add(classComponent.getLabel());
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
		List<String> listEdges = new LinkedList<>();
		for (Edge e : classComponent.getEdges()) {
			String str = "" + e.getVertex1() + "->" + e.getVertex2() + concatOptions(e.getOptions());
			list.add(str);
		}
		
		this.code.add(list);
		
	}
	
	private String concatOptions(Map<String, String> options) {
		String str = "[";
		for (String o : options.keySet()) {
			str = str + o + "=\"" + options.get(o) + "\", ";
		}
		return str + "]";
	}
	
	private String escape(String in){
		in = in.replace(">", "\\>");
		in = in.replace("<", "\\<");
		//in = in.replace("$", "");
		//String[] split = in.split("\\.");
		//return split[split.length-1];
		return in;
	}

	@Override
	public List<GraphVizComponents> getClasses() {
		return this.classes;
	}

	@Override
	public void addOption(String option, String value) {
		this.options.put(option, value);
	}

}
