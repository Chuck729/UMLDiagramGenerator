package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public class CompOverInherParser extends PatternDecorator {
	
	public CompOverInherParser(IDesignParser d) {
		this.decorated = d;
	}

	@Override
	public void parseProject() {
		this.decorated.parseProject();
		
		List<IClassContent> classContent = this.decorated.getClassContent();
		
		for (IClassContent clazz : classContent) {
			if (clazz.getParent().equals("")) {
				
			}
		}
	}

}