package servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.GoodServiceImpl;
import service.OrderGoodServiceImpl;
import service.OrderServiceImpl;
import service.UserServiceImpl;
import shop.model.bean.Good;
import shop.model.bean.Order;
import shop.model.bean.OrderGood;
import shop.model.bean.User;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The class is a Java Servlet that handles the logic of an online shop. It provides functionality
 * for adding items to a shopping cart, processing orders, and managing user sessions.
 *
 * @author Anna Astaptsova
 * @version 1.0
 */
@WebServlet(asyncSupported = false, name = "ShopServlet", urlPatterns = { "/hello" })
public class ShopServlet extends HttpServlet {

 RequestDispatcher dispatcher = null;
 GoodServiceImpl goodService = new GoodServiceImpl();
 OrderServiceImpl orderService = new OrderServiceImpl();
 UserServiceImpl userService = new UserServiceImpl();
 OrderGoodServiceImpl orderGoodService = new OrderGoodServiceImpl();

    /**
     * Handles the HTTP GET requests to the servlet.
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if the request for the GET could not be handled
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String submit = request.getParameter("submitForm");
        if (submit == null){      //удалить
            submit = "browse";
            System.out.println("browse");
        }
        if (submit.equalsIgnoreCase("addToCart")) {
            addItem(request, response);

            request.getRequestDispatcher("page3.jsp")
                    .forward(request, response);
                    }
        if (submit.equalsIgnoreCase("Submit")) {
            request.getRequestDispatcher("page4.jsp")
                    .forward(request, response);
        }
    }

    /**
     * Handles the HTTP POST requests to the servlet.
     *
     * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String name = (String) request.getParameter("name");
        session.setAttribute("name", name);
        if (request.getParameter("agree")!=null){
            request.getRequestDispatcher("page2.jsp")
                    .forward(request,response);}
        else {
            request.getRequestDispatcher("pageOops.jsp")
                    .forward(request,response);}
    }


    /**
     * Adds an item to the user's shopping cart.
     *
     * @param req  the HttpServletRequest object that contains the request the client has made of the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the addItem method could not be handled
     * @throws IOException      if an input or output error occurs
     */
    void addItem(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<String>());
        }
        Integer shopCart= (Integer) session.getAttribute("shopCart");
        if (shopCart == null) {
            shopCart=0;  }
        Integer keyyyy = Integer.parseInt(req.getParameter("item"));
        Integer priceItem=goodService.findPriceByKey(keyyyy);
        Long idItem=goodService.findIdByKey(keyyyy);
        User user = userService.findByLogin((String) session.getAttribute("name"));
        Good e=goodService.findIdBy(idItem);
        orderService.addOrder(user, priceItem);
        Order order_new = orderService.findOrderByIdUser(user.getId());
        orderGoodService.addOrderGood(e,user, order_new, keyyyy);
        Map<Integer, OrderGood> goodDaoMap = orderGoodService.findOrderedGoods(order_new);
        session.setAttribute("list2", goodDaoMap);
        session.setAttribute("list4", order_new);
    }
}

