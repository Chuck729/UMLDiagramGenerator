package edu.rosehulman.csse374.revengd;

import java.util.List;

import org.objectweb.asm.ClassReader;

public class DesignParser implements IDesignParser {
	
	private List<IClassContent> classes;
	private ICodeGenerator codeGenerator;
	private String outFile;
	private ClassReader reader;
	private List<String> classNames;

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile, List<String> classNames) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
		this.classNames = classNames;
	}

	@Override
	public void parseProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void findPattern() {
		// TODO Auto-generated method stub

	}

}
