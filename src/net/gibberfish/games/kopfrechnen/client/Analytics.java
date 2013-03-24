package net.gibberfish.games.kopfrechnen.client;

public class Analytics {
	
	public static native void gaPageTracker(String pageName)
    /*-{
        $wnd.pageTracker._trackPageview(pageName);
    }-*/;

	public static native void gaEventTracker(String category, String action, String opt_label)
    /*-{
        $wnd.pageTracker._trackEvent(category, action, opt_label, 1, true);
    }-*/;
	
}
