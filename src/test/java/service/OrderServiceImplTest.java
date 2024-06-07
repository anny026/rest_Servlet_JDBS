package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.model.bean.Order;
import shop.model.bean.User;
import shop.model.repository.OrderDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    User user;
    @Mock
    Order order;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testWhenAddOrder_NewOrder_ThenSave() {
        when(orderDao.get(user.getId())).thenReturn(order);
        when(order.getTotalPrice()).thenReturn(0f);

        orderService.addOrder(user, 5);

        Mockito.verify(orderDao, Mockito.times(1)).save(user, 5);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    void testWhenAddOrder_ExistingOrder_ThenUpdate() {

        when(orderDao.get(user.getId())).thenReturn(order);
        when(order.getTotalPrice()).thenReturn(10f);

        orderService.addOrder(user, 5);

        Mockito.verify(orderDao, Mockito.never()).save(user, 5);
        verify(orderDao).update(user, 5);
        verifyNoMoreInteractions(orderDao);
    }
}