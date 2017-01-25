package edu.rosehulman.csse374.revengd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import test.classes.A;

public class UMLGeneratorApp {
	
	private static String propertyileName = "";  // TODO this should actually be set to some default

	public static void main(String[] args) throws IOException {
	
		// using a class loader
//		ClassLoader cl = new PatternClassLoader();
//		A a = null;
//		try {
//			a = (A) cl.loadClass("test.classes.A").newInstance();
//		} catch (InstantiationException | IllegalAccessException
//				| ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		a.testA();
		
		if (args.length == 1) {
			propertyileName = args[0];
		}
		
		Properties p = new Properties();
		InputStream in = new FileInputStream(propertyileName);
		p.load(in);
		
		String[] clargs = p.getProperty("clo").split(",");
		String[] whiteList = p.getProperty("whitelist").split(",");
		String[] blackList = p.getProperty("blacklist").split(",");
		boolean synthetic = p.getProperty("synthetic").equals("yes");

		IGraphVizGenorator generator = new GraphVizGenerator();
		generator = new GraphVizAssociationSupersedeDecorator(generator);
		generator = new GraphVizOneToManyDecorator(generator);
		generator = new GraphVizBidirDecorator(generator);
		
		String out = "./Input-Output/output.txt";
		
		List<String> arguments = new ArrayList<String>();

		HashMap<String, IModification> modificationMap = new HashMap<String, IModification>();
		modificationMap.put("private", new PrivateOnly());
		modificationMap.put("public", new PublicOnly());
		modificationMap.put("protected", new ProtectedOnly());
		
		ArrayList<IModification> modifications = new ArrayList<IModification>();
		
		boolean recursive = false;
		
		String[] accessLevel = null;
		
		for(int i = 0; i < clargs.length; i++){
			if (clargs[i].equals("-r")) {
				recursive = true;
			} 
			else if (clargs[i].contains("-a")) {
				accessLevel = clargs[i].split("=");
			} else {
				arguments.add(clargs[i]);
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
		// edu.rosehulman.csse374.revengd.ClassContent edu.rosehulman.csse374.revengd.ConvertInternalTypes edu.rosehulman.csse374.revengd.DesignParser edu.rosehulman.csse374.revengd.FieldConvert edu.rosehulman.csse374.revengd.GraphVizGenerator edu.rosehulman.csse374.revengd.MethodConvert edu.rosehulman.csse374.revengd.PrivateOnly edu.rosehulman.csse374.revengd.ProtectedOnly edu.rosehulman.csse374.revengd.PublicOnly edu.rosehulman.csse374.revengd.UMLGeneratorApp -r -a=public
		
		
		// java.lang.String headfirst.factory.pizzafm.Pizza headfirst.factory.pizzafm.ChicagoPizzaStore headfirst.factory.pizzafm.ChicagoStyleCheesePizza headfirst.factory.pizzafm.ChicagoStyleClamPizza headfirst.factory.pizzafm.ChicagoStylePepperoniPizza headfirst.factory.pizzafm.ChicagoStyleVeggiePizza headfirst.factory.pizzafm.DependentPizzaStore headfirst.factory.pizzafm.NYPizzaStore headfirst.factory.pizzafm.NYStyleCheesePizza headfirst.factory.pizzafm.NYStyleClamPizza headfirst.factory.pizzafm.NYStylePepperoniPizza headfirst.factory.pizzafm.NYStyleVeggiePizza headfirst.factory.pizzafm.PizzaStore headfirst.factory.pizzafm.PizzaTestDrive
		// problem.AppLauncherApplication problem.ApplicationLauncher problem.DataFileRunner problem.DirectoryChangeLogger problem.DirectoryEvent problem.DirectoryMonitorService problem.ExecutableFileRunner problem.HtmlFileRunner problem.IApplicationLauncher problem.IDirectoryListener problem.IDirectoryMonitorService problem.ProcessRunner problem.TempFileRunner		
		// edu.rosehulman.csse374.revengd.ClassContent edu.rosehulman.csse374.revengd.ConvertInternalTypes edu.rosehulman.csse374.revengd.DesignParser edu.rosehulman.csse374.revengd.FieldConvert edu.rosehulman.csse374.revengd.GraphVizGenerator edu.rosehulman.csse374.revengd.MethodConvert edu.rosehulman.csse374.revengd.PrivateOnly edu.rosehulman.csse374.revengd.ProtectedOnly edu.rosehulman.csse374.revengd.PublicOnly edu.rosehulman.csse374.revengd.UMLGeneratorApp test.classes.A test.classes.B -r -a=private
	
		/* headfirst.factory.pizzaaf.Pizza
		headfirst.factory.pizzaaf.BlackOlives
		headfirst.factory.pizzaaf.Cheese
		headfirst.factory.pizzaaf.CheesePizza
		headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory
		headfirst.factory.pizzaaf.ChicagoPizzaStore
		headfirst.factory.pizzaaf.ClamPizza
		headfirst.factory.pizzaaf.Clams
		headfirst.factory.pizzaaf.Dough
		headfirst.factory.pizzaaf.Eggplant
		headfirst.factory.pizzaaf.FreshClams
		headfirst.factory.pizzaaf.FrozenClams
		headfirst.factory.pizzaaf.Garlic
		headfirst.factory.pizzaaf.MarinaraSauce
		headfirst.factory.pizzaaf.MozzarellaCheese
		headfirst.factory.pizzaaf.Mushroom
		headfirst.factory.pizzaaf.NYPizzaIngredientFactory
		headfirst.factory.pizzaaf.NYPizzaStore
		headfirst.factory.pizzaaf.Onion
		headfirst.factory.pizzaaf.ParmesanCheese
		headfirst.factory.pizzaaf.Pepperoni
		headfirst.factory.pizzaaf.PepperoniPizza
		headfirst.factory.pizzaaf.PizzaIngredientFactory
		headfirst.factory.pizzaaf.PizzaStore
		headfirst.factory.pizzaaf.PizzaTestDrive
		headfirst.factory.pizzaaf.PlumTomatoSauce
		headfirst.factory.pizzaaf.RedPepper
		headfirst.factory.pizzaaf.ReggianoCheese
		headfirst.factory.pizzaaf.Sauce
		headfirst.factory.pizzaaf.SlicedPepperoni
		headfirst.factory.pizzaaf.Spinach
		headfirst.factory.pizzaaf.ThickCrustDough
		headfirst.factory.pizzaaf.ThinCrustDough
		headfirst.factory.pizzaaf.VeggiePizza
		headfirst.factory.pizzaaf.Veggies */
		
		/* problem.AppLauncherApplication problem.ApplicationLauncher problem.DataFileRunner problem.DirectoryChangeLogger problem.DirectoryEvent problem.DirectoryMonitorService problem.ExecutableFileRunner problem.HtmlFileRunner problem.IApplicationLauncher problem.IDirectoryListener problem.IDirectoryMonitorService problem.ProcessRunner problem.TempFileRunner */
		
		/* edu.rosehulman.csse374.revengd.AssociationFinder
		edu.rosehulman.csse374.revengd.ClassContent
		edu.rosehulman.csse374.revengd.ConvertInternalTypes
		edu.rosehulman.csse374.revengd.DependencyFinder
		edu.rosehulman.csse374.revengd.DesignParser
		edu.rosehulman.csse374.revengd.DisplayJavaFX
		edu.rosehulman.csse374.revengd.Edge
		edu.rosehulman.csse374.revengd.FieldConvert
		edu.rosehulman.csse374.revengd.GraphVizAssociationSupersedeDecorator
		edu.rosehulman.csse374.revengd.GraphVizBidirDecorator
		edu.rosehulman.csse374.revengd.GraphVizComponents
		edu.rosehulman.csse374.revengd.GraphVizDecorator
		edu.rosehulman.csse374.revengd.GraphVizGenerator
		edu.rosehulman.csse374.revengd.GraphVizOneToManyDecorator
		edu.rosehulman.csse374.revengd.IClassContent
		edu.rosehulman.csse374.revengd.ICodeGenerator
		edu.rosehulman.csse374.revengd.IComponents
		edu.rosehulman.csse374.revengd.ICoupleFinder
		edu.rosehulman.csse374.revengd.IDesignParser
		edu.rosehulman.csse374.revengd.IDisplay
		edu.rosehulman.csse374.revengd.IGraphVizGenorator
		edu.rosehulman.csse374.revengd.IModification
		edu.rosehulman.csse374.revengd.MethodConvert
		edu.rosehulman.csse374.revengd.OldBadDesignParser
		edu.rosehulman.csse374.revengd.PrivateOnly
		edu.rosehulman.csse374.revengd.ProtectedOnly
		edu.rosehulman.csse374.revengd.PublicOnly
		edu.rosehulman.csse374.revengd.UMLGeneratorApp
		test.classes.A test.classes.B test.classes.C test.classes.D test.classes.E test.classes.F -r -a=private */
	}

}
