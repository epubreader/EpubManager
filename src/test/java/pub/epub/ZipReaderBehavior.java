package pub.epub;

import com.github.epubreader.manager.Constants;
import com.github.epubreader.manager.domain.*;
import com.github.epubreader.manager.epub.EpubReader;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * Created by Yong on 2017/5/14.
 */
public class ZipReaderBehavior {

    @Test
    public void epublib() {
        EpubReader reader = new EpubReader();
        //File file = new File("D:\\epub\\mobilism\\Tiger Tracks - The Classic Panz - Wolfgang Faust.epub");

        try {
            ZipFile file = new ZipFile("D:\\epub\\mobilism\\ACGH-IS.epub");
            Book readBook = reader.readEpubLazy(file, Constants.CHARACTER_ENCODING);
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();


            Metadata metadata = readBook.getMetadata();
            // title
            String title = metadata.getTitles().stream().findFirst().get();
            System.out.println(title);
            //language
            String language = metadata.getLanguage();
            System.out.println(language);

            //identifier
            Identifier identifier = metadata.getIdentifiers().stream().findFirst().get();
            System.out.println(identifier);

            //creator
            Author author = metadata.getAuthors().stream().findFirst().get();
            System.out.println(author.getFirstname() + " " + author.getLastname());


            //contributor
            if (!metadata.getContributors().isEmpty()) {
                Author contributor = metadata.getContributors().stream().findFirst().get();
                System.out.println(contributor.getFirstname() + " " + contributor.getLastname());
            }

            //publisher
            if (!metadata.getPublishers().isEmpty()) {
                String publisher = metadata.getPublishers().stream().findFirst().get();
                System.out.println(publisher);
            }

            //subject
            List<String> subjects = metadata.getSubjects();
            if (!subjects.isEmpty()) {
                for (String subject : subjects) {
                    System.out.println(subject);
                }
            }
            ;

            //description
            List<String> descriptions = metadata.getDescriptions();
            if (!subjects.isEmpty()) {
                for (String description : descriptions) {
                    System.out.println(description);
                }

            }

            // date
            List<Date> dates = metadata.getDates();
            if (!dates.isEmpty()) {
                for (Date date : dates) {
                    System.out.println(dates);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void epublibLazy() {
        EpubReader reader = new EpubReader();


        try {
            ZipFile file = new ZipFile("D:\\epub\\Summer_39_s_Destiny_-_Ariel_Marie.epub");
            Book readBook = reader.readEpubLazy(file, Constants.CHARACTER_ENCODING);
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();
            System.out.println(coverImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void epublib2() {
        EpubReader reader = new EpubReader();
        File file = new File("D:\\eclipse-epub\\mobilism\\web\\target\\web-0.0.1-SNAPSHOT.jar");

        try {
            Book readBook = reader.readEpub(new FileInputStream(file));
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    @Test
    public void epubCheck() {
        File epubFile = new File("D:\\epub\\mobilism\\2017-05-16\\welcome-to-my-country-lauren-slater.epub");

// simple constructor; errors are printed on stderr stream
        EpubCheck epubcheck = new EpubCheck(epubFile);

// validate() returns true if no errors or warnings are found
        boolean result = epubcheck.validate();
        System.out.print(result);
    }*/
}
