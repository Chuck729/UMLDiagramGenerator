package edu.rosehulman.csse374.revengd;

import java.util.List;

public interface IGraphVizGenorator extends ICodeGenerator {
	public List<GraphVizComponents> getClasses();
	public void addOption(String option, String value);
}
