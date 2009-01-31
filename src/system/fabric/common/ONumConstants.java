package fabric.common;

public final class ONumConstants {
  public static final long ROOT_MAP = 0;
  public static final long CORE_PRINCIPAL = 1;
  
  // This is the first available onum.
  public static final long FIRST_UNRESERVED = 2;
  
  public static final long TOP_PRINCIPAL = -1;
  public static final long TOP_CONFIDENTIALITY = -2;
  public static final long BOTTOM_CONFIDENTIALITY = -3;
  public static final long TOP_INTEGRITY = -4;
  public static final long BOTTOM_INTEGRITY = -5;
  public static final long EMPTY_LABEL = -6;
  public static final long PUBLIC_READONLY_LABEL = -7;
}
