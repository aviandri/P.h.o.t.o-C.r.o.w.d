package utils.photoservice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This is common implementation of {@link PhotoService}.
 * 
 * @author uudashr@gmail.com
 *
 */
public abstract class AbstractPhotoService implements PhotoService {
    private final Pattern urlPattern;
    
    public AbstractPhotoService(String urlPattern) {
        this.urlPattern = Pattern.compile(urlPattern);
    }

    public String[] findURL(String text) {
        Matcher matcher = urlPattern.matcher(text);
        List<String> urls = new ArrayList<String>();
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        if (urls.isEmpty()) {
            // this cannot find the supported URL
            return null;
        }
        return urls.toArray(new String[urls.size()]);
    }
    
    @Override
    public boolean recognize(String photoUrl) {
        return urlPattern.matcher(photoUrl).matches();
    }
}
