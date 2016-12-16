package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames, boolean isRecursive) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
		this.isRecursive = isRecursive;
		this.classes = new LinkedList<IClassContent>();
		this.methodConvert = new MethodConvert();
		this.fieldConvert = new FieldConvert();
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
		
		/*findAssociations();
		findDependencies();
		for(IClassContent c: classes) {
			System.out.println(c.getMethod());
			System.out.println(c.getDependency());
		}*/
	}
	
	@Override
	public void generate() {
		this.codeGenerator.generateCode(this.classes);
	}

	@Override
	public void findPattern() {
		// TODO Auto-generated method stub

	}
	
	//if the field is has a type of another class in the UML
	//add it to the associations
	/*private void findAssociations() {
		for(IClassContent c : classes) {
			ArrayList<String> associations = new ArrayList<String>();
			for(String field: c.getField()) {
				String parts[] = field.split(" ");
				if (foundAssociations(parts[parts.length - 1] , c))
					associations.add(parts[parts.length - 1]);
			}
			c.setAssociation(associations);
		}
	}*/

	//takes a className that is a field type
	//returns if that class was found in the UML
	/*private  boolean foundAssociations(String fieldName, IClassContent c) {
		for(IClassContent allClasses: classes) {
			if (allClasses.getName().equals(fieldName) && allClasses != c)
				return true;
		}
		return false;
	}*/
	
	//if another class appears in a method param or return type, 
	//add it to the dependencies
	/*private void findDependencies() {
		for(IClassContent c: classes) {
			ArrayList<String> dependencies = new ArrayList<String>();
			for(String method: c.getMethod()) {
				String parts[] = method.split(" ");
				if(foundDependencyInReturnType(parts[parts.length - 1], c))
					dependencies.add(parts[parts.length - 1]);
				String[] params = getParams(method);
				int index = foundDependencyInParams(params, c);
//				for(int x = 0; x < params.length; x++)
//					System.out.println("params: " + params[x]);
					if(index != -1 && !dependencies.contains(params[index]))
						dependencies.add(params[index]);
			}
			c.setDependency(dependencies);
		}
	}*/
	
	/*private String[] getParams(String method) {
		String params = method.substring(method.indexOf("(") + 1, method.indexOf(")"));
		return params.split(",");
	}*/
	
	/*private int foundDependencyInParams(String[] params, IClassContent c) {
		for (IClassContent allClasses: classes) {
			for (int x = 0; x < params.length; x++) {
				if ((params[x].contains(allClasses.getName())) && allClasses != c)
					return x;
			}
		}
		return -1;
	}*/

	//returns true if the a class is found in the method string
	/*private boolean foundDependencyInReturnType(String returnType, IClassContent c) {
		for(IClassContent allClasses: classes) {
			if (returnType.equals(allClasses.getName()) && allClasses != c)
				return true;
		}
		return false;
	}*/

}
