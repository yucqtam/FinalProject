import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
* @author Timmy Vo
* still WIP as still unsure what the full plan is..
*/

// tbh, I did some googling and looked at examples for the date format stuff for shipping speeds as it got a bit confusing 

public class Order {
    private static int order_seed = 1000000;
    private final int orderId;
    private final Customer customer; 
    private final String date; // ISO format
    private final ShippingSpeed shippingSpeed; // overnight, rush or standard
    private final long priority; // calculated priority weight based on shipping speed and order date

    // accessors
    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public String getDate() { return date; }
    public ShippingSpeed getShippingSpeed() { return shippingSpeed; }
    public long getPriority() { return priority; }

    public enum ShippingSpeed {
        /**
        * large values for a large buffer range to help with
        * calculating priority based on order dates and shipping speed
        */
        OVERNIGHT(3000000), RUSH(2000000), STANDARD(1000000);
        private int priority;
        

        ShippingSpeed(int priority) { this.priority = priority; } 
        public int getPriority() { return priority; }
    }
    
    // Constructor that initializes order and then calculates priority
    public Order(Customer customer, String date, ShippingSpeed shippingSpeed) {
        this.orderId = order_seed++;
        this.customer = customer;
        this.date = date;
        this.shippingSpeed = shippingSpeed;
        this.priority = calculatePriority();
    }

    /**
     * Calculates order priority 
     * Formula = (Shipping Speed) - (Days Since Epoch)
     * This calculates the priority value of orders
     * Using epoch as a baseline for calculating priority values/weights
     */
    private long calculatePriority() {
        long daysSinceEpoch = parseDateToDays();
        return shippingSpeed.getPriority() - daysSinceEpoch;
    }

    /**
     * Converts date in ISO format to total days since epoch
     */
    private long parseDateToDays() {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return dateTime.toLocalDate().toEpochDay();
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + date);
            return 0; 
        }
    }

    @Override
    public String toString() {
        return;
    }
}
