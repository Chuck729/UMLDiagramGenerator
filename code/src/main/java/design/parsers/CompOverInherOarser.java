package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public class CompOverInherOarser extends PatternDecorator {
	
	public CompOverInherOarser(IDesignParser d) {
		this.decorated = d;
	}

	@Override
	public void parseProject() {
		this.decorated.parseProject();
		
		List<IClassContent> classContent = this.decorated.getClassContent();
		
		for (IClassContent clazz : classContent) {
			
		}
	}

}
