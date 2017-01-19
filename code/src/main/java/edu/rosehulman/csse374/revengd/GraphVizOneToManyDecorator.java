package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphVizOneToManyDecorator extends GraphVizDecorator{

	public GraphVizOneToManyDecorator(IGraphVizGenorator gen) {
		generator = gen;

	}

	public void oneToMany(){
		List<GraphVizComponents> allClasses = generator.getClasses();
		//get class
		for(GraphVizComponents c : allClasses){
			//get class ID
			String thisClass = c.getID(c.getName());
			List<String> manyList = new ArrayList<String>();
			
			//check fields for collections or arrays, if found add class name to manyList
			for(String f : c.getFields()){
				if(f.contains("<") && f.contains(">")){
					manyList.add(f.substring(f.indexOf("<")+1, f.indexOf(">")));
				}else if(f.contains("[") && f.contains("]")){
					manyList.add(f.substring(0, f.indexOf("[")));
				}
			}
			
			//check methods for collections or arrays, if found add class name to manyList
			for(String m : c.getMethods()){
				if(m.contains("<") && m.contains(">")){
					manyList.add(m.substring(m.indexOf("<")+1, m.indexOf(">")));
				}else if(m.contains("[") && m.contains("]")){
					manyList.add(m.substring(0, m.indexOf("[")));
				}
			}
			
			//for each id in manyList, find the correct arrow that represents it and add the cardinality option
			for(String id : manyList){
				id = c.getID(id);
				for(Edge e : c.getEdges()){
					if(e.getVertex1().equals(thisClass) && e.getVertex2().equals(id)){
						e.appendOption("headlabel", "1\\:M");
					}/*else if (e.getVertex2().equals(thisClass) && e.getVertex1().equals(id)){
						e.appendOption("headlabel", "1\\:M");
					}*/
				}
			}
		}
		
	}
	
	@Override
	public void generateCode(List<IClassContent> classes) {
		this.generator.generateCode(classes);
		oneToMany();
		
	}

}
