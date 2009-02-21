package sif.servlet;

public interface SessionPrincipal extends jif.lang.AbstractPrincipal {
    
    public sif.servlet.SessionState get$session();
    
    public sif.servlet.SessionState set$session(sif.servlet.SessionState val);
    
    public static class $Proxy extends jif.lang.AbstractPrincipal.$Proxy
      implements sif.servlet.SessionPrincipal
    {
        
        public sif.servlet.SessionState get$session() {
            return ((sif.servlet.SessionPrincipal.$Impl) fetch()).get$session();
        }
        
        public sif.servlet.SessionState set$session(sif.servlet.SessionState val) {
            return ((sif.servlet.SessionPrincipal.$Impl) fetch()).set$session(
                     val);
        }
        
        public $Proxy(SessionPrincipal.$Impl impl) { super(impl); }
        
        public $Proxy(fabric.client.Core core, long onum) { super(core, onum); }
    }
    
    final public static class $Impl extends jif.lang.AbstractPrincipal.$Impl
      implements sif.servlet.SessionPrincipal
    {
        
        public sif.servlet.SessionState get$session() {
            fabric.client.transaction.TransactionManager.getInstance().
              registerRead(
              this);
            return this.session;
        }
        
        public sif.servlet.SessionState set$session(sif.servlet.SessionState val) {
            fabric.client.transaction.TransactionManager tm =
              fabric.client.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.session = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        sif.servlet.SessionState session;
        
        public $Impl(fabric.client.Core $location, jif.lang.Label $label,
                     final sif.servlet.SessionState session) {
            super($location, $label);
            this.jif$lang$AbstractPrincipal$("");
            this.set$session(session);
        }
        
        protected fabric.lang.Object.$Proxy $makeProxy() {
            return new sif.servlet.SessionPrincipal.$Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intracoreRefs,
                               java.util.List intercoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
        }
        
        public $Impl(fabric.client.Core core, long onum, int version,
                     long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intracoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(core, onum, version, label, in, refTypes, intracoreRefs);
        }
        
        public void $copyStateFrom(fabric.lang.Object.$Impl other) {
            super.$copyStateFrom(other);
            sif.servlet.SessionPrincipal.$Impl src =
              (sif.servlet.SessionPrincipal.$Impl) other;
            this.session = src.session;
        }
    }
    
    interface $Static extends fabric.lang.Object, Cloneable {
        
        public int get$__JIF_SIG_OF_JAVA_CLASS$20030619();
        
        public int set$__JIF_SIG_OF_JAVA_CLASS$20030619(int val);
        
        public int postInc$__JIF_SIG_OF_JAVA_CLASS$20030619();
        
        public int postDec$__JIF_SIG_OF_JAVA_CLASS$20030619();
        
        final public static java.lang.String jlc$CompilerVersion$jif = "3.0.0";
        final public static long jlc$SourceLastModified$jif = 1235174111000L;
        final public static java.lang.String jlc$ClassType$jif =
          ("H4sIAAAAAAAAAJVYC3BU1Rk+2bwfkAQSEzGPJQQJCrsxIFJi1RAIBDYSk/AK" +
           "I+vdu2c3F+7uvd57\nNtmEQnUY5dFaZQRqxxakZYoFrBVm1AkdpICvOlbLTI" +
           "GZDljUWtoK1U4daUfH/uec+9q7C2kzc2/u\nnvuf//yP7//+c8/hKyhX11Bj" +
           "RAhpkugjwyrWfR3sR7eg6TjcLgu63gfDQTExo67+wMJ72zwIJTVU\nu06KGP" +
           "JLpAgX7lbkYS575vE9+16fe/lND8oOoEIhQQYUTSLDBJUF1gmDgj9BJNkfkH" +
           "TSGkBlUlwn\nQpxIAsHhDk2JETQ5oIKqqKwQP04SvypoQszPFvN3M4tgWh4b" +
           "1R9Gm5AH7PGaMwyj0szfMFo0q3LN\n6ZFsVNqPSqV4LxGIJLYrcQJL9KOSGI" +
           "6FsKa3hcM43I/K4xiHe7EmCbI0AoJKvB9N0KVoXCAJDes9\nWFfkQSo4QU+o" +
           "WGNrmoMBVCIq4JOWEImicQvB3oiE5bD5KzciC1GdoJtsT7l/HXQc3CuSwDAt" +
           "IojY\nnJKzXoqHCap3z7B8bFwKAjA1P4Yh3tZSOXEBBtAEHnlZiEf9vUST4l" +
           "EQzVUSsApBk66rFIQKVEFc\nL0RxkKBqt1w3fwVShSwQdApBlW4xpgmyNMmV" +
           "JUd+luWVfL29+0uvB2WBzWEsytT+AphU55rUgyNY\nw3ER84nXEr6dnasTNR" +
           "yXlS5hLtM29ZXlgcuv1XOZWzLILAutwyIJil/Nqak90/ZxYTY1o0BVdIkm\n" +
           "P8VzBt5u401rUoUCusnSSF/6zJcnet5Y/chB/HcPKuhEeaIiJ2LxTlSI4+F2" +
           "4zkfngNSHPPRZZGI\njkknypHZUJ7CfkM4IpKMaTjy4VkVyAB7TqoIoXFwTY" +
           "ErD/E/9p+gil6s62BBNyRalFRB9kGJE9RC\nsE50vy5F/PB7pq6J7FnH2qCM" +
           "iT/TpCRdq2woKwvcrHEXmQz4XKzIYawFxQMf/fY7C5du28oTSEFn\nWElQDS" +
           "ziMxbxuRdBWVlMeVVqDGlSwpQiPj3SWvaDmfrLwCX9qFCKxRJECMkYqkyQZW" +
           "UIh4OEga7c\nAXCTJEpCgE+AelAGRZwqVDSooQY3Du167WRsJOIzm755/2pw" +
           "6CiFDE1xBdXOTYOEree2lUzvfXDJ\nQ1sbsqnQUA6EnnrSkMKNGXQHxeHfVM" +
           "4bPfHvYx6U2w8MqC/AESEhk+72+UoiDrRSYQ31YGCceEAI\nYTmAijmxCEAO" +
           "ZnnnqyKbQ1BVANY1Ck6m8n42C8JQrNlK6DQvFEHj2CEIile3dx09+86FJrso" +
           "CWpM\n44r0mbTW3THu1hQRh4E9bfX7by7NXolW7PCgHCAQ8I2AZ5SP6txrpN" +
           "R8q8mf1JdccC+iaDFBpq/M\nqBSRAU0ZskcYisez53LIUjFcrXAVGlXD/tOX" +
           "E+mtgmOeJt3lA2Pna5vzms/9uvh1FhSTyEsdja0X\nE04L5TZm+jSMYfzCM9" +
           "1P77qyZQ0DjIEYAr0sEZIlMcnsq84CgE7MQFG+6oqdu6f/+JyJyIm29jZN\n" +
           "E4YpIJOPnqn90ZvCT4C+gEZ0aQQzlsiysFllY5PBAYc5Q/6+eNZW752RCg+N" +
           "XiGDD7RQVjNeIEI6\nw/xdxMIzzgpmDVwtNwgmrDrJXpWpBoOjbPmguG31pC" +
           "PPnay8wjDgESWCatMBFrZA0+rkFshzQoWu\nwwrAMNUzCCpq3CpWCCnYrHLb" +
           "YxizYPPU9QX/iR1gxhSHsS5qkkqZ3FxOl2KqLEErN5fLI8oSSAPt\ny6z6NC" +
           "Guy7CT4bXXx14uTKoa7ZCDAuwngLpY5JpckJwxRhQJWk59iimaOiCJXma2V4" +
           "l4Ofa9ghZN\nxHCceHVOr/SVYxfibQrRGOGwVwgpg9gbGvZuIAOSvnE61T4P" +
           "UjSVhcQ039cuxOMKcTkRFP+27OqJ\nERW/xRtpfeqcNOnJv6z9rPHw2qkmYq" +
           "vcHLpY0AegKs7L5/p3Xbitjmt1VI3xfnTBY7t2v/rKbE6z\nJRCYsnvv42Fi" +
           "UZxKUL7hN6xyizu5feCokeCWr068991j7Z8wmHtEhudGwHckIcv3Wy2L3ufS" +
           "2wQI\ne6MVajKAvbqKRdgUes1cehtoGBtUlsdvq4w4xmh4tJGmdIiAIgqyzY" +
           "oVG+ffdeACfonzi+wkbfdW\nyDXzjd3R2XtffDGXx9HdrR01EBTnnh0sz/vV" +
           "3pgH5UMDYkiBPfgKQU5QmuqH/afebgwG0LiU96l7\nSb5xanXs2e5y0bOzXn" +
           "OoKTaJjE8hkXk3gH8WYrHtYhOmsfttFn/mRqS4IDON7exVC73NZgML2UAl\n" +
           "fHowWFGbfdxmhnx66zWJl9797LmZLs/UGwiDLYpNznb22nTIMiWIu6u2fvhe" +
           "7e/6ONTdKjKhsluQ\nNAOV5z7c+PaRrpP7GSqLINgR+JSSxGFKZO6e3m69pY" +
           "2dfiVETeHaNOFO+3WrjepbkyyWa8GqKakV\nbNnkrOI7H7n8r6N/eHmax4jF" +
           "PbSU69zu9GABtoF8raD45CppygNNNy9iHuUqQ6yr1zvsU81q8Ft1\nQT/qNK" +
           "aFrhICBFenxcxQ3xTIe/WFbz67h5WIg5TpPt0dA6f747n79I5TN2qWPb4+Rb" +
           "VMCoqLL538\n4LEfVr/jJDzXBIf0X2PPPvv9oep9zG2rNUxxtQZrwtjtgdsL" +
           "pt6amiinkc5cnX900sXbZzxp8LM7\nu5lm/OzQl/tHpv08yoDLkRsxVqX/1m" +
           "VM9kr4mreTrez4x6ydmzv2OpLNMgghGGKCPJ/0LtoJUK2m\n44rnfIUQJeaI" +
           "astbzVVtz3e9ZAIwakVleqqDrplON+8o+enxPx/8xXOmjvXc1Ycdrup8aI3K" +
           "y2OI\n/QryQUFls4b5JDVlguunwkU38VCqVhhTfxqUQz9G3DzdQQ8KTKaOhT" +
           "Z8cWpPkdfmlVrGKZ60LpIy\nLSg+MX7g6/urzgLBe/rR+AFB74zDNyk9zsDa" +
           "WG3FpWrk+PI9194lF1m12dt01juZr0stGp8I191w\n5Rs0np+Zxrel07gHej" +
           "hgAOoFGkyezk5noPt6g8ElnR3B3s5FwWUdwSVtK9qC7YG23t7GlubmWc1z\n" +
           "7vgW53sjnmbudmTI3dP/e+6eGUM0PWKAuRiEd9A4k9hRt/+Tox/1VPDvJn5w" +
           "MyXt7MQ5hx/e8J21\nSqE9+UYrMOnTt08+vKnnYojX+oTUz+eF8UTsL8On8L" +
           "S7n7iU4Ys8GxoH26tk2eHaYmWxCK7pcBUY\nWSzInMX9GZoxfX4g6diPsfSk" +
           "aPbeQDMTXMC3XvTeQW+H/o/NFevnTJCpPZTMgDRmI73NMKvQbvy1\ndseuvd" +
           "5hFYv+llWflzwunH7QJJR9BBUSRZ0p40Es28XtVtLFzubM2ipdWX+pY86Bje" +
           "7qRhDBMrdr\nFHfVacec/GhObDjzUNMptfxt9uVibcUKja2tcwfmeM5TNRyR" +
           "mPmFvN9w/B8jqNgRZJZN9sRMHOVC\nxwFFIEQfX1PNTRb70GN7rLYQPagQiW" +
           "W+g/Fqb9hGt53e+/na958qv+5WqiqF97oS/Ew4KH6qrl30\nx54PDhrNzwoV" +
           "ThIfOy02c2DNWPXCGm/ye31P8c4lysLICF2vIIDy+XeVdb48+braTF3Sn85G" +
           "tm/+\nuNQExHgbxWW263XX10OfV4xrPb90dPT5dEDYKhzuszkmmGY3fZF/7d" +
           "1/3pc5bv8F2FPbeusXAAA=");
        final class $Proxy extends fabric.lang.Object.$Proxy
          implements sif.servlet.SessionPrincipal.$Static
        {
            
            public int get$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                return ((sif.servlet.SessionPrincipal.$Static.$Impl) fetch()).
                         get$__JIF_SIG_OF_JAVA_CLASS$20030619();
            }
            
            public int set$__JIF_SIG_OF_JAVA_CLASS$20030619(int val) {
                return ((sif.servlet.SessionPrincipal.$Static.$Impl) fetch()).
                         set$__JIF_SIG_OF_JAVA_CLASS$20030619(
                         val);
            }
            
            public int postInc$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                return ((sif.servlet.SessionPrincipal.$Static.$Impl) fetch()).
                         postInc$__JIF_SIG_OF_JAVA_CLASS$20030619();
            }
            
            public int postDec$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                return ((sif.servlet.SessionPrincipal.$Static.$Impl) fetch()).
                         postDec$__JIF_SIG_OF_JAVA_CLASS$20030619();
            }
            
