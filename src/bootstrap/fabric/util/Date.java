package fabric.util;


public interface Date extends fabric.lang.JifObject, fabric.lang.Object {
    
    public int get$year();
    
    public int set$year(int val);
    
    public int postInc$year();
    
    public int postDec$year();
    
    public int get$month();
    
    public int set$month(int val);
    
    public int postInc$month();
    
    public int postDec$month();
    
    public int get$day();
    
    public int set$day(int val);
    
    public int postInc$day();
    
    public int postDec$day();
    
    public int get$hour();
    
    public int set$hour(int val);
    
    public int postInc$hour();
    
    public int postDec$hour();
    
    public int get$minute();
    
    public int set$minute(int val);
    
    public int postInc$minute();
    
    public int postDec$minute();
    
    public fabric.util.Date fabric$util$Date$();
    
    public fabric.util.Date fabric$util$Date$(final int pYear, final int pMonth,
                                              final int pDay)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date fabric$util$Date$(final int pYear, final int pMonth,
                                              final int pDay, final int pHour,
                                              final int pMinute)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date fabric$util$Date$(final int pYear,
                                              final java.lang.String pMonth,
                                              final int pDay)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date fabric$util$Date$(final int pYear,
                                              final java.lang.String pMonth,
                                              final int pDay, final int pHour,
                                              final int pMinute)
          throws java.lang.IllegalArgumentException;
    
    public int getYear();
    
    public int getYear_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int getMonth();
    
    public int getMonth_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int getDay();
    
    public int getDay_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int getHour();
    
    public int getHour_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int getMinute();
    
    public int getMinute_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int getDayOfWeek();
    
    public int getDayOfWeek_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String getMonthName();
    
    public java.lang.String getMonthName_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String getShortMonthName();
    
    public java.lang.String getShortMonthName_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String getDayOfWeekName();
    
    public java.lang.String getDayOfWeekName_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String getShortDayOfWeekName();
    
    public java.lang.String getShortDayOfWeekName_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean before(final fabric.util.Date when);
    
