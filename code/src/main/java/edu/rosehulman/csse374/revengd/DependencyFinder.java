package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class DependencyFinder implements ICoupleFinder {

	//if another class appears in a method param or return type, 
	//add it to the dependencies
	@Override
	public void find(List<IClassContent> classes) {
		for(IClassContent c: classes) {
			ArrayList<String> dependencies = new ArrayList<String>();
			for(String method: c.getMethod()) {
				String parts[] = method.split(" ");
				if(foundDependencyInReturnType(parts[parts.length - 1], c, classes))
					dependencies.add(parts[parts.length - 1]);
				String[] params = getParams(method);
				int index = foundDependencyInParams(params, c, classes);
				for(int x = 0; x < params.length; x++)
					//System.out.println("params: " + params[x]);
					if(index != -1 && !dependencies.contains(params[index]))
						dependencies.add(params[index]);
			}
			c.setDependency(dependencies);
		}
	}
	

	
	
	
	private String[] getParams(String method) {
		String params = method.substring(method.indexOf("(") + 1, method.indexOf(")"));
		return params.split(",");
	}
	
	private int foundDependencyInParams(String[] params, IClassContent c, List<IClassContent> classes) {
		for (IClassContent allClasses: classes) {
			for (int x = 0; x < params.length; x++) {
				if ((params[x].contains(allClasses.getName())) && allClasses != c)
					return x;
			}
		}
		return -1;
	}

	//returns true if the a class is found in the method string
	private boolean foundDependencyInReturnType(String returnType, IClassContent c, List<IClassContent> classes) {
		for(IClassContent allClasses: classes) {
			if (returnType.equals(allClasses.getName()) && allClasses != c)
				return true;
		}
		return false;
	}


}
