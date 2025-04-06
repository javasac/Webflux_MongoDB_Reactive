package com.sachin.Webflux_MongoDB_Reactive.MongoDB;
import com.sachin.Webflux_MongoDB_Reactive.model.Customer;
import com.sachin.Webflux_MongoDB_Reactive.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;

@RestController
public class DataController
{
    @Autowired
    private ReactiveMongoTemplate rmt;

    @GetMapping("/allCustomers")
    private Flux<Customer> getAllCustomers()
    {
        return rmt.findAll(Customer.class);
    }

    @GetMapping("/allOrders")
    private Flux<Order> getAllOrders()
    {
        return rmt.findAll(Order.class);
    }

    @GetMapping("/customer/{id}")
    private Mono<Customer> findCustomerID(@PathVariable String  id)
    {
        Criteria crit = Criteria.where("id").is(id);
        Query query = Query.query(crit);
        return rmt.findOne(query, Customer.class).log();
    }

    @GetMapping("/order/{id}")
    private Mono<Order> findOrderID(@PathVariable String  id)
    {
        Criteria crit = Criteria.where("id").is(id);
        Query query = Query.query(crit);
        return rmt.findOne(query, Order.class).log();
    }

    @GetMapping("/test")
    public String test()
    {
        return "Sachin";
    }

    @PostMapping("/customer/create")
    public Mono<Customer> createCustomer(@RequestBody Customer cus)
    {
        return rmt.save(cus);
    }

    @PostMapping("/order/create")
    public Mono<Order> createOrder(@RequestBody Order or)
    {
        return rmt.save(or);
    }

    @GetMapping("/sales/summary")
    public Mono<Map<String, Double>> SalesSummary()
    {
        return rmt.findAll(Customer.class).
                flatMap(customer -> Mono.zip(Mono.just(customer), calcOrderSum(customer.getId())))
                .collectMap(tuple2 -> tuple2.getT1().getName(), Tuple2::getT2);
    }

    private Mono<Double> calcOrderSum(String custId)
    {
        Criteria crit = Criteria.where("custId").is(custId);

        return rmt.find(Query.query(crit), Order.class)
                .map(Order::getTotal)
                .reduce(0d, Double::sum);
    }
}
