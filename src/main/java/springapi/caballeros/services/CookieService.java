package springapi.caballeros.services;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public static void setCookie(HttpServletResponse response, String key, String jwt) {
        Cookie cookie = new Cookie(key, jwt);
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        return Optional.ofNullable(request.getCookies()).flatMap(cookies -> Arrays.stream(cookies)
                .filter(cookie -> key.equals(cookie.getName())).findAny()).map(e -> e.getValue()).orElse(null);
    }
}