    public boolean before_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Date when);
    
    public boolean after(final fabric.util.Date when);
    
    public boolean after_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Date when);
    
    public int daysBefore(final fabric.util.Date when);
    
    public int daysBefore_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.util.Date when);
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public boolean equals(final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable o);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable o);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable o);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toLongString();
    
    public java.lang.String toLongString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public java.lang.String toShortString();
    
    public java.lang.String toShortString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.util.Date year(final int year)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date month(final int month)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date day(final int day)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date hour(final int hour)
          throws java.lang.IllegalArgumentException;
    
    public fabric.util.Date minute(final int minute)
          throws java.lang.IllegalArgumentException;
    
    public void jif$invokeDefConstructor();
    
    public fabric.lang.security.Label get$jif$fabric_util_Date_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifObject_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_JifObject_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_JifObject_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_IDComparable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_IDComparable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_Hashable_L();
    
    public fabric.lang.security.Label get$jif$fabric_lang_ToStringable_L();
    
    public fabric.lang.security.Label set$jif$fabric_lang_ToStringable_L(
      fabric.lang.security.Label val);
    
    public fabric.lang.security.Label jif$getfabric_lang_ToStringable_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Date
    {
        
        native public int get$year();
        
        native public int set$year(int val);
        
        native public int postInc$year();
        
        native public int postDec$year();
        
        native public int get$month();
        
        native public int set$month(int val);
        
        native public int postInc$month();
        
        native public int postDec$month();
        
        native public int get$day();
        
        native public int set$day(int val);
        
        native public int postInc$day();
        
        native public int postDec$day();
        
        native public int get$hour();
        
        native public int set$hour(int val);
        
        native public int postInc$hour();
        
        native public int postDec$hour();
        
        native public int get$minute();
        
        native public int set$minute(int val);
        
        native public int postInc$minute();
        
        native public int postDec$minute();
        
        native public fabric.lang.security.Label get$jif$fabric_util_Date_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        native public static fabric.util.Date valueOf(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          java.lang.String arg3)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$();
        
        native public fabric.util.Date fabric$util$Date$(int arg1, int arg2,
                                                         int arg3)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(int arg1, int arg2,
                                                         int arg3, int arg4,
                                                         int arg5)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(int arg1,
                                                         java.lang.String arg2,
                                                         int arg3)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(int arg1,
                                                         java.lang.String arg2,
                                                         int arg3, int arg4,
                                                         int arg5)
              throws java.lang.IllegalArgumentException;
        
        native public static int daysInMonth(fabric.lang.security.Label arg1,
                                             int arg2, int arg3);
        
        native public static fabric.util.Date lenientDate(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          int arg3, int arg4, int arg5);
        
        native public static fabric.util.Date lenientDate(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          int arg3, int arg4, int arg5, int arg6, int arg7);
        
        native public int getYear();
        
        native public int getYear_remote(fabric.lang.security.Principal arg1);
        
        native public int getYear$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int getMonth();
        
        native public int getMonth_remote(fabric.lang.security.Principal arg1);
        
        native public int getMonth$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int getDay();
        
        native public int getDay_remote(fabric.lang.security.Principal arg1);
        
        native public int getDay$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int getHour();
        
        native public int getHour_remote(fabric.lang.security.Principal arg1);
        
        native public int getHour$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int getMinute();
        
        native public int getMinute_remote(fabric.lang.security.Principal arg1);
        
        native public int getMinute$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int getDayOfWeek();
        
        native public int getDayOfWeek_remote(
          fabric.lang.security.Principal arg1);
        
        native public int getDayOfWeek$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getMonthName();
        
        native public java.lang.String getMonthName_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getMonthName$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getShortMonthName();
        
        native public java.lang.String getShortMonthName_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getShortMonthName$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getDayOfWeekName();
        
        native public java.lang.String getDayOfWeekName_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getDayOfWeekName$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getShortDayOfWeekName();
        
        native public java.lang.String getShortDayOfWeekName_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String getShortDayOfWeekName$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean before(fabric.util.Date arg1);
        
        native public boolean before_remote(fabric.lang.security.Principal arg1,
                                            fabric.util.Date arg2);
        
        native public boolean before$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Date arg2);
        
        native public boolean after(fabric.util.Date arg1);
        
        native public boolean after_remote(fabric.lang.security.Principal arg1,
                                           fabric.util.Date arg2);
        
        native public boolean after$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Date arg2);
        
        native public int daysBefore(fabric.util.Date arg1);
        
        native public int daysBefore_remote(fabric.lang.security.Principal arg1,
                                            fabric.util.Date arg2);
        
        native public int daysBefore$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.util.Date arg2);
        
        native public static boolean isLeapYear(fabric.lang.security.Label arg1,
                                                int arg2);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.IDComparable arg2);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.IDComparable arg2);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.security.Label arg2,
                                            fabric.lang.IDComparable arg3);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.security.Label arg2,
          fabric.lang.IDComparable arg3);
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toLongString();
        
        native public java.lang.String toLongString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toLongString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toShortString();
        
        native public java.lang.String toShortString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toShortString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public fabric.util.Date year(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date month(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date day(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date hour(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date minute(int arg1)
              throws java.lang.IllegalArgumentException;
        
        native public static fabric.util.Date addYear(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4);
        
        native public static fabric.util.Date addMonth(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4);
        
        native public static fabric.util.Date addMonth(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4, boolean arg5);
        
        native public static fabric.util.Date addDay(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4);
        
        native public static fabric.util.Date addHour(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4);
        
        native public static fabric.util.Date addMinute(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2,
          fabric.util.Date arg3, int arg4);
        
        native public void jif$invokeDefConstructor();
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.util.Date jif$cast$fabric_util_Date(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(Date._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Date
    {
        
        native public int get$year();
        
        native public int set$year(int val);
        
        native public int postInc$year();
        
        native public int postDec$year();
        
        native public int get$month();
        
        native public int set$month(int val);
        
        native public int postInc$month();
        
        native public int postDec$month();
        
        native public int get$day();
        
        native public int set$day(int val);
        
        native public int postInc$day();
        
        native public int postDec$day();
        
        native public int get$hour();
        
        native public int set$hour(int val);
        
        native public int postInc$hour();
        
        native public int postDec$hour();
        
        native public int get$minute();
        
        native public int set$minute(int val);
        
        native public int postInc$minute();
        
        native public int postDec$minute();
        
        native public static fabric.util.Date valueOf(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lbl, final java.lang.String d)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$();
        
        native public fabric.util.Date fabric$util$Date$(final int pYear,
                                                         final int pMonth,
                                                         final int pDay)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(final int pYear,
                                                         final int pMonth,
                                                         final int pDay,
                                                         final int pHour,
                                                         final int pMinute)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(
          final int pYear, final java.lang.String pMonth, final int pDay)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date fabric$util$Date$(
          final int pYear, final java.lang.String pMonth, final int pDay,
          final int pHour, final int pMinute)
              throws java.lang.IllegalArgumentException;
        
        native public static int daysInMonth(
          final fabric.lang.security.Label jif$L, final int month,
          final int year);
        
        native private static void checkDateValid(
          final fabric.lang.security.Label jif$L, final int pYear,
          final int pMonth, final int pDay, final int pHour, final int pMinute)
              throws java.lang.IllegalArgumentException;
        
        native public static fabric.util.Date lenientDate(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lbl, final int year, final int month,
          final int day);
        
        native public static fabric.util.Date lenientDate(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lbl, final int year, final int month,
          final int day, final int hour, final int minute);
        
        native public int getYear();
        
        native public int getYear_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int getMonth();
        
        native public int getMonth_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int getDay();
        
        native public int getDay_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int getHour();
        
        native public int getHour_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int getMinute();
        
        native public int getMinute_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int getDayOfWeek();
        
        native public int getDayOfWeek_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String getMonthName();
        
        native public java.lang.String getMonthName_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String getShortMonthName();
        
        native public java.lang.String getShortMonthName_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String getDayOfWeekName();
        
        native public java.lang.String getDayOfWeekName_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String getShortDayOfWeekName();
        
        native public java.lang.String getShortDayOfWeekName_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean before(final fabric.util.Date when);
        
        native public boolean before_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Date when);
        
        native public boolean after(final fabric.util.Date when);
        
        native public boolean after_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Date when);
        
        native public int daysBefore(final fabric.util.Date when);
        
        native public int daysBefore_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.util.Date when);
        
        native private static int daysAfterJan_1_1900(
          final fabric.lang.security.Label jif$L, final int year,
          final int month, final int day);
        
        native public static boolean isLeapYear(
          final fabric.lang.security.Label jif$L, final int pYear);
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public boolean equals(final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable o);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable o);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable o);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toLongString();
        
        native public java.lang.String toLongString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public java.lang.String toShortString();
        
        native public java.lang.String toShortString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public fabric.util.Date year(final int year)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date month(final int month)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date day(final int day)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date hour(final int hour)
              throws java.lang.IllegalArgumentException;
        
        native public fabric.util.Date minute(final int minute)
              throws java.lang.IllegalArgumentException;
        
        native public static fabric.util.Date addYear(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta);
        
        native public static fabric.util.Date addMonth(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta);
        
        native public static fabric.util.Date addMonth(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta, final boolean smartUpdate);
        
        native public static fabric.util.Date addDay(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta);
        
        native public static fabric.util.Date addHour(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta);
        
        native public static fabric.util.Date addMinute(
          final fabric.lang.security.Label jif$L,
          final fabric.lang.security.Label lb, final fabric.util.Date date,
          final int delta);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native public void jif$invokeDefConstructor();
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.util.Date jif$cast$fabric_util_Date(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public fabric.lang.security.Label get$jif$fabric_util_Date_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_JifObject_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_IDComparable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public fabric.lang.security.Label get$jif$fabric_lang_Hashable_L(
          );
        
        native public fabric.lang.security.Label set$jif$fabric_lang_Hashable_L(
          fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_ToStringable_L();
        
        native public fabric.lang.security.Label
          set$jif$fabric_lang_ToStringable_L(fabric.lang.security.Label val);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.Worker get$worker$();
        
        public fabric.lang.arrays.ObjectArray get$monthNames();
        
        public fabric.lang.arrays.ObjectArray get$daysOfWeek();
        
        public fabric.lang.arrays.ObjectArray get$shortDaysOfWeek();
        
        public fabric.lang.arrays.ObjectArray get$shortMonthNames();
        
        public fabric.lang.arrays.intArray get$maxDaysInMonth();
        
        public int get$DAYS_IN_400_YEARS();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Date._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public fabric.lang.arrays.ObjectArray get$monthNames();
            
            native public fabric.lang.arrays.ObjectArray get$daysOfWeek();
            
            native public fabric.lang.arrays.ObjectArray get$shortDaysOfWeek();
            
            native public fabric.lang.arrays.ObjectArray get$shortMonthNames();
            
            native public fabric.lang.arrays.intArray get$maxDaysInMonth();
            
            native public int get$DAYS_IN_400_YEARS();
            
            public _Proxy(fabric.util.Date._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Date._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            native public fabric.lang.arrays.ObjectArray get$monthNames();
            
            native public fabric.lang.arrays.ObjectArray get$daysOfWeek();
            
            native public fabric.lang.arrays.ObjectArray get$shortDaysOfWeek();
            
            native public fabric.lang.arrays.ObjectArray get$shortMonthNames();
            
            native public fabric.lang.arrays.intArray get$maxDaysInMonth();
            
            native public int get$DAYS_IN_400_YEARS();
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
