package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphVizBidirDecorator extends GraphVizDecorator{

	public GraphVizBidirDecorator(IGraphVizGenorator gen) {
		this.generator = gen;
	}
	
	public void findBidirectional(){
		List<GraphVizComponents> allClasses = generator.getClasses();
		for (GraphVizComponents classes : allClasses) {
			List<Edge> arrows = classes.getEdges();
			List<Edge> toDelete = new ArrayList<>();
			Edge current = null;
			for(int i = 0; i < arrows.size(); i++){
				current = arrows.get(i);
				for(int j = 0; j < arrows.size(); j++){
					if(arrows.get(j).getVertex1().equals(current.getVertex2()) && 
					   arrows.get(j).getVertex2().equals(current.getVertex1()) && 
					   arrows.get(j).getOptions().get("arrowhead").equals(current.getOptions().get("arrowhead")) &&
					   arrows.get(j).getOptions().get("style").equals(current.getOptions().get("style"))) {
							toDelete.add(arrows.get(j));
							current.appendOption("dir", "both");
					}
				}
			}
			if(toDelete.size() != 0) {
				for(Edge e : toDelete) {
					arrows.remove(e);
				}
			}
		}
	}
	
	@Override
	public void generateCode(List<IClassContent> classes) {
		this.generator.generateCode(classes);
		findBidirectional();
		
	}

}
