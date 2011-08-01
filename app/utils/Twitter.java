package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;

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

        public TwitterQueryResult execute() {
            Logger.debug("Query %1s", query);
            String encodedQuery = query;
            try {
                encodedQuery = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Logger.error("Unsupported encoding UTF-8", e);
            }
            Logger.debug("Encoded query %1s", encodedQuery);
            
            WSRequest req = WS.url("http://search.twitter.com/search.json").setParameter("q", encodedQuery);
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
                String errorMesssage = jsonObj.get("error").getAsString();
                Logger.error("Got non 200 HTTP Status code: %1s error=%2s", resp.getStatus(), errorMesssage);
            }
            return new TwitterQueryResult(jsonObj);
        }
    }
}
