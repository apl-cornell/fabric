package fabric.util;


public interface MapEntry extends fabric.lang.JifObject, fabric.lang.Object {
    
    fabric.lang.JifObject getKey();
    
    fabric.lang.JifObject getValue();
    
    fabric.lang.JifObject setValue(final fabric.lang.JifObject value);
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_K();
    
    fabric.lang.security.Label jif$getfabric_util_MapEntry_V();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.MapEntry
    {
        
        native public fabric.lang.JifObject getKey();
        
        native public fabric.lang.JifObject getValue();
        
        native public fabric.lang.JifObject setValue(
          fabric.lang.JifObject arg1);
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_K(
          );
        
        native public fabric.lang.security.Label jif$getfabric_util_MapEntry_V(
          );
        
        native public fabric.lang.security.Label jif$getfabric_lang_JifObject_L(
          );
        
        native public boolean equals(fabric.lang.IDComparable arg1);
        
        native public boolean equals(fabric.lang.security.Label arg1,
                                     fabric.lang.IDComparable arg2);
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_IDComparable_L();
        
        native public int hashCode();
        
        native public fabric.lang.security.Label jif$getfabric_lang_Hashable_L(
          );
        
        native public java.lang.String toString();
        
        native public fabric.lang.security.Label
          jif$getfabric_lang_ToStringable_L();
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    long jlc$SourceLastModified$fabil = 1282915709000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAANV6W+zkaHZXdU9Pz0xtszM9O7tZdnd2Z3cHlMGk7apyVdmM" +
       "EFS5fLerynbZLhdE\nHd/vd7vKNrAKAmVDwi3J5oIIyQtSJLQPiBXwEgEi4a" +
       "ogoX0gvBBAiRASBMEDYhUFgquq/9P//s/s\nbiTggZJsf/58vvOd73y/c459" +
       "Tn39Nwcvl8XgS45u+NGTqs3s8gmhGzS31YvStrBIL8td3/vUvP/H\nv+fH/8" +
       "if/a1/eH8waIrBO1katW6UVs/GfIj8D335t0+/8lXmsy8NXj8MXvcTqdIr38" +
       "TSpLKb6jB4\nFNuxYRflwrJs6zB4nNi2JdmFr0d+1xOmyWHwZum7iV7VhV2K" +
       "dplGxzPhm2Wd2cVlzptObvDITJOy\nKmqzSouyGrzBBfpRB+vKj0DOL6v3uc" +
       "FDx7cjq8wHXxnc5wYvO5Hu9oSf4m5WAV44gsS5vycf+r2Y\nhaOb9s2QB6Gf" +
       "WNXgC3dHfLDid9meoB/6SmxXXvrBVA8Sve8YvHkVKdITF5Sqwk/cnvTltO5n" +
       "qQaf\n+bZMe6JXM90Mddd+Wg0+fZdue33UU712Uct5SDX45F2yC6d+zz5zZ8" +
       "9u7dbm4aP/+SPb//HO/cG9\nXmbLNqOz/A/7QZ+/M0i0HbuwE9O+DvxW/eRr" +
       "tFZ/7oqKT94hvtIsft/flbn/+Pe/cKX57EfQbIzA\nNqun5m/PPvf2Nxe/8d" +
       "pLZzFezdLSP0PhhZVfdnX77Mn7TdaD91MfcDw/fHLz8B+I/1j7wb9h/6f7\n" +
       "g9fowUMzjeo4oQev2YmFPWu/0rc5P7GvvRvHKe2KHjyILl0P08t9rw7Hj+yz" +
       "Ol7u237ipDftTK+8\nS7vJBoPBK/3xe/vj44Pr73KtBo94PcOTqmif9CaWVQ" +
       "MKlMse92B6shMwK9LzwkuwV7iflTbY0xS+\nCZaFCRZ1UvnxB12Xdd/m1Zyn" +
       "/vjp3r1eA5+7a41RD10qjSy7eGr+wq//8z+Js3/uh697e8bjM6Gr\nwVtX5l" +
       "e93TAf3Lt3Yfo9L6r1vE/W2Zz+8996/42/+H3l37k/eOkweM2P47rSjcjuzV" +
       "CPon5Z1tPq\ngsPHtzB/gVqP00dGD9ke/U+jntHFRHrdHXv/cxeazw2a7lt6" +
       "j7dvfuV3/uV/eXr6xhlF511/68z9\nKlq/h+FVtkfvSd/P/MAPf+mlM9HpwX" +
       "kLmospfuo8y101EWencMM/Nv7Ef/+lnxu+c+V/HvPZC4MH\n5YeN4IWBT83u" +
       "78k/961/Uf3aRcOv9c6o0nsI9Zb9+bum+IL1nG3yrkiKXjzni/yr4+OHf/Pn" +
       "4/uD\nVw6DNy5OTk8qRY9qW7J7Jzr0S+xZJzf4PS88f9HlXO3r/WemXQ0+d1" +
       "euW9O+f+Mfzyp46TZi+vaZ\n+tx+9YK+Rxea13/n+vtf5+MZ+N+9gv+676t+" +
       "zl1KnCMM3vRW/uQs2Dvfa6Zx1ltW8Y5r98rSK9t6\nL8uae4PszPT3n3f4rt" +
       "bPUn3rzzyEfvUXP/aPLrq+ceSv3/L4vWaubuHxc4DsCvussX/zM9uf+Mnf\n" +
       "/Oofu6DjCo+Xqp6Jn+i9Uh5mtRH5Zt8oL7GqqQavnNIitIt3L+v8xHN7uXY/" +
       "US+Xix1eKL74bfXx\nF676eO+ij5s413P4jpq418sGPRk9gc5c4Qvv9y7nP/" +
       "hM9nP7yfkEnk9QL/Bngsh8F3vGTun9TO8H\n370KfbOGNy5qOcPiyTUS3ZL/" +
       "fJo2F+v/+HMyLu3D1Y/+xl/+lb/05X/bq50ZvHw8Q6xH7y1e6/oc\nz3/o6z" +
       "/59se+9u9+9GJEg8G9P/CNX4SoM9c//LsS/+2z+FJaF6bN6WXFp5bfB27rZg" +
       "UfNsRt4ce9\nsz8+i0Y/9vm//h++8eviW1dXdw3ZX/5Q1Lw95hq2L2j+WNb0" +
       "M3zxO81wof5l4Itf/4r4a8Y1nL35\nopfEkzqe/vy/tt/7o4/Mj3C4D6L0Ix" +
       "VevfljFFzSi5sfD+nYfiErjrH11i0jSZhgYgLPEPim1bFF\nWx6Uk4cz5EHX" +
       "lmtR2xx3EIoJ6601qtApGg7d0maLBafW0YyugNUyzX1YT0K5BkplhR4CZU6o" +
       "uHA8\nVntMEaBS3kuTCeiA8yQL8VAvLGW7385R8HgEwXqYTkp7TNetJUyUpv" +
       "Bp5siNahxR0G3av9tsWd1I\nhURaN3UFL9sxBbQhUqsjZtaiLE7NyNDLc9Xf" +
       "4SsnxbWhhmCVX+0ojbJM0+y99rRtp60Mtm7uOdhs\nWTfKuqEgLdJ01dyNTJ" +
       "4mCFZ0Jcs6CULPpyDXm8ZnKa9nOcxKoSE9S1oo6j7bYG7Ii8vI2sA2s7CJ\n" +
       "uI0InhxTXJ7v4UUt7GcuM17VPO2xtIPu1zsQOhbZ7GS7pbJmmaEUSlNqv9am" +
       "GzPlUy3YRIS4pz1q\niY+OAa4TOmbm4Un2xv40pX2bYU9LIdwdRZmYlGEQm9" +
       "BCQnfWQsk1czFcVfIuz1cij0wIHCt8wl4y\nErjUINEnBBLlCVZK7YNphXO3" +
       "pnReXOc2f2z1dlWfFB/HNKJewyfr1B64MB2KHa7P0JGHKIGxcLHT\nemfWLt" +
       "dWKWzQFNa6qdy1uG9NllbdMGVmLZi8YjYLOcAPkbjIFrZUUBxgz7zVkhxGyg" +
       "xWjhqdbtmM\n5l01oA/zk+TG0JpOg6lmAlEsy+AuiPSJszx29LITqaUC++jG" +
       "WWZxsc0CzWNiH+sWVTmEZwQOGkVs\nNkwQE9AGPS26mNdnTKNTaHBCo5Lkd+" +
       "6mYzmGhylSAwEy5BV5TgYg07VMOGVjlvO4qO78ZNhUVb4l\nIzqvlwu6bfkp" +
       "SaKAgx0qz9nWe4lDuNCZ7uyAImYinpBT0pY5M5E2vAkQtuzLS0OfsUtjt7Ra" +
       "fuiF\nHsTKtoe22CbGXEZctlN+6UQxyW0UYTqBJpEkCkF0CmSpiHkBmG1BXs" +
       "r0lk26A9uNRtrGyJtgvWrn\n6vBksSspR9zlSFnTWlcIIgbnmwwLaRVL2Xkj" +
       "SNBy78WTyA+cXdQwZiJmBYJQuBcE6A5hdpQN0KFM\ncGZzHILHIPIBfVQQk5" +
       "WFH0tIWujphA5QApvGpi92kC0qRUBIR0FmFCrwIVOXjshJ3CPmKSyT1nMC\n" +
       "RNjFdEYvhkFpMXixUFbqOvfG8ibunECMcHy31Mtte1rSnoYqaylIHcKG5qJV" +
       "MyHJEnPNNKNtASAc\n2k3ZgvbS46Fxh4eZtFxlZjdO07jKgIm2iXVECfGcMm" +
       "lZJqU6pqONKReZr0kE3iGI6RPL3kmwC/cE\nkuI20sLF6ritGZryhxCbm7kK" +
       "gpmzINjI7bJMgLW4nbtMTERVDFk9hhabem6McxRMdTRHgMhbMWzZ\nrVi45R" +
       "br0gpWND0SGowc6kxNSxh3hElRI307L9rGqSZz31DROSlhswKPdxqa7kzGDZ" +
       "auyB2j6DTK\nnO0xC+tm73ktBKDa5uCbFnkYrijdF4URjkxynUzbapJMCmYy" +
       "t/lJkh8MZ66rLW+u8lGzOwkJBLu8\nkIW7AJaVEz8CII7dZ4ln6VuXDTBkWK" +
       "TL6X5HCR3NsvDeWIuANx5RmdVi7hJq3eWppTQypSNzK6s9\nHhAF0xkPjJte" +
       "XKd/7wLmcRgft4wsaM50uAVAYHbMUGl0MEV8XHh8Gje4jOeH3p1armCsOFyL" +
       "gdzY\nO6u14bbmqFC8bTxbQcXEAbdG0Bq7kKzyk5rgQ3x5kALKBbKJs7IToy" +
       "5iZy62DUqfjtN4hC0zaiUy\n6WzG8QU1OWBp3eIYY+13hxG1Jrb7+KR4ObkR" +
       "6Rio1KGQ7TJPpYDmMDlGiAbWwDrWfW4a8mWzBAmN\nhpJFYwidJBaH2kqXsM" +
       "mxYcK3a4Eu4d1uPiW29sxPOMDakMN6CUZWzkhqqCwOhoSHTb+srvbYapWQ\n" +
       "dOkpm2Y2L+enbuHqAd6ykOgdxUQzx2OMXMWMMDbhxWrPYztbI4ajjb8p9QZY" +
       "Ex1lz8b2lmm98EiG\nDM5ApmtYsryltBmUhg20O2hwDOChoE5E3fflZkag25" +
       "1jV7HnU8Y6C4bZtipyAJyia3I7RrIDFxNS\nAYWZNG3mqzW5g+BMwcOCWu0F" +
       "zNtPAaRwKS6O5r1vQrSZO1+Z4x0O4gdyorFDfGwqiBq2anHAOwY0\nk7BrNV" +
       "crUsQy13uuVzS3ATUZCqayNHEOJ5ni2I7epnKxLfd+KlvScj8NVSVsPW5o2I" +
       "viyIVHgQfH\ntC0AsT5rkTbWeQqcZJoN1ONyBDZkdBx5Izk1xBZvyN2mnZzS" +
       "o3paeRPFmrTHDRjE4CgdSgtXEV3V\nGkGSQuAkyYj7VItaRWR9TFBxJeiBF/" +
       "krQiizWYGdFmzby+ruxQ0R79PV2l0sJxBstGGekeww7Oi1\nfUB0ZTahBBxY" +
       "zSbikZcFe5Wvx6owj5sU3sgbmJAodRmlCwkDV3h+2matR0tueZDQcJzH/YdB" +
       "XnLI\nsA1H1gScw3NGX60XakutvRSSd1LD+Ut9xZpMxpG66SPePuVJmcNVwK" +
       "vYdoKxG8/nE8GloFRiZ+ia\nQFV0uDDSDJzZ5PpUTbiZOA7pzVw0RmO2jljI" +
       "VSY+T4XumqeRPOO9XTuaoUrijCJo6TiILQu9W5zE\nGqRatBuiQzeW9jgBh9" +
       "g4cTQvM1olCrbj00x3tsQkpn0JtNVlPUU1Z6ZYULrpmCPQ5hYQ4SNUZ2EB\n" +
       "bmjJyKBOhRJ6CPVBSIWqalGIHe/JFIlNirG/UrX5etJGyrROHTNxSyF2R9sI" +
       "WTiNO6dFxj3Omgg9\n9JG5ZsbjU6hBuzrxhhOm1CcrEnaZia7Je39EsiNFWc" +
       "O0kFv5flIHFrjdTpYoWnZUvkJm4cgfoZPl\nfgso62UqN6JWdXgShVguL4Z8" +
       "fpT6lw2YosFwTOaw5J4CDsT1Wh2fOPc4CcGwvyHI2A4DuBEFtFvM\n552Vk5" +
       "VFzequx2ofI+cgPhGmynDRiXxdeFi6ggQz25tLV2v3tQhLm+mcdggIMzxitl" +
       "5bggbl/StG\ne9iuSdgQRq4YN2akMRC1WFIpsjYRNxhukGYe+Ri3V9mUxF2e" +
       "2DIlNoHhPcaT7hGcIlpRAg2EamwY\nFIsdwzMjqYYPEbvjNtlmm+UHVUpMsV" +
       "JTBxuu7S6F8t77yacWtlaq5Y8gN2z7t8j4hFpSKnTKGinr\njFBLAAgdZdId" +
       "y82GkNeSN7HI1CdWcqCDMVjb82h4UNBylsss5y6OviHn0MaBQq1g4hkXqzi4" +
       "lISM\nMVExWkR2tFg5RjBKMkiOSWZRe+sTo8hIl096i5sm5WTozt3NUjzgvq" +
       "4qYxmt0E2Py24c8LOiYXyi\n8Kdj+hC1sylQUSCYwGZvJD0aIoEGDTVRXTSl" +
       "uhnSqVXJGcNxxsxIqBASizOSnVMm0W5dUkVu7n3A\n6EVfzxoQSZOs9zgVHR" +
       "7CeCkqoqHsCRXCiEaGmNw8OeD+oJXjwxCHdFswVLFwdpK92qu0clQ4aowp\n" +
       "HoHrrr7WfFKaCJ6s9B8EY2gB0oddN6V5kNr5ayao9luFPaQBkMzSqTfM/W0w" +
       "MVe4vaxBUUZRb8bo\nDN27VBEBULCXbKQDsCcBB5hfAcUWLgwHOBZOuyvzsS" +
       "KsFHuRujqydUURC4YE5KKjfDRmpsRmbqPm\nxs7R1rSc426MWvreMB0HnaCz" +
       "Di5c0AoSNqFqXkZi0EYVqA2mHD4nK2Oz10Frqw4LtdBhYjEeSRid\nMibGJ2" +
       "MJkafGQimXpgLNbHOq8oThr/LYrqlRLtobFGuQ/rNguY4iIa69BJpTUtu/nY" +
       "rDvQyiOlKh\nJASZaEeo1IQ9AcHSUHOzHU1m/KQa4Q1aI6twoa5nrgxNeT60" +
       "MwwuIjSkKSDSon0lS8Y0OnTD6WG8\nwI1WBBxgn60B2V9l2iGBNmnX0QUnFJ" +
       "mwKOQp44s13TK8yiO4rRIEYrBYDCBCvYNLwLPMxGNkZTsk\nR/hq1ApQvwEN" +
       "Aq50H1PCpkrp3otOSwDZsGAtIUYBNQW2WW0nPBgg2mgLH/hlCFb1mjyiNSxP" +
       "Tbvi\nnGpY7senCQABhb83yNHGSpFW2K8T0XRnDnKY7wx7NjJxyJitgjYqYY" +
       "MR6xGX87C6P25y3BxvAwvh\n2okHGg09nLnd0WrSiI3FnGzYnI2DDVruHXrK" +
       "HPBZRXFYnqxCLRK9STjmfD+3IL9D4Qbn11Ehkwto\nnkpm71Ndce4PEVtYAq" +
       "eFAtGQizOuQCsCDQmLvbzPcYEVjiZJcY24VtipaKR7d8MgJbvLow1YjNJJ\n" +
       "xxcKSc5J7XDkNdoeko4WcebR2s69vN0uRjJALHmv9es5EATbeQKerGIWH7HG" +
       "YVpyT4c6VG2cTT1S\nUMCM0SZv87W2E2A7OlrYUF1po8XRBhapQFo1BJFW65" +
       "6K1bZgCrREYMOJ1J0kawmMkulyPkvAPjIk\nocqXSV125JLqmq27SQiX7lps" +
       "KG0QA9YKqMgsH0NETdrg01adHdoWsB2Hiycmt5qTNTvddO1RQqLt\nrnL2a2" +
       "TkmOvVaqwaYRYUmIt3S4DjhuJ4MzKgJodYYqbL8WxJ6lpcjvXE8BAYSSBsyf" +
       "TG3C2TNpkJ\nIr2ndWdDO7kVZJ2fQ1FDcPTM34UcuUfcIXqiWLqR1wcbPq2Y" +
       "bkGodrpZsZTGmacetgG7rjyTxthT\n51TYUkkUr6HkPZEzubIWYUvE1n4YGr" +
       "YJwwAwJCmwIjD+QPWIgBvrgE0TVR71L8g7mIQPiqRlrS1A\nCDhP0G6Lu00H" +
       "Rm0dcJHYrUqvOSLAcsQxoFQc9kAwjLTdCIYLBOdzjpF2DGBMKL7302sJj+W4" +
       "6FQF\nZYCGcuj0xFJCgemLYCrCW9RoPJxE+u/4Aza34oN86j/Ph7NjI7e7qb" +
       "gx4FQ5TTQmR/sX/M1+OkeB\nnZKQo+6kzBMHw5wDl+5WoLcvANIGxkcDJHQS" +
       "7pzeZVrjrihG9Xyoo+Z8rZ+6pVaZpcaaUWNhtd6F\nqWnv9s143cLBusEUAC" +
       "GMso1UJ2L8g+sYsdCMRkysopuAt/kIlSYc1g0PVj220S3C80sMVlcOao5i\n" +
       "IJVWtWb18+eqOp4C9axd8wQIci2G0ZLMMe5icc5Nsb+rtN1bl6zjB5Wwa7bu" +
       "/PD9S6qr+Qgm1eBV\n3SirQjeravDaB+W2C9tbGftPfESx6Mmn3/raT733s7" +
       "96N1F/7ybD+clnWdpLXpLxnWv2+5zce/vb\nVcAuib2v7v/box/Sf/n7z4zP" +
       "rKhesirNvi+yj3b0XKa7TPhLwe8mff66+oV/T8x+4U/dle5cZf3C\ndxz51H" +
       "x8/Kzwkuf/0/uXvPs1Vf+hiuOLg95/MUE/LOyqLpLdB2n6D5dWtkWPIqsu7O" +
       "fz/vRvUf/1\nJ15G//b9wYPb9Yszhy/dqQZ8zEmLWI/OE9yUOIeVV6Sn5z23" +
       "SwP9wl/tj8/3x+vPUuGX6/nh40ui\n/83meTr0BYjcP7fF24C4dz4rvR4/8T" +
       "yzvygKvT1Xfpo//c23/8o/0f/aS4N79OBB6Xf2pSo3eKEI\n9IzD+ezfeni+" +
       "t6rBQ9euWLs93+0vNPoLi3inP954tog3/k8XcVeE7ypf0ltML9+luPOdJHz8" +
       "TMLH\n/7ckvHc1rsuc313MUy9m+e3EPFdFnlVK712lvPfOtTDy5FahiOk397" +
       "vXif6fK/wHz+UI33m3V/rV\nnzw9A+7pTZX0KXvjbj5z292UtlkXftU+4XTj" +
       "mdP4/3Hxf/67LF45E/1I7/pfvek6V9w+/aE/hVz/\numB+6Zs/8L2/lD3+Zx" +
       "cH88HfC17hBq86dRTdLjDeaj/MCtvxL+K8cvUp14X/TNV7oed162rw4Hy5\n" +
       "LOOnrxR/tbfl50HoZ7ObnXrz9k49iwr/G8WmEv0BIwAA");
}
