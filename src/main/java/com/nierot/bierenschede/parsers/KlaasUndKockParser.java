package com.nierot.bierenschede.parsers;

import java.util.Arrays;
import java.util.Calendar;

public class KlaasUndKockParser extends BrochureParser {

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

  public String getStoreName() {
    return "K+K";
  }

  public String getBrochureUrl() {
    return "https://www.klaas-und-kock.de/kataloge/blaetterkatalog/catalogs/kundk_kw" + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + "/pdf/complete.pdf";
  }

  public void processPage(String pageString, int pageNumber) {
    Arrays.asList(pageString.split("\n")).forEach(line -> {
      log(line);
    });
  }
}
