package utils;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class TwitterUtil {

    public static JsonArray searchTwitter(String q, Long sinceId) {
        String callUrl = "http://search.twitter.com/search.json?q=" + q
                + "&since_id=" + sinceId;
        Logger.info("Call twitter url:" + callUrl);
        HttpResponse res = WS.url(callUrl).get();
        Logger.info("Call to twitter : " + callUrl);
        JsonElement element = res.getJson();
        JsonArray results = element.getAsJsonObject().getAsJsonArray("results");
        return results;
    }
}
