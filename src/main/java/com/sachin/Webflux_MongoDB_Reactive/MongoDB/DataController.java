package com.sachin.Webflux_MongoDB_Reactive.MongoDB;
import com.sachin.Webflux_MongoDB_Reactive.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DataController
{
    @Autowired
    private ReactiveMongoTemplate rmt;

    @GetMapping("/find/all")
    private Flux<Customer> getAllCustomers()
    {
        return rmt.findAll(Customer.class);
    }

    @GetMapping("/find/{id}")
    private Mono<Customer> findCustomerID(@PathVariable String  id)
    {
        //Criteria crit = new Criteria();
        //Query query = Query.query(crit);
        return rmt.findById(id, Customer.class);
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
}
