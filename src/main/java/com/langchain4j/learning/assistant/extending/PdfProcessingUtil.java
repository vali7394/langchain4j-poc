package com.langchain4j.learning.assistant.extending;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PdfProcessingUtil {


    public static void main(String[] args) {
      //  Document acesPdf = pdfDocExample();
        Document acesPdf = fileSystemDocumentLoader("ACES_4_2_TD.pdf");
       // System.out.println(acesPdf.text().substring(100,10000));
        System.out.println(acesPdf.metadata());
        DocumentSplitter documentSplitter = new DocumentByParagraphSplitter(10000,10);
        List<TextSegment> textSegments = documentSplitter.split(acesPdf);
        System.out.println("Size of total segments" + textSegments.size());
        textSegments.stream().limit(2).forEach(System.out::println);
    }

    private static InputStream toStream(String fileName){
        return PdfProcessingUtil.class.getClassLoader().getResourceAsStream(fileName);
    }

    private static Document fileSystemDocumentLoader(String fileName) {
       Path path = toPath(fileName);
       return FileSystemDocumentLoader.loadDocument(path,new ApachePdfBoxDocumentParser());
       //return FileSystemDocumentLoader.loadDocument(path);
    }

    private static Document pdfDocExample(){
        InputStream inputStream = toStream("ACES_4_2_TD.pdf");
        return new ApachePdfBoxDocumentParser(true).parse(inputStream);
    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = PdfProcessingUtil.class.getClassLoader().getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
