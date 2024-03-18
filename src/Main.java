import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static List<Product> generateProduct() {
        Product product1 = new Product(1, "Remote",100,"Electronics");
        Product product2 = new Product(2, "laptop",100,"Electronics");
        Product product3 = new Product(3, "Pencil",100,"Stationary");
        Product product4 = new Product(4, "Eraser",100,"Stationery");
        Product product5 = new Product(5, "Bulb",100,"Electrical");
        return List.of(product1, product2, product3, product4, product5);
    }

    public static List<Order> generateOrder() {
        List<Item> dummy = List.of(new Item(1,"pen",2,120),
                new Item(2,"eraser",5,150), new Item(3,"sharpener",5,200),
                new Item(4,"item4",5,300));
        Order order1 = new Order(1, 2, new ArrayList<Item>(dummy));
        dummy = List.of(new Item(1,"item1",2,200),
                new Item(2,"item2",5,120), new Item(3,"item3",5,120),
                new Item(4,"item4",5,175));
        Order order2 = new Order(2, 2, new ArrayList<>(dummy));
        dummy = List.of(new Item(1,"item1",2,200),
                new Item(2,"item2",5,120), new Item(3,"item3",5,120),
                new Item(4,"item4",5,175));
        Order order3 = new Order(3, 4, new ArrayList<>(dummy));
        dummy = List.of(new Item(1,"item1",2,200),
                new Item(2,"item2",5,120), new Item(3,"item3",5,120),
                new Item(4,"item4",5,175));
        Order order4 = new Order(4, 4, new ArrayList<>(dummy));
        dummy = List.of(new Item(1,"item1",2,250),
                new Item(2,"item2",5,95), new Item(3,"item3",5,300),
                new Item(4,"item4",5,200));
        Order order5 = new Order(5, 5, new ArrayList<>(dummy));
        return List.of(order1, order2, order3, order4, order5);
    }

    public static void filterProductWithPrice(List<Product> products, double threshold) {
        products.stream().filter(x -> x.getPrice() < threshold).forEach(System.out::println);
    }

    public static void groupByCategory(List<Product> products) {
        Map<String, List<Product>> map = products.stream().collect(Collectors.groupingBy(Product::getCategory));
        System.out.println(map);
    }

    public static void findMaxInEachGroup(List<Product> products) {
        Map<String, Optional<Product>> map = products.stream().collect(Collectors.groupingBy(Product::getCategory,
                Collectors.maxBy(Comparator.comparing(Product::getPrice))));
        System.out.println(map);
    }

    public static void totalPrice(List<Product> products) {
        System.out.println(products.stream().map(Product::getPrice).reduce(0.0,Double::sum));
    }

    public static void sort(List<Product> products) {
        products.stream().sorted((o1,o2) -> o2.getName().compareTo(o1.getName())).forEach(System.out::println);
    }

    public static void filterByTotalOrderValue(List<Order> orders, double minTotal) {
        orders.stream().filter(x -> x.orderItems().stream().map(Item::price).reduce(0.0,Double::sum) > minTotal).forEach(System.out::println);
    }

    public static void groupByCustomerId(List<Order> orders, double minTotal) {
        Map<Integer, List<Order>> map = orders.stream().filter(x -> x.orderItems().stream().map(Item::price).reduce(0.0,Double::sum) > minTotal).collect(Collectors.groupingBy(Order::customerId));
        System.out.println(map);
    }

    public static void findHighestOrderWithinGroup(List<Order> orders, double minTotal) {
        Map<Integer, Optional<Order>> map = orders.stream().filter(x -> x.orderItems().stream().map(Item::price).reduce(0.0,Double::sum) > minTotal).collect(Collectors.groupingBy(Order::customerId, Collectors.maxBy(Comparator.comparingDouble(o -> o.orderItems().stream().map(Item::price).reduce(0.0, Double::sum)))));
        System.out.println(map);
    }

    public static void totalOrderValueForEachCustomer(List<Order> orders, double minTotal) {
        Map<Integer, Double> map = orders.stream().
                filter(x -> x.orderItems().stream().map(Item::price).reduce(0.0,Double::sum) > minTotal).
                collect(Collectors.groupingBy(Order::customerId,Collectors.summingDouble(x -> x.orderItems().stream().mapToDouble(Item::price).reduce(0.0, Double::sum))));
        System.out.println(map);
    }

    public static void sortByOrderValue(List<Order> orders) {
        orders.stream().sorted(Comparator.comparingDouble(o -> o.orderItems().stream().map(Item::price).reduce(0.0, Double::sum))).forEach(System.out::println);
    }

    public static void main(String[] args) {
//        findHighestOrderWithinGroup(generateOrder(), 300);
//        sortByOrderValue(generateOrder());
//            groupByCustomerId(generateOrder(), 300);
//        totalOrderValueForEachCustomer(generateOrder(), 300);
    }
}