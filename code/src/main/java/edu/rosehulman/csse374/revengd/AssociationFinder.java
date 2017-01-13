package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class AssociationFinder implements ICoupleFinder {

	//if the field is has a type of another class in the UML
	//add it to the associations
	@Override
	public void find(List<IClassContent> classes) {
		for(IClassContent c : classes) {
			ArrayList<String> associations = new ArrayList<String>();

			for(String field: c.getField()) {
				String parts[] = field.split(" ");
				String f = parts[parts.length -1];
				if (foundAssociations(f , c, classes)) {
					associations.add(f);
				}
			}
			c.setAssociation(associations);
		}
	}

	//takes a className that is a field type
	//returns if that class was found in the UML
	private  boolean foundAssociations(String fieldName, IClassContent c, List<IClassContent> classes) {
		for(IClassContent allClasses: classes) {
			if (allClasses.getName().contains(fieldName) && allClasses != c)
				return true;
		}
		return false;
	}
}
