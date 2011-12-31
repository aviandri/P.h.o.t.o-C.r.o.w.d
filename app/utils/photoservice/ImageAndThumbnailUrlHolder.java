package utils.photoservice;

/**
 * Holder of URL and thumbnail URL.
 * 
 * @author uudashr@gmail.com
 *
 */
public class ImageAndThumbnailUrlHolder {
    public final String originalUrl;
    public final String url;
    public final String thumbUrl;
    public final Long expires;
    
    public ImageAndThumbnailUrlHolder(String originalUrl, String url,
            String thumbUrl, Long expires) {
        this.originalUrl = originalUrl;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.expires = expires;
    }
    
    public ImageAndThumbnailUrlHolder(String originalUrl, String url,
            String thumbUrl) {
        this(originalUrl, url, thumbUrl, null);
    }
}