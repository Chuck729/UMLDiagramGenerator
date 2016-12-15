package edu.rosehulman.csse374.revengd;

import java.util.List;

public abstract class GraphVizDecorator implements IGraphVizGenorator {
	
	@Override
	public abstract void generateCode(List<IClassContent> classes);

}
