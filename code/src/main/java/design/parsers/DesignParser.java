package design.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.directory.ModificationItem;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import edu.rosehulman.csse374.revengd.AssociationFinder;
import edu.rosehulman.csse374.revengd.ClassContent;
import edu.rosehulman.csse374.revengd.DependencyFinder;
import edu.rosehulman.csse374.revengd.FieldConvert;
import edu.rosehulman.csse374.revengd.IClassContent;
import edu.rosehulman.csse374.revengd.ICoupleFinder;
import edu.rosehulman.csse374.revengd.IModification;
import edu.rosehulman.csse374.revengd.MethodConvert;
import graph.viz.ICodeGenerator;

public class DesignParser implements IDesignParser {
	
	private List<IClassContent> classes;
	private ICodeGenerator codeGenerator;
	private String outFile;
	private ClassReader reader;
	private List<String> classNames;
	private boolean isRecursive;
	private MethodConvert methodConvert;
	private FieldConvert fieldConvert;
	private List<IModification> modifications;
	private ICoupleFinder dpFinder;
	private ICoupleFinder assFinder;
	private String[] blackList;
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames, 
			boolean isRecursive, List<IModification> modifications, String[] blackList) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
		this.isRecursive = isRecursive;
		this.blackList = blackList;
		this.classes = new LinkedList<IClassContent>();
		this.methodConvert = new MethodConvert();
		this.fieldConvert = new FieldConvert();
		this.modifications = modifications;
		this.assFinder = new AssociationFinder();
		this.dpFinder = new DependencyFinder();
	}

	//for each class name passed in, create a classContent to get the content from the class
	//then set the class contents fields to the parsed UML format version
	//and set the class contents methods to the parsed UML format version
	@Override
	public void parseProject() {
		for (String name: classNames) {
//			System.out.println(name);
			IClassContent classContent = new ClassContent(name);
			classContent.setField(fieldConvert.convert(classContent.getFields()));
			classContent.setMethod(methodConvert.convert(classContent.getMethods()));
			this.classes.add(classContent);
		}
		
		assFinder.find(classes, classNames, isRecursive);
		dpFinder.find(classes, classNames, isRecursive);
		for (IClassContent c : classes) {
			System.out.println(c.getName() + c.getDependency());
		}
		for (IClassContent c : classes) {
			ArrayList<String> newDep = new ArrayList<String>();
			for (String insnDep : c.getInsnDep()) {
				if (!c.getDependency().contains(insnDep) && classNames.contains(insnDep))
					newDep.add(insnDep);
			}
			for (String d : newDep) {
				c.addDependency(d);
			}
		}
		System.out.println();
		for (IClassContent c : classes) {
			System.out.println(c.getName() + c.getDependency());
		}
		findRecursive();
		
//		for(IClassContent c: classes) {
//			System.out.println(c.getMethod());
//			if(c.getName().equals("headfirst.factory.pizzaaf.Pizza"))
//			System.out.println(c.getName() + ": " + c.getAssociation() + "  : " + c.getDependency());
//		}
		
		for(IClassContent c: classes) {
			for (IModification modification: modifications) {
				c.setField(modification.modify(c.getFields()));
				c.setMethod(modification.modify(c.getMethods()));
			}
		}
	}
	
	@Override
	public void generate() {
		this.codeGenerator.generateCode(this.classes);
	}
	
	private void findRecursive() {
		findParent();
	}
	
	private void addNewClass(String name) {
		for (String b : this.blackList) {
			if (name.contains(b)) {
				return;  // do not add TODO not tested
			}
		}
		name = name.replace("[", "");
		name = name.replace("]", "");
		ClassContent classContent = new ClassContent(name);
		classContent.setField(fieldConvert.convert(classContent.getFields()));
		classContent.setMethod(methodConvert.convert(classContent.getMethods()));
		this.classes.add(classContent);
		this.classNames.add(name);
	}
	
	private void findParent() {
		boolean found = false;
		for(int i = 0; i < this.classes.size(); i++) {
			String p = this.classes.get(i).getParent();
			if (!this.classNames.contains(p) && p != null && isRecursive) {
				addNewClass(p);
				found = true;
			} else if (!this.classNames.contains(p)) {
				this.classes.get(i).removeParent();
			}
		}
		if (found) {
			findParent();
		} else {
			findInterface();
		}
	}
	
	private void findInterface() {
		boolean found = false;
		for(int i = 0; i < this.classes.size(); i++) {
			for(String intName : this.classes.get(i).getInterfaces()) {
				if (!this.classNames.contains(intName) && isRecursive) {
					addNewClass(intName);
					found = true;
				} else if (!this.classNames.contains(intName)) {
					this.classes.get(i).removeInterface(intName);
				}
			}
		}
		if (found) {
			findParent();
		} 
//			else {
//			findAss();
//		}
	}

	private void findAss() {
		List<String> newClasses;
		newClasses = assFinder.find(classes, classNames, isRecursive);
		if(isRecursive && newClasses.size() != 0) { // quit before find
//			for (String c : newClasses) {
//				System.out.println("ass: " + c);
//				//addNewClass(c);
//			}
			findParent();
		} else {
			findDP();
		}
	}
	
	private void findDP() {
		List<String> newClasses;
		newClasses = dpFinder.find(classes, classNames, isRecursive);
		if(isRecursive && newClasses.size() != 0) { // quit before find
			for (String c : newClasses) {
//				System.out.println("dp: " + c);
				//addNewClass(c);
				
			}
			findParent();
		} else {
			// yayayayayayayay
		}
	}

	@Override
	public List<IClassContent> getClassContent() {
		return this.classes;
	}
	
}
