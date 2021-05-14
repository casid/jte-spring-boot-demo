package gg.jte.demo;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.LongAdder;

@Service
public class VisitsRepository {
    private final LongAdder visits = new LongAdder();

    public void add() {
        visits.increment();
    }

    public long get() {
        return visits.longValue();
    }
}
