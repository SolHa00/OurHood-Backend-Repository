package server.photo.global.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
}
