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
		for (GraphVizComponents classes1 : allClasses) {
			List<Edge> arrows1 = classes1.getEdges();
			List<Edge> toDelete = new ArrayList<>();
			GraphVizComponents currentClass = null;
			for (GraphVizComponents classes2 : allClasses) {
				
				boolean leftMany = false;
				boolean rightMany = false;
				
				List<Edge> arrows2 = classes2.getEdges();
				currentClass = classes2;
				Edge current = null;
				for(int i = 0; i < arrows1.size(); i++){
					current = arrows1.get(i);
					for(int j = 0; j < arrows2.size(); j++){
						if(arrows2.get(j).getVertex1().equals(current.getVertex2()) && 
								arrows2.get(j).getVertex2().equals(current.getVertex1()) && 
								arrows2.get(j).getOptions().get("arrowhead").equals(current.getOptions().get("arrowhead")) &&
								arrows2.get(j).getOptions().get("style").equals(current.getOptions().get("style"))) {
							if (current.getOptions().containsKey("taillabel") && current.getOptions().get("taillabel").equals("1\\:M")) {
								leftMany = true;
							}
							if (arrows2.get(j).getOptions().containsKey("taillabel") && arrows2.get(j).getOptions().get("taillabel").equals("1\\:M")) {
								rightMany = true;
							}
							toDelete.add(arrows2.get(j));
							current.appendOption("dir", "both");
							if(leftMany && rightMany) {
								current.appendOption("taillabel", "M\\:M");
							} else if (leftMany) {
								current.appendOption("taillabel", "M\\:1");
							} else if (rightMany) {
								current.appendOption("taillabel", "1\\:M");
							}
						}
					}
				}
			}
			if(toDelete.size() != 0) {
				for(Edge e : toDelete) {
					currentClass.getEdges().remove(e);
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
