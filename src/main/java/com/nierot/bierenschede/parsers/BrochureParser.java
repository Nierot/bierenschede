package com.nierot.bierenschede.parsers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

abstract class BrochureParser implements Parser {

  String brochurePath = "./temp/" + this.getStoreName() + ".pdf";

  public BrochureParser() {
    log("Initialized");
  }

  /**
   * Generates a URL to the brochure of the store.
   */
  abstract String getBrochureUrl();

  /**
   * Process a single page of the PDF
   */
  abstract void processPage(String pageString, int pageNumber);

  /**
   * Download the brochure from the store's website.
   */
  protected void download() {
    log("Begin download");
    
    try {
      URL url = (new URI(getBrochureUrl())).toURL();

      BufferedInputStream in = new BufferedInputStream(url.openStream());

      FileOutputStream fs = new FileOutputStream(this.brochurePath);

      byte[] dataBuffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
        fs.write(dataBuffer, 0, bytesRead);
      }

      fs.close();
      in.close();
    } catch (Exception e) {
      log(e);
    } finally {
      log("Finished downloading");
    }
  }

  /**
   * Process!
   */
  public void process() throws IOException, ParseException {
    download();

    var reader = new PdfReader(new FileInputStream(this.brochurePath));

    for (int page = 1; page <= reader.getNumberOfPages(); page++) {
      var strategy = new SimpleTextExtractionStrategy();

      String curText = PdfTextExtractor.getTextFromPage(reader, page, strategy);

      processPage(curText, page);
    }

    reader.close();
  }
}
