package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.templates.JavaExtensions;
import play.templates.BaseTemplate.RawData;

/**
 * @author uudashr@gmail.com
 *
 */
public class LinkifyExtensions extends JavaExtensions {
    
    public static RawData linkify(String message) {
        Pattern pattern = Pattern.compile("(http|https|ftp)\\:\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?\\/?([a-zA-Z0-9\\-\\._\\?\\,\\'\\/\\\\\\+&amp;%\\$#\\=~])*");
        Matcher matcher = pattern.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = message.substring(matcher.start(), matcher.end());
            matcher.appendReplacement(sb, "<a href=\"" + match + "\">" + match + "</a>");
        }
        matcher.appendTail(sb);
        return raw(sb.toString());
    }
}
