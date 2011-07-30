package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a query builder for twitter search. We can specify such as keyword,
 * date range and location.
 * 
 * @author uudashr@gmail.com
 * 
 */
public class SearchQueryBuilder {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private String keyword;
    private String since;
    private String until;
    private String location;
    
    /**
     * Construct by specifying the keyword.
     * 
     * @param keyword is the keyword.
     */
    public SearchQueryBuilder(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Set since criteria. The format will be yyyy-MM-dd.
     * 
     * @param date is the formatted {@link String} date.
     * return the current builder.
     */
    public SearchQueryBuilder since(String date) {
        since = date;
        return this;
    }

    /**
     * Set since criteria.
     * 
     * @param date is the date.
     * @return the current builder.
     */
    public SearchQueryBuilder since(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return since(dateFormat.format(date));
    }
    
    /**
     * Set until criteria. The format of <code>date</code> will be yyyy-MM-dd.
     * 
     * @param date is the formatted {@link String} of date.
     * @return the current builder.
     */
    public SearchQueryBuilder until(String date) {
        until = date;
        return this;
    }
    
    /**
     * Set the until criteria.
     * 
     * @param date is the date.
     * @return the current builder.
     */
    public SearchQueryBuilder until(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return until(dateFormat.format(date));
    }
    
    /**
     * Set near location criteria.
     * 
     * @param location is the location.
     * @return the current builder.
     */
    public SearchQueryBuilder near(String location) {
        // remove the (") character.
        this.location = location.replaceAll("\"", "");
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(keyword);
        if (location != null) {
            builder.append(" near:");
            if (location.contains(" ")) {
                builder.append("\"").append(location).append("\"");
            } else {
                builder.append(location);
            }
        }
        
        if (since != null) {
            builder.append(" since:").append(since);
        }
        
        if (until != null) {
            builder.append(" until:").append(until);
        }
        
        return builder.toString();
    }
}
