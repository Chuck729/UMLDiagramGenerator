package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class FieldConvert extends ConvertInternalTypes{

	public FieldConvert() {
		
	}
	
	@Override
	public List<String> convert(List<String> fields) {
		return parseFields(fields);
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

}
