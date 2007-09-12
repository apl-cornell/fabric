package regression;

public class LRValue01 extends diaspora.client.DObject {
    private diaspora.client.DRef $t;
    
    diaspora.client.DRef get$t() {
        diaspora.client.TransactionManager.INSTANCE.registerRead(this);
        return this.$t;
    }
    
    diaspora.client.DRef set$t(diaspora.client.DRef val) {
        diaspora.client.TransactionManager.INSTANCE.registerWrite(this);
        return this.$t = val;
    }
    
    void m() { this.set$t(this_ref); }
    
    public LRValue01(diaspora.client.Core $location) { super($location); }
    
    public void copyStateFrom(diaspora.client.DObject obj) {
        super.copyStateFrom(obj);
        LRValue01 other = (LRValue01) obj;
        this.$t = other.$t;
    }
    
    final public static String jlc$CompilerVersion$diaspora = "0.1.0";
    final public static long jlc$SourceLastModified$diaspora = 1189613297000L;
    final public static String jlc$ClassType$diaspora =
      ("H4sIAAAAAAAAAIVWb2gcRRSf+5vc9WpyadLGNM2fGrVReqkIoXB+MIQWY6/k" +
       "TNK0uVKuc7tzl23n\ndtfZ2csl1aIIthYUilUsaP1SKEg/iAUFERUS/yNIPr" +
       "SfWigVFbQFP4j5oOib2dv7s5e2Bzs7N/Pe\nm7fv93vvzeXbKGQxNGAadLFA" +
       "DZ7giyaxEmnMLKKOU2xZM7CQVU58Gn2y6/DKUgC1ZVCbpk9zzDVl\n3NA5Kf" +
       "MMihVJMUeYNaaqRM2guE6IOk2Yhqm2BIKGnkEdllbQMbcZsaaIZdCSEOywbJ" +
       "Mweaa7mEIx\nxdAtzmyFG8ziqD11DJfwiM01OpLSLJ5MoXBeI1S1nkcnkS+F" +
       "QnmKCyC4OeV+xYi0OLJXrIN4VAM3\nWR4rxFUJHtd0laN+r0b1i4f2gQCoth" +
       "QJnzeqRwV1DAuow3GJYr0wMs2ZphdANGTYcApHPXc1CkKt\nJlaO4wLJctTt" +
       "lUs7WyAVkWERKhx1ecWkpTJDPR7M6tCaDMf+PZP+e8AvfVaJQoX/IVDq8yhN" +
       "kTxh\nRFeIo7hmJ85NzNm9foRAuMsj7MiMPfzJgdRvX/Q7MlvXkZnMHSMKzy" +
       "r/jPZuWx37ORIQbrSahqUJ\nKjR8uUQ1XdlJlk3g4uaqRbGZcDe/nPp67qUP" +
       "yO9+1DqBwopB7aI+gSJEV8cr8xaYpzSdOKuT+bxF\n+AQKUrkUNuR/CEdeo0" +
       "SEIwhzE/N5OS+bCKEWeHzwtCLnFxADR7HU1CymNtn1RELVONrCSAFIbIFP\n" +
       "I/U7ZWFo44LPB9/Q680nCuR7xqAqYVnl0q3vX9iz77XT/iqjKi5w1Fmznaja" +
       "Rj6fNLqlMTAi0qpI\niD8+Sra/sdP62I8CGRTRikWb4xwlkEiYUmOBqFkumR" +
       "SvY60kCzAtlgPSAX+zFAxJkkMkSgxt95Kr\nlpITMMPAmNWT//10J7twRfBA" +
       "4NYprDuuAQrHHd9iw9NHnj16entACC0ERYBBdOj+1rPKnTP7r1z9\n4fqOGo" +
       "k5GmrKrWZNkRte99PMUIgKtadm/uKDbYGDaPasXxAhAiWHYyAK5G+f94yGHE" +
       "m69UYEy59C\nG/IGK2IqttwiEeXzzFiorUhibBBDm8MRESyPg7JUrb0S3nXt" +
       "sw1f+eurWltd+Zsm3MmReC3WM4wQ\nWL/+TvrNt26fOiwDXYk0R2HTzlFNKU" +
       "tHunwA7KZ18jXR3Xnu7eF3r7lIbqpZH2MMLwogyy+vbjv/\nDX4PchlyytKW" +
       "iEwZJE9C7gFiHJbzx+o2xf+Biogglzc79opq7iJTzJ34a/lCdMBxRuj0VHnj" +
       "rV4N\nilll6fMDF9Z+5Ddk/GqQChv95eZjZ3Ed23ZfLcXDH75f9KOWDGqXHQ" +
       "jrXGYgRDcDPcQaryym0MaG\n/cZ+4BS/ZJWyvV461R3rJVOtGMBcSIt5i8Mf" +
       "KfMABAICI9/BSpWSb7HZLoZ42YdMMRmVCoNyHDIr\nSHAghaSgHMrNIhLBR1" +
       "ysanD2uDgztO1ufUf2zFOH/oy9ileOON2ho7Fk7dHt4q+Ly+TRp16/uU71\n" +
       "i3DD3ElJidAaV7yn7Zf92A1g28H+m3tHL724Hln676mZVeKlrc8F5rVvoWxW" +
       "wWq6EDQqJRshijIC\n9xl9pgGovgagovCEKkCFPEBVgdh9z+y5b2pNAKrFdb" +
       "IjzbQitM5Spbef7bv4y5VbU53+ugvQQ013\nkHod5xIkz4iaAvrBe50gpVce" +
       "H7x8cupGzl/x7WmOgiVDU4F5kWpDEzWmu+nG6dyLlO2rR3csm/Hv\nZF2u3l" +
       "3CcIHI25TWJ0jdPGwyknc6cNhJF1O+DnEBkttSxcqcQ5MOuFfKKidyNuHkbP" +
       "l/wYRrUQ0L\nAAA=");
    final public static String jlc$CompilerVersion$jl = "2.2.3";
    final public static long jlc$SourceLastModified$jl = 1189613297000L;
    final public static String jlc$ClassType$jl =
      ("H4sIAAAAAAAAAJVYaazk2FX2W/p1d3UnvaRnYdIz/TJ5Ubop8ly7q+hEyLW7" +
       "XK7Ftddo9OLl2uUq\nb2VfV9keZhSESEJCgIgJAgnCn0iRovkRZaQgIQRIM+" +
       "wgofmR/EpglAiQIJH4gRghIFy7Xr2lXk83\nlGS/++4959zjc75zzzn3jR9h" +
       "l2wL2zcN1ZNVAx5CzwT2YYuzbCAWVM62u2jiSHjlDyLJp15629/B\nboyxG4" +
       "regRxUhIKhQ+DCMXZdAxoPLJsURSCOsVs6AGIHWAqnKj4iNPQxdttWZJ2Djg" +
       "VsFtiGuggI\nb9uOCaxwz/VkHbsuGLoNLUeAhmVD7GZ9yi043IGKitcVGz6s" +
       "Y3uSAlTRnmOvYVt17JKkcjIifLq+\n/go8lIiXg3lEHlGQmpbECWDNsjtTdB" +
       "Fi9zY5Tr74gEYEiPWyBuDEONlqV+fQBHZ7pZLK6TLegZai\ny4j0kuGgXSD2" +
       "3PsKRURXTE6YcTI4gtizm3St1RKiuhqaJWCB2FObZKEk18Ke2/DZGW81967/" +
       "9xda\n/7G/HeosAkEN9L+EmF7YYGKBBCygC2DF+J5z+Do1cu5uYxgifmqDeE" +
       "VDfuz3e/V//uN7K5oPP4Km\nyU+BAI+E/8rcff4d8odXdwI1rpiGrQRQOPfl" +
       "oVdbxysPXRNh8ekTicHi4XrxT9g/G33mG+BftrEr\nFLYnGKqj6RR2Fehi4X" +
       "h8GY3rig5Ws01JsgGksF01nNozwv+ROSRFBYE5dtHY5OAkHLsmhmGX0bOF\n" +
       "nivY6rcTvCB2vc72OdUBsfihqEDsGQvICMQ20gk/u+IGgj6w3NpC33B3M55U" +
       "BL6qoYrAOhK+/oO/\n+vkS/cuf3z5B1LEKELtzKvvwRDa2tRUKfea8YQJLi0" +
       "FA/Ou3Ht781U/Y397GdsbYVUXTHMjxKkCB\nxKmqsQTiEQyRdOsMakOwIKRd" +
       "5xHoEH6PVCQoBDmyxMLCXtwE12lIUmjEIcS889pP/u7HR8s3AxwE\nfrsTSF" +
       "+phrwwW+l2/UHn5dqnP//iTkC03A0MjEgPniz9SPjxF5g3v/PX37t/CmKIHV" +
       "yIrYucQWxs\nqt+yDAGI6Ow5Ff+1n7qxM8D6X94OgHAVHTmQQ0BB8fvC5h7n" +
       "YuTh+rwJjLVdx65JhqVxarC0PiQi\ncGIZy9OZEBjXwvEHkQGeOX52zqIsWL" +
       "wZvG6tYBTYc+MbwtPsvV/ci333D6/96fbZg+/GmROyA+Aq\njG6duqNrAYDm" +
       "v/dbrd/4yo8+91Loi2NnQGzPdHhVEdxQv6e2kO8/9IiQPnz2zuu/+eB3vrt2" +
       "9odO\npZOWxXmBr91feOf53/5z7ndRuKOwsxUfhFG1dez2QP5tBHFR4WzTsL" +
       "hDQVWADpHzLbDePXj/dDiO\nBlYJObHQIvvHJAE4N6OrHGSDtWc1/pV/f+ur" +
       "kf2VpgHPc6GYXfvi6XeO8Ujw/6j31ff+Fn4/NO4p\nJAIZ99yL2/a5M2jNfm" +
       "dxa++bv6dtY5fH2M0wg3E6DCMYmX6McpBdOJ6sYx84t34+n6wOz4cnkL+7\n" +
       "Cccz226C8fQwQeOAOhhf3sAfMkz4d/cYf7sb+NvCzGCQCxleDN8fO0HLZdNS" +
       "FlyQ9LHtA/i+Pi2i\nmFnhOHinV3v/ZPX7n+AJ9gz+WZ2wtwuGZqJj2dqvAG" +
       "RwJF803S203aXYYfwwFnCTF9XZQeuSonNh\ncvs4QrIdFiVItbtTVThYy+yj" +
       "ugSdpwdrHddK3wwhHNj8cJXDzygcvPJueOp+8JSsbqBE/8Uf/vrf\n/NpH/x" +
       "5hpIZdWgT+Q9A4I6vhBJXQZ9/4yvPXXv+HL4YoRJa785/+y2GmqQevEio9Ah" +
       "U7hmMJoM7Z\nkDFEBRU14qmWF9HashQNpcLFca7+8gtf+8c3f8De2T5T0Hz0" +
       "Qk1xlmdV1IRwiJgu2uEjj9shpH47\n+pE3XmO/z6+S/e3zGaikO9o/eW+Bj3" +
       "/yS+8+IpntqsYjjQovf7Kasily/aP6Az4hCyzbkaLphZAb\nmB4n1isNJiFV" +
       "rD4L5jPKGOajDNUejDL4rNugY06fdgQC+moiqUdEKI0Tgq3GOYqCk1FPUXs2" +
       "14p3\n6JKMK2SvXGJ7HdxrUC2rIw0UuV/m2+VWapo2mgqfEBeEnQaJvG8TDb" +
       "wFIhohLXLJxcIHQ56utN1G\nhUTKlMnimJAVj3NLms7UabrZS7hjhii0+710" +
       "tqInvAxepHtyIT7uF3hzlvP9Yl+NJBtkmpYYRpn0\na0wPjBocN2eZhOyUMl" +
       "RzlhhN5loiT9EVfVYSVZVvM7wwtjmOGouFTqnpLfkaswQcR2glyo24Igl9\n" +
       "zStXsr4yL8bjxZq0VJhYvGnWO/VamkE2AlS8E9dngj7rRMttXaMLXiHVBP2o" +
       "O1JizXptaFGNVJ9k\nkpEBm7Lccq9WhWJM5RpRK8bHlZSUkNsFA07aNd0Hc3" +
       "bZjxdJx1gmK7WRFVsydmJsTkSyUemOBNKs\ntoZCvDCjYaSmcs3Usl4TYBci" +
       "2xMDj+zPBiWWnrhxIAxmZrdWbLEEk4EFrshI0da4FQVZciH1uVrb\nGOJUvz" +
       "jPepPGrOtOImwZUAYx0WrZdMNvaDnJYEmHsacEozAtya/JIrsY4m2CURfD2V" +
       "hnqh2G9j0L\nn7bGzsBNEk2WavNWh5vMe7UIPWWzTq8dH4pqTZ0l5ZSDHMBX" +
       "xkIR4HrLzBDzXstr5qLx4jy/NMw8\nwVFRuVMr9bOClXW5kel3xCbtdKO8W4" +
       "5EyTbds2a9hsPihZIIjFinRC0nfglvpJudKF+gSwNQ9Zew\nUNfLLllkiWov" +
       "XvC0BtTjbKnebzasQocsUzxVbkUovEeMB2Ujlaq38jm8ncnG9ILDEu6cnI/6" +
       "uhqb\nJxfDnOITxLCLK/MpraSX8bLm69yUY6cZqVGfq0yXVGJzk4ooxWy0Y3" +
       "G4LbYyCSpbqLBdVpkN+JS7\nVGclZdTTmvl5eeZobGcxysRmBZ+EqtzqkMOo" +
       "Xybd2mzGzDqyC3Np2oqoJtVlmBapNQZdHI/HagRc\nslKeTDVGplV0HLJtVa" +
       "tiTlnmGlV9Gs+g854sFGIldpTPi81pNG61aL3bIqy4n4ukpHmzYszSFc2i\n" +
       "rcG4MWMhyOVaTI+SaciJGu7QRX5UKXItzh665IRUBtWRR9LpSlTKpbxKtaRl" +
       "cr1RyUvnCpEKTlXo\nBDWHcKmUwND3y4ph9SYKn88XtSzDljpeSyZqPYuPdZ" +
       "L6ENrLuBQFjjVQoRgd21qS0VhQSA6Y1LId\nGdTxaHdhNWAiIzWVZXE4msXY" +
       "RLXrq+UG3YNVR1+SBD2OZ6dDNjnUHQKUkxahUvzcHFcTDFNfGrLn\nRRf8dI" +
       "FHisNUS5iongfpgVJajmulXIGQy0q1xvtu1Ei4UjSvGLKs+gKRM8w5odS6fi" +
       "2ZQjm/IDJ5\nl3WmsT6yptVPNSKKqqoZiSmWc+NouijO8Mq8XZgscmkwno6T" +
       "JTGRHEt1YVisEmkpRzcHxcp8WG1P\nrWFlJLNFodgS2pzleVqM4SoRXBhHU/" +
       "kOPq4NvEU/lYmzSavWb8IKkxc6FEwrWUZCRxSZJ6kClcPz\nHXPQ4YnquG6M" +
       "U4mRyDCs1i8YDa9apKxIp0rKE64jVxmrQU3qeTbu2ZxeTDhF2YfNuCP1pIRf" +
       "blZx\nw8uh1NtsNhITgyjPCLEKU/No3Yt2WTOewZ0eEY8oyWIxWVJrikhyfc" +
       "LTibGUS5BxOZGtRrUkrNaJ\nRKqq6hkio437gFYtIVtOW/2FMXAHy8E8Uyq2" +
       "5zXWnihTqhDJuWrOlCvGQuZkY1Eo0bNCuVXK92Vx\nkvOjXppPmA2StsoiT0" +
       "19vq1AJt0xeSAR/CLlpft8L52MOvKi3Y7Fk5FZ1RDTLjXzUwpHLqw8SMdk\n" +
       "s48aOMLNCo5Z8VXcJQZaU1b6xV6qnJ4NzGLJH2hOVK9kRK+uNhMWmxRsDa8u" +
       "IuNywkmqyYJAValE\nujuoxaZiq7L0ZrqQERqTWjnejBb1Un4GYccV5kI7zT" +
       "SdskTTiug1nOqMb6n4csRavXasHkHZ8lNB\nGh0eVxFPh4XOyaXFungIVmth" +
       "5g2T/cFx6XxaXT93XFmjGuD597tGCPP/54b/dv2z3Nsvbx+X4T2I\nXYWG+Q" +
       "kVLIB6WpFvCmHCW5N1mXpjcO/dcubrrz6qJL/3WM4j4dbiw+2difIXqLk9KY" +
       "kvXNucZ3p4\nvhCOWAA6lt49Vw6/cK4cvnbc+D+qHD4tYM4Vn9jjW5Yn9jMS" +
       "Kl5lAA/CpvvnQhLxnE63nqRT8Jo8\ncp/jjiuU/GRNDKSJ/ThNIui5dKzJpf" +
       "+fJv9ncywgtqWFK21zRd1FBeTCUMQNhYI7mswZ02ytOoh3\nL3YQP7s/dzhb" +
       "mTsGBPdXTe5+IHBfMEwvuL8EZcvQ7l9oXVYN2L7BTx/sv7L/EkA1/v3wPu7w" +
       "PCMi\neIXT+FcfPHj4M/uPurfZR5UUUuhT+/cftfpgfy0AsYebwIliHx5AxB" +
       "Ayhmvo/wcPX+7sv/rAPLX1\nweO8vm5vnnmfD3uyLz4Dgwb1zKcGk6+iturq" +
       "ifLBFcCzF+6MVzebwovvfPr+W+atvwxvVk5uH/fq\n2BXJUdWzLeqZ8Z5pAW" +
       "l1h7a3alhXH/wrMAjgtfmCmS+F7ekvuf8L2zlsF7sWAAA=");
}
