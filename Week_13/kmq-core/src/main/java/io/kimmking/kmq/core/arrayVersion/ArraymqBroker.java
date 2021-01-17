package io.kimmking.kmq.core.arrayVersion;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
public class ArraymqBroker {

    final private Lock lock;
    final private Condition notFull;

    public ArraymqBroker(Lock lock, Condition notFull) {
        this.lock = lock;
        this.notFull = notFull;
    }

    public static final int CAPACITY = 23;

    private final Map<String, Arraymq> amqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name){
        amqMap.putIfAbsent(name, new Arraymq(name,CAPACITY));
    }

    public Arraymq findAmq(String topic) {
        return this.amqMap.get(topic);
    }

    public ArrayProducer createProducer() {
        return new ArrayProducer(this);
    }

    public ArraymqConsumer createConsumer() {
        return new ArraymqConsumer(this, System.currentTimeMillis()+"");
    }

}
