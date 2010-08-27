package fabric.util;


public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {
    
    boolean hasPrevious();
    
    fabric.lang.JifObject previous() throws fabric.util.NoSuchElementException;
    
    int nextIndex();
    
    int previousIndex();
    
    void set(final fabric.lang.JifObject o)
          throws java.lang.IllegalStateException;
    
    void add(final fabric.lang.JifObject o) throws java.lang.ClassCastException,
        java.lang.IllegalArgumentException;
    
    fabric.lang.security.Label jif$getfabric_util_ListIterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.ListIterator
    {
        
        native public boolean hasPrevious();
        
        native public fabric.lang.JifObject previous()
              throws fabric.util.NoSuchElementException;
        
        native public int nextIndex();
        
        native public int previousIndex();
        
        native public void set(fabric.lang.JifObject arg1)
              throws java.lang.IllegalStateException;
        
        native public void add(fabric.lang.JifObject arg1)
              throws java.lang.ClassCastException,
            java.lang.IllegalArgumentException;
        
        native public fabric.lang.security.Label
          jif$getfabric_util_ListIterator_L();
        
        native public boolean hasNext();
        
        native public fabric.lang.JifObject next()
              throws fabric.util.NoSuchElementException;
        
        native public void remove() throws java.lang.IllegalStateException;
        
        native public fabric.lang.security.Label jif$getfabric_util_Iterator_L(
          );
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    long jlc$SourceLastModified$fabil = 1282915709000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAL16aewrWXaX3+vd/WZ6ncmop3u6p6dBaUxebS5XFa0I7HKt" +
       "LrvKVeVaDNFLrXbZ\ntbl2mzACgTIhEetMWCSYCAkpEhohxAj4wIhEJKwKEp" +
       "oPhC8EUCKEBEHwATGKAuHafsv//btnGAkY\nS2Vf3zr33HPO/Z1z7q1T3/jN" +
       "3nNl0Xs/dNwovl8d86C8zzquIClOUQY+HTtlqYPeB97dP/RDf/73\n/4nf+o" +
       "d3e72u6L2XZ/FxE2fVwzEfI/99X/rt9le+In7+md4r694rUapVThV5dJZWQV" +
       "ete/eSIHGD\nohz7fuCve6+lQeBrQRE5cXQChFm67r1eRpvUqeoiKNWgzOLm" +
       "TPh6WedBcZnzUafUu+dlaVkVtVdl\nRVn1XpV2TuNAdRXFkBSV1UdS7/kwCm" +
       "K/PPS+3Lsr9Z4LY2cDCD8rPdICunCE2HM/IO9HQMwidLzg\n0ZBn91HqV713" +
       "b494rPEHM0AAhr6QBNU2ezzVs6kDOnqvX0WKnXQDaVURpRtA+lxWg1mq3lvf" +
       "lSkg\nejF3vL2zCR5Uvc/dplOutwDVSxeznIdUvc/cJrtwAmv21q01u7Fa8v" +
       "P3/udPK//jvbu9O0BmP/Di\ns/zPg0FfuDVIDcKgCFIvuA78Tn3/a4Jdv31F" +
       "xWduEV9pxr/r762k//gL715pPv8JNLK7C7zqgffb\no7ff+fb4N1565izGi3" +
       "lWRmcoPKX5ZVWVh3c+6nIA3s8+5ni+ef/RzV9U/7H9R/9G8J/u9l4Ses97\n" +
       "WVwnqdB7KUh9+mH7BdCWojS49sphWAaV0Hs2vnQ9n13+A3OEURyczfEcaEdp" +
       "mD1q5061vbS7vNfr\nvQCud8B1r3f9XH7PYAQIFMA6OwCc94Gb5VVPglYlwD" +
       "6UtUEK5UV2Vr6EgNGjvAwgQFNEHlQWHlTU\naRUlj7seI/omv+4swqfbO3eA" +
       "Jd6+7ZUxgDCfxX5QPPB+/tf/+U8wsz/5U9c1PuPyofDAvtcJrva7\nOUHvzp" +
       "0L4x962sTnNfPPdP/5b3/06p/+kfLv3u09s+69FCVJXTluHACXdOIYqOc/qC" +
       "6YfO0G/i+w\nA5i95wL4Ak94EANGF3cBdmxALLoN0yfOLYCWA7D37S//zr/8" +
       "Lw/ab54RdUbAm2fuj6RP91fZ7n2o\n/Zj44z/1/jNnovbZ83J0F7f87HmW26" +
       "ZizwHiEf/E/cP//Ze+3n/vyv885vMXBs+WH3eIpwY+8E7/\nYPX17/yL6tcu" +
       "Vn4JBKbKAXACXv6F2275lCed/fO2SIZTPOFL/qvmtef/1s8ld3svrHuvXgKe" +
       "k1aG\nE9eBFoCA2o9K+mGn1PvUU/efDj9XX/vooZtXvbdvy3Vj2o8excqzCZ" +
       "65iRrQPlOf2y9eEHjvQvPK\n71w//+t8PXSED66OcF33KZhTz9hztmE64PH3" +
       "z4K998NeluTAy4r3NkF6Rl7gf5jn3Z1efmb6u88r\nfNvqZ6m+88efh3/1Wy" +
       "//o4utHwX1V25Ef2CZa4h47QlA9CI4W+zf/CXlqz/7m1/5gxd0XOHxTAWY\n" +
       "RKkDjPJ8Xrtx5IFGeclbXdV7oc2KfVB8cNHzjar35kOfuXbfNy8/F1+8UHzx" +
       "u9rjT13t8eHFHo9y\nHuDwPS1xB8gG30fuw2euwwvvDy/fv/eh7Of2/fMXdP" +
       "6CgcBv7WLvA/ohOwPEGxATP7gK/UiHVy9m\nOcPi/jUr3ZD//IV3F+//9BMy" +
       "KQOp62d+48/+yp/50r8FZhd7zzVniAH03uC1qM+5/Se/8bPvvPy1\nf/czFy" +
       "fq9e78nm9+C+bPXH/0+xL/nbP4WlYXXiA5ZTXP/Agkcf+RBh93RKWIEhD4m4" +
       "eZ6c994a//\nh2/+uvrmNdxd0/eXPpZBb465pvALml/OOzDDF7/XDBfqXx58" +
       "8RtfVn/Nvaa215+OkkxaJ/jP/evg\nwz9wz/uEoPtsnH2iwavPrPlhKYwffe" +
       "YwQVtjD1kbkCjXJswx6+Mw2JemOZmu+N12WkV0KGwlvha2\n5tItxvC8WA+D" +
       "g8Uq/qgm+mqXERir06XXcNPYG041dYMUU5lJsu1kmUw8QR0vM1HcyfJK0Jj9" +
       "RC3l\nBDnI1twcUBgRetTGXNmzw2APYX0CCqF6BNVIgzTlSdLtcaW1xXgbIZ" +
       "ipxvCxxgpgBLLV6QQ3Juzc\na5G5SGqFRVCl5SkLHDVm3Lx0CsUg+7JpTQtc" +
       "1XlpG65O6yMqImETYnIOU6Mh7MXFVJcPmTZfONm6\nLCzV3mAdOTQ6Ye10a3" +
       "plxjBayOTQWS9MuD9ZmNpkVc2obe6giXQa7vJy4YiZsT/NzB3rzwUV3olT\n" +
       "dpDtI5Ndy+XAtuthZIyniLoeGgtjTODpABqOSFXpF/EGz3HdmBLsyFRReMKU" +
       "ZoweDhMt4kwOcbQN\nJW2rRNeZQhR8J9Ni8biaivvlmsHGDp1MCyZZdbg60a" +
       "pdX5Od8ZorZvJYQBM19uebKJ13McgMAqdM\n9mvYF+Z6xC8TXIjFIPZiZ7Wy" +
       "PQ1GZSbI8SNk5S1PHfJYmHdCn3akvSQm6NJwdDYplQKb4JY0no25\nSa4aGR" +
       "24dZYf9rQujRMpjdkV40cb3N4ejvOxeVjHY6Fh19vQY4Xxoe+veE8QOoG2a2" +
       "Qr86OBho4P\nkTgZTmbSaRlN4dVok/v7rRSmcEkcSCqhLAojbclk1Gy6z6Nl" +
       "oVltIqH7mdSfHLTVmokOM4VjedQc\nFXXlpngqEZbI0eJuSvHLQTN1B90Gcm" +
       "Tv6ClTutiSA0ulyy3UmEztwESAjJrZfnXsz1JrEbM8PvYI\nMVCaSqaQNa+e" +
       "Mo/3+WaRxhgPkCCmSIweLSje0Ycmt2tnkyeu7nSjbL9w05VyGIxJIir7Wtqh" +
       "vpbH\nWmyHychdGlLMIUdajmedMBOGM1kMjVh2vNFQQLTaX6c6a45XXsFL5h" +
       "yimGawbYfmFsrM5eTUJ8ai\nhRxhczeCkdbPd2im7btUnS4H7oIbb31kD4Pg" +
       "sZicpjqTiMzkBB+J1YjYDZpQEjb7BYMzgsaJNTom\njf5cZowkn612gjYkrF" +
       "Cx3AmJGIblbdGd5RfMBjtVDKMfyeNis19uK2U6z+xKXTM1Hwz2ONZipGef\n" +
       "hkxmOkLf8NLcn9vadrsSU3ayZLlxk7hTQdxWFFVDzH6Rb+cs1G6FJc1BrJAV" +
       "i25DwkoyStNTCq8K\nRs1xeBVC0NHvd3Nt6rBsNvbQYQ3LVJ3xulFUGSkeDR" +
       "yB8abTJuLB2cqFtI8jVBC148HatMtTQ+RD\nbQBBA71CDG6gjPC874M4sZY5" +
       "gwNWqouOJL0mPGAJGN0wMZaHzQRdSdyo2qmjymk7cdMkFDaIIKo5\nYAW0tY" +
       "Rc1c3k0GkJY/fRyZo5ttJErnlhU0YuZ6z3sBeK1LSVEd/SwgW8iFCckr12PC" +
       "qEnU4M16fx\n+GidjgJiDVrycKJ04riSKJTp2+tK4fQKL9zTSB+PiONsxYHd" +
       "j5UvY8Kxdv7UbgYyT8DDsqpnI2Yo\na0d7LQwMYTrUBQ02pfVRcGZUPW9gp2" +
       "8fIJUqJ+taxW0Omg3YNcQMMIEkkC2tkIvoZA1Dr5yMMWku\nRXq5rRpvUPEp" +
       "H3qCUssaAKDpMRPIH1AjpE+MTq4/5teGDotiNJxrZLZhKUb1jO3+SKpuxY1b" +
       "euH5\n2oLFqbWwlDFIJ+UhzxIUupgbgdoaA/qET2F+XvYDwdjsSpE0PTMQ8S" +
       "EJSa2lIbEojSpzQfPrGU5D\nZZQX7FRCZ4K2KZQRCxNbR4zyrSvRlbd2eV8n" +
       "NdzdbvrLtEuoUbkY6X7TDNMyaw9aDQKpPbVbrtVs\nKvElGUaxgTama0goUw" +
       "9BIMykhyYzcHx/aEMx5MKoVQrZto/sp/FEVIVRu4w3g+FoIpuT2teCnSSP\n" +
       "WgXZHGofL/xKPqiOy8HEQil0X6+6cXAsTiq6GYKLLI6OK5Mt0h/4cXsa166y" +
       "GJJWGXZbutv5vDAq\ngmhDLkH2ELV4zZobnSdXk4mjO8WsnIQZ5kGLQXPkUs" +
       "FBZRQD+0aqxvpQM90QreMEurRdAT0OK4h0\n0JMqEPaID5Ss4QSbVg55Ngj9" +
       "kOLHM1LxFPxAy2aEJ/t92GAmVskRiBSs0teiSoeVU0fu1flubJa7\nxcSbD5" +
       "p6gVvHAZplkW5u3NJjD1PEIPYMjDgel6P5niG3hmrsLZYT87HgIdJ8RvePwk" +
       "bY5aIplmpj\nNZKaDkajZXpcaDNChzzVdll7W8OmnqppgFR4hwamzx0LKF3g" +
       "ZntwWXOFaPO9MUUKZtwvMzRFc12v\nI3E6WbajpE6nS6lhnIlXjjSkEtVNjR" +
       "Qbxt5Eh8NqYrVj3oTnWGz6INPlMjO2koDg4smRXiv9Ta3s\nw6mNp6tjx2mn" +
       "0mQEXwyGMS1aasCqkroyYJyLtqvNcqefarEFm4EjZe4kmg2CBuxiMIqwm4aI" +
       "lfWw\nj9VVNzIP04yY25brNDQfkxOPsvaOsmFoL23dxWBW4K0p+myCjJbS0O" +
       "FbjG0QL/QlyioVezHPFq0T\nezDbjwiwbVLDmRBIEXEII/yAsqM0OA6jRgYr" +
       "ThgBl3v4BCv3UcYdu4UCO2oXWeyhc8FWppsQdr5b\nQ0ZahQO0Tw3Vih26xT" +
       "YVTtZ4xSBHi8ExymhQCPc8CMpszdh7XrlHRFot+FF8JNR5dICdEqsRYcxY\n" +
       "62423EwI3ANRQ+VdyVnvD77Im0N5tVxONoHCoJMZsbTWmrBdZIQiuwvY4Eem" +
       "nIwJCiRKt2RZzOME\nk0S7IuV3boL7aEJl/SzXmG2lT4OBHBqDI1HVpz10ZC" +
       "1qFpTVbnpcTkpj2sDIzEMEui0CL1O1qRWv\nEmiSLHdD7OQNWd9dV8jQqPrJ" +
       "oIURYiKRfLvl3LW/Wq1bZeyuFB+TyB0Kpu8Yi+yCulCQfEuKFcwt\nxjyqzc" +
       "yBdlCM7QYcYLANz+vuyO0HItqZczQbz1idywNkx+25zTbPTaY4QooYnVzWUj" +
       "NKWYTKXh8w\ndLNAj64jaMZpZyl0aE5MtTOn7JqR1XVfSo/V1Jgvw1Tq6MoP" +
       "1P1Ga4rpUV2jO4VaRKwnO/k+oukN\nbMZdY5L5EGyZj9lqx83Tk3Q6Vv4AaQ" +
       "qaK8m0D5kCy4F9xkkPjrYW8PvNYABVJ9ylYGppgQiRVXPK\nbJZKcmLMSPPo" +
       "tTPySWbjENOU47mxicnjAXFylVnS9DuU87ajZrFcrDq9wopt0qTwer3AJtlC" +
       "TDRW\nHQzjHQWraKgUYIG47Dj3cAqOrUMLQbmAQTU0sU44LLKk2j9uWhxWFm" +
       "gl466nDIBbEbw8Vby5KcQ0\naflubcVZ0GB8ZXF+ixjAdaG9aYR1OOKIE07y" +
       "HHmAraVDSDOzbywY0yjNschMT6fAXDEgAVZV1+Gl\nuM7xOXoslmK0wSqlSg" +
       "96DBINWUr6HOYIe6Y1yyRzylStCIxq0x3e9yGoGGJoukoEMVdQk1cwHGxb\n" +
       "bKND5aoVdBIQWtZhhCQRCCIibSm8yxPEKFbBprDxJIXQxxMISQb52u1DKRrh" +
       "KzxCR/BJ3ovItmg2\nrbTi8s4XFG2W+buBGtZY06Q6OoN9CE8aBS+COqTrQe" +
       "APeIzKYZDods2+iPqLbVmcT3c4AsSy12Xg\nFWZVdATBmxaFEshsXcI0PbMF" +
       "hq71rmuordZOdbc+0aECDBE7ZhIaUwgj5Nm6r4RBQNq0TnIUlrtC\nduQmWb" +
       "6GFifKDSh/11QQlPLeMPAx1iQgs6tzRLGcxQmh8ImBVFkzJcrxdIdP1wjaD7" +
       "JJk4P9FG1g\nQQsz4bCWDMvnk9pfTocWCrZ0szUJVeIqRmR1jmBzVICUki8i" +
       "BHHqejxs1E2VGdqJF4igbze0rp8M\nBR9pDgdyI4FAnWrCrTAc1sZIg3O2kM" +
       "qRyOjLmThCOMkIcnm1GR0jrlOUdmmSZuRw23V45Jiuz6Y7\ndNMay8pGzTBC" +
       "zAm+OrikVG1PFLXHTsPlACHt6VzDGJyHd4sShIP6ODR39THUB8KK1E9UC6K1" +
       "gpfT\nrp/oYylQ5lanME0a4vvTiEdFohT8GcaO9mZaDlGNjmbzravx6KGABm" +
       "ilUGhZH2E42XqLOmml2m0G\nJtZ5VJ9spnhQNStu4bmy5wK0o2Ob5FcZDxcT" +
       "FG7hnQXiMy8TuMrL6YBqNtRIY62ZmSKHVVkk9PJk\ndM5GolV+0W8OmQ3JGj" +
       "sQ/Wht2TkriWDfgjHHAzGxdTKuvQgeH218mh1b1sC7cjqwW2uWEg1nItZB\n" +
       "9FOyVVsn8RZHtd8dcYegp3S6oKCcRRgu4o5yk0QJB69yiR67qsakhFqpMoh+" +
       "S5nftZAJzkEgEOwS\nfth24yktpw6R2JpY9qczEBKJLlaJxPHRCdnVkwWrC1" +
       "w+S0U2xnSMLG0y9siDmKJ2vWt3q2Ao1cNO\nrTuUmhVISVCnrqXShoHBXoPJ" +
       "GU5vJHJeNZK2N4Io0mHLEskp6c22iUU6R8sfjevYmivqzGKNiHcW\nMy1ZGe" +
       "keVadVpFNDcLLjtLFT9PNZxIEMsggrbVXIO9peLWvRUehyateq7WFqATbzGK" +
       "0SlVu6JUoZ\n7MQ4eXm+hCiM0mvFpWbxGkdGA+RA9Ov9CpEGzqGtFIWPNzua" +
       "t2lHw8F+ZhSKMNhL5dgA9+WTMxi5\ncWEdJltwrglHNo7JRNiAqISvY/+0Ox" +
       "4W8qmvOrDKeI6Yb7eMX40FXT1m0XynH6p4hXLrNdrwtROE\n83rmWoI5p3Cc" +
       "wcKc1QdzauyAs4s0Q8ahITFEZLt9cJqUcWk31lo7WwxkZ0bvAo/cJTQeeDWH" +
       "NhSI\nVtwIG/I4ymGDmjF2+Kkltx3weorQRZ4pSzoFZzyOA2Hb3RYpXtoOXN" +
       "aHgi5blGzJKhGGDbPU8UVC\nRNtQliU4FfmshGp/vgu2PB1rDaOl/MqaLGtJ" +
       "2QR0JrfkMu1Htm0rotpyKNjiaq3nZgvNmxTz2XpK\nm8xInZiIIp/gTTZyUh" +
       "3h9syKn0hEvV/y3noZIJyRhUWZpLskb8l+pQghAHGw2FmeZdB7aB5qDbY4\n" +
       "wCwRqcNm4AURuknBmYFeHbd7hRMxwRM3w42dzAJNYu3IcvdKx0IbT0z6dYQh" +
       "I5PLdzAi2iAZ82GI\npJPZTlC6vbZpt4nW2XZ2xBN7BzYFSEA3xKg101yNqS" +
       "gfBPJ+XeU5Ug4Wbi33V1ah0AgfzfIgcBdb\no+WcbLyfZHbr6xO7xWWrk5pS" +
       "G+DOWAs6ttJPNqNMIlkRmQYPdG+T7KpUadpl4Hp9jpgHagzJUEjY\nIlnjqy" +
       "NtdG0TY3P/WIxRPidSdmhtimlGaulx6dgFnemBKU4muzpB0TppFuxm1ynbtq" +
       "D7OjKcnQaW\nVFsylTJVOKZbQ5epzs0iSxiwtreJyGyRE3nmWGkYD5M1pSuQ" +
       "bns6ay+TEzd0R+CYvUknqwPXn+lT\nVVn66nzfTtA4PMbLI7+uM4FCZW2EzY" +
       "eNPF00++gkY7zt2xEt4sxyYw63pJNE9F4NOGgUHGgrhTp3\n2K8gN4JELJWs" +
       "TnOhtlMcv22GQRMVysxg1irEQsnAIyR/tZgd1/rh0GwJLR5ajRl0XE5xWaiz" +
       "/DDf\nDU4p0x90dgH2uUPK5uuaCXRoBNPhyNoMIBqrgm2BjOoALQoIE6nGjU" +
       "5D1JvOh4pHciGh8OBIKIWY\nB2MkRco52h+gRt4GFNeopqIsYhxZD6lmUGzc" +
       "UzHZTv0KnFYMz3Anhw6hdxCZzQ8QOP7gAZlQaO6M\nBpBijPKWi30CAWE7mS" +
       "kmRxbNeBAUo6bACuywX4X1KVSoqqjZHYSNc8zlJesIDQ9y2KS+s+LwYLOU\n" +
       "8pFCTk5bsp0V+9N4PP7R80PW2ff1/PnNy+Pzx+Xd62Pn882PLs9su09gUvVe" +
       "dNyyKhyvqnovPa4h\nX9jeKD298QkV0Pufe/Nrf+HDv/KrtytOd26XGy5Plh" +
       "+V584Pqd/5blXdywPqr1j/7d5POr/8Y2e+\nZ048EKzK8h+JgyaIn4h0m8n8" +
       "UsR+VAZ6xXz337Ojn/8jt4V7AUz/7vcc+cB7rfn88plt9E/vXupH\n15LTx6" +
       "roTw/66OlCU78IqrpI9cflpo+XCJUi8wK/LoIn8/7F3+L/61efo/7O3d6zN+" +
       "twZw7v36pq\nvRxmReLE5wkele371bbI2ic9N0tc5zoguN4C16celnQuv+eb" +
       "r10KVq93Tx7rP4WQu+e2ehMPd87f\nBrDjG08qVOOicI7nCmb3x779zl/+J8" +
       "5ffaZ3R+g9W0an4FJp7j1VzHzI4fwd3bh5/u9XvZe3TqkU\nICRkdXmhmebX" +
       "cVzVe8HNsjhw0st/5y");
    java.lang.String jlc$ClassType$fabil$1 =
      ("nl3gbXpx8q9+n/W+Vui/aJN+88Dff3b8J9kWm1t2XiIAnS\nium8ID/X9y9y" +
       "HIDP5Tf1A2M/83DspRYlRuG14vkJWr4JrlceavnKD0TLm6vzZeCKadBVQuoH" +
       "3e21\neQZEj0+Q+HPgevWhxK/+wCX+6ar3qUfGvkh97vzKJ4h5fvfh9Ydivv" +
       "7/SsyHCDn//YnvC0PvPqlI\nCnEcbJz4/BJS8DSAvgpsXQbVbfs/22SR/100" +
       "e+OhZm/84DS7e2X0SLO3n2h2Cfe0Uz7xi8cu9DH1\nx8Wm/rgL/TVgAcf3z8" +
       "2v39T4XLfuXd9ruXNV+M5719L1/RulfBFM8n+u5P9/h+bfrHpf3EXhB5ug\n" +
       "unr/g3PkeHDzXZYH0iPLvHUzQJRgg19E1fG+5LhBDBL/vZuDzi8PfO5j77pd" +
       "38jy3v/2j//wL+Wv\n/bNLjnn81tQLUu/FsI7jm+9K3Gg/DzwojC5Cv3BNK1" +
       "fzfAsE6xtBD0Dw/HMR+e9fKX6h6j3/ZBvy\ni/kjdV6/qc412HX/G4iZ07XY" + "JwAA");
}
