package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassContent implements IClassContent {

	private List<String> association;
	private List<String> inheritance; 
	private List<String> implementation;
	private List<String> dependency;
	private List<String> aggregation;
	private List<String> composition;
	private List<String> method;
	private List<String> field;
	private ClassNode classNode;
	
	private ClassReader classReader;
	
	//takes in a class reader and populates all the needed fields
	public ClassContent(String className) {
		try {
			this.classReader = new ClassReader(className);
		} catch (IOException e) {
			e.printStackTrace();
		}
		populateFields();
	}
	
	//populates classNode and gets all the needed fields
	@SuppressWarnings("unchecked")
	private void populateFields() {
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
		this.classNode = classNode;
		this.method = new ArrayList<String>();
		this.field = new ArrayList<String>();
		createFieldList((List<FieldNode>)classNode.fields);
		createMethodList((List<MethodNode>)classNode.methods);
	}
	
	private void createFieldList(List<FieldNode> fNodes) {
		for(FieldNode fn: fNodes) {
			this.field.add(parseField(fn));
		}
	}
	
	
	private String parseField(FieldNode fn) {
		return ((fn.access & Opcodes.ACC_PUBLIC) > 0) + " " + fn.name + " " + Type.getType(fn.desc);
	}
	
	private void createMethodList(List<MethodNode> mNodes) {
		for(MethodNode mn: mNodes) {
			this.method.add(parseMethod(mn));
		}
	}
	
	
	private String parseMethod(MethodNode mn) {
		return ((mn.access & Opcodes.ACC_PUBLIC) > 0) + " "+ mn.name + " " + parseArgs(mn) + " " + (Type.getReturnType(mn.desc).getClassName());
	}
	
	private List<String> parseArgs(MethodNode mn) {
		List<String> args = new ArrayList<String>();
		for (Type argType : Type.getArgumentTypes(mn.desc)) {
			args.add(argType.getClassName());
		}
		return args;
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
	public ClassNode getClassNode() {
		return this.classNode;
	}
	
	public void setMethod(List<String> methods) {
		this.method = methods;
	}
	
	public void setField(List<String> fields) {
		this.field = fields;
	}

}
