package lab1_1;

public class GoogleLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		return line.replace('-', ':');
	}

}
