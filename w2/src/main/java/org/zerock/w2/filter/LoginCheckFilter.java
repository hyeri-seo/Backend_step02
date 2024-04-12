package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Login Check Filter ...........");

        // 로그인 정보가 세션에 존재하는지 여부를 판단
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();

        // 로그인 인포가 없는 경우 요청을 보냄
        if(session.getAttribute("loginInfo") == null) {
            filterChain.doFilter(req, resp);
            return;
        }

        // session에 loginInfo 값이 없지만, remember-me를 보냈는지 찾음
        // 쿠키를 체크
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // remember-me가 세션에도 없고 쿠키도 없다면 그냥 로그인하라고 /login으로 보냄
        if(cookie == null) {
            resp.sendRedirect("/login");
            return;
        }

        // 쿠키가 존재하는 상황이라면
        log.info("cookie는 존재하는 상황");
        // remember-me의 uuid 값 추출
        String uuid = cookie.getValue();

        try {
            // 데이터베이스 확인
            // 브라우저의 remember-me 쿠키 값과 MariaDB의 uuid 값이 일치하는지
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조히한 사용자 정보: " + memberDTO);
            
            // remember-me의 정보가 MariaDB의 uuid와 일치하지 않을 때
            if(memberDTO == null) {
                throw new Exception("Cookie value is not valid");
            }
            // 쿠키값과 DB 값이 일치하면, 회원 정보(loginInfo)를 세션에 추가
            // 서블릿 전송
            session.setAttribute("loginInfo", memberDTO);
            filterChain.doFilter(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name) {

        if(cookies == null || cookies.length == 0) {
            return  null;
        }

        // Cookie 객체를 얻는데, 추가로 Optional에서 제공하는 메서드를 사용할 수 있음
        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(name))
                .findFirst();

        // result가 정상이면 쿠키 객체를 리턴하고, 아니면 null 값을 리턴함
        return result.isPresent() ? result.get() : null;
    }
}
