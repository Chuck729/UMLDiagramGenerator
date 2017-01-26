package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class DependencyFinder implements ICoupleFinder {

	//if another class appears in a method param or return type, 
	//add it to the dependencies
	@Override
	public List<String> find(List<IClassContent> classes, List<String> classNames, boolean isRecursive) {
		List<String> newClasses = new ArrayList<>();
		for(IClassContent c: classes) {
			ArrayList<String> dependencies = new ArrayList<String>();
			for(String method: c.getMethods()) {
//				if(isRecursive) {
//					List<String> temp = (checkArgsAndReturn(classNames, method));
//					newClasses.addAll(temp);
//					classNames.addAll(temp);
//				}
				for (IClassContent dpClass: classes) {
					if (method.contains(dpClass.getName()) && !dpClass.getName().equals(c.getName())) {
						if (!dependencies.contains(dpClass.getName())) {
							dependencies.add(dpClass.getName());
						}
						
					}
				}
			}
			c.setDependency(dependencies);
		}
		return newClasses;
	}
	
	public List<String> checkArgsAndReturn(List<String> classNames, String m) {
		ArrayList<String> newClasses = new ArrayList<String>();
		String method = m;
		String args = method.substring(method.indexOf('(') + 1, method.indexOf(')'));
		String returnType = method.substring(method.indexOf(':') + 2);
		String[] params = args.split(",");
		for (int x = 0; x < params.length; x++) {
			if (params[x].length() > 0) {
				params[x] = params[x].trim();
				if (!classNames.contains(params[x]) && params[x].contains(".") && !newClasses.contains(params[x]))
					newClasses.add(params[x]);
			}
		}
		if (!(returnType.equals("void") || !returnType.contains(".") || 
				returnType.contains(".Class<>") || classNames.contains(returnType) || newClasses.contains(returnType)))
			newClasses.add(returnType);
		return newClasses;
	}
}
