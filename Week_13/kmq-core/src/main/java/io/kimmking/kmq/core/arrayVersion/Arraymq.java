package io.kimmking.kmq.core.arrayVersion;

import io.kimmking.kmq.core.KmqMessage;
import lombok.Data;

@Data
public class Arraymq {

    private String topic;
    private int capacity;
    private KmqMessage[] queue;
    //当前最大的可读位置
    private int readableIndex;

    public Arraymq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new KmqMessage[capacity];
        //-1表示目前queue是空的
        this.readableIndex = -1;
    }

    public boolean send(KmqMessage message) {
        if (this.readableIndex == capacity-1) {
            System.out.println("当前无空闲存储空间");
            return false;
        }
        queue[this.readableIndex+1] = message;
        this.readableIndex++;
        System.out.println("当前readableIndex: " + this.readableIndex);
        return true;
    }

    //每个消费者消费的offset都不一致，所以这里的消费poll方法放在消费者端进行定义
}
