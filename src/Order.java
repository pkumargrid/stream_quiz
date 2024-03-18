import java.util.List;

public record Order(int orderId, int customerId, List<Item> orderItems) {
}
