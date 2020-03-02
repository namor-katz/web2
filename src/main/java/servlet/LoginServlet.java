package servlet;

import model.User;
import org.eclipse.jetty.server.Authentication;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();
    private PageGenerator pageGenerator = PageGenerator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //принять запрос пОСТ, распарсить, создать юзера, если есть такой - авторизовать.
        System.out.println("АААА меня вызвали!!!! Я из логин сервлет! str 25");
        String UserMail = req.getParameter("email");
        String UserPassword = req.getParameter("password");

        User user = new User(0L, UserMail, UserPassword);
        if (userService.isExistsThisUser(user)) {
            System.out.println("да, такой юзер есть " + user.getEmail());
            userService.authUser(user);
            System.out.println("в логин_сервлет.доПост авторизован новый пользователь " + user.getEmail());
        }
        else {
            System.out.println(UserMail + " это юзер мейл неудачника!");
            System.out.println("ты кто? иди нахуй!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("КТО_ТО ЗАПРОМИЛ ДУГЕТ из логин сервлета");
        Map<String, Object> pageVariables = createPageVariablesMap(req);
        resp.getWriter().println(pageGenerator.getPage("authPage.html", pageVariables));
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
        //pageVariables.put("email", request.getParameter("email"));
        //pageVariables.put("password", request.getParameter("password"));

        return pageVariables;
    }
}
