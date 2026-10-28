package objects.register;

import objects.customer.Customer;

public record RegisterQueueEntry(Customer customer, Runnable afterCheckout){}
