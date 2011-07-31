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
        HttpResponse resp = WS.url(callUrl).get();
        JsonElement element = resp.getJson();
        if (resp.getStatus() != 200) {
            String errorMesssage = element.getAsJsonObject().get("error").getAsString();
            Logger.error("Got non 200 HTTP Status code: %1s error=%2s", resp.getStatus(), errorMesssage);
        }
        JsonArray results = element.getAsJsonObject().getAsJsonArray("results");
        return results;
    }
}
