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

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
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
			findAssociations();
			findDependencies();
			this.classes.add(classContent);
		}
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
		transformed = transformed.substring(0,  transformed.length() - 2) + ") : " + parts[parts.length - 1];
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
		if (type.charAt(0) == '[') {
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
		if (type.contains("/"))
			return type.substring(type.lastIndexOf('/') + 1, type.length() - 1);
		else
			return type.substring(type.lastIndexOf('.') + 1, type.length() - 1);
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
	private void findAssociations() {
		for(IClassContent c : classes) {
			ArrayList<String> associations = new ArrayList<String>();
			for(String field: c.getField()) {
				String parts[] = field.split(" ");
				if (foundAssociations(parts[parts.length - 1] , c))
					associations.add(parts[parts.length - 1]);
			}
			c.setAssociation(associations);
		}
	}

	//takes a className that is a field type
	//returns if that class was found in the UML
	private  boolean foundAssociations(String fieldName, IClassContent c) {
		for(IClassContent allClasses: classes) {
			if (allClasses.getName().equals(fieldName) && allClasses != c)
				return true;
		}
		return false;
	}
	
	//if another class appears in a method param or return type, 
	//add it to the dependencies
	private void findDependencies() {
		for(IClassContent c: classes) {
			ArrayList<String> dependencies = new ArrayList<String>();
			for(String method: c.getMethod()) {
				if(foundDependency(method, c))
					dependencies.add(c.getName());
			}
			c.setDependency(dependencies);
		}
	}
	
	//returns true if the a class is found in the method string
	private boolean foundDependency(String methodString, IClassContent c) {
		for(IClassContent allClasses: classes) {
			if (methodString.contains(allClasses.getName()) && allClasses != c)
				return true;
		}
		return false;
	}

}
