package com.sachin.Webflux_MongoDB_Reactive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class MainController
{
    @GetMapping("/")
    //public String handleMain()
    public Mono<String> handleMain()
    {
        System.out.println("Sachin");
        //return "Sachin";
        //return Mono.just("templates");
        return Mono.just("home.html");
    }
}
