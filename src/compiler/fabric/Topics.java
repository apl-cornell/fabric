package fabric;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import polyglot.main.Report;

/**
 * Extension information for ../../fabric extension.
 */
@SuppressWarnings("unchecked")
public class Topics {
    public static final String fabric = "fabric";
    public static final String mobile = "mobile";
    static {
        Report.topics.add(fabric);
        Report.topics.add(mobile);
    }
}
