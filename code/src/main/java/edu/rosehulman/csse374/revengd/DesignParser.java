package edu.rosehulman.csse374.revengd;

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

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames, 
			boolean isRecursive, List<IModification> modifications) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
		this.isRecursive = isRecursive;
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
			IClassContent classContent = new ClassContent(name);
			classContent.setField(fieldConvert.convert(classContent.getField()));
			classContent.setMethod(methodConvert.convert(classContent.getMethod()));
			this.classes.add(classContent);
		}
		
		// TODO this all should be in a recursive method. I think it would make
		// dependencies and associations easier
		for(int i = 0; i < this.classes.size(); i++) {
			for(String intName : this.classes.get(i).getInterfaces()) {
				if (!this.classNames.contains(intName) && isRecursive) {
					ClassContent classContent = new ClassContent(intName);
					classContent.setField(fieldConvert.convert(classContent.getField()));
					classContent.setMethod(methodConvert.convert(classContent.getMethod()));
					this.classes.add(classContent);
					this.classNames.add(intName);
				} else if (!this.classNames.contains(intName)) {
					this.classes.get(i).removeInterface(intName);
				}
			}
			String p = this.classes.get(i).getParent();
			if (!this.classNames.contains(p) && p != null && isRecursive) {
				ClassContent classContent = new ClassContent(p);
				classContent.setField(fieldConvert.convert(classContent.getField()));
				classContent.setMethod(methodConvert.convert(classContent.getMethod()));
				this.classes.add(classContent);
				this.classNames.add(p);
				
			} else if (!this.classNames.contains(p)) {
				this.classes.get(i).removeParent();
			}
			
		}
		
		assFinder.find(classes);
		dpFinder.find(classes);
		for(IClassContent c: classes) {
//			System.out.println(c.getMethod());
//			System.out.println(c.getName() + ": " + c.getAssociation() + "  : " + c.getDependency());
		}
		
		for(IClassContent c: classes) {
			for (IModification modification: modifications) {
				c.setField(modification.modify(c.getField()));
				c.setMethod(modification.modify(c.getMethod()));
			}
		}
	}
	
	@Override
	public void generate() {
		this.codeGenerator.generateCode(this.classes);
	}

	@Override
	public void findPattern() {
		// TODO Auto-generated method stub

	}
	
	
	
}
