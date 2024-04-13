package ru.zachesov.learn.chapter14.target;

import static ru.zachesov.learn.chapter14.target.ArgsException.ErrorCode.MISSING_STRING;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
  private String[] stringArrayValue = new String[0];

  public void set(Iterator<String> currentArgument) throws ArgsException {
    String parameter = null;
    try {
      parameter = currentArgument.next();
      stringArrayValue = parameter.split(",");
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_STRING);
    }
  }

  public static String[] getValue(ArgumentMarshaler am) {
    if (am instanceof StringArgumentMarshaler) {
      return ((StringArrayArgumentMarshaler) am).stringArrayValue;
    } else {
      return new String[0];
    }
  }
}
