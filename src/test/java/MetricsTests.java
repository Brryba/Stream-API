import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.StreamAPIMetrics;
import shop.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MetricsTests {
    @BeforeAll
    public static void setUp() {
        setUpCustomers();
        setOrders();
        StreamAPIMetrics.setOrders(orders);
    }

    private static void setOrders() {
        orders.add(new Order("0",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(new OrderItem("laptop", 1, 10, Category.ELECTRONICS),
                        new OrderItem("shirt", 2, 50, Category.CLOTHING)),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("1",
                LocalDateTime.now().minusDays(100),
                customers.get(1),
                List.of(new OrderItem("shirt", 1, 50, Category.CLOTHING)),
                OrderStatus.SHIPPED
        ));
        orders.add(new Order("2",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(new OrderItem("shirt", 1, 50, Category.CLOTHING),
                        new OrderItem("mirror", 1, 10, Category.BEAUTY),
                        new OrderItem("sofa", 5, 15, Category.HOME)),
                OrderStatus.NEW
        ));
        orders.add(new Order("3",
                LocalDateTime.now().minusDays(100),
                customers.get(2),
                List.of(new OrderItem("sofa", 2, 15, Category.HOME)),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("4",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(new OrderItem("phone", 3, 20, Category.ELECTRONICS),
                        new OrderItem("laptop", 1, 10, Category.ELECTRONICS)),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("5",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(new OrderItem("laptop", 1, 10, Category.ELECTRONICS),
                        new OrderItem("shirt", 1, 50, Category.CLOTHING),
                        new OrderItem("sofa", 2, 15, Category.HOME)),
                OrderStatus.CANCELLED
        ));
        orders.add(new Order("6",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(new OrderItem("laptop", 1, 10, Category.ELECTRONICS),
                        new OrderItem("shirt", 1, 50, Category.CLOTHING),
                        new OrderItem("sofa", 1, 15, Category.HOME)),
                OrderStatus.CANCELLED
        ));
        orders.add(new Order("7",
                LocalDateTime.now().minusDays(150),
                customers.getFirst(),
                List.of(new OrderItem("phone", 2, 20, Category.ELECTRONICS),
                        new OrderItem("shirt", 1, 50, Category.CLOTHING),
                        new OrderItem("sofa", 3, 15, Category.HOME)),
                OrderStatus.SHIPPED
        ));
    }

    private static void setUpCustomers() {
        customers.add(new Customer("0", "Yury", "abc@gmail.com", LocalDateTime.now().minusDays(125), 19, "Narach"));
        customers.add(new Customer("1", "Egor", "abcd@gmail.com", LocalDateTime.now().minusDays(115), 18, "Minsk"));
        customers.add(new Customer("2", "Alexey", "12345@mail.ru", LocalDateTime.now().minusDays(25), 19, "Minsk"));
    }

    private static final List<Order> orders = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<OrderItem> items = new ArrayList<>();

    @Test
    public void testUniqueCitiesGetter() {
        assertEquals(Set.of("Narach", "Minsk"), new HashSet<>(StreamAPIMetrics.getUniqueCities()));
    }

    @Test
    public void testTotalIncomeForAllCompletedOrders() {
        assertEquals(210, StreamAPIMetrics.getTotalIncomeForAllCompletedOrders());
    }

    @Test
    public void testFindingMostPopularProduct() {
        assertEquals("sofa", StreamAPIMetrics.getMostPopularItem().getProductName());
    }

    @Test
    public void testAverageIncomeForAllSuccessfullyCompletedOrders() {
        assertEquals(70, StreamAPIMetrics.getAverageCheckForSuccessfullyDeliveredOrders());
    }

    @Test
    public void testGettingCustomersWithMoreThan5Orders() {
        Assertions.assertIterableEquals(List.of(customers.getFirst()),
                StreamAPIMetrics.getCustomersWithMoreThan5CompletedOrders());
    }
}
