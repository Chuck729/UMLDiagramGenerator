package edu.rosehulman.csse374.revengd;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class ClassContent implements IClassContent {

	private List<String> association;
	private List<String> inheritance; 
	private List<String> implementation;
	private List<String> dependency;
	private List<String> aggregation;
	private List<String> composition;
	private List<String> method;
	private List<String> field;
	private String name;
	
	private ClassReader classReader;
	
	//takes in a class reader and populates all the needed fields
	public ClassContent(ClassReader classReader) {
		this.classReader = classReader;
		populateFields();
	}
	
	//populates classNode and gets all the needed fields
	private void populateFields() {
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
		this.name = classNode.name.substring(classNode.name.lastIndexOf('/'));
		this.field = classNode.fields;
		this.method = classNode.methods;
	}
	
	
	@Override
	public List<String> getAssociation() {
		return this.association;
	}

	@Override
	public List<String> getInheritance() {
		return this.inheritance;
	}

	@Override
	public List<String> getImplementation() {
		return this.implementation;
	}

	@Override
	public List<String> getDependency() {
		return this.dependency;
	}

	@Override
	public List<String> getAggregation() {
		return this.aggregation;
	}

	@Override
	public List<String> getComposition() {
		return this.composition;
	}

	@Override
	public List<String> getMethod() {
		return this.method;
	}

	@Override
	public List<String> getField() {
		return this.field;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
