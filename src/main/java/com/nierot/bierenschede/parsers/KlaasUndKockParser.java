package com.nierot.bierenschede.parsers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

public class KlaasUndKockParser extends BaseParser implements BrochureParser {

  private final String[] BEER_NAMES = {
    "Krombacher",
    "Veltins",
    "Grolsch",
    "Oberdorfer",
    "Kronsberg",
    "Warsteiner",
    "Pott's Landbier",
    "König",
    "Pülleken",
    "Weihenstephan"
  };

  private String brochurePath = "./temp/K+K.pdf";

  public KlaasUndKockParser() {
    log("Initialized");

  }

  public String getStoreName() {
    return "K+K";
  }

  public String getBrochureUrl() {

    // Get the current week number
    int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

    return "https://www.klaas-und-kock.de/kataloge/blaetterkatalog/catalogs/kundk_kw" + week + "/pdf/complete.pdf";
  }

  private void download() {
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

  public void processPage(String page, String beer) {
    Arrays.asList(page.split("\n")).forEach(line -> {
      if (line.contains(beer)) {
        log(line);
      }
    });
  }

  public void parse() throws IOException, ParseException {
    download();

    var beerList = Arrays.asList(BEER_NAMES);

    var reader = new PdfReader(new FileInputStream(this.brochurePath));

    for (int page = 1; page <= reader.getNumberOfPages(); page++) {
      var strategy = new SimpleTextExtractionStrategy();

      String curText = PdfTextExtractor.getTextFromPage(reader, page, strategy);

      for (String beer : beerList) {
        if (curText.contains(beer)) {
          log("Found " + beer + " on page " + page);
          processPage(curText, beer);
        }
      }
    }

    reader.close();
  }
}
