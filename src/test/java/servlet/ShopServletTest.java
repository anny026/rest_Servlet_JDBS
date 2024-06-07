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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.GoodServiceImpl;
import service.OrderGoodServiceImpl;
import service.OrderServiceImpl;
import service.UserServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher dispatcher;

    @InjectMocks
    ShopServlet shopServlet;

    private final static String path = "page4.jsp";

    @Test
    public void whenDoGet_GivenSubmit_ThenPage4() throws ServletException, IOException {

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getParameter("submitForm")).thenReturn("Submit");

        shopServlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenDoGet_GivenAddToCart_ThenPage3() throws ServletException, IOException {
        ShopServlet spyServlet = spy(shopServlet);
        when(request.getParameter("submitForm")).thenReturn("addToCart");
        when(request.getRequestDispatcher("page3.jsp")).thenReturn(dispatcher);
        doNothing().when(spyServlet).addItem(any(HttpServletRequest.class), any(HttpServletResponse.class));

        spyServlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("page3.jsp");
        verify(dispatcher, times(1)).forward(request, response);
    }


    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("name")).thenReturn("Bob");
        when(request.getParameter("agree")).thenReturn("true");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("page2.jsp")).thenReturn(dispatcher);

        shopServlet.doPost(request, response);

        verify(session, times(1)).setAttribute("name", "Bob");
        verify(request, times(1)).getRequestDispatcher("page2.jsp");
        verify(dispatcher, times(1)).forward(request, response);
    }
}