package fabric.util;


public interface Iterator extends fabric.lang.Object {
    
    boolean hasNext();
    
    fabric.lang.JifObject next() throws fabric.util.NoSuchElementException;
    
    void remove() throws java.lang.IllegalStateException;
    
    fabric.lang.security.Label jif$getfabric_util_Iterator_L();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Iterator
    {
        
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
    long jlc$SourceLastModified$fabil = 1281544489000L;
    java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAMU5W8zj6FWZ2dmZ3ey0e+l2u+ylu90uqIvp2IkTO2FVQW5O" +
       "fIuT+BYbqqnvcXy/\nxjZQgUBtacW1LS2itC9IlVAfEBXwggDRclWR0D5QXi" +
       "igVggJiuABUVWFYif/P/PPv9NSCSEi2f7y\nfeec79w/+5zPfLX1YBK3XjIV" +
       "1XZvpWVoJLcwRcWplRInhj5xlSTh6tnb2tUffssv/sBPff0Pr7Za\nRdx6MQ" +
       "zc0nKD9AzndeDf//ZvHL7wfuLZB1qPyq1HbZ9NldTWJoGfGkUqt256hqcacT" +
       "LSdUOXW4/7\nhqGzRmwrrl3VgIEvt55IbMtX0iw2ko2RBG7eAD6RZKERH/c8" +
       "n6RaN7XAT9I409IgTtLWY9ReyRUw\nS20XpOwkfZVqXTdtw9WTqPXe1lWq9a" +
       "DpKlYN+BR1LgV4pAhizXwN3rZrNmNT0YxzlGuO7etp64XL\nGHckfpmsAWrU" +
       "G56R7oI7W13zlXqi9cSJJVfxLZBNY9u3atAHg6zeJW098y2J1kAPhYrmKJZx" +
       "O209\nfRludVqqoR4+qqVBSVtvvgx2pFTb7JlLNrtgLeb6zf/84Oo/Xrzaul" +
       "LzrBua2/B/vUZ66yWkjWEa\nseFrxgnxa9mtj+BS9tzJK958CfgEM/ru3+Gp" +
       "f/z9F04wz94HhlH3hpbe1r6BPPf8a6OvPPxAw8ZD\nYZDYjSvcI/nRqquzlV" +
       "eLsHbep+5QbBZvnS/+weaPpR//deOfrrYexlvXtcDNPB9vPWz4+uRsfKMe\n" +
       "U7ZvnGYZ00yMFG9dc49T14Pj/1odpu0ajToerMe2bwbn41BJd8dxEbZarRv1" +
       "9V31dbN1+h2faesm\nXttYqR3zVh1iYdpagHxS+z0YHAwfDOOgETwBa4XbYW" +
       "KANUxsa2ASa2Cc+ant3Zk6yn2RVtFs/cbD\nlSu1Bp67HI1u7bqLwNWN+Lb2" +
       "6S//+Y/OyJ/+wMm2jT+eMZ22njwRP+ntnHjrypUj0bfcq9bGTnoT\nTv/8m6" +
       "8+9rPvTH77ausBufWw7XlZqqiuUYeh4rq1WPrt9OiHj1/w+aOr1X56U61dtv" +
       "b+225N6Bgi\nte7yOv9cds27AY3XI6X2t9fe+82//Jfbh882XtRY/cmG+om1" +
       "2obOibebr7DvJt7zgZceaIAO1xoT\nFMdQfKrZ5bKasCYpnNP31B/59899sv" +
       "3iiX6D8+yRwLXk9UFwD+Jtrfo9/pNf+4v0S0cNP1wno1Sp\nXaiO7LdeDsV7" +
       "oqeJycssCUp8l+7gr/LHr//Gp7yrrRty67FjklP8VFDczGCNOom27WRyNkm1" +
       "3nDP\n+r0p5xRfr56Fdtp67jJfF7Z99Tw/Nip44KLH1OMGuhk/dPS+m0eYR7" +
       "95+v1Xc505/8sn5z/ZfVrv\nyQVYc8LMijrKbzWMvfgOLfDCOrLiFy3DbzzP" +
       "0F8Jw+JKK2yIfk9j4ctab7j62k9eh774u4/80VHX\n54n80QsZv9bMKS08ft" +
       "dBuNhoNPY3H199+KNfff8PHb3j5B4PpDUR21dqpVwPM9W1tXqQHM+qIm3d\n" +
       "OASxY8QvH+V80914OU3fEo+PYxweId72LfXxMyd9vHLUx/k5V1P4tpq4UvMG" +
       "3ercghqqvSPtV473\n7zvjvRnfam5gc4Nqhp/Zu9rLkzNyQp1n6jz48onpcx" +
       "keO6qlcYtbp5PoAv/NrV8co/+Nd8GooD6u\nPvSVn//Cz739b2u1E60H88bF" +
       "au+9QGuZNef5+z7z0ecf+cjffegYRK3Wle9Vrzz0jobqu74j9p9v\n2GeDLN" +
       "YMSklSOtDt+uDWzyV4fSCuYturk31+dhr9wlt/7R8+++XNk6dUdzqy3/66U/" +
       "MizunYPnrz\nI2FR7/C2b7fDEfrzwNs+897Nl9TTcfbEvVly5mde/1N/bbzy" +
       "gze1+yTca25wX4Wnj71r0Uvw0fmP\nFuSJPOI7Gxdg4MTMRhqVFQV6mPRwfJ" +
       "84VjJPsHJE2T06yXo2Qm5cxEjRZJlvoHTYydt6YAr4LOID\nqOItzKB7O4ee" +
       "J/nMdsNAWRcGtClJjOeTFe65fUkQ1xQLVSsQ6AxhDuYYlFx3hK4SZ8gWzdsm" +
       "CKIV\nAIJAvbzoLydlyaYT1vVIH1fFrr6OEGi6RQMHQqhirISOyKYWMwnz1X" +
       "bfL6PQjHkEm8w8NZzPl20n\nnoa1woi1XroRkMxQvRerKDxEVD9fFYkNbDCW" +
       "jAQp7/YidQztBXnWgYIRz+0xbMprvI0KC5YQbZGv\n2LY3wYt5NjgM2fXeKf" +
       "F5woScvlE8ZSN1CWxJ7vx5pY11ckxnpLZkEZs8+Lg88buCu5v0eRcMhmq+\n" +
       "QwcIIrntGUgQMguEArU2iHq3bTSOp5QaEBuB8IJiA/WD7X647FGIL5I4DPMY" +
       "sxHmPD5ZiKk73Ehs\nOPASeUPEYj9qI0YwIpCy4C2po/FDn57hkTzuQLRLlx" +
       "AE7XDNVnnSUvRhj5whMS9Xs3IZMSFEAtN9\nb4nI6wCXbcnxuJBqT1IpEsNy" +
       "E2YjUhiYYtKbjdyd5JP6XoWVcOR0J6POaE9FfUrEgP4ujKjOSPR2\nfkDo87" +
       "EDUhhtkfuZYygw394gDN0ZmWGWrHm+ViRuWuwmkWalPOKMuTs4zOr3kPIQR/" +
       "nOU4batmLn\n7LiiFpFSlACfWry4tHhOX/LEMmrTO57aI6qS+rrhEEHQ8wna" +
       "wvbrisjLAeVthv1hMNuvOS0dJTbS\n3dFBTm77g0EiLvIcZ1xAKOmZw8AY0x" +
       "+0fUCScgzucotcsUZrBYEpx+nCTJ8AEB91rUOJIpJk83qI\n26Kgc6zS5VwM" +
       "T/NkDFCB6tsksaHVIHHUUdBGyjVGWj0BMS2m71gBmW09Epf9DZ8HVUzvkpoN" +
       "bpfN\nnDnZKxJMWGtrE9wTLLR2ANce8d1yupzykrgbAJ02vqSmgD0VDoESag" +
       "Mxpcc7jWYPCuaO1qlz6Kuk\nZJH9VTZj4rQvbz2xoy37mTges9J8AAEYhHWT" +
       "CMaiTSbqbbTqg6VGVmn3IObZtlBG+ZIloeVKtOBd\n5RMlxHj6Fqm0PYgxYj" +
       "gY4mY3ckE6HfcmBuO4IR+UnkiwHCmu2wZBRpYbFyRN9+KVOstxWsW4GS5K\n" +
       "eyAaRfR4EUgyPXKcHrPzeH/HWevAM/o6CHQpH1ovFsBmU1Ado0Qdqg1pk4XS" +
       "ExJMX6KUW5ZGvnLM\nWQ8S6bHu0DIfMwY6pmH+wC/GPRmgt9uiVyEdt5JwcD" +
       "tcWTQ+XVp20ufHld4Wp1zc0UzSh9QpE+5Y\nRFtIiySc7TDZl7HOjrfi/biL" +
       "Jl1f1Yt+ClgBTrImLZMyZgeU5NAHZa6zE3ykR15bigLbqNayzzJE\nNwvjtD" +
       "MAQd3I+y5rSepe2wwrRV/1fUzQSa7OF3TMzVHQxkQ76MsDDWBymJ7s3cWU4M" +
       "s2LNshZM0j\nOVlTaCzxtD4vdEboc+IQ9qmqCiFsPEA6lmwyuT0I8hyT+wqz" +
       "SLBhj3I0wiAxWBS59S4UOLGdihPA\nT0lrkbLIegjxbMCLjkjwe3lc8puZQ5" +
       "PetByI8/08kSIpH7l7mY3CA7AnE0uk8+V0zADqBJVCz521\nK4Uoh9pk1jf9" +
       "YekvJaPAcK1YL0GE6MaQuBsGOz0TwR6LOQhTZQiHdT2NtTFv28mBeVwEYhcL" +
       "wUOx\nGUVWex5MgCnFuYXCFxnlrA7LTD8wgopJFDnROzRrR6ZmF0KRh0uTG1" +
       "VWvKsW0/7WGjK93kCdLLVo\nvjIr10HoZduMBgKNbx1qa5tkhA8LAvAHekLo" +
       "buUPzJS28cXGE0tZ7scsE6ISouXdZVpB8WJNCSN5\npMwSObBspCDMfXtp6Y" +
       "APAHKfAXxaoDSq4/trKh8HLMTJxk6f7wwBibalU0lwBfn8Lt7b4cZaQnRg\n" +
       "8XQXs5LNlmGGbrlQ+HZ1QBd7cb2YpoysyjMY7TMOMQvWrgFq2oqjmEol0O6E" +
       "3GsUUPp1gAD0YK3H\nrm8QnZxF8U4m9zqJFCpQBrTZDJ15E3vFQ0kXk5bbjW" +
       "2y1EQI61CCNj6mHkrSow4LddCfYiBfEEmH\nC6YzgYzpKbET/Wl/DC+sETec" +
       "GyOgLUajzmzMC6klztO1tMdTT+GFGckvNU3RJa7iqEVoyJoseTlz\nMOguzi" +
       "lMiBOzruTYg2gL6zBccPoK3Ud0G6Gpsd6p5qspym0MwR/GHdk1K6sTZrOs6h" +
       "A8MCiWg8k6\nLYBqPljMe5uDDSRjSI+xOqQ82TcMgHbH+pCeMG0Zt/hRf7Rb" +
       "zWZc7hIsjx56wxk+VezuYVzOJHO1\nAmSXgLgMyjrOga4Dzu92J6XRmfsW5f" +
       "Z6oOkYlOWN9URpx6VGHCSNSlxam7jhbp3OnDE94AK8kLsr\ncYqCtC34st8V" +
       "+YgV99o4q5hBGs+zYR8dLOFMdLFytxTUZB9abbSI1SFhb8cJALPsKrWWm1Ul" +
       "7yC4\n/h5edvHdvJQ9S7MjC9QFMOSWer5aFUN4aAkhpFZud+1uNVm2+N50xb" +
       "RHgYYszeE6E8kxRkTJnMAj\nPVvDbI7jood0cGVMj2ObRNSQJNYdhF5jWc4U" +
       "fki4/nqTEw7o81kC5yC52rXRMik7OkuZynSym1c8\nzx9U3CEWZu4PvADMFs" +
       "maJnG6xw7Gfjgh815vxlN4QsGYa8xhF4Uybb3srjd6V/HaO16zwYWMzL0V\n" +
       "NiZdrz4Jh6otoVsU6WqTFJpjY97RuWqBDlIVBOs3pEMyD1kRH7E9tsIP9Nyc" +
       "AuAQGBCDVXvBDsXx\nbLS34ZgF192FmA3VpSaEVJZNKkOwSyNaVMvSz9MQha" +
       "UZN7eELddJ/QoYAEgvjmMfdHq6Z4MA22/z\n/nbusUSRTRLFQtAg3FEQQhMM" +
       "b/rRoC+KfX01p3gix5hoas+E5SJNFC6zZzA7tRcA7WnZXMw3thMl\nYNlOBx" +
       "1AjLjA0YdDA9hB1cRaAau84PL9CkHj/Y7JeyZXp2vOn8J90VcloeaZwPqakI" +
       "OgvyS7SGAO\nsBikR5P2FOQXpZzmuRcdQF+uT+jOmJJmbH+C9tCugJejzOZy" +
       "EYXZzOmAOUrtcrAIDlwM9zId5lzR\nX/i5AGrLLTdoq2Cc4AkN50vOcM1hsl" +
       "zgfaAqOGnj+shQB4yU6u43ELqi+/NpDyfGIbYGiJVJDZye\nNEoOfkhRfr7s" +
       "FDnrtAskAM15X4TVAnVz1LBUaRm7ogcUPB2v2AiQGJIpULCDoCacqQeAcNe6" +
       "ki46\nSl6/fyB4h+rN4bIcTA8S17ZGA2au7UKvO3YAwppt68QF7TLMWMPbdO" +
       "oaJsDFByBOt8Ghl4NDatWP\nsIhyvAE8ZvEVbwv9+h2FgArHLvB2prHu6tDN" +
       "IMiwQbivQxCqSZTnc/hwJ1PkFtiVQ6LcwlRnEzpe\nmEhC5HUnih2J/R00MQ" +
       "9x6uVzV4eHC33d3guDTtFBOmO4jNYG6jGzPGU1bJEaRkJ2ir5Mu32/gvta\n" +
       "hOw7k3GKKs7W35JZ4pX9gICz4W5pOVOJ72A9pb1dmMPhaLCQkxIBxzKNBqQk" +
       "z7FcWJOE6mPkJowW\nq+4G21pEv+zAKKZnqUi46QosXHnM4RQWjgDYDgdwYr" +
       "Sp7qxCRGY+2lf2jk6kLjrR4qTrjnrZajFA\nbV71wh6CxWg83BoOLsWVqkLY" +
       "og+tGVMFU6evj1yxTg718SO1PTibuYqKAPAY5cVs5SOorh9IHaqE\njaGFqi" +
       "APjMxk9fnWhpDFfmx07EqYkuXUS3uCZBaHjBpyQE/OwMhtm2MqwudiRCU8Sk" +
       "xLBY+2B40v\nU9PtLsMDpPbLYM5W+yjbTtc9MDyM3IM/9mGcgPU9OEWjdFkb" +
       "eG67gMlgbXtWpwMrRJVVj++h5sKZ\nrsdAsmPGw4gu5c2CnYxWht4X6pgcLK" +
       "lD32MkLNyL26i3NJByP0xz2B1YSwoyBawdY0AhemaBg662\n8AbbQuRXpclY" +
       "aoXDy7kOLUTTr6aLuY8ZA8BTDx0BmJAZJOE0moOIM1tqo45cbuTRXkLbEwWV" +
       "8SEG\nrvqJXmHixNtPNzNshgjytsuyMZgW006nDw73KwNmt93ReA+sJ5YMES" +
       "aq4xFFxg63EKm0DgCr164/\n6kg6d8arrS+uBvvZCt465TbdcsN0L6wGWNXd" +
       "ayZav105u9pmYgRbQ6iCmY2kkzMrk0Js080RgmVo\njMrazgaji0V9BtDMpO" +
       "dkhzCaWmpnIO/tNMpXoyxZdqpueqg3ZsC+4ug+0kEGchxJ3HCEwMUhma7r\n" +
       "z8REcrlB2RbsDpKb+XK7VdUVW07kjprA+6VbgugwRkXc33kavd84naFga9tp" +
       "CDp6CcVTbYGhpIgM\nnNTrDUTGh9J8L7R5ZkVwAlt/YlkAuPDdaCUPZQA0TB" +
       "5gaNjY7nh9CBeLCTr3FnYPBFCTrsCtUkEd\ne4RsB6MCi2mExY3RaPSupo5A" +
       "fkclliePFaI7XYtTZaVZfPVYlijuQyRtPaSoSRorWpq2Hr7TGjmS\nvVBdfd" +
       "N9Cvu3nn7yI7/0yie+eLmo2mpKLc9/q37Esczy/u2/3Xyf8vl3N6gN4qLeOw" +
       "3Cd7pGbrh3\nd71MhD62X86LmY+KL/w9hnz6x+5X1H3h22Le1h7Pn10/sLP/" +
       "9OqxCnoqnL6u/3Mv0qv3lkvbsZFm\nsc/dKZq+vtC9igPN0LPYuLvvx76++N" +
       "cPPzj8rautaxeryQ2Fly7VZh8xg9hT3GaD84ZTO93F9aF3\nZ+ZiobYRvL6e" +
       "qq83nBUmj89m8fFj2fWJ4m5x6h4nuNqMNxdNfqW5C7Ue33S3zjqKY6Vs6vDF" +
       "T7z2\n/C//ifKrD7Su4LWy7co49kha95Tkzyg0d+vCYvNfTVs3dkqyNIr0uD" +
       "4NTzjzel4NAtdQ/OP/2/cI\n9nR9vfFMsDf+bwW7zNZ9F68cF6+c11ZfuthP" +
       "WQZspu1mruEZfjorNCNsulJHPoK09pNz2Wq8N5/h\nHSuphG2e6vX3kbB5Pn" +
       "Ym4WP/HxK+cLfii7uuYSlu09g17hXvR9PW9djwgty4bLxreWDrF+VqCuVn\n" +
       "zbMrJ7GuvHiqld+60Dsg6k3/59bB/4n8F73yg02F2jZftoz0ZLDbjaFvnzfO" +
       "blPnWnrmoj0TQ8ti\nOy1vUYpquHUKfugcoelSPP26Rvqp3au99Np73vG58P" +
       "E/O6aBOy3ZG1TrITNz3YtNmQvj62FsmPaR\n2RunyD+p5eNpnSvu+mZtiOZx" +
       "ZPdjJ4hfqW129zD4RHguyhMXRTn5ZfHfLqG8OTUgAAA=");
}
