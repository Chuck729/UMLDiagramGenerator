package graph.viz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

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
							if (current.getOptions().containsKey("headlabel") && current.getOptions().get("headlabel").equals("1\\:M")) {
								leftMany = true;
							}
							if (arrows2.get(j).getOptions().containsKey("headlabel") && arrows2.get(j).getOptions().get("headlabel").equals("1\\:M")) {
								rightMany = true;
							}
							toDelete.add(arrows2.get(j));
							current.appendOption("dir", "both");
							current.appendOption("arrowtail", current.getOptions().get("arrowhead"));
							if(leftMany && rightMany) {
								current.appendOption("headlabel", "N\\:M");
							} else if (leftMany) {
								current.appendOption("headlabel", "1\\:M");
							} else if (rightMany) {
								current.appendOption("headlabel", "M\\:1");
							}
						}
					}
				}
				for(Edge e : toDelete) {
					classes2.getEdges().remove(e);
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
