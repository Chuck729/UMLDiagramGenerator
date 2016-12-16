package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMLGeneratorApp {

	public static void main(String[] args) throws IOException {
		
		ICodeGenerator generator = new GraphVizGenerator();
		
		String out = "./Input-Output/output.txt";
		
		List<String> arguments = new ArrayList<String>();
		
		boolean recursive = false;
		
		for(int i = 0; i < args.length; i++){
			if (args[i].equals("-r")) {
				recursive = true;
			} else {
				arguments.add(args[i]);
			}
		}
		
		IDesignParser parser = new DesignParser(generator, out, arguments, recursive);
		
		parser.parseProject();
		
		parser.generate();
		
		generator.write(out);
		
		// problem.AmazonLineParser problem.GrouponLineParser problem.GoogleLineParser problem.MicrosoftLineParser problem.ILineParser problem.DataStandardizer
		// javax.swing.JComponent -r
		// java.lang.String
	}

}
