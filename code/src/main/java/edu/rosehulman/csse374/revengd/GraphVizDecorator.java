package edu.rosehulman.csse374.revengd;

import java.util.List;

public abstract class GraphVizDecorator implements IGraphVizGenorator {
	
	protected IGraphVizGenorator generator;
	
	@Override
	public abstract void generateCode(List<IClassContent> classes);
	
	@Override
	public IComponents getClasses() {
		return (IComponents) this.generator.getClasses();
	}

}
