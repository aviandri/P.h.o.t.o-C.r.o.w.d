package utils;

/**
 * @author uudashr@gmail.com
 *
 */
public class SearchQueryBuilder {
    private String keyword;
    private String since;
    private String until;
    private String location;
    
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
        this.since = date;
        return this;
    }

    /**
     * Set until criteria. The format of <code>date</code> will be yyyy-MM-dd.
     * 
     * @param date is the formatted {@link String} of date.
     * @return the current builder.
     */
    public SearchQueryBuilder until(String date) {
        this.until = date;
        return this;
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