            public $Proxy(sif.servlet.SessionPrincipal.$Static.$Impl impl) {
                super(impl);
            }
            
            public $Proxy(fabric.client.Core core, long onum) {
                super(core, onum);
            }
            
            final public static sif.servlet.SessionPrincipal.$Static $instance;
            
            static {
                sif.servlet.SessionPrincipal.$Static.$Impl impl =
                  (sif.servlet.SessionPrincipal.$Static.$Impl)
                    fabric.lang.Object.$Static.$Proxy.$makeStaticInstance(
                      sif.servlet.SessionPrincipal.$Static.$Impl.class);
                $instance = (sif.servlet.SessionPrincipal.$Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class $Impl extends fabric.lang.Object.$Impl
          implements sif.servlet.SessionPrincipal.$Static
        {
            
            public int get$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                fabric.client.transaction.TransactionManager.getInstance().
                  registerRead(
                  this);
                return this.__JIF_SIG_OF_JAVA_CLASS$20030619;
            }
            
            public int set$__JIF_SIG_OF_JAVA_CLASS$20030619(int val) {
                fabric.client.transaction.TransactionManager tm =
                  fabric.client.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.__JIF_SIG_OF_JAVA_CLASS$20030619 = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                int tmp = this.get$__JIF_SIG_OF_JAVA_CLASS$20030619();
                this.set$__JIF_SIG_OF_JAVA_CLASS$20030619((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$__JIF_SIG_OF_JAVA_CLASS$20030619() {
                int tmp = this.get$__JIF_SIG_OF_JAVA_CLASS$20030619();
                this.set$__JIF_SIG_OF_JAVA_CLASS$20030619((int) (tmp - 1));
                return tmp;
            }
            
            private int __JIF_SIG_OF_JAVA_CLASS$20030619;
            
            public $Impl(fabric.client.Core core, jif.lang.Label label)
                  throws fabric.client.UnreachableNodeException {
                super(core, label);
            }
            
            protected fabric.lang.Object.$Proxy $makeProxy() {
                return new sif.servlet.SessionPrincipal.$Static.$Proxy(this);
            }
            
            private void $init() { __JIF_SIG_OF_JAVA_CLASS$20030619 = 0; }
        }
        
    }
    
    final public static java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    final public static long jlc$SourceLastModified$fabil = 1235174111000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAIU5a8zj2FWZmd2Z2ey0u7O7bZd2t7vtLtAqdJzYiR2zRZXj" +
       "2IkdJ7Hj+JGU6qvf\ncfx+Oy5UIFCf4lFoK5Cg/KlUCfUHohJICAGiBcRLQv" +
       "uj/dVC1QqQoJX4gagQUOzk++bxzRQiXfvm\n3nPOPfc87z3+4ndajydx652m" +
       "otrunfQQGskdUlEphlXixNBxV0mSdT16pl39ybf8yvt+/j//9Gqr\nVcatl8" +
       "PAPVhukJ7jPAT+oT9oQ296/1eqa62ntq2nbJ9PldTW8MBPjTLdtm55hqcacY" +
       "LpuqFvW7d9\nw9B5I7YV165qwMDftp5JbMtX0iw2kpWRBG7eAD6TZKERH9e8" +
       "GGRat7TAT9I409IgTtLW08xeyRUg\nS20XYOwkfY1pXTdtw9WTqPXh1lWm9b" +
       "jpKlYN+GbmYhfAkSJANuM1eNuu2YxNRTMuUB5zbF9PWy9d\nxri741dnNUCN" +
       "esMz0l1wd6nHfKUeaD1zYslVfAvg09j2rRr08SCrV0lbb/2BRGugm6GiOYpl" +
       "nKWt\n5y/DsaepGuqJo1galLT1pstgR0q1zt56SWf3aWt5/dZ/f4L9j5evtq" +
       "7UPOuG5jb8X6+R3n4JaWWY\nRmz4mnFC/F5259PUJnvhZBVvugR8gsF++PcF" +
       "5p//+KUTzNseAbNU94aWnmn/Bb/w4uvYt5+41rBx\nMwwSuzGFB3Z+1Cp7Pv" +
       "NaGdbG++a7FJvJOxeTf7L6883P/LbxL1dbN6nWdS1wM8+nWk8Yvo6f92/U\n" +
       "fcb2jdPo0jQTI6Vaj7nHoevB8X8tDtN2jUYcj9f9UEl3x34ZtlqtN9Ttlbpd" +
       "b51+x3faeo43kqTm\ngK0Vrdmh4t6p3SttgamRpAmQ2CZQ/39PEmvHfmLEuW" +
       "ukwKOQymatNxZXrtTbfOGyy7m1fU4DVzfi\nM+0L3/qrnyJmH//YSYGN0Z1z" +
       "mbZeqBe5c77IncuLtK5cORJ/y4MybJSiN77zr7/72tO/+J7k9662\nrm1bT9" +
       "iel6WK6hq1zymuGxSGfpYeje72fQZ+tKvaKG+ptX3Wpn7m1oSO/lALLa+DzW" +
       "U7vOe9VN1T\nauN6/cPf/7vvnhVfakymUfFzDfUTa7XCnBNvt97Nf4D+4Mfe" +
       "ea0BKh6rRd/s5NX/n/qZ9t1PzL/0\n1b/++rvu2XvaevUhN3wYs3Gjy+yzca" +
       "AZeh2m7pH//A89dU1qiZ+62nqs9s06OqVKbVO1q7/98hoP\nuNNrF6GpEdY1" +
       "pvWkGcSe4jZTF/Gkne7ioLg3cjSQW8f+U7UAnqzba3V74twgj+9m8nbzeOZk" +
       "To08\nL+3hGPi+93PXu1/7wyf/7CiUixj51H3BlDfSk8fdvqeOdWwY9fjXf4" +
       "391c9856PvP+riXBlp63qY\nqa6tlUf+3nyl1v2zj/D+O88/9+nPvvs3vnah" +
       "7GfvUcfiWDk0ui5/9vUXf/0vlN+sI0PtoYldGUcH\nvHKu9ob+s3XkP2I2Rn" +
       "jnFFIuVm6enWP/xxqJHLFaR2m84xykMczLHkY2SeNCq576oX//8ufaL5+4\n" +
       "bHDediTTpM/LQfIBxDOt+iPhc9/72/QbR8HeM4eGxsvlw8uKyn2WOvxqfvv6" +
       "7/yWd7V1Y9t6+pjo\nFD8VFTdrxL6tU1WCnw8yrTc8MP9g2jkJ5LW75v7CZV" +
       "O8b9nLhngvoNT9Brrp37xke8/W7b11u3Fu\nezcu2d6VVth0fvyI8Mrx+SMn" +
       "S7matm6EsZ0rac3z9eR4UijT1stnZzRFnvHU5GxJntGYiJ3hDMbz\nr4LdLt" +
       "SFe+gjJF8HNq+O/vl5evrU2z//j1/61uq5k5+fcvgrD6XR+3FOefy4tSfDsl" +
       "7hHf/XCkfo\nr3Te8cUPr76hnvLbMw9GUsLPvH86fNn40ff+wjcfEZyv1SeN" +
       "k1M2T/iuMNt1e3fdbp4L8+ajhTl9\nWJitWnI3klOYb4bvPET++6ff/zStQW" +
       "j+nBLXM3jghXW2i1+eGLWB1vrQw/JK7ciPQ3e6d7oN9vzh\nFa/V86btK8cz" +
       "w7uax/tqHt6yd7VXL+iJ9VGv5ufVvW0+wldPJ6L7+Gwei/KYmN54D4wJ6mPT" +
       "J7/9\ny3/zS6/8fS1KuvV43ph5LfP7aC2y5lz5kS9+5sUnP/0Pnzw6ax0gXv" +
       "rsi8jzDVWpeXD1mavhjg+y\nWDMYJUnngW7XR0T9LoNYeIoL47Q+DwSPZC59" +
       "YzDtJxR28aPFDQ6NhN7K6YBqYToYxxPVhKU5jsbj\npKRI2u6tMGo03vAOgM" +
       "2oTq5noAaRHSTL9QHSXqQewfnCZqCTypZbRWWpONtNQMYj0ethjh5SeL50\n" +
       "VWZNTbZjdy9IaTDrCX1dEcwpkBlaB40QHHQqDQSytp9nJgAcWAQAOtoQ2M0O" +
       "O7nXG+1X2C50Mmtf\nHGLJXEHJCjEseL3XGCuDIcscc2uiDGCkU8xpBKwKil" +
       "6LQjvrwrN6I0yIWTLiqs6GHyCDnEA7Pdg0\nAHcxHgbLgKB2HLcR1jwzHtEC" +
       "2bO8sbVHR/TCMZ3pmp84I1dwC7/NhSWFJdPd2AFpYs0NAmoWRbOC\nY7t9Pj" +
       "+EAZdULJkvoHk/taRdX+2t0Ik3wKYIO1HmxWHfTSpzTgbdCGIWbXhLJuIiYv" +
       "lI7DPlhKKZ\nTaxxNIPOlvyEgslkpkA7bd6HKGDWL+2pgRAerlJdLKBn+Qhd" +
       "dGdiIOMYPTYStC2RpsPxQZ8TOoE0\nWcReMB+JOBX16aVEUJ6tcfkICmxioI" +
       "4Hs0qz8d5ED+g0WSMuMJU5DcIzvhhjKRXsN22fnlVyEE1x\nJYQqZDRUyWKG" +
       "7bEe29/soHTLL2ViNEoxYuq7W5sw0Vzg132X4CpR4BMsHDkwHJMUNxMJUmsz" +
       "3VXp\nzEZR2isgsxD5HV9weg3kSv0ytLzDYu6JtKB33EwSTQZEO90sH4HSqJ" +
       "KI5JCuO1i4EwaDWLAOi027\ngIjVsO/LRAzrvpKsF6uD7PKDHZPOQXQru5aW" +
       "5T15uZUqhmKwLC0OFF0n/0WkOGgX2Jab9aYXdEei\nTjlemx2K1SgdZP1uLx" +
       "YCAc8Sn+dJAK5ozTfSwQbQdiNgqrGzbEcYCmXTIFOuXZ7qd0iwXzmmby34\n" +
       "KcuuttsJ1d47jn8Ip1uBRnDTmfepcDZX1vtDX3TEAzZC1lFABTJZrDdEPAYI" +
       "AA07lu3CCQyORovD\nSnHJ1Qh1JjSBrvB2Qgy6+WFAAuwICSOYRkh/Ckb43F" +
       "4D6xBf6RFYBLMi1+IpIXtc1ZdnFbVRCrLk\n5vX5fIPgg9o9h8GaJ0i/LREu" +
       "N+Yre16qZbbSR5Lozg1RjMYTly9Czmb3EC/lytgzY6bAZTQi8Vm5\nogl87m" +
       "wi58BtJYHa9ZeEpSBaG5PxNU2EuEV0MMPFJqttEYljPgi4DTTOejAirRJ6SV" +
       "UeMhgAYFjA\nuE2OiV450uXlerMk6hMX6ApVbspjus31EjWJfE7AYML3Y96Z" +
       "zxYTmZuWxtQU532VNaf5poQFsBjO\nIxzACJ7YOR0E7AiBPY5X/IaDepsJrh" +
       "kERrU3aw+z916JE9hy6/t+dJipXG8kYotND3dgb+j3QXxI\nc/HCncFjLieJ" +
       "6R7ECaoO8ZBedtHOECbpOAlwKZy3+/2466lrCVpPs96OW+36BKqaMqIekIUc" +
       "uBLC\n4tYUx7ghBWLbAc/CaRmHi9UwGnpmj9Y1De/YypgchREwb0cjfoUJ3W" +
       "3iaBs7MaoBxFDCntRiGRMn\nrjwG0tqhOl5qeUuDTiEXA1lfGeSVlwBolu96" +
       "5I5fH4hE3ytduU10+8xgvfU6e36+MSt8jvZYBIkO\nW6473lPEBou7CbGl3L" +
       "nmJ9GwXysKmXPkpAMHkZ7BJtChD/SB8kB0v120if4mpOgl60c+2t3m+LqP\n" +
       "dyxi21U4rnIT+5CQrrsZsCNsow04AO4Yi62EDMAVbnUWsj0Zj5EVJ+4XBkVQ" +
       "cLsASr2cU1Vlhd5Y\nLNJFYXnxOKblUApHwXKvsp6VTaBeiLP1/g7DYj+2CF" +
       "7ClXQ+IuMt1u3EDpnsKi6QsnZRxNh8IVQ4\nHkYQHko47m/iKaNuSrvasKyA" +
       "cjJgr3pWueOnCT+IHCfaQmS5dMFZxBBEvBiYDDUMeB5j0/YBruAF\nPskOSL" +
       "1DVZbLXj9bzwIF2eJjLCt8iwk3AzrUHLooVzuOlpwBatuesrRxWvYUUOy7/a" +
       "WpoNaullk5\nrQ1uKi5jgBc3pWmrXLyaLCeFWFihhVrqJN9mFLbkc5+Ae+s+" +
       "K+x5cadxXQjVsnI5n2lrZ0WyHRBZ\nRnSbWElAuUpD04i0xdKnc7hCY3ZagJ" +
       "HjbaJcm9acdTHDmiUIMwBCSFbtdO+lPZGCStNfjzZICuZA\nPFMJuGrTK3yT" +
       "+HneB0lb2JYzgUF2C7g82FU2MA08cAxcirvwEEV7e2Q7LLxpSuwyfVJMNYfS" +
       "ckg6\ngAyUmTxYcFTbgOeisp9huRrNVrolGElPM3ZpMBmlgpvoedQv+qW366" +
       "f0xNMpG6Os6FBtAnHSMU3d\nwHAz8Cu7UziqqUvtw1iu9jq0AuXUNMcTuUSn" +
       "erzq9ojR2vd3dVYEchEKrelAmI83jj1iTWvGCQPO\ny6IpoJiGahWTLmMLKO" +
       "wibV4dlIovJ6AxOHTVZSX7y2C2Vug9Mdw5sgNZ06FU20q62S29xExT0ge8\n" +
       "VcT3qfFygRiliCPQYmvNGVDA3LY5DQqJnNRc1pvxSxiuJNpFERRhvC2TAMKk" +
       "sMfMJlxUvgqC/pqa\n2xKVwRlo5jsDMQB4uVjrs/1sl7gZ115gS3FQIVqHVV" +
       "ZxyCITPmKohbXq0DxfTsjZdgkbkAej4QbU\nTVsk1sZUWSzJZQeS4r0Rqs7h" +
       "ICnDSTRTHLGtLnwnXlLIFPGHMbwAjKmnlP09yaq8AKNVhk6wURqQ\nw1gZ9w" +
       "d4GB6sxWJxQO1pJkQcfIh7VZ4Iw140JNNem1sD0WKmkPGOwkek4ZdayUHlmj" +
       "IAiVQrGNkN\nJMoarEM27eoYbCJ9xEdQarBegJHEqv3OShXneBAxzAAatvsM" +
       "YYoxsKfHlTyizK4Msiyb52hghGg1\nAeOFUJYpt42LdJ9aesKEKax4tu072k" +
       "DzZbTbTWR1W5XhkJkj7WzgesCy4lwgnYZ5d3gIxoyzSkox\nkoGeJ85RJ+57" +
       "sVFf1at+CR90r8rBZcgs5pa1m0xmOEsDXSYNaGEGek3Y9k1K4Og6XdcnY7US" +
       "xbSC\nDHSLUUUtU9eIonEcustFZxh0aimwJTAs5aW3z2Wtt9H0OAuHCgXNDu" +
       "CSagNcfOAE0k366oAei66V\nZX7hCNBQxCNxt1ubZSRwFTqBxpXOccPZeqYs" +
       "d+vtUJNC1tFNzxXjyWwLd5mQOrQZ3urMZcSCgSyu\nLGZkAPGqg5EjpF+NFw" +
       "cHrZKxIk0gMpRip8NSaA/tu2TP64F62u3ts20d1dNJOKR6aK9oD1ZJ1w87\n" +
       "2zI23C5iTY39zsKWATlxDkblDPyATWFrSLj+ChpByYAsJEEveDGdoRGObCYg" +
       "lCITN1M9SetVbSS3\npAGU7H1PzXRvTUE6Cxgd2JgI8y4rzbBsZRM6ivUddL" +
       "dKgc46KIGeyI5z2h8U84Oc1ullsCsBY0iD\nvTYf1SdrYRPPl/467Q47+cz3" +
       "N8Fe7gRroeoY5FBYJ8xw6Q8ZvrYwW+hvkuGkDKYgnDpg7Q0bTyrx\nGRJqI2" +
       "HTlviK8ucyOtkGpSn3LVi32cTkHYVVDW48X8tzHJLztcFJnSkQAAvSyP01jY" +
       "fsdj23xiuP\nqh19Pss5NT/02npiNFeVHbnJO64rmW5VZP4U4Wh6hY5GAr/a" +
       "dsN+l0jAoZyKKUNWmToH7VCX5E64\nGcKjvYFEq914NI/1uE3W5xNFMzRgsl" +
       "uq430lLOZhapYpmom2qgmzvQKmWmF6xnzQHFFXHuvV1wK0\nAEbDfVQHhC29" +
       "XaDJaj5dini7LEiSIjhT7rGL9UpFupMFEjMbjlUszpakjgWQkYruJukAMjqA" +
       "r6CD\nYZR3BlJkRySKBDxAFP3ZFI5iF+q2y0GfHe/iRVUE4npHmctDF9TA8U" +
       "JiZXziAQcD6rFDre8eEobC\nSTCnqPGhIxuirvaHHTVW1iTE+8PBJPWATjtb" +
       "cEQ5tdRSkkAYkuVed2mK/TJmVbSrs+uBTow3s12V\nJZXjeZMNZG5JANF7AB" +
       "RqAzUfkmAdrIwskesL5k80N8+z8wvs7eP1+u7Xh/re2kwIx3tq+YgSS9Pn\n" +
       "jxf0i4LXvZrY284LB/V1+8Uf9I3gWOn4qPxvtz6ifOUDV8+LZ0TaeiINwve4" +
       "Rm649+pol4nMj59E\nLopLT0kvfZOEv/DTlwtpTeHi6cv16aYe+PxD35pOX0" +
       "S0d77+wXd9Obz9l8cy692vFjeY1k0zc937\na1b39a+HsWHaR/ZvnCpY4fEV" +
       "pK0n76uUH4sox96RRf8EFKeta8lJ1El4Ucl4ay38U/EBU5M0VrT0\nLvvl/w" +
       "LqZwW2YBsAAA==");
}
