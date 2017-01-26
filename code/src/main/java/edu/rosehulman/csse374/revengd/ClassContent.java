package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassContent implements IClassContent {

	private List<String> dependencies;
	private List<String> associations;
	private List<String> inheritance; 
	private List<String> implementation;
	private List<String> method;
	private List<String> field;
	private ClassNode classNode;
	private boolean isInterface;
	private boolean isAbstract;
	private List<String> removedInterfaces;
	private Map<String, String> options;
	
	private ClassReader classReader;
	
	//takes in a class reader and populates all the needed fields
	public ClassContent(String className) {
		try {
			this.classReader = new ClassReader(className);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.removedInterfaces = new LinkedList<String>();
		this.options = new HashMap<>();
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
	
	//puts all the fields in a list after parsing
	private void createFieldList(List<FieldNode> fNodes) {
		for(FieldNode fn: fNodes) {
			this.field.add(parseField(fn));
		}
	}
	
	//gets true or false value for whether field is public
	//the field name
	//and the field type
	private String parseField(FieldNode fn) {
		String type = fn.signature;
		if (type == null) {
			type = Type.getType(fn.desc).toString();
		}
		return ((fn.access & Opcodes.ACC_PUBLIC) > 0) + " " + fn.name + " " + type;
	}
	
	//puts all the methods in a list after parsing
	private void createMethodList(List<MethodNode> mNodes) {
		for(MethodNode mn: mNodes) {
			this.method.add(parseMethod(mn));
		}
	}
	
	//gets true of fase value for whether field is public
	//the method name
	//the parameter types
	//and the return type
	private String parseMethod(MethodNode mn) {
		String type = mn.signature;
		if (type == null) {
			type = (Type.getReturnType(mn.desc).toString());
		} else {
			type = type.substring(type.indexOf(')') + 1);
		}
		//System.out.println("                 method:         " + ((mn.access & Opcodes.ACC_PUBLIC) > 0) + " "+ mn.name + " args:" + parseArgs(mn) + " return:" + type);
		return ((mn.access & Opcodes.ACC_PUBLIC) > 0) + " "+ mn.name + " " + parseArgs(mn) + " " + type;
	}
	
	//gets the type of each parameter
	private List<String> parseArgs(MethodNode mn) {
		List<String> args = new ArrayList<String>();
		String type = mn.signature;
		if (type == null) {
			for (Type argType : Type.getArgumentTypes(mn.desc)) {
				args.add(argType.getClassName());
			}
			return args;
		}
		else {
			String arguments = mn.signature.substring(1, mn.signature.indexOf(')'));
			while(arguments.length() > 1) {
				if (arguments.substring(0, arguments.indexOf(';')).contains("<")) {
					args.add(arguments.substring(0, arguments.indexOf('>') + 1));
					arguments = arguments.substring(arguments.indexOf('>') + 2);
				} else {
				args.add(arguments.substring(0, arguments.indexOf(';')));
				arguments = arguments.substring(arguments.indexOf(';') + 1);
				}
			}
		}
		return args;
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
	public List<String> getMethods() {
		return this.method;
	}

	@Override
	public List<String> getFields() {
		return this.field;
	}

	@Override
	public ClassNode getClassNode() {
		return this.classNode;
	}
	
	@Override
	public void setMethod(List<String> methods) {
		this.method = methods;
	}
	
	@Override
	public void setField(List<String> fields) {
		this.field = fields;
	}

	@Override
	public String getName() {
		//return this.cutPath(this.classNode.name);
		return Type.getObjectType(classNode.name).getClassName();
	}

	@Override
	public boolean isInterface() {
		return this.classNode.access == 1537;  // Opcodes.ACC_INTERFACE;
	}

	@Override
	public boolean isAbstract() {
		return this.classNode.access == 1057;  // Opcodes.ACC_ABSTRACT;
	}

	@Override
	public String getParent() {
		if(classNode.superName == null) {
			return null;
		}
		return Type.getObjectType(classNode.superName).getClassName();
		//return this.cutPath(this.classNode.superName);
	}

	@Override
	public List<String> getInterfaces() {
		List<String> inters = this.classNode.interfaces;
		List<String> newInters = new LinkedList<String>();
		for (String inter : inters) {
			//newInters.add(this.cutPath(inter));
			String name = Type.getObjectType(inter).getClassName();
			if(!this.removedInterfaces.contains(name)) {
				newInters.add(name);
			}
		}
		return newInters;
		//return this.classNode.interfaces;
	}

	@Override
	public List<String> getAssociation() {
		return this.associations;
	}
	
	public void setAssociation(List<String> associations) {
		this.associations = associations;
	}

	@Override
	public List<String> getDependency() {
		return this.dependencies;
	}

	@Override
	public void setDependency(List<String> dependencies) {
		this.dependencies = dependencies;
	}
	
	private String cutPath(String text) {
		String[] name = text.split("/");
		return name[name.length-1];
	}

	@Override
	public void removeInterface(String intName) {
		this.removedInterfaces.add(intName);
		
	}

	@Override
	public void removeParent() {
		this.classNode.superName = null;
	}

	@Override
	public void addOption(String option, String val) {
		this.options.put(option, val);
	}

	@Override
	public Set<String> getOptionKeys() {
		return this.options.keySet();
	}

	@Override
	public String getOption(String option) {
		return this.options.get(option);
	}

}
