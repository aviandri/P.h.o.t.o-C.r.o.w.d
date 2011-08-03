package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author uudashr@gmail.com
 *
 */
public class Twitter {
    public static TwitterQuery query(String query) {
        return new TwitterQuery(query);
    }
    
    public static class TwitterQuery {
        private String query;
        private Long sinceId;
        private Long maxId;
        private Integer page;
        private Integer rpp;
        
        public TwitterQuery(String query) {
            this.query = query;
        }
        
        public TwitterQuery sinceId(Integer sinceId) {
            this.sinceId = sinceId.longValue();
            return this;
        }
        
        public TwitterQuery sinceId(Long sinceId) {
            this.sinceId = sinceId;
            return this;
        }
        
        public TwitterQuery maxId(Long maxId) {
            this.maxId = maxId;
            return this;
        }
        
        public TwitterQuery page(Integer page) {
            this.page = page;
            return this;
        }
        
        public TwitterQuery rpp(Integer rpp) {
            this.rpp = rpp;
            return this;
        }

        public QueryResult execute() throws QueryExecutionException {
            Logger.debug("Query %1s", query);
            WSRequest req = WS.url("http://search.twitter.com/search.json").setParameter("q", query);
            
            if (sinceId != null) {
                req.setParameter("since_id", sinceId);
            }
            
            if (maxId != null) {
                req.setParameter("max_id", maxId);
            }
            
            if (page != null) {
                req.setParameter("page", page);
            }
            
            if (rpp != null) {
                req.setParameter("rpp", rpp);
            }
            Logger.debug("requesting to %1s", req.url + " params " + req.parameters);
            HttpResponse resp = req.get();
            JsonObject jsonObj = resp.getJson().getAsJsonObject();
            if (resp.getStatus() != 200) {
                String errorMessage = jsonObj.get("error").getAsString();
                String message = String.format("Found non 200 HTTP status code: statusCode = %1s; %2s", resp.getStatus(), errorMessage);
                Logger.error(message);
                throw new QueryExecutionException(resp.getStatus(), message);
            }
            return new QueryResult(jsonObj);
        }
    }
    
    /**
     * This is a wrapper of Twitter search response.
     * 
     * @author uudashr@gmail.com
     *
     */
    public static class QueryResult {
        private JsonObject jsonObj;
        
        public QueryResult(JsonObject jsonObj) {
            this.jsonObj = jsonObj;
        }

        public JsonArray getTweets() {
            return jsonObj.getAsJsonArray("results");
        }
        
        public long getMaxId() {
            return jsonObj.get("max_id").getAsLong();
        }
        
        public int getPage() {
            return jsonObj.get("page").getAsInt();
        }
        
        public boolean hasNextPage() {
            return jsonObj.get("next_page") != null;
        }
        
        public JsonObject getJsonObject() {
            return jsonObj;
        }
    }
    
    public static class QueryExecutionException extends RuntimeException {
        private Integer status;
        
        public QueryExecutionException(Integer status, String message) {
            super(message);
            this.status = status;
        }
        
        public Integer getStatus() {
            return status;
        }
    }
}
