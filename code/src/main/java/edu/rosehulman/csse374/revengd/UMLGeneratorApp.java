package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UMLGeneratorApp {

	public static void main(String[] args) throws IOException {
		
		ICodeGenerator generator = new GraphVizGenerator();
		
		String out = "./Input-Output/output.txt";
		
		List<String> arguments = new ArrayList<String>();
		

		HashMap<String, IModification> modificationMap = new HashMap<String, IModification>();
		modificationMap.put("private", new PrivateOnly());
		modificationMap.put("public", new PublicOnly());
		modificationMap.put("protected", new ProtectedOnly());
		
		ArrayList<IModification> modifications = new ArrayList<IModification>();
		
		boolean recursive = false;
		
		String[] accessLevel = null;
		
		for(int i = 0; i < args.length; i++){
			if (args[i].equals("-r")) {
				recursive = true;
			} 
			else if (args[i].contains("-a")) {
				accessLevel = args[i].split("=");
			} else {
				arguments.add(args[i]);
			}
		}
			if (accessLevel != null) {
				if(accessLevel.length == 1) {
					modifications.add(modificationMap.get("private"));
				}
				else {
					modifications.add(modificationMap.get(accessLevel[1]));
				}
			}
		
		
		IDesignParser parser = new DesignParser(generator, out, arguments, recursive, modifications);
		
		parser.parseProject();
		
		parser.generate();
		
		generator.write(out);
		
		// problem.AmazonLineParser problem.GrouponLineParser problem.GoogleLineParser problem.MicrosoftLineParser problem.ILineParser problem.DataStandardizer
		// javax.swing.JComponent -r
		// java.lang.String
	}

}
