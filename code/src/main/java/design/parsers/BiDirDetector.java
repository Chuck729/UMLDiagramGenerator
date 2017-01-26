package design.parsers;

import java.util.List;

import edu.rosehulman.csse374.revengd.IClassContent;

public class BiDirDetector extends PatternDecorator {

	private final String OPTION = "color";
	private final String VALUE = "red";

	public BiDirDetector(IDesignParser d) {
		this.decorated = d;
	}

	@Override
	public void parseProject() {
		this.decorated.parseProject();

		List<IClassContent> classContent = this.decorated.getClassContent();

		for (IClassContent clazz1 : classContent) {
			for (IClassContent clazz2 : classContent) {
				if (!clazz1.equals(clazz2)) {
					if (containsBiDirectional(clazz1, clazz2)) {
						clazz1.addOption(OPTION, VALUE);
						clazz2.addOption(OPTION, VALUE);
						// TODO still need to turn arrow red
					}
				}
			}
		}
	}

	private boolean containsBiDirectional(IClassContent c1, IClassContent c2) {
		boolean dir1 = has(c1.getFields(), c2.getName()) || has(c1.getMethods(), c2.getName());
		boolean dir2 = has(c2.getFields(), c1.getName()) || has(c2.getMethods(), c1.getName());

		return dir1 && dir2;
	}
	
	private boolean has(List<String> list, String name) {
		for (String item : list) {
			if (item.contains(name)) {
				return true;
			}
		}
		return false;
	}

}
