package design.parsers;

public class BiDirectionalDetector extends PatternDecorator {

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
