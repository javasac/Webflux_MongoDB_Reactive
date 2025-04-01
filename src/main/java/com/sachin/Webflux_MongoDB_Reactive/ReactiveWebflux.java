package com.sachin.Webflux_MongoDB_Reactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class ReactiveWebflux {
    private Mono<String> testMono() {
        return Mono.just("Java").log();
    }

    private Flux<String> testFlux() {
        List<String> lang = List.of("Java", "C++", "Golang", "Python");
        return Flux.fromIterable(lang);
    }

    private Flux<String> testMap() {
        Flux<String> flux = Flux.just("Java", "C++", "Golang", "Python");
        return flux.map(s -> s.toLowerCase(Locale.ROOT));
    }

    private Flux<String> testflatMap() {
        Flux<String> flux = Flux.just("Java", "C++", "Golang", "Python");
        return flux.flatMap(s -> Mono.just(s.toUpperCase(Locale.ROOT)));
    }

    private Flux<String> testSkip()
    {
        Flux<String> flux = Flux.just("Java", "C++", "Golang", "Python")
                .delayElements(Duration.ofSeconds(1));
        return flux.skip(Duration.ofSeconds(1)).skipLast(2);
    }

    private Flux<Integer> integerSkip()
    {
        Flux<Integer> flux1 = Flux.range(1, 20);
        Flux<Integer> flux2 = Flux.range(10, 25);
        return flux1.merge(flux1, flux2);
    }

    private Flux<Tuple3<Integer, Integer, Integer>> testZip()
    {
        Flux<Integer> flux1 = Flux.range(1, 20)
                .delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux2 = Flux.range(10, 25)
                .delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux3 = Flux.range(100, 125)
                .delayElements(Duration.ofSeconds(1));
        return Flux.zip(flux1, flux2, flux3);
    }

    private Mono<List<Integer>> testCollect()
    {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        return flux.collectList();
    }

    private Flux<List<Integer>> testBuffer()
    {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(100));
        return flux.buffer(3);
    }

    public static void main(String[] args) throws InterruptedException
    {
        ReactiveWebflux rw = new ReactiveWebflux();
        rw.testBuffer().subscribe(System.out::println);
        Thread.sleep(3000);

        //List<Integer> lt = rw.testCollect().block();
        //System.out.println(lt);

        //rw.integerSkip().subscribe(System.out::println);
        /*rw.testMono().subscribe(data -> System.out.println(data));
        rw.testFlux().subscribe(System.out::println);
        rw.testMap().subscribe(System.out::println);
        rw.testflatMap().subscribe(System.out::println);*/
        //rw.testSkip().subscribe(System.out::println);
        //rw.testZip().subscribe(System.out::println);
        //Thread.sleep(Duration.ofSeconds(5));
    }
}
