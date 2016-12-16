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

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames, boolean isRecursive) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
		this.isRecursive = isRecursive;
		this.classes = new LinkedList<IClassContent>();
	}

	//for each class name passed in, create a classContent to get the content from the class
	//then set the class contents fields to the parsed UML format version
	//and set the class contents methods to the parsed UML format version
	@Override
	public void parseProject() {
		
		for (String name: classNames) {
			ClassContent classContent = new ClassContent(name);
			classContent.setField(parseFields(classContent.getField()));
			classContent.setMethod(parseMethods(classContent.getMethod()));
			this.classes.add(classContent);
		}
		
		if (isRecursive) {
			for(int i = 0; i < this.classes.size(); i++) {
				for(String intName : this.classes.get(i).getInterfaces()) {
					if (!this.classNames.contains(intName)) {
						System.out.println("added");
						ClassContent classContent = new ClassContent(intName);
						classContent.setField(parseFields(classContent.getField()));
						classContent.setMethod(parseMethods(classContent.getMethod()));
						this.classes.add(classContent);
						this.classNames.add(intName);
					}
				}
				String p = this.classes.get(i).getParent();
				if (!this.classNames.contains(p) && p != null) {
					System.out.println("added");
					ClassContent classContent = new ClassContent(p);
					classContent.setField(parseFields(classContent.getField()));
					classContent.setMethod(parseMethods(classContent.getMethod()));
					this.classes.add(classContent);
					this.classNames.add(p);
					
				}
			}
		}
		
		/*findAssociations();
		findDependencies();
		for(IClassContent c: classes) {
			//System.out.println(c.getMethod());
			System.out.println(c.getDependency());
		}*/
	}
	
	//go through each method the class and transform into UML format
	private List<String> parseMethods(List<String> method) {
		ArrayList<String> methods = new ArrayList<String>();
		for (String m: method) {
			String transformed = transformMethod(m);
			if(transformed != "")
			methods.add(transformed);
		}
		return methods;
	}

	//transform the method based on the parts from contentclass.getMethod()
	//1. true or false string meaning whether it is public
	//2. method name
	//3 - ? param types
	//last part is the return type
	private String transformMethod(String method) {
		String transformed = "";
		String[] parts = method.split(" ");
		if (parts[0].equals("true")) {
			transformed += "+ ";
		}
		else {
			transformed += "- ";
		}
		
		if (parts[1].equals("<init>")) {
			return "";
		}
		else {
			transformed += parts[1];
		}
		transformed += "(";
		for (int x = 2; x < parts.length - 1; x++) {
			transformed += convertType(parts[x]) + ", ";
		}
		transformed = transformed.substring(0,  transformed.length() - 2) + ") : " + getFriendlyName(parts[parts.length - 1]);
		return transformed;
	}

	//go through each field of the class and transform it into UML format
	private List<String> parseFields(List<String> field) {
		ArrayList<String> fields = new ArrayList<String>();
		for (String f: field) {
			fields.add(transformField(f));
		}
		return fields;
	}
	
	//transform field based on the 3 parts from each field in the contentclass.getField()
	//1. true or false string meaning whether it is public
	//2. field name
	//3. field type
	private String transformField(String field) {
		String transformed = "";
		String[] parts = field.split(" ");
		if (parts[0].equals("true")) {
			transformed += "+ ";
		}
		else {
			transformed += "- ";
		}
		
		transformed += parts[1] + ": ";
		
		transformed += convertType(parts[2]);
		return transformed;
	}

	//converts java's type symbols into what they represent
	//[] means the array of parameters is null so return
	//[ signals it is the type of an array
	private String convertType(String type) {
		boolean array = false;
		if (type.equals("[]"))
			return "";
		if (type.charAt(0) == '[' && type.charAt(type.length() - 1) != ']') {
			array = true;
			type = type.substring(1);
		}
		String conversion = "";
		switch(type) {
		case "B": 
			conversion = "byte";
			break;
		case "C":
			conversion = "char";
			break;
		case "D":
			conversion = "double";
			break;
		case "F":
			conversion = "float";
			break;
		case "I":
			conversion = "int";
			break;
		case "J":
			conversion = "long";
			break;
		case "Z":
			conversion = "boolean";
			break;
		case "S":
			conversion = "short";
			break;
		default: 
			conversion = getFriendlyName(type);	
		}
		if(array)
			conversion += "[]";
		return conversion;
	}

	//since some objects are in the form java.lang.String or java/lang/String 
	//this method gets just String
	private String getFriendlyName(String type) {
		String friendly = "";
		if (type.contains("/"))
			friendly = type.substring(type.lastIndexOf('/') + 1);
		else
			friendly = type.substring(type.lastIndexOf('.') + 1);
		if (friendly.charAt(friendly.length()-1) == ']')
			friendly = friendly.substring(0, friendly.length() -1);
		return friendly;
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
