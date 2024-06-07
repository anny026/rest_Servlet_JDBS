package servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.model.bean.Good;
import service.GoodServiceImpl;
import service.UserServiceImpl;


import java.io.IOException;
import java.util.Map;
/**
 * The UserController class is a servlet that handles user registration and login functionality.
 *  * @author Anna Astaptsova
 *  * @version 1.0
 */

@WebServlet(urlPatterns = "/user")
public class UserController extends HttpServlet {

    RequestDispatcher dispatcher = null; //????***
    GoodServiceImpl goodService = new GoodServiceImpl();
    UserServiceImpl userService= new UserServiceImpl();

    /**
     * Handles the HTTP POST request for user registration.
     *
     * @param req the HttpServletRequest object containing the request parameters
     * @param resp the HttpServletResponse object used to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String password1= req.getParameter("password");
        String name1= req.getParameter("name");
        session.setAttribute("password", req.getParameter("password"));
        session.setAttribute("name", req.getParameter("name"));
        if (req.getParameter("agree")!=null){
            Map<Integer, Good> goods = goodService.findAllGoods();
            System.out.println(goods);
            session.setAttribute("list", goods);//**
            userService.registration(name1, password1);
            req.getRequestDispatcher("page2.jsp")
                    .forward(req,resp);
        } else {
            req.getRequestDispatcher("pageOops.jsp")
                    .forward(req,resp);}
    }
    }
