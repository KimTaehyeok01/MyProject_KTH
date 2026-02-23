package com.study.Ex07Thymeleaf;

// javax -> jakarta 오픈소스로 바뀌면서 이름이 바뀜.
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 톰캣WAS서버 : 서블릿을 생성/관리/유지한다.
// Servlet : 자바를 사용하여 웹페이지를 동적으로 생성하는 서버측 프로그램
//         : 생명주기(interface Servlet)
//         : 서블릿 객체 생성(init()), 요청처리(service()), 소멸 destroy() 함수가 호출됨.
// HttpsServlet : 자바 서블릿 API에서 제공하는 추상화클래스로서 특화된 기능들을 제공한다.

// 내장 톰캣에 서블릿으로 등록해보자.
// @WebServlet을 사용하려면 메인 자바 코드에서 @ServletComponentScan 어노테이션을 선언해야함
@WebServlet(name = "exampleServlet", urlPatterns = "/example")
public class ExampleServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service()함수 호출됨.");
        super.service(req,res);
        // GET/POST/PUT/DELETE
//        System.out.println("GET/POST/PUT/DELETE 요청처리");
//        HttpServletRequest request = (HttpServletRequest)req; // 다운캐스팅
//        String method = request.getMethod();
//        if("GET".equalsIgnoreCase(method)) {
//        }
//        else if("POST".equalsIgnoreCase(method)){
//        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet()함수 호출됨");

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println("<h2>GET요청을 처리했습니다.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost()함수 호출됨");

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println("<h2>POST요청을 처리했습니다.");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("서블릿 생성");
    }

    @Override
    public void destroy() {
        System.out.println("서블릿 소멸");
    }
}
