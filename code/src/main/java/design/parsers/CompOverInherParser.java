package design.parsers;

import java.io.IOException;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import edu.rosehulman.csse374.revengd.ClassContent;
import edu.rosehulman.csse374.revengd.IClassContent;

public class CompOverInherParser extends PatternDecorator {
	
	private final String OPTION = "color";
	private final String VALUE = "orange";
	
	public CompOverInherParser(IDesignParser d) {
		this.decorated = d;
	}

	@Override
	public void parseProject() {
		this.decorated.parseProject();
		
		List<IClassContent> classContent = this.decorated.getClassContent();
		
		for (IClassContent clazz : classContent) {
			if (clazz.getParent() == null || clazz.getParent().equals("java.lang.Object")) {
				// do nothing
			} else {
				String parent = clazz.getParent();
				IClassContent parentContent = new ClassContent(parent);
				if (!parentContent.isAbstract()) {
					System.out.println("----------------------------------------------");
					clazz.addOption(OPTION, VALUE);
				}
			}
		}
	}

}
