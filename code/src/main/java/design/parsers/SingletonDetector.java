package design.parsers;


public class SingletonDetector extends PatternDecorator {

	IDesignParser parser;
	
	public SingletonDetector(IDesignParser parser){
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
