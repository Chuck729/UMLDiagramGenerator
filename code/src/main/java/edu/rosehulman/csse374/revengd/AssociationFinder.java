package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class AssociationFinder implements ICoupleFinder {

	//if the field is has a type of another class in the UML
	//add it to the associations //
	@Override
	public List<String> find(List<IClassContent> classes, List<String> classNames, boolean isRecursive) {
		List<String> newClasses = new ArrayList<>();
		for(IClassContent c : classes) {
			ArrayList<String> associations = new ArrayList<String>();

			for(String field: c.getFields()) {
				String parts[] = field.split(" ");
				String f = parts[parts.length -1];
				if (foundAssociations(f , c, classes) && !associations.contains(f)) {
					if (f.contains("<"))
						if(f.contains("Map")) {
							String new1 = f.substring(f.indexOf("<") + 1, f.indexOf(","));
							String new2 = f.substring(f.indexOf(",") + 1, f.indexOf(">"));
							if(classNames.contains(new1)) {
								associations.add(new1);
							}
							if(classNames.contains(new2)) {
								associations.add(new2);
							}
						} else {
							associations.add(f.substring(f.indexOf("<") + 1, f.indexOf(">")));
						}
					else
						associations.add(f);
				}
			}
			c.setAssociation(associations);
		}
		return newClasses;
	}

	//takes a className that is a field type
	//returns if that class was found in the UML
	private  boolean foundAssociations(String fieldName, IClassContent c, List<IClassContent> classes) {
		for(IClassContent allClasses: classes) {
			if (fieldName.contains(allClasses.getName()) && allClasses != c)
				return true;
		}
		return false;
	}
}
