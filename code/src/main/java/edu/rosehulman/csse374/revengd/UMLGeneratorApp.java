package edu.rosehulman.csse374.revengd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UMLGeneratorApp {

	private static String propertyFileName;
	private static String out = "./Input-Output/output.txt";

	/**
	 * 
	 * @param args
	 *            -p=(property file) specify a property -r recursive parsing
	 *            -a=(access level) access level
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// using a class loader
		// ClassLoader cl = new PatternClassLoader();
		// A a = null;
		// try {
		// a = (A) cl.loadClass("test.classes.A").newInstance();
		// } catch (InstantiationException | IllegalAccessException
		// | ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// a.testA();

		String[] clargs;
		String[] whiteList;
		String[] blackList;
		boolean synthetic;

		if (args.length == 1 && args[0].split("=")[0].equals("-p")) {
			propertyFileName = args[0].split("=")[1];

			Properties p = new Properties();
			InputStream in = new FileInputStream(propertyFileName);
			p.load(in);

			clargs = p.getProperty("clo").split(",");
			whiteList = p.getProperty("whitelist").split(",");
			if (whiteList[0].equals("")) {
				whiteList = new String[0];
			}
			blackList = p.getProperty("blacklist").split(",");
			if (blackList[0].equals("")) {
				blackList = new String[0];
			}
			synthetic = p.getProperty("synthetic").equals("on");
		} else {
			clargs = args;
			whiteList = new String[0];
			blackList = new String[0];
			synthetic = true;
		}

		IGraphVizGenorator generator = new GraphVizGenerator();
		generator = new GraphVizAssociationSupersedeDecorator(generator);
		generator = new GraphVizOneToManyDecorator(generator);
		generator = new GraphVizBidirDecorator(generator);

		List<String> arguments = new ArrayList<String>();

		Map<String, IModification> modificationMap = new HashMap<String, IModification>();
		modificationMap.put("private", new PrivateOnly());
		modificationMap.put("public", new PublicOnly());
		modificationMap.put("protected", new ProtectedOnly());

		List<IModification> modifications = new ArrayList<IModification>();

		boolean recursive = false;

		String[] accessLevel = null;

		for (int i = 0; i < clargs.length; i++) {
			if (clargs[i].equals("-r")) {
				recursive = true;
			} else if (clargs[i].contains("-a")) {
				accessLevel = clargs[i].split("=");
			} else if (clargs[i].contains("-p")) {
				// does nothing, do not add
			} else {
				arguments.add(clargs[i]);
			}
		}
		if (accessLevel != null) {
			if (accessLevel.length == 1) {
				modifications.add(modificationMap.get("private"));
			} else {
				modifications.add(modificationMap.get(accessLevel[1]));
			}
		}
		
		arguments = whiteBlackList(arguments, whiteList, blackList);

		IDesignParser parser = new DesignParser(generator, out, arguments,
				recursive, modifications, blackList);  // need to pass synthetic

		parser.parseProject();
		parser.generate();
		generator.write(out);
	}
	
	private static List<String> whiteBlackList(List<String> args, String[] white, String[] black) {
		List<String> ret = new LinkedList<String>();
		
		if (black.length == 0) {
			ret = args;
		}
		for (String b : black) {
			for (String a : args) {
				if (!a.contains(b)) {
					System.out.println("b");
					ret.add(a);
				}
			}
		}
		
		for (String w: white) {
			if (!ret.contains(w)) {
				System.out.println(w);
				ret.add(w);
			}
		}
		
		return ret;
	}
}
