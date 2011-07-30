package util;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.SearchQueryBuilder;

/**
 * @author uudashr@gmail.com
 *
 */
public class SearchQueryBuilderTest extends UnitTest {
    private SearchQueryBuilder builder;
    
    @Before
    public void prepare() {
        builder = new SearchQueryBuilder("wedding");
    }
    
    @Test
    public void simpleQuery() {
        assertEquals("wedding", builder.toString());
    }
    
    @Test
    public void querySince() {
        builder.since("2011-07-30");
        assertTrue(builder.toString().contains("since:2011-07-30"));
    }
    
    @Test
    public void queryUntil() {
        builder.until("2011-07-30");
        assertTrue(builder.toString().contains("until:2011-07-30"));
    }
    
    @Test
    public void queryLocation() {
        builder.near("san francisco");
        assertTrue(builder.toString().contains("near:\"san francisco\""));
    }
    
    @Test
    public void queryLocationNoQuote() {
        builder.near("NYC");
        assertTrue(builder.toString().contains("near:NYC"));
    }
}
