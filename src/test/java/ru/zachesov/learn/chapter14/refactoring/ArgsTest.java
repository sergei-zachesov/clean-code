package ru.zachesov.learn.chapter14.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArgsTest {

  @Test
  void createWithNoSchemaOrArguments() throws Exception {
    Args args = new Args("", new String[0]);
    assertEquals(0, args.cardinality());
  }

  @Test
  void testWithNoSchemaButWithOneArgument() throws Exception {
    try {
      new Args("", new String[] {"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  void withNoSchemaButWithMultipleArguments() throws Exception {
    try {
      new Args("", new String[] {"-x", "-y"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  void nonLetterSchema() throws Exception {
    try {
      new Args("*", new String[] {});
      fail("Args constructor should have thrown exception");
    } catch (ArgsException e) {

      assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, e.getErrorCode());
      assertEquals('*', e.getErrorArgumentId());
    }
  }

  @Test
  void invalidArgumentFormat() throws Exception {
    try {
      new Args("f~", new String[] {});
      fail("Args constructor should have throws exception");
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.INVALID_FORMAT, e.getErrorCode());
      assertEquals('f', e.getErrorArgumentId());
    }
  }

  @Test
  void simpleBooleanPresent() throws Exception {
    Args args = new Args("x", new String[] {"-x"});
    assertEquals(1, args.cardinality());
    assertEquals(true, args.getBoolean('x'));
  }

  @Test
  void simpleStringPresent() throws Exception {
    Args args = new Args("x*", new String[] {"-x", "param"});
    assertEquals(1, args.cardinality());
    assertTrue(args.has('x'));
    assertEquals("param", args.getString('x'));
  }

  @Test
  void missingStringArgument() throws Exception {
    try {
      new Args("x*", new String[] {"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.MISSING_STRING, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  void testSpacesInFormat() throws Exception {
    Args args = new Args("x, y", new String[] {"-xy"});
    assertEquals(2, args.cardinality());
    assertTrue(args.has('x'));
    assertTrue(args.has('y'));
  }

  @Test
  void testSimpleIntPresent() throws Exception {
    Args args = new Args("x#", new String[] {"-x", "42"});
    assertEquals(1, args.cardinality());
    assertTrue(args.has('x'));
    assertEquals(42, args.getInt('x'));
  }

  @Test
  void testInvalidInteger() throws Exception {
    try {
      new Args("x#", new String[] {"-x", "Forty two"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.INVALID_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }
  }

  @Test
  void testMissingInteger() throws Exception {
    try {
      new Args("x#", new String[] {"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.MISSING_INTEGER, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }

  @Test
  void testSimpleDoublePresent() throws Exception {
    Args args = new Args("x##", new String[] {"-x", "42.3"});
    assertEquals(1, args.cardinality());
    assertTrue(args.has('x'));
    assertEquals(42.3, args.getDouble('x'), .001);
  }

  @Test
  void testInvalidDouble() throws Exception {
    try {
      new Args("x##", new String[] {"-x", "Forty two"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.INVALID_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
      assertEquals("Forty two", e.getErrorParameter());
    }
  }

  @Test
  void testMissingDouble() throws Exception {
    try {
      new Args("x##", new String[] {"-x"});
      fail();
    } catch (ArgsException e) {
      assertEquals(ArgsException.ErrorCode.MISSING_DOUBLE, e.getErrorCode());
      assertEquals('x', e.getErrorArgumentId());
    }
  }
}
