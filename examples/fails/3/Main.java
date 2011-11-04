import jif.runtime.Runtime;

public class Main {
    
    public static void main(final String[] args) throws Exception {
        final jif.lang.Principal p = Runtime.user(null);
        {
            final jif.lang.Principal q =
              new PrincipalTest().PrincipalTest$(
                jif.principals.AliceNode.getInstance());
            if (jif.lang.PrincipalUtil.actsFor(
                  q, jif.principals.AliceNode.getInstance())) {
                Runtime.getRuntime(jif.principals.AliceNode.getInstance()).
                  stdout(
                  jif.lang.LabelUtil.singleton().toLabel(
                    jif.lang.LabelUtil.singleton().readerPolicy(
                      jif.principals.AliceNode.getInstance(),
                      jif.lang.PrincipalUtil.topPrincipal()),
                    jif.lang.LabelUtil.singleton().topInteg())).
                  println(
                  "delegation successful");
            } else {
                Runtime.getRuntime(jif.principals.AliceNode.getInstance()).
                  stdout(
                  jif.lang.LabelUtil.singleton().toLabel(
                    jif.lang.LabelUtil.singleton().readerPolicy(
                      jif.principals.AliceNode.getInstance(),
                      jif.lang.PrincipalUtil.topPrincipal()),
                    jif.lang.LabelUtil.singleton().topInteg())).
                  println(
                  "delegation failed");
            }
        }
    }
    
    public Main Main$() {
        this.jif$init();
        {  }
        return this;
    }
    
