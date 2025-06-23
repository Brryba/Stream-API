package shop;

import lombok.Setter;
import shop.model.Order;
import shop.model.OrderItem;
import shop.model.OrderStatus;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamAPIMetrics {
    @Setter
    private static List<Order> orders;

    public static List<String> getUniqueCities() {
        return orders.stream().map(order -> order.getCustomer().getCity()).distinct().collect(Collectors.toList());
    }

    public static double getTotalIncomeForAllCompletedOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getItems)
                .flatMap(Collection::stream)
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }

    public static OrderItem getMostPopularItem() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static double getAverageCheckForSuccessfullyDeliveredOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getItems)
                .mapToDouble(items -> items.stream()
                        .mapToDouble(OrderItem::getPrice)
                        .sum())
                .average()
                .orElse(0);
    }
}
