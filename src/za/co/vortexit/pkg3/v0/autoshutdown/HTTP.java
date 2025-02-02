package za.co.vortexit.pkg3.v0.autoshutdown;

/**
 * Provides HTTP status code constants and methods for handling HTTP status codes. More quality of (my) life than useful tbh
 * 
 * @author Connor Lewis
 */
public class HTTP {

    /**
     * HTTP status code constant for OK (200).
     */
    public static final String $200 = "OK";

    /**
     * HTTP status code constant for Bad Request (400).
     */
    public static final String $400 = "Bad Request";

    /**
     * HTTP status code constant for Not Authenticated (Token Invalid / Disabled) (403).
     */
    public static final String $403 = "Not Authenticated (Token Invalid / Disabled)";

    /**
     * HTTP status code constant for Not Found (404).
     */
    public static final String $404 = "Not Found";

    /**
     * HTTP status code constant for Request Timeout (408).
     */
    public static final String $408 = "Request Timeout";

    /**
     * HTTP status code constant for Too Many Requests (429).
     */
    public static final String $429 = "Too Many Requests";

    /**
     * HTTP status code constant for Server side issue (5xx).
     */
    public static final String $5xx = "Server side issue";

    /**
     * Gets the corresponding description for the provided HTTP status code.
     * 
     * @param code the HTTP status code.
     * @return the description of the HTTP status code.
     */
    public static String get(int code) {
        switch (code) {
            case 200:
                return $200;
            case 400:
                return $400;
            case 403:
                return $403;
            case 404:
                return $404;
            case 408:
                return $408;
            case 429:
                return $429;
            default:
                if (Integer.parseInt(Integer.toString(code).substring(0, 1)) == 5) {
                    return $5xx;
                } else
                    return null;
        }
    }

    /**
     * Gets the default description for the server-side issue (5xx) HTTP status codes.
     * 
     * @param code the HTTP status code.
     * @return the default description for server-side issue (5xx) HTTP status codes.
     */
    public static String get(String code) {
        return $5xx;
    }
}