    final public static String jlc$CompilerVersion$jif = "3.0.0";
    final public static long jlc$SourceLastModified$jif = 1320429450000L;
    final public static String jlc$ClassType$jif =
      ("H4sIAAAAAAAAANVaC3AV1Rk+9+Z5Q0ISCASBJBcSDKgkPvERhxJCwutiQgDR" +
       "OHi72XtusrJ3d909\n9+aCQnFQUDv1URHrTCm02uIDtUhRi5ZaTdVRnKlaEa" +
       "34GK2jLSLQUmzH1v7nnH3vJaSddqbNzJ67\ne/ac/5z/9f3/+Tc7PkcFho5q" +
       "rpGSTWSVho2mBVKyS9ANnOhS5VVLoSsu7j70HN5VcGE2jApiKCKk\nSb+qS2" +
       "QVQRWxa4SM0JwmktwckwzSEkMjRFUxiC5ICjGuRWtRKIYqJOgRFCIJBCc6dD" +
       "VF0KSYBtT7\nZJU04yxp1gRdSDWz9Zu72mTBMIBSIeu1iBRrupqRElgnqC4G" +
       "uzVHy0Ivlpu7zHcx+tSS1VHUIm8y\nxTlilDlLl9R/NbBv44IJeai8B5VLyh" +
       "IiEElsUxUC++lBpSmc6sW60ZpI4EQPqlQwTizBuiTI0moY\nqCo9aJQh9SkC" +
       "SevY6MaGKmfowFFGWoMt0jWtzhgq5SJJi0TVLXYKkxKWE9ZTQVIW+gyCxjpi" +
       "4ex1\n0H6QRQmIE+tJQcTWlPyVkpKgsvDNsHlsWAgDYGpRCoO+7KXyFQE60C" +
       "iuOVlQ+pqXEF1S+mBogZom\nVMDjT0q0hSpCEFcKfThO0Dj/uC7+CkZFmCDo" +
       "FILG+IcxSqCl8T4tufTTWVj691u7TkTDbM8JLMp0\n/8UwqdY3qRsnsY4VEf" +
       "OJX6abNs2/Mj0xjBAMHuMbzMe0TnlyWezTX9bxMRNyjOnsvQaLJC5+NWNi\n" +
       "zeutH0fyuAmqhkSV7+GcGX+X+aYlq4E3jbUp0pdN1stnu1+4ct1D+I9hFJmP" +
       "CkVVTqeU+SiClUSb\neV8E9zFJwby3M5k0MJmP8mXWVaiyZxBHUpIxFUcR3E" +
       "tKUrXuNYH0s/ushhAqgqsSrjzE/9gvQcWL\nwDebwIM0OnR0lrYVA6EQ7Hui" +
       "32tkMLh5qgyeFRe3f/Ty9e0Lb7k5bFuRuRRB+ZQkCoUYkWov81Sa\nCYoNhx" +
       "5vqbhtuvFEGOX1oIiUSqWJ0CtjcA9BltUBnIgTZi2VLsu0oKC0FwwLbDQuAy" +
       "FmyMBhRkeT\n/QbkONp8hjkifn3t1785HB/YRXVNdVNFqfOtgaRX8r2VTluy" +
       "YsE3b56cRwcN5IOcKCeTPaCYg3Zc\nfPDGN1+esXHCSkDGHsA5Yw5OCmmZdL" +
       "XNVtMK4EGV3dWNASoUBlA5QbJIE9kcgqoD8MZhDabpDhE6\nrQ6st+HUIoiL" +
       "h29dtGv/KwenOt5EUEPAyYMzqZP6ZQxYK+IEwJ5D/p6/zTtyV8HFu8MoHzwf" +
       "eCPA\nGQWSWv8aHmdtsYCP8gKRZURS1VOCTF9ZUikh/bo64PQwax1Jm1HccK" +
       "lGfRtkmPnl+sKz33pmxK/D\nbngtd4WrJZhwZ610DGKpjjH0H/xe1113f77x" +
       "KmYNpjkQCEfpXlkSs2wj1SGwvtE5gKNpXNWmzdO+\n/5ZlbqMd6q26Lqyi1p" +
       "a94fWae18UtgCogHMb0mrM/BWxlZC1AG2ns/sm10v63AB+5u+HpSY45srM\n" +
       "BoKexCNiXFz+8Gcz95cuWxFGYRAqaCgJ0V0SIYRPDFhbm/2WmhwNPH3W4JrA" +
       "4PnOa2os1f49mOvf\nsWP/8chFn3UwCxmRwIaoSxrFRBO6SgwppckSBMUEM2" +
       "wIkERdAKKzo70uKIYMCQR3hqXsZXtW02ms\nyQgQmQFLmFROz1Kd2tvooklE" +
       "XPzTc8JVjZ1lStiU4Ug2lsJjo3l5YJJBowmQ7OdiEHA9o2ptxBGv\ns5m4eM" +
       "G6T/+8680nGnloqfPOCIye9GjNkYYdV0+xrKXaD07zBKMfLPKA/FbP3QfPqO" +
       "VUXRZrvt8z\n56a7Nz/15Pkcv0qBgYpvzOLsUMuo9WulGwuA6VxtcfH2K6T6" +
       "xVNPm8uMo0AdUPxJlgb5gShpAk20\nzDuan+mMCpXObNjVuID5meSnxgqfeu" +
       "TrIzOZK7oUTaNoIJEzLcl280vYbbsXje39NC1VNXtLcXHe\nh8+9f9M9415x" +
       "C983wTVan1M4d2fnj/cxtm1zq/eZmz1hSJOj7UV8v7DV071qd2/Srf0DN4x/" +
       "78yz\nbn+Jb9dvXblm3PfwiftXN/6kj9mLxtbuMFelPwu0XMpeDrm6o2z1zi" +
       "/O27S+Y6tL2UyDIIIBNpDr\nk7ZtjgK6gPCUXPKcrRKiplxSPfels6tbH1i0" +
       "03K0ubZUpnkZ9M10s3lO6Y/2/v6hB7dZNBZyVhe7\nWF3Cu87TuHsuZ08trJ" +
       "3pdwDa2aoxSj2ckOYh4nvs5EPjXLyaLVrvownTNAvxp00dNLW3omOq97rj\n" +
       "z/+gJMpdnM6Z6ILyRpOKA/YTHUCvOVl6zFL7jVccLd0gDK7g1jPKm3W1K+nU" +
       "BVsP4GmzSsUcCVuE\nqNp0GWew7HDhX20ROzZYbJQvr/uwY8b2NX42QoHDo3" +
       "deXPzh2R/vHUTau/+5FMmMELmyobohmYiL\nlZkJi/P6JXC5PDsRChyhvJNa" +
       "3MIDAOOrUjXQnhKmxsl2NAH5oELzon8F/mjCsxbaTmHtVJ5ghOn9\nGZBlGO" +
       "wgmjsJCPOhhifOMv5xgp9sXhtx3s3RC5JVzLsjzP8hABJTYsV0hvXMd15m75" +
       "ySLoOr2Nx5\nsX/nsOx4P7a06n1mhF/2XXHvhmhlEYvwYVGiyUIgwUzgk0k1" +
       "rcFx0a3dcEaiyYmPxOWCKzelI2fQ\nZm2WoMvoyJSqa/2SGGWbi6rJKM8oo4" +
       "Lel05hhUQ12skPxNEU2FZ0ai9dEyeiQq+awdHeVdHrzpg+\nc800zQY8G7Da" +
       "BEVRSSCG/6Hz8LOrNfySBVaXcvdgOJrh6qbNwJAapc/Xc1bY/bf4PW3XM/Hf" +
       "9L9D\nNQh4y5SVCkQSboLn7n913xtzso9a8ijjcLopt0mHOIrwEbRNw3GSao" +
       "b1IJQr0Q1EOHN90xLz6r+Y\ntWLMdzqsDczg3Jnx8kb+c6+vk6CQGVE8DlE+" +
       "lEOwn62njDy02cZ2cp8Tsbd5w0qwq9OZ9oAT/bZ5\nA5e3CxTjQeKYKgqyg3" +
       "1Va2ZfuP0g3smPRLIbNv01Fd/MFzb3nb/1sccKeKjxa9/lk3Hxov2ZysKf\n" +
       "bk2FURGgPcNwQSGXC3Kanqx6UIlktJmdMVTmee8tSvEKTIur+HOD77joxo98" +
       "4sHjkVwwIcRUtDMI\nt3CeK0hKiiBnmeKDEoC8JCURKWNWle6svf+TXR91V4" +
       "Vdpbf6QPXLPYeX3ywPgBUmDbUCGz145qQd\na7vf67UMdyUEajvTCjgwbZ7h" +
       "TkzbDbTZzSyS8UybpzgCeCyaWmjEtOhIAOLtRfaCYBYPC1Lhxhg+\nqnJvo+" +
       "0d4Oh0bg6vqxxqj+zn+WF53SCT1IuOiw0GvW4w6HV82j7HxQaDXufteoR3mf" +
       "b2Wg57o/c/\ny1oseyIp8wdWHOC29o3rNl/64p2f3AUxvAcVmf7CPOcyVWEP" +
       "OUqnrvmP5zUcDT89toFF4vxeweCO\n4a85B0vKnkoxk0EpNwUnkJj3Vtr99j" +
       "DT7neHn3Z/OIyhVQRVsDMwRYomXr7WNA2F3PZL22dp8ylt\nfuU4yW+ZATmK" +
       "ejPIJOONNp8wskOGLp+P0arrWL/9ElTWnhUxq3iw0itknYbUN93QxWYH8jxD" +
       "sj6/\noAnlKLhKTLolOf3i6CkV4tlo5VAEaXscTLUx17mvPQvGA/jpOvnduK" +
       "Uvs+ad7LX+8wZt5zIIPNOb\nUAVouNOqLdXqB+u2F3U6Z0BAw1Y4w+LLII10" +
       "/PcvQf89xib8w3HWY0H/PeY3qNGOQdl64AkJbe5h\nw54wzfDn4McZVUq40p" +
       "OQfRpyeXZrmvS32ceYuKg8nVqV98JpTbwS4oX2kK8sRcmNg2ukqZ6RObGa\n" +
       "1SFrhix0rLs+/cYXyoVbnJOb33hPmGz9NafZuMWe9VslNaLRyDnuFOayylDV" +
       "cNA6NIZKIDTO1hF0\n+NXm6+q091FsesdJ90En13i5zW1QoVrLoJg/0fSA3g" +
       "SCqgs03ICjOWDzbRPt/xtwQ5vDbN5Ruuc8\nl6XS54Iclkn7I7QZ4aIfGpkj" +
       "Ew9VZJm8qkwJhLXGf1MEGyDNoiJo/D+XQdb8xqXlij88U7XqQTkJ\n/qtuxm" +
       "qIdpG3TZVlWAEAyWhYpqTUhJRktUf6LeGr8inn7D50WwUHXefzmI7OOjUBp/" +
       "+02WjdK1ef\nqGVkQiL9Eu18KnGGtbgQxPVFIrTQW5KgVaA0/44fFw9pV8/9" +
       "Xff7D5lVTjtvwVnSxL7wW6Uae8ae\n56fc8s47b9fzEqUoC6tX00WLIUPhya" +
       "dh8TjppNQsWtIH+5O3rv+43K7708b6dsQ1lnFn/wE69P7y\nspYDC/fseSBY" +
       "w3NIVPs+OVhHovOnHi/68tVjs7zw6zrN1gXq5u5/ZoiLR8rq5i7v7W5n6WBE" +
       "Mpbq\naYPQfyuIiFY90FtJp98q7e/1/ATM07VQjx3NXWVm12Lu+LvwttqBXx" +
       "ivTrbLGjmtlvlnxT8Bxn6O\nzUUiAAA=");
    
