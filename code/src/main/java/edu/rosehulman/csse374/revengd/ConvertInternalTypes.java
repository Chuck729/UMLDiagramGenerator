package edu.rosehulman.csse374.revengd;

import java.util.List;

public abstract class ConvertInternalTypes {
	
	//converts java's type symbols into what they represent
	//[] means the array of parameters is null so return
	//[ signals it is the type of an array
	protected String convertType(String type) {
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
	protected String getFriendlyName(String type) {
		if(type.equals("void")) {
			return type;
		}
		type = type.replace(";L", ",");
		type = type.replace(";", "");
		type = type.replace("/", ".");
		if (type.substring(0, 1).equals(type.substring(0, 1).toUpperCase())) {
			type = type.substring(1);
		}
		type = type.replaceAll("<.", "<");
		type = type.replaceAll(", [a-zA-Z0-9]", ", ");
		return type;
	}
	
	public abstract List<String> convert(List<String> string);


}
