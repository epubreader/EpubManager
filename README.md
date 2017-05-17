# EpubManager

EpubManager is a java library for reading/manipulating epub files. Support both epub 2.0 and epub 3.0.

Example usage:

        EpubReader reader = new EpubReader();
        File file  = new File("D:\\epub\\The Good, the Bad, and the Grace of God_ W - Jep Robertson.epub");

        try {
            Book readBook =reader.readEpub(new FileInputStream(file));
            readBook.getTableOfContents();
            Resource coverImage = readBook.getCoverImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

for lazy load zip entry, usage:

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