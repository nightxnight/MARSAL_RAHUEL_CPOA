package utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TextFormat {
	
	public static final Pattern VALID_TEXT_WORD = Pattern.compile("^[a-zA-Z\\u00C0-\\u00FF\\-]*$");
	public static final Pattern VALID_TEXT_SENTENCE = Pattern.compile("^[a-zA-Z\\s\\u00C0-\\u00FF\\-]*$");
	
	public static boolean checkWord(String word) {
        Matcher matcher = VALID_TEXT_WORD.matcher(word);
        return matcher.find();
	}
	
	public static boolean checkSentence(String sentence) {
        Matcher matcher = VALID_TEXT_SENTENCE.matcher(sentence);
        return matcher.find();
	}
	
}
