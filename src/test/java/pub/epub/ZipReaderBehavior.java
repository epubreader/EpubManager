package pub.epub;

import com.epub.manager.domain.Book;
import com.epub.manager.domain.Resource;
import com.epub.manager.epub.EpubReader;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Yong on 2017/5/14.
 */
public class ZipReaderBehavior {

    @Test
    public void epublib() {
        EpubReader reader = new EpubReader();
        File file  = new File("D:\\epub\\The Good, the Bad, and the Grace of God_ W - Jep Robertson.epub");

        try {
            Book readBook =reader.readEpub(new FileInputStream(file));
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
