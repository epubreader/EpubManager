package pub.epub;

import com.adobe.epubcheck.api.EpubCheck;
import com.epub.manager.Constants;
import com.epub.manager.domain.Book;
import com.epub.manager.domain.Resource;
import com.epub.manager.epub.EpubReader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * Created by Yong on 2017/5/14.
 */
public class ZipReaderBehavior {

    @Test
    public void epublib() {
        EpubReader reader = new EpubReader();


        try {
            ZipFile file = new ZipFile("D:\\epub\\Summer_39_s_Destiny_-_Ariel_Marie.epub");
            Book readBook = reader.readEpubLazy(file, Constants.CHARACTER_ENCODING);
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void epubCheck() {
        File epubFile = new File("D:\\epub\\Summer_39_s_Destiny_-_Ariel_Marie.epub");

// simple constructor; errors are printed on stderr stream
        EpubCheck epubcheck = new EpubCheck(epubFile);

// validate() returns true if no errors or warnings are found
        boolean result = epubcheck.validate();
        System.out.print(result);
    }
}
