package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

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
		for(IClassContent clazz : this.parser.getClassContent()){
			List<String> methods = clazz.getMethods();
			List<String> fields = clazz.getFields();
			boolean returnType = false;
			boolean selfField = false;
			for(int i = 0; i < methods.size(); i++){
				if(methods.get(i).endsWith(clazz.getName())){
					returnType = true;
				}
			}
			for(int j = 0; j < fields.size(); j++){
				if(fields.get(j).endsWith(clazz.getName())){
					selfField = true;
				}
			}
			if(returnType && selfField){
				clazz.addOption("color", "blue");
			}
			
		}
//		System.out.println(this.parser.getClassContent().get(0).getName());
//		System.out.println(this.parser.getClassContent().get(0).getFields());
	}

}
