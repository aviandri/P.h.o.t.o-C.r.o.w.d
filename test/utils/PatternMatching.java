package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import play.test.UnitTest;

/**
 * @author uudashr@gmail.com
 *
 */
public class PatternMatching extends UnitTest {
    private Pattern pattern = Pattern.compile("http://twitpic.com/\\w+(/\\w+)*");
    
    @Test
    public void testNotMatch() {
        assertFalse(pattern.matcher("http://lockerz.com/asdf").matches());
    }
    
    @Test
    public void testMatch() {
        assertTrue(pattern.matcher("http://twitpic.com/qwert").matches());
    }
    
    @Test
    public void testTwitpicURLs() {
        assertFalse(pattern.matcher("http://twitpic.com/qwert http://twitpic.com/qwert").matches());
    }
    
    @Test
    public void testTwitpicWithWhitespace() {
        assertFalse(pattern.matcher("http://twitpic.com/qwert ").matches());
    }
}
