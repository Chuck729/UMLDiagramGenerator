package edu.rosehulman.csse374.revengd;

import java.io.IOException;
import java.util.List;

public interface ICodeGenerator {
	public void generateCode(List<IClassContent> classes);
	public IComponents getClasses();
	
	public void write(String file) throws IOException;
}
