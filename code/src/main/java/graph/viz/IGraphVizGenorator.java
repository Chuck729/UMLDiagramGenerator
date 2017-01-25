package graph.viz;

import java.util.List;

public interface IGraphVizGenorator extends ICodeGenerator {
	public List<GraphVizComponents> getClasses();
	public void addOption(String option, String value);
}
