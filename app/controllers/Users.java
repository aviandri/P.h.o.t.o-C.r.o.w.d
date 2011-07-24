package controllers;

import models.User;
import play.Logger;
import play.Play;
import play.libs.OAuth;
import play.libs.OAuth.Response;
import play.libs.OAuth.ServiceInfo;
import play.libs.WS;
import play.mvc.Controller;

import com.google.gson.JsonElement;

public class Users extends Controller {

    private static final String twitterRequestTokenUrl = "https://api.twitter.com/oauth/request_token";
    private static final String twitterAuthorizeUrl = "https://api.twitter.com/oauth/authorize";
    private static final String twitterAccessTokenUrl = "https://api.twitter.com/oauth/access_token";

    public static void index() {
        render();
    }

    public static void authenticate() {
        if (OAuth.isVerifierResponse()) {
            Response accessTokenResp = OAuth.service(TWITTER).retrieveAccessToken(session.get("twitter.token"), session.get("twitter.secret"));

            JsonElement json = WS.url("http://api.twitter.com/1/account/verify_credentials.json").oauth(TWITTER, accessTokenResp.token, accessTokenResp.secret).get().getJson();
            Long twitterId = json.getAsJsonObject().getAsJsonPrimitive("id").getAsLong();
            User user = User.findByTwitterId(twitterId);
            if (user == null) {
                user = new User();
                user.accessToken = accessTokenResp.token;
                user.secretToken = accessTokenResp.secret;
                user.twitterId = twitterId;
                user.save();
            }
            session.put("session.userid", user.id);
            Galleries.index();

        }

        OAuth twitt = OAuth.service(TWITTER);
        Response reqTokenResp = twitt.retrieveRequestToken();
        session.put("twitter.secret", reqTokenResp.secret);
        session.put("twitter.token", reqTokenResp.token);
        Logger.debug("secret first :" + reqTokenResp.secret);
        Logger.debug("token first :" + reqTokenResp.token);
        redirect(twitt.redirectUrl(reqTokenResp.token));

    }

    private static ServiceInfo TWITTER = new ServiceInfo(
            twitterRequestTokenUrl, twitterAccessTokenUrl, twitterAuthorizeUrl,
            Play.configuration.getProperty("twitter.consumer.key"),
            Play.configuration.getProperty("twitter.consumer.secret"));

}
