import edu.rosehulman.csse374.revengd.IDesignParser;
import edu.rosehulman.csse374.revengd.PatternDetector;

public class SingletonDetector extends PatternDetector {

	IDesignParser parser;
	
	public SingletonDetector(IDesignParser parser){
		this.parser = parser;
	}
	
	@Override
	public void parseProject() {
		this.parser.parseProject();
		
	}

	@Override
	public void generate() {
		this.parser.generate();
		
	}

}