    public Main() { super(); }
    
    public void jif$invokeDefConstructor() { this.Main$(); }
    
    private void jif$init() {  }
    
    final public static String jlc$CompilerVersion$jl = "2.4.0";
    final public static long jlc$SourceLastModified$jl = 1320429450000L;
    final public static String jlc$ClassType$jl =
      ("H4sIAAAAAAAAAK15aezsWHZXva27X00n069nMplkpqdfMi+hW8482+Wyy+5W" +
       "CFWuzUt5t8tVo9HD\na9ku73t5yAiElMkisSgTFgmSL0iR0HxAGQESIEAiLI" +
       "IgofmQ8CUBlAghQSLyATFCgeCq//9t/9fT\n8wFKsuvWveeee5bfvefWOd/8" +
       "/cG9Ih88TJPwdAiT8nF5Sp3isWDkhWOToVEUSt/xxPrgi3/U/MbX\n6c/dGX" +
       "xyP/ikH8ulUfoWmcSl05b7wZuRE5lOXkxt27H3gwex49iyk/tG6Hc9YRLvB2" +
       "8X/iE2yip3\nCskpkrA+E75dVKmTX9Z82skO3rSSuCjzyiqTvCgHb7GBURtg" +
       "VfohyPpF+SE7eM31ndAussHXBrfY\nwT03NA494WfYp1qAF47g8tzfkw/9Xs" +
       "zcNSzn6ZS7Rz+2y8G7N2c80/gR0xP0U1+PnNJLni11Nzb6\njsHbVyKFRnwA" +
       "5TL340NPei+p+lXKwQ9/V6Y90RupYR2Ng/OkHHz2Jp1wNdRT3b+Y5TylHPzA" +
       "TbIL\npzYf/PANn73gLf61N//3zwv/8+Hti8y2Y4Vn+e/1k75wY5LkuE7uxJ" +
       "ZzNfE71eNvULvq87cHg574\nB24QX9FMf+zvq+x/+SfvXtF87iNoeDNwrPKJ" +
       "9UfY59/59vT37t85i/FGmhT+GQovaX7xqnA98mGb\n9lj8zDOO58HHTwf/qf" +
       "Qvdn/2bzv/9fbgPjV4zUrCKoqpwX0ntsnr9ut9m/Vj56qXd93CKanB3fDS\n" +
       "9Vpy+d2bw/VD52yOu33bj93kaTs1Su/SbtPBYPB6/zzonzuDq8/luxy8sTH8" +
       "+HHgu+mZ9K32/P6+\n5tatXu7P39xDYQ+4dRLaTv7E+tXf/dd/ZsH83M/efo" +
       "ai66XKwd0zy8GtWxcmP/iy8mdr2mfQ/7df\n+/Ctv/Cl4u/dHtzZD+77UVSV" +
       "hhk6/WYxwjBpHPtJeUHLgxeQeQFEj6Y3zR5YPUafhD2jC5B7Det8\n8KM3Af" +
       "R821F9y+hR8e2v/fG/+4MnzbfOvj775tNn7lei9ZY+Xsn25vvyV+g//bM/eu" +
       "dM1Nzt7XTW\n5NH35v7E+oOf33zrN//Nb7/3HKjl4NEr++fVmWf83xRfyBPL" +
       "sfvz5Tn7v/q/1v/9F+8Rf/f22cH3\n+2Ol7E193qNfuLnGS/vgw6dnytlYt9" +
       "nBJ9wkj4zwPPT0IBiWXp40z3suQPjEpf39f3z1+T/n54ya\n848r9LxNJlHa" +
       "wy9/uHJ6OYzS6Q+hd6ahbzlcYjtf+pMfPIXXB6PRT6DYlzAsvYLY2fY39L2c" +
       "bt/5\n869Bv/WPPvHPb794EH7yhRNTdsqrbfXgueuU3HH6/t/+a8Iv/tLvf/" +
       "3LF79dO64cvJZWZi9Qe9Hl\n07d6nHzqI7b4489++ht/5f2/8VtPgfGp59yn" +
       "eW6czrho/9y33/nr/9L4m/3277dh4XfOZWcNLisN\nni5wfgOX9k+8MHj+/e" +
       "41yRmrNzfX8hwAnjo6Mr/6P/7ZLw8fXglznvNDFzZ3ilcPvJcmPrG6f6z+\n" +
       "8nf+bfk7F/s9R8iZxxfaV5fVjBfAi/9m/eC1v/Mr0e3B6/vBW5egZcSlZoTV" +
       "2br7PuwU5HUnO/i+\nl8ZfDiFX5+WHz3bA52+i84Vlb2Lz+VnSt8/U5/brV3" +
       "A8vx62t3qn3kMeQ4+h82/8MvGLl/ePXzn9\nTj/u+rFxCRLv9QgoLsG9LQc/" +
       "GITWo6eY1fr43p/Dj3p0XmY/6EPzxetnHR5fhcErrJ7f46eL9z74\n/udkbN" +
       "LHyl/4vb/0G3/xi/+htzk9uFef7dGb+gVeXHW+TPzMN3/pnU984z/+wsWrPT" +
       "SR5B8Ub525\nTs+vD/pAe5ZOTqrcclijKDeJ7ff3Avsi4KuOF3I/6gNJfR3p" +
       "/vIX/tZ//tbvSp++/cJ14IuvROQX\n51xdCS6WHaZtv8KPfNwKF+pfB37km1" +
       "+Tfse8CpVvv3y2L+IqQn/l3zvv/6k3rY8KC2HykfYsH3Tr\ncUFNn344zSCR" +
       "qQZLR4AA9qgIUTI5lWf0zJLnzOEo1AvJa2my6dcUD3OJX/uTkV5WRU4jNgER" +
       "QwIw\nSDFeiuq8hlVpnStkBI0qal6qnuSje0OxNSlmQmNhIOMVMwpYmRXVqF" +
       "vD9SSO10hNG7JtZybX0dhk\n6ICGBaIdSLgICCjWRDhaSy9BszxdycpW1rxk" +
       "FM005iQTUy7mPRnVNDaRi2oSTKA1h2rWajEKpqnP\nkMxxKJtAJo9ETZS2rT" +
       "GWNxDdRMeysY5NJy+Om0Baqq1PohQZrCppW0oMtcto/iAtSYqekRZmz7Ul\n" +
       "xMi7WalMhntahcanNoUPki3Ptx2xryajSJltqJlMORXsz6S8SjNpER7ijByv" +
       "qk5bnyCqDtNq0q2t\nFihlGdbWKhXhp2GrhH44D9mQkEDOnNXjMUU7ho/PUI" +
       "09eZS/WISF2qokAISzVI4srNhi0D6lxgtR\nyEKxUfxuVvkKBUXJbpiRCWHQ" +
       "okfIR1J26h22MVZM5CbNcrnaJpsSCrZl5x0yVKZpH1I0Vt8xx5bZ\nLoi9Im" +
       "rUFjicyDZI/bHTwkNojYmSGFELoKRFS9mGwkk7psyhqOdcsQ6L7fQw361SiU" +
       "x4YMLop6W4\nwCDSr3gtFIE00xdLWAaiWXLYJ9FQ28wDBcogT07JxdJiXMXj" +
       "oXkAVuQEII/7ZUTOkyXFrgDKauWM\nDHFlXFauomy1ACPgaqIclbm5wWl2k0" +
       "JDEFebzN4hmVTExW6sLVye1jHD5IoRuAmgNphNN+tqY3Yb\nB1Li8LR1OkUn" +
       "zHYjcaeAj4taNFiaRAFuy/nDzqnKNeSeqmmUeUdZmwAra6UDOmDswmy8QAJT" +
       "TaHU\n5uCijCsYGFslPAcowlvSkUAeNU3JHBWQfTJUZXPoaPiC6JZ0Y56QDX" +
       "qENj0RHulbzfBP+3a2VlOq\nXaqWhCxKRs31itutoQKsCs+bZoeskPOgW4DE" +
       "oRatLTts5o7ZUBuIjyUozHfaFFuIk3XlI6PyuGE9\nNDucnKXkIG5/jycciD" +
       "UnR9RcVlIlSTNgLx7JRN0dt0mNw7IxJHcJ57VonZleWdXLxYrX4YTyVTnb\n" +
       "0hBZBtaUmIWlOoupg7L3RxpbVFDCkNCyaVhzLlbT0zI6bWaedHSUISiaR55L" +
       "cS84+m6MlPAER9cz\ncCfpGmdr3DJz+fJgSDqejzQlrE67YoGYKGZJjcWhFG" +
       "8xpKMIBrk7IHtr6ArzFQcDTqGvSfk4X62L\nKbprUJ6Mor3N6pxWbpjxYqUh" +
       "9pabyok6C6eoaNLUgjhahyyZ45u9NzfMdO4tsGEuYkUJ9Hsr8Vlv\nRNjlqg" +
       "aZxon5El2oC2F62m26bC9ge7HdMQC1aGh0DqYY2CnlScPXywrfJOuucR1SmA" +
       "5X4lbe1rix\njuyy2JECfmCkvSr1m5cnlSWxV/2jB5oJNhbExmhhOoLXlsWE" +
       "K1cqj5EQh0DBdAiSRi0DzodmfCjt\n8Tan916zx2BovmLSIFhDa7B1YBhG3M" +
       "mkZaIdlwZHkVy4qR4e5WLUBLC8aupygR4YLtW5bdTZPDAE\n0X1oZhsby0YY" +
       "m518ODWIsbs1HBhXywOZHNYbE4ryDTjbtuGehKerUVyARQRDNYZMZwBaAetU" +
       "3o3R\nIt8MZwnjtCTn68sOwFdWR6BWtW2Xq500zQzyQEMaJFM7SZgvPTMCR9" +
       "vIazhhjWzHdD4dCzFvjJIC\ndsdExNfusNgKIG55yxhRG7KNpIYyErnNl9KM" +
       "x+RDk6bWUZ5t1MVm1FDHhWRPGaZLJlDVn7gbUpQQ\nL9OLvV5uoVFUDE/iQY" +
       "zEJRstJb6DGXtU1klyDBe60CkWTmvBHg7l0lfH6mEUFnSQICG6o+lZ0kTi\n" +
       "hLf9xXpKJKO0bGerelig0djZrDAFBHAMh+JVHCaZK/AkdXKIVcAddztbg4A2" +
       "Z3RjDk26GbNxWYGZ\ngktTQERXyHhN1aV9Xpd4N0QxY8J1IIz2YamGstlhl9" +
       "YdzxbqpkqIbRWummqKaW1puesRgylYq3FC\nhFebBJraqtDS4cFED7KXTo8j" +
       "aNjNKyMGog2Z6rzJBVofMtHIkeP2kLlOpeRUx++ZSaIqBpdQ9KJj\nrXCEQ/" +
       "XONAs9cBCCI2GN00vW0tfO0MMBwiU2xBhGW5LAkUbRjBjejO2RGwdgfxWL4C" +
       "4DnX3UwZ5M\n88FE1fB6MtegCZzZI1DOXWjOjVtwgp+6HrQAbRJ2rZthhAhu" +
       "47ulzoHYMSAiti3NarN0QqGpIH2T\nOvM8wpJRB3c+yI8mMrDDSnaknuYURc" +
       "24dJ4Pj5N9GRwY7CCQqi4wVi4xfhq2suA6YYGV5hr2IQhh\noMrcq2l83BMb" +
       "GCqQkMd24AisMW82sffcbH/SPGcy3C4RssInSrUuqj2El/0dJLWbuTEyklYO" +
       "RtaR\n33njuC7QhRDo7MlgtbxOmljhpnWbQpDNsVskifiMmOTBsAOBA71J7X" +
       "iD7TGKPUkp3/t9p6xVsu7D\nGdYt2UmgWRZrQ4c5MznAgcBEyBZmgFidaYUw" +
       "XkWdXKgWqVWLYcal0GrrNJP13NNHUI/c/c6kxToH\nq2aHmyNOH4/EZD1PEN" +
       "vpgeyNJLBe976tQABdbqLNgo1OiZl40c5nh+H+SPuggNE6iQrzQiK9k1fT\n" +
       "nIFsqPmU53M10MXYY6K2CEfcdI0mC7ZLYnwJRgFZtKahROtSo9GDx8fEcLWf" +
       "hSu+OURBQZMC73m+\ny29qtD2kRKF7ZYsUiESitNaxRdAJuDed0cqu0CCv1j" +
       "c2jEYT9CSzCZ+PuBYdFjoYF3w7FiYGhMVw\n5C3Bka6vay/ywsWubnbayj+p" +
       "1KFRu8MROsK5tWwhA7GtRR8Ld5HQ8luS6ZyQGrnicB+N+vtJnZxo\nOTPAkb" +
       "xabU0fy2lmgyfJmHVCCPU8VYRHxVFouYU6Wu4hwyfsxcnxxQwwGnvtaPY679" +
       "IDMLRwCOrE\nHb6azI3ZQpNkmVyB2LrDWT7n3JM80wzdqu0DT+ce1l83G3pq" +
       "pBaoW7C5A013LGwRfQOuXYIl5kNO\n9MYnVNbIoyibCRtPYgJG2rIr+psW7g" +
       "eBxSZpCUVrnsqbhSlxDbKSjcSEKVrMIRFe8GuOgFq2w3Jg\nNyy37MbusmJu" +
       "dQeEjlv8aAIWBtRHjBRX2gKfhwsgXPBNjI3iYH6CRtZ+ZKW4Upg22kBZkxH8" +
       "nkim\nCmju0eEUdRCyVQLRYXbMITsdEFgzRnu3EqADUkSCxFImV0Pw0gY3y8" +
       "5EYhOEiz4+OUzQGgQYHpF1\nm9Sj/alg3KF5HC1VaXUQLK4R5ZVOIowRZy2m" +
       "UuGxpCN7oymR4+ClXC8YhapyYM3PJ5MgaRMCHxfr\n4Kg1DWVJajnfUMMSIR" +
       "0G8hjfn8WRw8wXCYnTpCoxW24bOlhRu4CiSwSsjTU7NV1MFHRW2Dk6cmqE\n" +
       "w0yN5qZKrjp4jGaiPTQmtYtN1I19UhdGUCtkcGjM/cRTjp05N5V9J59kXD2Z" +
       "aXcgHfJkbKNgUfIe\nNeXZUnL7S8ZsJ5Aw67VeD40spusEgOwx70HmAauzts" +
       "0CoIuB7RJFZFKBqYr3G2hVb8cNttbwUTBZ\nt6Ix1yu3dBIJiVxztD2gkwS0" +
       "wOEJTgrnRKV4Y2/XPDkiEiTnQhAbpyMXmaptPbW0yJ30O806aduF\nwKtZnN" +
       "eRgHaqNVdwokyWy3HdOo1D8sOxgaMzqGbiY9IeoUXKNWp8pANEK3OCCObKJH" +
       "bWMbIHeerk\n9X9jtGK+aaRdEun5pL8WjZaIsGdHIzvFT2E5dCt1fNoC+6kS" +
       "MU4gUDTPc/11rSmO/IpNAQEy8nzL\ngsQiVLaxp7YZ0ivtl8d4Z6ocgYjtdr" +
       "fGg3ZeBBg4TKZxyLAeo7BJyLuIj/W33nEZJqg2XRh6YSms\n2sKTqBbEejUF" +
       "g5SvY7ti1LoADHvC6gDe7Eu0oyiB482hOtWSiuMz1tazGVD1cqvYHppNG3g3" +
       "bgCW\npSgYAgWrFl2caH0Tb5D1dtffkQSnv+5zK160Q2w8PdKmg3rDBh03nT" +
       "uPxxtrr6nZGEp50ZXSk6Pv\n86iWkZHl7CrmgMdaphE4nSpmI48TfXRk8EKy" +
       "Iu4wqo7ZHpN4c2UMYXmZe34L5WwBgNZOPyWaGdO8\niSCeEK06gjPcfbXZOo" +
       "RWhJzBSqd5XC1543QK55rFTTGO2jPUXOFhbwkNAU6PMCCDCHhZOIVNE9Ai\n" +
       "hhEf7bawmGg5m9AHEHLlKXKQyexQQEj/z5PfzjtTEkqwLRsRrecmh0pVZeHD" +
       "ENNldExZ4TGeUBNx\nPzYod5Ueg0VDaHs6wrNjQ3usvnLhzWJ1mnOJTyfgHN" +
       "BF28TbTA/9AMfViZ6JypEegllZtVFG2ekK\nUfZauJ0upqett0szRfVo6Viq" +
       "LsY6qxz343gyR6Fto651x+0iasLzgTpLQQAabQObgR13mK20o+iz\nxKKCcS" +
       "ZerUrzILaCtqRyRdyizEEzphA4HrvLhkg1HCR01+15hpoSlHWjxXLLTpotph" +
       "oObjj9lcq1\n8GI8MQm3W2RqQmKlDkFbllIF72TYHq4EacqJxbak1gDKI3kN" +
       "jBFmMoFwE7MLEQ7jZQw2C4wQJqdh\nke7mJOgWjjzbNQhHaUIB8Jq3Tmo+Xv" +
       "sghpdaUEy6ButyDj7oGVqh4L7/zyDheYb6k3qyRWNsO1ts\nUjMbGjtB7P8g" +
       "Uv5yR0DLkk4JPJs4IAxOlQ000cSgRbj+P14Ej9gKYOumLLOZ3vq1kpvsKojK" +
       "sA7j\nVsFnbYzxw05V/el0+pPnJMzmOv304JIce1YwCnz3PPBTl5TNJUv0Y9" +
       "fpy+cZzh96mvrMB+98t+rN\nJXH0df0P3/wZ49e/cvs6FUqXg/tlkn4pdOr+" +
       "2vUsK3qTyeZSrHqaKvzk9t3/tMR+9advpkXv9su/\n+7Ezn1gP6s+Jdzz/X9" +
       "0e3HmWlnylWvbypA9fTkYOc6es8lh5KSX5zlWGvBeiF2rw2vVz/tw7vy41\n" +
       "lRcrK68mK29fkpTn109+dAb51rMKxM0C2SUxfZX9+7U7j/7w9j/8zKNLYeCu" +
       "aRRXMt6sLL5aOHyp\nHniR8f4zlc7rvtk/969Vuv9RKv1UmqYfL/h1bvVTz/" +
       "Ohi9Zy0nMN7MLhy+XgbmT48YVwec2M6jvr\nxL8SbPdMpD9x/bxUxXrFyi+A" +
       "9buk479nrr6Hx71z7eLRc7c9l+JcS/vxs27XUty6qobUr1ZDHr73\nscWQ9z" +
       "94mFVG4WdVUjrvXRUqHp7VfthTPfLjOjk6c8d9oWD03vsPv1p6fg/ws3Dvvf" +
       "/hT7+fpv/f\ntC7KwWe/28rncf+GId64LireMMST/1dD5H7dT3rREn551vzh" +
       "l78iP3yu8au76VY5eP169sdr+z1N\n8dVy8MbTpS+qt9eVzTT9iGrBVcWj/b" +
       "+kMgv8fiAAAA==");
}
