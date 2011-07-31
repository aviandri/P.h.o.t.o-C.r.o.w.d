package utils;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TwitterUtil {

    public static SearchResponse searchTwitter(String q, Long sinceId) {
        String callUrl = "http://search.twitter.com/search.json?q=" + q
                + "&since_id=" + sinceId + "&rpp=100";
        Logger.info("Call twitter url:" + callUrl);
        HttpResponse resp = WS.url(callUrl).get();
        JsonObject jsonObj = resp.getJson().getAsJsonObject();
        if (resp.getStatus() != 200) {
            String errorMesssage = jsonObj.get("error").getAsString();
            Logger.error("Got non 200 HTTP Status code: %1s error=%2s", resp.getStatus(), errorMesssage);
        }
        return new SearchResponse(jsonObj);
    }
}
