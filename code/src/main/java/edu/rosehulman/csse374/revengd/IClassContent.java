package edu.rosehulman.csse374.revengd;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.tree.ClassNode;

public interface IClassContent {
	public List<String> getAssociation();
	
	public void setAssociation(List<String> associations);
	
	public List<String> getInheritance();
	
	public List<String> getImplementation();
	
	public List<String> getDependency();
	
	public void setDependency(List<String> dependencies);
	
	public List<String> getMethod();
	
	public List<String> getField();
	
	public String getName();

	public ClassNode getClassNode();
	
	public boolean isInterface();
	
	public boolean isAbstract();
	
	public String getParent();
	
	public List<String> getInterfaces();
	
	public void setMethod(List<String> methods);
	
	public void setField(List<String> fields);

	public void removeInterface(String intName);

	public void removeParent();
	
	public void addOption(String option, String val);
	
	public Map<String, String> getOptions();
	
}
