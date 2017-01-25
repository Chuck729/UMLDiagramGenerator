package graph.viz;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public abstract class GraphVizDecorator implements IGraphVizGenorator {
	
	protected IGraphVizGenorator generator;
	
	@Override
	public abstract void generateCode(List<IClassContent> classes);
	
	@Override
	public List<GraphVizComponents> getClasses() {
		return this.generator.getClasses();
	}
	
	@Override
	public void write(String file) throws IOException {
		this.generator.write(file);
	}
	
	@Override 
	public void addOption(String option, String value) {
		this.generator.addOption(option, value);
	}
	

}
