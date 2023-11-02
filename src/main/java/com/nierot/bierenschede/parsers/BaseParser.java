package com.nierot.bierenschede.parsers;

import java.io.IOException;

abstract class BaseParser {
  public abstract String getStoreName();

  public abstract void parse() throws IOException, ParseException;

  public void log(Object o) {
    System.out.println("[Parser] [" + this.getStoreName() + "] " + o);
  }
}
