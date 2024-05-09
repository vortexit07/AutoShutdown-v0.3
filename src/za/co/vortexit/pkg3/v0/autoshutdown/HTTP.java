package za.co.vortexit.pkg3.v0.autoshutdown;

/**
 *
 * @author Connor Lewis
 */
public class HTTP {

    public static final String $200 = "OK";
    public static final String $400 = "Bad Request";
    public static final String $403 = "Not Authenticated (Token Invalid / Disabled)";
    public static final String $404 = "Not Found";
    public static final String $408 = "Request Timeout";
    public static final String $429 = "Too Many Requests";
    public static final String $5xx = "Server side issue";

    public static String get(int code) {
        
        int firstDigit = Integer.parseInt(Integer.toString(code).substring(0, 1));
        
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
                if(firstDigit == 5){
                    return $5xx;
                } else return null;
        }

    }

    public static String get(String code) {
        return $5xx;
    }

}
