package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public abstract class PatternDecorator implements IDesignParser {
	
	protected IDesignParser decorated;

	@Override
	public abstract void parseProject();

	@Override
	public void generate() {
		this.decorated.generate();
	}
	
	@Override
	public List<IClassContent> getClassContent() {
		return this.decorated.getClassContent();
	}

}
