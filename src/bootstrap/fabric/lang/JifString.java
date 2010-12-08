package fabric.lang;

public interface JifString extends fabric.lang.JifObject, fabric.lang.Object {
    
    public java.lang.String get$s();
    
    public java.lang.String set$s(java.lang.String val);
    
    public fabric.lang.JifString fabric$lang$JifString$(
      final java.lang.String s);
    
    public int length();
    
    public int length_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public char charAt(final int index)
          throws java.lang.StringIndexOutOfBoundsException;
    
    public char charAt_remote(
      final fabric.lang.security.Principal worker$principal, final int index)
          throws java.lang.StringIndexOutOfBoundsException;
    
    public boolean equals(final fabric.lang.Object anObject);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.Object anObject);
    
    public boolean equals(final fabric.lang.IDComparable other);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.IDComparable other);
    
    public boolean equals(final fabric.lang.security.Label lbl,
                          final fabric.lang.IDComparable obj);
    
    public boolean equals_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.security.Label lbl, final fabric.lang.IDComparable obj);
    
    public boolean equalsIgnoreCase(final java.lang.String anotherString);
    
    public boolean equalsIgnoreCase_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String anotherString);
    
    public boolean equalsIgnoreCase(final fabric.lang.JifString anotherString);
    
    public boolean equalsIgnoreCase_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifString anotherString);
    
    public int compareTo(final java.lang.String anotherString)
          throws java.lang.NullPointerException;
    
    public int compareTo_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String anotherString)
          throws java.lang.NullPointerException;
    
    public int compareTo(final fabric.lang.JifString anotherString)
          throws java.lang.NullPointerException;
    
    public int compareTo_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifString anotherString)
          throws java.lang.NullPointerException;
    
    public int compareTo(final java.lang.Object o)
          throws java.lang.ClassCastException, java.lang.NullPointerException;
    
    public int compareTo_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.Object o)
          throws java.lang.ClassCastException,
        java.lang.NullPointerException;
    
    public int compareToIgnoreCase(final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int compareToIgnoreCase_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int compareToIgnoreCase(final fabric.lang.JifString str)
          throws java.lang.NullPointerException;
    
    public int compareToIgnoreCase_remote(
      final fabric.lang.security.Principal worker$principal,
      final fabric.lang.JifString str)
          throws java.lang.NullPointerException;
    
    public boolean regionMatches(final int toffset,
                                 final java.lang.String other,
                                 final int ooffset, final int len)
          throws java.lang.NullPointerException;
    
    public boolean regionMatches_remote(
      final fabric.lang.security.Principal worker$principal, final int toffset,
      final java.lang.String other, final int ooffset, final int len)
          throws java.lang.NullPointerException;
    
    public boolean regionMatches(final boolean ignoreCase, final int toffset,
                                 final java.lang.String other,
                                 final int ooffset, final int len)
          throws java.lang.NullPointerException;
    
    public boolean regionMatches_remote(
      final fabric.lang.security.Principal worker$principal,
      final boolean ignoreCase, final int toffset, final java.lang.String other,
      final int ooffset, final int len)
          throws java.lang.NullPointerException;
    
    public boolean startsWith(final java.lang.String prefix, final int toffset);
    
    public boolean startsWith_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String prefix, final int toffset);
    
    public boolean startsWith(final java.lang.String prefix);
    
    public boolean startsWith_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String prefix);
    
    public boolean endsWith(final java.lang.String suffix);
    
    public boolean endsWith_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String suffix);
    
    public int hashCode();
    
    public int hashCode_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public int indexOf(final int ch);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal, final int ch);
    
    public int indexOf(final int ch, final int fromIndex);
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal, final int ch,
      final int fromIndex);
    
    public int lastIndexOf(final int ch);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal, final int ch);
    
    public int lastIndexOf(final int ch, final int fromIndex);
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal, final int ch,
      final int fromIndex);
    
    public int indexOf(final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int indexOf(final java.lang.String str, final int fromIndex)
          throws java.lang.NullPointerException;
    
    public int indexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String str, final int fromIndex)
          throws java.lang.NullPointerException;
    
    public int lastIndexOf(final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String str)
          throws java.lang.NullPointerException;
    
    public int lastIndexOf(final java.lang.String str, final int fromIndex)
          throws java.lang.NullPointerException;
    
    public int lastIndexOf_remote(
      final fabric.lang.security.Principal worker$principal,
      final java.lang.String str, final int fromIndex)
          throws java.lang.NullPointerException;
    
    public java.lang.String toString();
    
    public java.lang.String toString_remote(
      final fabric.lang.security.Principal worker$principal);
    
    public fabric.lang.security.Label get$jif$fabric_lang_JifString_L();
    
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
      implements fabric.lang.JifString
    {
        
        native public java.lang.String get$s();
        
        native public java.lang.String set$s(java.lang.String val);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifString_L();
        
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
        
        native public fabric.lang.JifString fabric$lang$JifString$(
          java.lang.String arg1);
        
        native public int length();
        
        native public int length_remote(fabric.lang.security.Principal arg1);
        
        native public int length$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public char charAt(int arg1)
              throws java.lang.StringIndexOutOfBoundsException;
        
        native public char charAt_remote(fabric.lang.security.Principal arg1,
                                         int arg2)
              throws java.lang.StringIndexOutOfBoundsException;
        
        native public char charAt$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2)
              throws java.lang.StringIndexOutOfBoundsException;
        
        native public boolean equals(fabric.lang.Object arg1);
        
        native public boolean equals_remote(fabric.lang.security.Principal arg1,
                                            fabric.lang.Object arg2);
        
        native public boolean equals$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.Object arg2);
        
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
        
        native public boolean equalsIgnoreCase(java.lang.String arg1);
        
        native public boolean equalsIgnoreCase_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean equalsIgnoreCase$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean equalsIgnoreCase(fabric.lang.JifString arg1);
        
        native public boolean equalsIgnoreCase_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifString arg2);
        
        native public boolean equalsIgnoreCase$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifString arg2);
        
        native public int compareTo(java.lang.String arg1)
              throws java.lang.NullPointerException;
        
        native public int compareTo_remote(fabric.lang.security.Principal arg1,
                                           java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int compareTo$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int compareTo(fabric.lang.JifString arg1)
              throws java.lang.NullPointerException;
        
        native public int compareTo_remote(fabric.lang.security.Principal arg1,
                                           fabric.lang.JifString arg2)
              throws java.lang.NullPointerException;
        
        native public int compareTo$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifString arg2)
              throws java.lang.NullPointerException;
        
        native public int compareTo(java.lang.Object arg1)
              throws java.lang.ClassCastException,
            java.lang.NullPointerException;
        
        native public int compareTo_remote(fabric.lang.security.Principal arg1,
                                           java.lang.Object arg2)
              throws java.lang.ClassCastException,
            java.lang.NullPointerException;
        
        native public int compareTo$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.Object arg2)
              throws java.lang.ClassCastException,
            java.lang.NullPointerException;
        
        native public int compareToIgnoreCase(java.lang.String arg1)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase(fabric.lang.JifString arg1)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase_remote(
          fabric.lang.security.Principal arg1, fabric.lang.JifString arg2)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, fabric.lang.JifString arg2)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches(int arg1, java.lang.String arg2,
                                            int arg3, int arg4)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches_remote(
          fabric.lang.security.Principal arg1, int arg2, java.lang.String arg3,
          int arg4, int arg5)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2, java.lang.String arg3,
          int arg4, int arg5)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches(boolean arg1, int arg2,
                                            java.lang.String arg3, int arg4,
                                            int arg5)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches_remote(
          fabric.lang.security.Principal arg1, boolean arg2, int arg3,
          java.lang.String arg4, int arg5, int arg6)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2, int arg3,
          java.lang.String arg4, int arg5, int arg6)
              throws java.lang.NullPointerException;
        
        native public boolean startsWith(java.lang.String arg1, int arg2);
        
        native public boolean startsWith_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2, int arg3);
        
        native public boolean startsWith$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2, int arg3);
        
        native public boolean startsWith(java.lang.String arg1);
        
        native public boolean startsWith_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean startsWith$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean endsWith(java.lang.String arg1);
        
        native public boolean endsWith_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public boolean endsWith$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2);
        
        native public int hashCode();
        
        native public int hashCode_remote(fabric.lang.security.Principal arg1);
        
        native public int hashCode$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public int indexOf(int arg1);
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         int arg2);
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2);
        
        native public int indexOf(int arg1, int arg2);
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         int arg2, int arg3);
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2, int arg3);
        
        native public int lastIndexOf(int arg1);
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, int arg2);
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2);
        
        native public int lastIndexOf(int arg1, int arg2);
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, int arg2, int arg3);
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, int arg2, int arg3);
        
        native public int indexOf(java.lang.String arg1)
              throws java.lang.NullPointerException;
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int indexOf(java.lang.String arg1, int arg2)
              throws java.lang.NullPointerException;
        
        native public int indexOf_remote(fabric.lang.security.Principal arg1,
                                         java.lang.String arg2, int arg3)
              throws java.lang.NullPointerException;
        
        native public int indexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2, int arg3)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf(java.lang.String arg1)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf(java.lang.String arg1, int arg2)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf_remote(
          fabric.lang.security.Principal arg1, java.lang.String arg2, int arg3)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, java.lang.String arg2, int arg3)
              throws java.lang.NullPointerException;
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          fabric.lang.security.Principal arg1);
        
        native public java.lang.String toString$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1);
        
        native public static boolean jif$Instanceof(
          fabric.lang.security.Label arg1, java.lang.Object arg2);
        
        native public static fabric.lang.JifString
          jif$cast$fabric_lang_JifString(fabric.lang.security.Label arg1,
                                         java.lang.Object arg2);
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_JifObject_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_Hashable_L();
        
        final native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(JifString._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    final public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.JifString
    {
        
        native public java.lang.String get$s();
        
        native public java.lang.String set$s(java.lang.String val);
        
        native public fabric.lang.JifString fabric$lang$JifString$(
          final java.lang.String s);
        
        native public int length();
        
        native public int length_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public char charAt(final int index)
              throws java.lang.StringIndexOutOfBoundsException;
        
        native public char charAt_remote(
          final fabric.lang.security.Principal worker$principal,
          final int index)
              throws java.lang.StringIndexOutOfBoundsException;
        
        native public boolean equals(final fabric.lang.Object anObject);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.Object anObject);
        
        native public boolean equals(final fabric.lang.IDComparable other);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.IDComparable other);
        
        native public boolean equals(final fabric.lang.security.Label lbl,
                                     final fabric.lang.IDComparable obj);
        
        native public boolean equals_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.security.Label lbl,
          final fabric.lang.IDComparable obj);
        
        native public boolean equalsIgnoreCase(
          final java.lang.String anotherString);
        
        native public boolean equalsIgnoreCase_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String anotherString);
        
        native public boolean equalsIgnoreCase(
          final fabric.lang.JifString anotherString);
        
        native public boolean equalsIgnoreCase_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifString anotherString);
        
        native public int compareTo(final java.lang.String anotherString)
              throws java.lang.NullPointerException;
        
        native public int compareTo_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String anotherString)
              throws java.lang.NullPointerException;
        
        native public int compareTo(final fabric.lang.JifString anotherString)
              throws java.lang.NullPointerException;
        
        native public int compareTo_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifString anotherString)
              throws java.lang.NullPointerException;
        
        native public int compareTo(final java.lang.Object o)
              throws java.lang.ClassCastException,
            java.lang.NullPointerException;
        
        native public int compareTo_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.Object o)
              throws java.lang.ClassCastException,
            java.lang.NullPointerException;
        
        native public int compareToIgnoreCase(final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase(final fabric.lang.JifString str)
              throws java.lang.NullPointerException;
        
        native public int compareToIgnoreCase_remote(
          final fabric.lang.security.Principal worker$principal,
          final fabric.lang.JifString str)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches(final int toffset,
                                            final java.lang.String other,
                                            final int ooffset, final int len)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches_remote(
          final fabric.lang.security.Principal worker$principal,
          final int toffset, final java.lang.String other, final int ooffset,
          final int len)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches(final boolean ignoreCase,
                                            final int toffset,
                                            final java.lang.String other,
                                            final int ooffset, final int len)
              throws java.lang.NullPointerException;
        
        native public boolean regionMatches_remote(
          final fabric.lang.security.Principal worker$principal,
          final boolean ignoreCase, final int toffset,
          final java.lang.String other, final int ooffset, final int len)
              throws java.lang.NullPointerException;
        
        native public boolean startsWith(final java.lang.String prefix,
                                         final int toffset);
        
        native public boolean startsWith_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String prefix, final int toffset);
        
        native public boolean startsWith(final java.lang.String prefix);
        
        native public boolean startsWith_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String prefix);
        
        native public boolean endsWith(final java.lang.String suffix);
        
        native public boolean endsWith_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String suffix);
        
        native public int hashCode();
        
        native public int hashCode_remote(
          final fabric.lang.security.Principal worker$principal);
        
        native public int indexOf(final int ch);
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal, final int ch);
        
        native public int indexOf(final int ch, final int fromIndex);
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal, final int ch,
          final int fromIndex);
        
        native public int lastIndexOf(final int ch);
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal, final int ch);
        
        native public int lastIndexOf(final int ch, final int fromIndex);
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal, final int ch,
          final int fromIndex);
        
        native public int indexOf(final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int indexOf(final java.lang.String str,
                                  final int fromIndex)
              throws java.lang.NullPointerException;
        
        native public int indexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String str, final int fromIndex)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf(final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String str)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf(final java.lang.String str,
                                      final int fromIndex)
              throws java.lang.NullPointerException;
        
        native public int lastIndexOf_remote(
          final fabric.lang.security.Principal worker$principal,
          final java.lang.String str, final int fromIndex)
              throws java.lang.NullPointerException;
        
        native public java.lang.String toString();
        
        native public java.lang.String toString_remote(
          final fabric.lang.security.Principal worker$principal);
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label,
                     final fabric.lang.security.Label jif$L) {
            super($location, $label);
        }
        
        native private void jif$init();
        
        native public static boolean jif$Instanceof(
          final fabric.lang.security.Label jif$L, final java.lang.Object o);
        
        native public static fabric.lang.JifString
          jif$cast$fabric_lang_JifString(final fabric.lang.security.Label jif$L,
                                         final java.lang.Object o);
        
        native public fabric.lang.security.Label
          get$jif$fabric_lang_JifString_L();
        
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
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.JifString._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
            public _Proxy(fabric.lang.JifString._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.JifString._Static
        {
            
            native public fabric.worker.Worker get$worker$();
            
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
