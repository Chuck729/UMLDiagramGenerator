package lab1_1;

public class MicrosoftLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		String[] fields = line.split(",");
		return fields[0].trim() + " : " + fields[1].trim();
	}
	
	private GoogleLineParser testDep(GrouponLineParser g) {
		return new GoogleLineParser();
	}

}
