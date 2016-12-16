package edu.rosehulman.csse374.revengd;

import java.util.List;

import org.objectweb.asm.tree.ClassNode;

public interface IClassContent {
	public List<String> getAssociation();
	
	public List<String> getInheritance();
	
	public List<String> getImplementation();
	
	public List<String> getDependency();
	
	public List<String> getAggregation();
	
	public List<String> getComposition();
	
	public List<String> getMethod();
	
	public List<String> getField();

	public ClassNode getClassNode();
	
}
