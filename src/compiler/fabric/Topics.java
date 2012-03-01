package fabric;

import polyglot.main.Report;

/**
 * Extension information for ../../fabric extension.
 */
@SuppressWarnings("unchecked")
public class Topics {
    public static final String fabric = "fabric";
    public static final String mobile = "mobile";
    public static final String profile = "profile";
    static {
        Report.topics.add(fabric);
        Report.topics.add(mobile);
        Report.topics.add(profile);
    }
}
