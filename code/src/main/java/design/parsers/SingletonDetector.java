package design.parsers;


public class SingletonDetector extends PatternDecorator {

	IDesignParser parser;
	
	public SingletonDetector(IDesignParser parser){
		this.parser = parser;

	}
	
	@Override
	public void parseProject() {
		this.parser.parseProject();
		findSingletons();
	}

	@Override
	public void generate() {
		this.parser.generate();
		
	}
	
	public void findSingletons() {
		//have field that is of thier own type
		//have a method that returns themselves
		
		//go through list of classes
		//get class content
		//go through list of methods: if return type == class && 
		//go through list of fields: if a field == class -> singleton!
		
		//put "color" "Blue" in options
		System.out.println(this.parser.getClassContent().get(1).getField());
	}

}
