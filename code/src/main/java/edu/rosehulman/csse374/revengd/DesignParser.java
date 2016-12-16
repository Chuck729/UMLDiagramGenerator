package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

	@Override
	public void parseProject() {
		
		for (String name: classNames) {
			ClassContent classContent = new ClassContent(name);
			classContent.setField(parseFields(classContent.getField()));
			classContent.setMethod(parseMethods(classContent.getMethod()));
			this.classes.add(classContent);
		}
	}
	
	private List<String> parseMethods(List<String> method) {
		ArrayList<String> methods = new ArrayList<String>();
		for (String m: method) {
			String transformed = transformMethod(m);
			if(transformed != "")
			methods.add(transformed);
		}
		return methods;
	}

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

	private List<String> parseFields(List<String> field) {
		ArrayList<String> fields = new ArrayList<String>();
		for (String f: field) {
			fields.add(transformField(f));
		}
		return fields;
	}
	
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

}
