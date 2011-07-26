package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static String regex = "http://(twitpic.com|lockerz.com)[/\\w]*";
	
	public static String[] grabImageServiceURLs(String input){
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(input);
		System.out.println(m.matches());
		List<String> urls = new ArrayList<String>();
		while(m.find()){
			urls.add(m.group());
		}
		return urls.toArray(new String[urls.size()]);
	}
}
