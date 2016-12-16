package lab1_1;

public class GrouponLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		// got this regex from http://stackoverflow.com/questions/10079415/splitting-a-string-with-multiple-spaces
		String[] fields = line.split(" +"); 
		return fields[0].trim() + " : " + fields[1].trim();
	}

}
