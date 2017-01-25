package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public interface IDesignParser {
	public void parseProject();
	public void generate();
	public List<IClassContent> getClassContent();
}
