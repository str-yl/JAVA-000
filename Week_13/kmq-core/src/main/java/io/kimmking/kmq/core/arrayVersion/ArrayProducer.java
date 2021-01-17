package io.kimmking.kmq.core.arrayVersion;

import io.kimmking.kmq.core.KmqMessage;

public class ArrayProducer {

    private ArraymqBroker broker;

    public ArrayProducer(ArraymqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, KmqMessage message) {
        Arraymq amq = this.broker.findAmq(topic);
        if (null == amq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        return amq.send(message);
    }
}
