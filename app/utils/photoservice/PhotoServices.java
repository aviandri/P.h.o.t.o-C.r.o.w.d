package utils.photoservice;

import java.util.HashMap;
import java.util.Map;

/**
 * This is utility to extract photo resource from various photo services.
 * 
 * @author uudashr@gmail.com
 *
 */
public class PhotoServices {
    private static final Map<String, PhotoService> photoServices = new HashMap<String, PhotoService>();
    
    static {
        photoServices.put("twitpic", new TwitgooPhotoService());
        photoServices.put("lockerz", new LockerzPhotoService());
        photoServices.put("twitgoo", new TwitgooPhotoService());
    }
    
    /**
     * This will extract the photo resources from a tweet text.
     * 
     * @param tweet is the tweet text
     * @return the photo resources.
     */
    public static PhotoResource[] extractPhotoResource(String tweet) {
        for (PhotoService service : photoServices.values()) {
            String[] urls = service.findURL(tweet);
            if (urls != null) {
                PhotoResource[] tweetPhotos = new PhotoResource[urls.length];
                for (int i = 0; i < urls.length; i++) {
                    tweetPhotos[i] = new PhotoResource(urls[i], service);
                }
                return tweetPhotos;
            }
        }
        return null;
    }
    

    /**
     * Return all the URL prefixes from the available photo services.
     * 
     * @return URL prefixes.
     */
    public static String[] getUrlPrefixes() {
        String[] prefixes = new String[photoServices.size()];
        int i = 0;
        for (PhotoService photoService : photoServices.values()) {
            prefixes[i++] = photoService.getUrlPrefix();
        }
        return prefixes;
    }


    /**
     * Represent the photo resource from photo service.
     * 
     * @author uudashr@gmail.com
     *
     */
    public static class PhotoResource {
        
        public final String url;
        private PhotoService photoService;
        
        /**
         * Represent the tweet photo resource, URL that contained on the tweet.
         * 
         * @param url is the tweet photo URL.
         * @param photoService
         */
        public PhotoResource(String url, PhotoService photoService) {
            this.url = url;
            this.photoService = photoService;
        }
        
        /**
         * This will grab the URL of original size image and the thumbnail.
         * 
         * @return the holder of original size and thumbnail.
         */
        public ImageAndThumbnailUrlHolder grab() {
            return photoService.grab(url);
        }
        
    }

}
