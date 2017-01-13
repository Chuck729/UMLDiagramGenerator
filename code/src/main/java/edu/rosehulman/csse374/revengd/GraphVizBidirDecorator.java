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
			List<Integer> toDelete = new ArrayList<Integer>();
			Edge current = null;
			for(int i = 0; i < arrows.size(); i++){
				current = arrows.get(i);
				for(int j = 0; j < arrows.size(); j++){
					if(arrows.get(j).getVertex1().equals(current.getVertex2()) && 
					   arrows.get(j).getVertex2().equals(current.getVertex1()) && 
					   arrows.get(j).getArrowType().equals(current.getArrowType())){
							toDelete.add(j);
							current.appendOption("dir", "both");
					}
				}
			}
			if(toDelete.size() != 0){
				for(int i = toDelete.size() - 1; i > -1; i--){
					arrows.remove(toDelete.get(i));
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
