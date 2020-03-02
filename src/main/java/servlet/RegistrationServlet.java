package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        resp.getWriter().println(PageGenerator.getInstance().getPage("registerPage.html", pageVariables));
        //System.out.println(pageVariables.get("email") + " Это МОЙ емейл!");
        resp.setContentType("text/html; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("старт допост! ИЗ регсервлет");
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        User user = new User((String)pageVariables.get("email"), (String)pageVariables.get("password"));
        UserService userService = UserService.getInstance();
        userService.addUser(user);
        resp.setContentType("text/html; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        pageVariables.put("email", request.getParameter("email"));
        pageVariables.put("password", request.getParameter("password"));
//        String tmp0 = request.getParameter("value");  //example
        return pageVariables;
    }
}
