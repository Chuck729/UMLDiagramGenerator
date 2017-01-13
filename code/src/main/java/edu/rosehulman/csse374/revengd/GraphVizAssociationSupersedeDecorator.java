package edu.rosehulman.csse374.revengd;

import java.util.ArrayList;
import java.util.List;

public class GraphVizAssociationSupersedeDecorator extends GraphVizDecorator {
	
	public GraphVizAssociationSupersedeDecorator(IGraphVizGenorator gen) {
		this.generator = gen;
	}
	
	public void findDoubleArrow(){
		List<GraphVizComponents> allClasses = generator.getClasses();
		for (GraphVizComponents classes : allClasses) {
			List<Edge> arrows = classes.getEdges();
			List<Edge> toDelete = new ArrayList<>();
			Edge current = null;
			for(int i = 0; i < arrows.size(); i++){
				current = arrows.get(i);
				for(int j = 0; j < arrows.size(); j++){
					if(arrows.get(j).getVertex1().equals(current.getVertex1()) && 
							   arrows.get(j).getVertex2().equals(current.getVertex2())){ 
						if(arrows.get(j).getOptions().get("style").equals("solid") && current.getOptions().get("style").equals("dashed")){
							toDelete.add(current);
						}
					}
				}
			}
			if(toDelete.size() != 0){
				for(Edge e : toDelete) {
					arrows.remove(e);
				}
			}
			
		}
	}
	@Override
	public void generateCode(List<IClassContent> classes) {
		this.generator.generateCode(classes);
		findDoubleArrow();
	}

}
