package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * This is a wrapper of Twitter search response.
 * 
 * @author uudashr@gmail.com
 *
 */
public class TwitterQueryResult {
    private JsonObject jsonObj;
    
    public TwitterQueryResult(JsonObject jsonObj) {
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
