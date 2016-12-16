package lab1_1;

public class AmazonLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		String[] fields = line.split(",");
		String returnString = "";
		for(int i = 0; i < fields.length; i++) {
			String[] newFields = fields[i].split("ttl");
			returnString = returnString + newFields[0].trim() + " : " + newFields[1].trim();
			if(i + 1 != fields.length) {
				returnString = returnString + "\n";
			}
		}
		return returnString;
	}

}
