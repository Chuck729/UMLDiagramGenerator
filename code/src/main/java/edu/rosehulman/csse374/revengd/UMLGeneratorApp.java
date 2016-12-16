package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMLGeneratorApp {

	public static void main(String[] args) throws IOException {
		
		ICodeGenerator generator = new GraphVizGenerator();
		
		String out = "../Input-Output/output.txt";
		
		List<String> arguments = new ArrayList<String>();
		
		boolean recursive = false;
		
		for(int i = 0; i < args.length; i++){
			if (args[i].equals("-r")) {
				recursive = true;
			} else {
				arguments.add(args[i]);
			}
		}
		
		IDesignParser parser = new DesignParser(generator, out, arguments);
		
		parser.parseProject();
		
		parser.generate();
		
		generator.write(out);
		
		//lab1_1.MicrosoftLineParser lab1_1.DataStandardizer lab1_1.AmazonLineParser lab1_1.GoogleLineParser lab1_1.GrouponLineParser lab1_1.ILineParser

	}

}
