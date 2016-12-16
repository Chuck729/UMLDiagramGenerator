package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
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
	}

	@Override
	public void parseProject() {
		
		for (String name: classNames) {
			ClassContent classContent = new ClassContent(name);
			parseFields(classContent.getField());
			parseMethods(classContent.getMethod());
		}
	}
	
	private void parseMethods(List<String> method) {
		
	}

	private void parseFields(List<String> field) {
		ArrayList<String> fields = new ArrayList<String>();
		for (String f: field) {
			fields.add(transformField(f));
		}
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
		System.out.println("Transformed: " +transformed);
		return transformed;
	}

	private String convertType(String type) {
		boolean array = false;
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
		return type.substring(type.lastIndexOf('/') + 1, type.length() - 1);
	}

	@Override
	public void generate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void findPattern() {
		// TODO Auto-generated method stub

	}

}
