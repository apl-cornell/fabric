package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Set;

/**
 * Represents an observable object that can be monitored by {@link Observer}s.
 * After a {@link Subject} changes, the API uses the current set of
 * {@link Observer}s returned by {@link #getObservers()} to compute resulting
 * changes for {@link Observer}s due to the update.
 */
public interface Subject extends fabric.lang.Object {
    /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
    public void addObserver(fabric.metrics.util.Observer o);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    /**
   * @return true iff there are any observers of this subject, currently.
   */
    public boolean isObserved();
    
    /**
   * @return the set of the current observers of this subject.
   */
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Subject {
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.Subject) fetch()).observedBy(arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.Subject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 59, -6, 36, 25, 3,
    -113, 43, 76, -83, -75, -75, 119, -13, -76, -28, 45, -105, 22, -17, -72,
    -87, 69, 38, -14, 125, 2, -29, 38, 81, 27, -72, -123 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1504028847000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcxRWfO19sn+PGH8GGOIkTgmMaSO4aVaoEbinxyUmOHI2xkz+IFZy53Tl7yd7uZnbOPoeYj0ppov5hKHUCSI2LRCoIhCChpqStXFGgbSJSJOgXlfiI1FKgNG1ppFJVbel7M3u36/PlnJDkpH2zN/Nm5vfevK/Zo2fJPJeTFRmaNsyYGHOYG1tP08lUL+Uu0xMmdd0t0DuozY8kD37wpN4eJuEUqdeoZVuGRs1ByxVkQepuOkLjFhPxrX3JrgES1XDiRuoOCxIe6M5zstyxzbEh0xbeJrPWP3BjfPKRuxqfryIN20iDYfULKgwtYVuC5cU2Up9l2TTj7jpdZ/o20mQxpvczblDT2A2MtrWNNLvGkEVFjjO3j7m2OYKMzW7OYVzuWehE+DbA5jlN2BzgNyr4OWGY8ZThiq4Uqc4YzNTdXeReEkmReRmTDgFja6ogRVyuGF+P/cBeZwBMnqEaK0yJ7DQsXZBlpTOKEndsAgaYWpNlYtgubhWxKHSQZgXJpNZQvF9wwxoC1nl2DnYRpO28iwJTrUO1nXSIDQpyTSlfrxoCrqhUC04RpKWUTa4EZ9ZWcmaB0zr7tS9P3GNttMIkBJh1ppmIvxYmtZdM6mMZxpmlMTWx/obUQdo6vT9MCDC3lDArnhf2fHzr6vYXTyqexWV4NqfvZpoY1A6nF7y+JLHqpiqEUevYroGmMENyeaq93khX3gFrby2uiIOxwuCLfT+/8/6n2UdhUpck1Zpt5rJgVU2anXUMk/ENzGKcCqYnSZRZekKOJ0kNvKcMi6nezZmMy0SSREzZVW3L/6CiDCyBKqqBd8PK2IV3h4ph+Z53CCE18JAQPHFCIqehnQ9/BwS5LT5sZ1k8bebYKJh3HB5GuTYcB7/lhhZ3uRbnOUsYwOR1gRVB4yr5+3NSYTFA4VzW1fKIvXE0FAK1LtNsnaWpC2fk2Ut3rwkusdE2dcYHNXNiOkkWTj8mbSaKdu6CrUqthOCcl5RGiODcyVx3z8fHBl9V9oZzPaUJslhBjHkQ1Zl6EAFVPTpSDEJTDELT0VA+lphKPiPtpdqVjlVcqB4WutkxqcjYPJsnoZCU6io5Xy4Kx7wTwgdEiPpV/dtv27F/RRVYqDMawVPLSw9eUvgDE0vkkbHiK/3OoTdf+/CLMooWwkpDIP70M9EVMGVcs0EabZOPYwtnDPjefrT32wfO7huQIIDjunIbdiBNgAlTsF2b7z256/fvvnP41+Ei8CpBqp1c2jQ0QWppGnRCNSFItBjRlGBNn8IvBM//8EEZsQNbCFYJz0WWF33EcUrVsfR8wUQGwsNfn5zSN39vrXL55pkO2mPlss/+9r+nY4+eOVXGAKLCdtaYbISZgT0XwJbXzspqt8tYm4TgTyEiDWpnPlp6U2Lne0Nq22UlEEu5j9x+9NSGTu3hMKnygl6ZAD9zUlcQLOQJziA/WSg29tTBpitKzZ7bGtMhifn73rCcHh+cHu8IY4KIQu4SFKILJIL20s1nBNuugoXhVvNSZD7aNTVxqJBt6sQwt0f9HunOC9SBgxKjeHgr4WkhJLzHa3fg6EIH6VXK/SX/MklXIFkpTyCMr51Irpdsq+BEOn0jhvhpgoeCjbsdW62srRsZg6ZNhu71n4aVa4//ZaJRHbYJPQodJ6vnXsDvX9RN7n/1rk/a5TIhDfO372g+m0oKC/2V13FOxxBH/oE3lj72C3oIwgWEdNfYzWSUDkn5QlLgFkGWlAtAm9Mu4yOMI0+blP+rkv9mSW9BxXq+gf8TSL4kyHyq64WZ7uxc2suNLISFES+Xsv2T3/w0NjGp1KQKjutm5fzgHFV0yC0/J88EPfPaSrvIGevff278x0+N7wt7cNcIEhmxDVW0rJ1pLp+HZymElS94bdNnNpeZ6vKUjn9vlQy9FfTZh2QTFKacZe0RFjyMDeVAXw9PB4B9y2t/cqVA31kB9ACSLYLU2Qqu3j0m+Xq8k8JmoyA1ads2GbXKyQEKJ6ugdOjz2lsukxxBmKzCmEymO0AEw/V0rmPP9nJYAR+JAcZTXnvkCmC1KozJnQxI+0NMFH2u4NMNnk+rYoLJ+NMWlEJmv/LpsNUv3VWdGpO3IcepJFWVLxUkZLjGgP/NKd49FcbGFVokuXxBrEYZ5BBaTEGTA4sgh2LtY9pwoctXwicgoxgWlcX+mIKHZDeSPUjuhSIC9CmTe1GX/qZ+/6LSegs7v4FkXxD4hWksAKeCRh6sMPat0k3vm8v4AuJPIHkIycMg/jDcehNQCZfz3SqoqvD1AJKDFyjoRQSZ+yTDoQqCfvcCBZXLdfoyfgfJFJLHwT7ZrhxV1dZ2JE8EnPySxQmifarC2NOfUZInkchg8wyclrDV9bqMhwQGylrrMSTPXSlrfaHC2A8vUHR/070lJvsDJCeQ/AjDjS2MzBj+24BkWr5dKcFeqjD2yiUL9lMkLyP5GUQ1Jdg60yzKdvKyyxasBkvcPWLa1pCc9MsKk8Dq2n2rS+K9i+ccuET15DXmFG6BD8h13rhkBZ1G8jqS3wDAUWqIom7evBjdzOXGYZ/rNSQHJdfb549dv5IM7150IN7ry/YWkneQnPHlueySBQ32/bnk+fBS5PkTkg+Q/LmcPHkoBr0PHXiDWVzm+4v3LVBLvMIOv7dpdct5vr1cM+vrrDfv2FRD7dVTW38nr57F73zRFKnN5EwzcLMN3nKrHc4yhhQjqm6UqvT5uyALy9yVwAyxkRr5q+I8B6X7TE4hP5TiW5DvnxC8FB/++0SWdG0+KbhXs7dWmcJnZliXi7blOH60Pnru6n9V1245Iz+VwAkt7/p3x6KqB29MPXv8+Oi57/9xzSOtfztxpKfzH+PhP3TesfjE3v8DdVol4EwXAAA=";
}
