package fabric.common;

public final class ONumConstants {
  public static final long ROOT_MAP = 0;
  public static final long STORE_PRINCIPAL = 1;
  public static final long DERIVED_MAP = 2;

  // This is the first available onum.
  public static final long FIRST_UNRESERVED = 3;

  // These should be longs, but they are ints so that they can be used in a
  // switch statement.
  public static final int TOP_PRINCIPAL = -1;
  public static final int TOP_CONFIDENTIALITY = -2;
  public static final int BOTTOM_CONFIDENTIALITY = -3;
  public static final int TOP_INTEGRITY = -4;
  public static final int BOTTOM_INTEGRITY = -5;
  public static final int EMPTY_LABEL = -6;
  public static final int PUBLIC_READONLY_LABEL = -7;

  /**
   * Determines whether the given onum is a global constant.
   */
  public static boolean isGlobalConstant(long onum) {
    return onum < 0;
  }
}
