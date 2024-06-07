package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import service.GoodServiceImpl;
import service.UserServiceImpl;
import shop.model.bean.Good;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private GoodServiceImpl goodService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    RequestDispatcher dispatcher;
    @InjectMocks
    UserController userController;


    @Test
    void testWhenDoPost_GivenAgreement_ThenPage2() throws Exception {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("password")).thenReturn("passw");
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("agree")).thenReturn("true");
        Map<Integer, Good> goods = new HashMap<>();
        goods.put(1, new Good());
        when(goodService.findAllGoods()).thenReturn(goods);
        when(request.getRequestDispatcher("page2.jsp")).thenReturn(dispatcher);

        userController.doPost(request, response);

        verify(session).setAttribute("password", "passw");
        verify(session).setAttribute("name", "Bob");
        verify(session).setAttribute("list", goods);
        verify(userService).registration("Bob", "passw");
        verify(dispatcher, times(1)).forward(request, response);
    }


    @Test
    void testWhenDoPost_GivenWithoutAgreement_ThenPageOops() throws Exception {

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agree")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("passw");
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getRequestDispatcher("pageOops.jsp")).thenReturn(dispatcher);

        userController.doPost(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }



}