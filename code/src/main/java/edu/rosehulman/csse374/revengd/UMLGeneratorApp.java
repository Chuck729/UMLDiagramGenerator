package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class UMLGeneratorApp {

	public static void main(String[] args) {
		
		ICodeGenerator generator = new GraphVizGenerator();
		
		String out = "../Input-Output/output.txt";
		
		List<String> arguments = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++){
			arguments.add(args[i]);
		}
		
		IDesignParser parser = new DesignParser(generator, out, arguments);
		
		parser.parseProject();
		
		parser.generate();
		
		
		
		

	}

}
