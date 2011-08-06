package utils.photoservice;

/**
 * Photo service implementation of Lockerz.
 * 
 * @author uudashr@gmail.com
 *
 */
public class LockerzPhotoService extends AbstractPhotoService {

    public LockerzPhotoService() {
        super("http://lockerz.com/");
    }
    
    @Override
    public ImageAndThumbnailUrlHolder grab(String photoUrl) {
        return new ImageAndThumbnailUrlHolder(
                "http://api.plixi.com/api/tpapi.svc/imagefromurl?url=" + photoUrl + "&size=large", 
                "http://api.plixi.com/api/tpapi.svc/imagefromurl?url=" + photoUrl + "&size=small");
    }
}
