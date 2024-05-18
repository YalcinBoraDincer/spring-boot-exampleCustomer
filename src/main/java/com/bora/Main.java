package com.bora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final  CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public static void main(String[] args) {

        SpringApplication.run(Main.class,args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }
    @PostMapping()
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int id){
        customerRepository.deleteById(id);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long id, @RequestBody NewCustomerRequest request) {
        Optional<Customer> optionalCustomer = customerRepository.findById(Math.toIntExact(id));
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(request.name);
            existingCustomer.setEmail(request.email);
            existingCustomer.setAge(request.age);
            customerRepository.save(existingCustomer);
            return ResponseEntity.ok(existingCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }



    }

















    record Person(String name, int agem,double savings) {}
    @GetMapping("/greet")
    public GreetResponse greet(){
         GreetResponse response=new GreetResponse(
                "Hellou",
                List.of("Java","Phyton","NetJS"),
                new Person("Bora",21,32_000));
         return  response;
    }
    record GreetResponse(
            String greet,
            List<String> favProgrammingLanguages,
            Person person
    ){}



}

