# EpubManager

EpubManager is a java library for reading/manipulating epub files.

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
