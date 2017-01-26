package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public abstract class PatternDecorator implements IDesignParser {
	
	protected IDesignParser decorated;

	
	public abstract void parseProject();

	public void generate() {
		this.decorated.generate();
	}
	
	public List<IClassContent> getClassContent() {
		return this.decorated.getClassContent();
	}

}
