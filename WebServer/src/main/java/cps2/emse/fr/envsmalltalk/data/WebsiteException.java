package cps2.emse.fr.envsmalltalk.data;

public class WebsiteException extends RuntimeException {
    private static String getMessageOf(WebsiteScraper s) {
        return "Can't scrap with " + s.getName() + "(" + s.getUid() + ")";
    }

    private static String getMessageOf(WebsiteScraper s, String msg) {
        return getMessageOf(s) + ": " + msg;
    }

    public WebsiteException(WebsiteScraper s) {
        super(getMessageOf(s));
    }

    public WebsiteException(WebsiteScraper s, Throwable e) {
        super(getMessageOf(s), e);
    }

    public WebsiteException(WebsiteScraper s, String msg, Throwable e) {
        super(getMessageOf(s, msg), e);
    }

    public WebsiteException(WebsiteScraper s, String msg) {
        super(getMessageOf(s, msg));
    }

}
