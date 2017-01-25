package graph.viz;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public interface ICodeGenerator {
	public void generateCode(List<IClassContent> classes);
	
	public void write(String file) throws IOException;
}
