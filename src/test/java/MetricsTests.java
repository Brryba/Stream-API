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
        setUpItems();
        setOrders();
        StreamAPIMetrics.setOrders(orders);
    }

    private static void setOrders() {
        orders.add(new Order("0",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(items.getFirst(), items.get(2)),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("1",
                LocalDateTime.now().minusDays(100),
                customers.get(1),
                List.of(items.get(2)),
                OrderStatus.SHIPPED
        ));
        orders.add(new Order("2",
                LocalDateTime.now().minusDays(100),
                customers.getFirst(),
                List.of(items.get(2), items.get(3), items.get(4)),
                OrderStatus.NEW
        ));
        orders.add(new Order("3",
                LocalDateTime.now().minusDays(100),
                customers.get(2),
                List.of(items.getLast()),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("4",
                LocalDateTime.now().minusDays(100),
                customers.get(1),
                new ArrayList<>(items),
                OrderStatus.DELIVERED
        ));
        orders.add(new Order("5",
                LocalDateTime.now().minusDays(100),
                customers.get(2),
                List.of(items.get(0), items.get(2), items.get(4)),
                OrderStatus.CANCELLED
        ));
    }

    private static void setUpItems() {
        items.add(new OrderItem("laptop", 20, 10, Category.ELECTRONICS));
        items.add(new OrderItem("phone", 30, 20, Category.ELECTRONICS));
        items.add(new OrderItem("shirt", 10, 52, Category.CLOTHING));
        items.add(new OrderItem("mirror", 1, 10, Category.BEAUTY));
        items.add(new OrderItem("sofa", 912, 15, Category.HOME));
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
        Assertions.assertIterableEquals(Set.of("Narach", "Minsk"), new HashSet<>(StreamAPIMetrics.getUniqueCities()));
    }

    @Test
    public void testTotalIncomeForAllCompletedOrders() {
        assertEquals(184, StreamAPIMetrics.getTotalIncomeForAllCompletedOrders());
    }
}
