package com.sachin.Webflux_MongoDB_Reactive;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class BackpressureTutorial
{
    private Flux<Long> noOverFlow()
    {
        return Flux.range(1, Integer.MAX_VALUE)
                .log().concatMap(x -> Mono.delay(Duration.ofMillis(100)));
    }

    private Flux<Long> yesOverFlow()
    {
        return Flux.interval(Duration.ofMillis(1))
                .log().concatMap(x -> Mono.delay(Duration.ofMillis(10)));
    }

    private Flux<Long> dropOnBackPressure()
    {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer : " + a));
    }

    private Flux<Long> createBufferOnBackPressure()
    {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureBuffer(5000, BufferOverflowStrategy.DROP_OLDEST)
                .concatMap(a -> Mono.delay(Duration.ofMillis(5)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer : " + a));
    }

    public static void main(String[] args)
    {
        BackpressureTutorial bp = new BackpressureTutorial();
        bp.createBufferOnBackPressure().blockLast();
    }
}
