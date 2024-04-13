package ru.zachesov.learn.chapter14.target;

import static ru.zachesov.learn.chapter14.target.ArgsException.ErrorCode.INVALID_INTEGER;
import static ru.zachesov.learn.chapter14.target.ArgsException.ErrorCode.MISSING_INTEGER;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
  private double doubleValue = 0.0;

  public void set(Iterator<String> currentArgument) throws ArgsException {
    String parameter = null;
    try {
      parameter = currentArgument.next();
      doubleValue = Double.parseDouble(parameter);
    } catch (NoSuchElementException e) {
      throw new ArgsException(MISSING_INTEGER);
    } catch (NumberFormatException e) {
      throw new ArgsException(INVALID_INTEGER, parameter);
    }
  }

  public static double getValue(ArgumentMarshaler am) {
    if (am instanceof DoubleArgumentMarshaler) {
      return ((DoubleArgumentMarshaler) am).doubleValue;
    } else {
      return 0;
    }
  }
}
