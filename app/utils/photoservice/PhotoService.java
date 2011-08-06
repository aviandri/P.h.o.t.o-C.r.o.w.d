package utils.photoservice;


/**
 * This is a interfacing to photo service such as Twitpic, Twitgoo, Lockerz, etc.
 *  
 * @author uudashr@gmail.com
 *
 */
public interface PhotoService {
    
    /**
     * Find URL supported by the TwitterPhotoService.
     * <p>
     * If it return not-null value means it has minimum one element and every
     * returned URL(s) should return <tt>true</tt> if you pass it to
     * {@link #recognize(String)} method.
     * </p>
     * 
     * @param text is the text contains the URL to page of photo service.
     * @return the URLs or <tt>null</tt> if not found.
     */
    String[] findURL(String text);
    
    /**
     * Grab the URL of original size and thumbnail.
     * 
     * @param photoUrl is the URL to photo service page.
     * @return the URL for original image size and the thumbnail.
     */
    ImageAndThumbnailUrlHolder grab(String photoUrl);
    
    /**
     * Check whether this service recognize the photo resource URL.
     * 
     * @param photoUrl is the photo resource URL.
     * @return <tt>true</tt> if this service recognize, otherwise <tt>false</tt>.
     */
    boolean recognize(String photoUrl);
}
