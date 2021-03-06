package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class MethodConvert extends ConvertInternalTypes{

	public MethodConvert() {
	}

	@Override
	public List<String> convert(List<String> methods) {
		return parseMethods(methods);
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
			//System.out.print("before: " + method);
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
			//remove [ from first param and the , or ] from every param
			if (parts[2].length() > 2)
				parts[2] = parts[2].substring(1);
			for (int y = 2; y < parts.length - 1; y++) {
				if (parts[y].length() > 2)
				parts[y] = parts[y].substring(0, parts[y].length() -1);
			}
			for (int x = 2; x < parts.length - 1; x++) {
				transformed += convertType(parts[x]) + ", ";
			}
			transformed = transformed.substring(0,  transformed.length() - 2) + ") : " + convertType(parts[parts.length - 1]);
			return transformed;
		}
	
}
