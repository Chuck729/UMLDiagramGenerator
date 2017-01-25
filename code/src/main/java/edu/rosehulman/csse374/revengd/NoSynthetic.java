package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class NoSynthetic implements IModification {

	@Override
	public List<String> modify(List<String> parsedClassContent) {
		ArrayList<String> modified = new ArrayList<String>();
		for(String s: parsedClassContent) {
			if(!s.contains("$")) {
				modified.add(s);
			}
		}
		return modified;
	}

}
