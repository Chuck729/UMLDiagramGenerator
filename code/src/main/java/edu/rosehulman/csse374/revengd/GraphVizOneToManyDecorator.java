package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.List;

public class GraphVizOneToManyDecorator extends GraphVizDecorator{

	ICodeGenerator generator;
	public GraphVizOneToManyDecorator(ICodeGenerator gen) {
		generator = gen
;
	}

	@Override
	public void generateCode(List<IClassContent> classes) {
		// TODO Auto-generated method stub
		
	}

}
