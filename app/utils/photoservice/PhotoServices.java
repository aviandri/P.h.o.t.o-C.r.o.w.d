package utils.photoservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;

import play.Logger;

/**
 * This is utility to extract photo resource from various photo services.
 * 
 * @author uudashr@gmail.com
 *
 */
public class PhotoServices {
    private static final Map<String, PhotoService> photoServices = new HashMap<String, PhotoService>();
    
    static {
        photoServices.put("lockerz", new LockerzPhotoService());
        photoServices.put("twitgoo", new TwitgooPhotoService());
        photoServices.put("twitpic", new TwitpicPhotoService());
        photoServices.put("twitter", new TwitterPhotoService());
        
    }
    
    /**
     * Filter the URLs to photo resources
     * 
     * @param urls is the URLs.
     * @return the photo resources.
     * @see PhotoResource
     */
    public static PhotoResource[] filterToPhotoResources(String[] urls) {
        for (PhotoService service : photoServices.values()) {
            if (Logger.isDebugEnabled()) {
                Logger.debug("Using service %1s to check url: %2s", service.getClass().getName(), Arrays.toString(urls));
            }
            String[] filteredUrls = service.filter(urls);
            Logger.debug("Found %1s", Arrays.toString(filteredUrls));
            if (filteredUrls != null) {
                PhotoResource[] tweetPhotos = new PhotoResource[filteredUrls.length];
                for (int i = 0; i < filteredUrls.length; i++) {
                    tweetPhotos[i] = new PhotoResource(filteredUrls[i], service);
                }
                return tweetPhotos;
            }
        }
        return null;
    }
    
    public static PhotoResource photoResource(String url) {
        for (PhotoService service : photoServices.values()) {
            if (service.recognize(url)) {
                return new PhotoResource(url, service);
            }
        }
        return null;
    }
    

    /**
     * Return all the search keys from the available photo services.
     * 
     * @return search keys.
     */
    public static String[] getSearchKeys() {
        String[] prefixes = new String[photoServices.size()];
        int i = 0;
        for (PhotoService photoService : photoServices.values()) {
            prefixes[i++] = photoService.getSearchKey();
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
