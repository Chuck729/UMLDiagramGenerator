package edu.rosehulman.csse374.revengd;

public class BiDirectionalDetector extends PatternDetector {

	IDesignParser parser;
	
	public BiDirectionalDetector(IDesignParser parser){
		this.parser = parser;
	}
	
	@Override
	public void parseProject() {
		this.parser.parseProject();
		
	}

	@Override
	public void generate() {
		this.parser.generate();
		
	}

}
