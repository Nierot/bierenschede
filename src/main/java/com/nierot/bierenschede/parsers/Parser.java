package com.nierot.bierenschede.parsers;

import java.io.IOException;

interface Parser {
  public abstract String getStoreName();

  public abstract void process() throws IOException, ParseException;

  public default void log(Object o) {
    System.out.println("[Parser] [" + this.getStoreName() + "] " + o);
  }
}
