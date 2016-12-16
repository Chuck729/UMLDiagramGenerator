package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class ProtectedOnly implements IModification {

	@Override
	public List<String> modify(List<String> parsedClassContent) {
		ArrayList<String> modified = new ArrayList<String>();
		for(String s: parsedClassContent) {
			if(s.charAt(0) == '#')
			{
				modified.add(s);
			}
		}
		return modified;
	}

}
