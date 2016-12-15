package edu.rosehulman.csse374.revengd;

import java.util.List;

public class DesignParser implements IDesignParser {
	
	private List<IClassContent> classes;
	private ICodeGenerator codeGenerator;
	private String outFile;

	// TODO add parameter for recursiveParsing and accessLevel
	
	public DesignParser(ICodeGenerator codeGenerator, String outFile) {
		this.codeGenerator = codeGenerator;
		this.outFile = outFile;
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
