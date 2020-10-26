package utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ImageFileFormat {

	private static final Pattern VALID_FILE_NAME_REGEX = Pattern.compile("([^\\s])+(\\.(?i)(png|gif|jpg|bmp)$)", Pattern.CASE_INSENSITIVE);
	
	public static boolean check(String fileName) {
        Matcher matcher = VALID_FILE_NAME_REGEX.matcher(fileName);
        return matcher.find();
	}
}
