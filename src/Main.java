import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /**
     *
     * @return dummy list of product
     */
    public static List<Product> generateProduct() {

        Product product1 = new Product(1, "Remote",120,"Electronics");
        Product product2 = new Product(2, "laptop",90,"Electronics");
        Product product3 = new Product(3, "Pencil",130,"Stationary");
        Product product4 = new Product(4, "Eraser",200,"Stationary");
        Product product5 = new Product(5, "Bulb",30,"Electrical");

        return List.of(product1, product2, product3, product4, product5);
    }

    /**
     *
     * @return dummy list of orders
     */

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
    //queries for products start from here
    //We have a list of product objects, each containing attributes like name, price and category.

    /**
     *
     * queries the product on the basis of threshold
     *
     * @param products dummy set of products
     * @param threshold to filter with
     */

    public static void filterProductWithPrice(List<Product> products, double threshold) {
        products.stream().filter(product -> product.getPrice() > threshold).forEach(System.out::println);
    }

    /**
     *
     * grouping the filtered products on the basis of product category
     *
     * @param products dummy set of products
     * @param threshold to filter with
     */

    public static void groupByCategory(List<Product> products, double threshold) {
        Map<String, List<Product>> map = products.stream().filter(product -> product.getPrice() > threshold).collect(Collectors.groupingBy(Product::getCategory));
        System.out.println(map);
    }

    /**
     * finding the max product for each category
     *
     * @param products dummy set of products
      * @param threshold to filter with
     */

    public static void findMaxInEachGroup(List<Product> products, double threshold) {
        Map<String, Optional<Product>> map = products.stream().filter(product -> product.getPrice() > threshold).collect(Collectors.groupingBy(Product::getCategory,
                Collectors.maxBy(Comparator.comparing(Product::getPrice))));
        System.out.println(map);
    }

    /**
     * find the total price of the inventory
     * @param products dummy list of values
     */

    public static void totalPrice(List<Product> products) {
        System.out.println(products.stream().map(Product::getPrice).reduce(0.0,Double::sum));
    }

    /**
     * sorts the inventory of products in descending order on the basis of name of product
     * @param products dummy list of values
     */

    public static void sortProductByName(List<Product> products) {
        products.stream().sorted((product1,product2) -> product2.getName().compareTo(product1.getName())).forEach(System.out::println);
    }

    // from here order queries start
    // We have a list of Order objects and we have to perform the operations accordingly

    /**
     * filters the order on the basis of order value
     * here order value is the sum of item.price*item.product for list of items in order
     * @param orders dummy list
     * @param minTotal on the basis of which we will filter the order
     */

    public static void filterByTotalOrderValue(List<Order> orders, double minTotal) {
        orders.stream().filter(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).reduce(0.0,Double::sum) > minTotal).forEach(System.out::println);
    }

    /**
     * grouping the filtered orders on the basis of customerId
     * @param orders dummy list of values
     * @param minTotal on the basis of which we want to filter
     */

    public static void groupByCustomerId(List<Order> orders, double minTotal) {
        Map<Integer, List<Order>> map = orders.stream().
                filter(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).reduce(0.0,Double::sum) > minTotal).
                collect(Collectors.groupingBy(Order::customerId));
        System.out.println(map);
    }

    /**
     * finding the highest order made by a customer
     * @param orders dummy list of order
     * @param minTotal on the basis of which we are going to filter the orders
     */

    public static void findHighestOrderWithinGroup(List<Order> orders, double minTotal) {
        Map<Integer, Optional<Order>> map = orders.stream().
                filter(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).
                        reduce(0.0,Double::sum) > minTotal).
                collect(
                        Collectors.groupingBy(Order::customerId,
                                Collectors.maxBy(
                                        Comparator.comparingDouble(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).reduce(0.0, Double::sum)))));
        System.out.println(map);
    }

    /**
     * finding the total order value for list of orders made up by the customer.
     * @param orders dummy list of order
     * @param minTotal on the basis of which we are going to filter the orders
     */

    public static void totalOrderValueForEachCustomer(List<Order> orders, double minTotal) {
        Map<Integer, Double> map = orders.stream().
                filter(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).reduce(0.0,Double::sum) > minTotal).
                collect(
                        Collectors.groupingBy(Order::customerId,
                                Collectors.summingDouble(order -> order.orderItems().stream().mapToDouble(item -> item.quantity()*item.price()).reduce(0.0, Double::sum))));
        System.out.println(map);
    }

    /**
     * sorting the order by order value defined by total cost of items made in that order
     * which is sum of item.price*item.quantity for list of items in order
     * @param orders dummy set of orders
     */

    public static void sortByOrderValue(List<Order> orders) {
        orders.stream().
                sorted(Comparator.comparingDouble(order -> order.orderItems().stream().map(item -> item.quantity()*item.price()).
                        reduce(0.0, Double::sum))).forEach(System.out::println);
    }

    /**
     * main method from where all queries are tested
     * @param args for command line arguments
     */
    public static void main(String[] args) {
        // for orders
//        filterByTotalOrderValue(generateOrder(), 300);
//        groupByCustomerId(generateOrder(), 300);
//        findHighestOrderWithinGroup(generateOrder(), 300);
//        totalOrderValueForEachCustomer(generateOrder(), 300);
//        sortByOrderValue(generateOrder());

        // for products
//        filterProductWithPrice(generateProduct(), 100);
//        groupByCategory(generateProduct(), 100);
//        findMaxInEachGroup(generateProduct(), 100);
//        totalPrice(generateProduct());
//        sortProductByName(generateProduct());

    }
}