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
		String friendly = "";
		if (type.contains("/"))
			friendly = type.substring(type.lastIndexOf('/') + 1);
		else
			friendly = type.substring(type.lastIndexOf('.') + 1);
		if (friendly.charAt(friendly.length()-1) == ']')
			friendly = friendly.substring(0, friendly.length() -1);
		return friendly;
	}
	
	public abstract List<String> convert(List<String> string);


}
