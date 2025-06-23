package shop;

import lombok.Setter;
import shop.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class StreamAPIMetrics {
    @Setter
    private static List<Order> orders;

    public static List<String> getUniqueCities() {
        return orders.stream().map(order -> order.getCustomer().getCity()).distinct().collect(Collectors.toList());
    }
}
