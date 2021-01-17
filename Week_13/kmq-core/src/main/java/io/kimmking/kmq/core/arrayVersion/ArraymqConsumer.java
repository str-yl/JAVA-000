package io.kimmking.kmq.core.arrayVersion;

import io.kimmking.kmq.core.KmqMessage;
import lombok.Data;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
public class ArraymqConsumer<T> {
    final private Lock lock;
    final private Condition notFull;

    private final ArraymqBroker broker;

    //目前消费的最新偏移量
    private Integer currentConsumerOffset;

    private Arraymq amq;

    //消费者ID，用以区分不同的消费者
    private String consumerID;

    public ArraymqConsumer(ArraymqBroker broker, String consumerID) {
        this.broker = broker;
        this.lock = broker.getLock();
        this.notFull = broker.getNotFull();
        //表示还没开始消费
        this.currentConsumerOffset = -1;

        this.consumerID = consumerID;
    }

    public void subscribe(String topic) {
        this.amq = this.broker.findAmq(topic);
        if (null == amq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

//    @SneakyThrows
    public KmqMessage<T> poll(long timeout) {
        KmqMessage<T> meg = null;
        try {
            lock.lock();
            Thread.sleep(timeout);
            if(currentConsumerOffset == ArraymqBroker.CAPACITY-1) {
                System.out.println("当前数据容器已满，将退出消费线程");
            }
            if (currentConsumerOffset == amq.getReadableIndex()) {
                System.out.println("当前无新数据可供消费。。");
                notFull.await();
            }
            meg = amq.getQueue()[currentConsumerOffset + 1];
            this.currentConsumerOffset++;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
            return meg;
        }
    }
}
