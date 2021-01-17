package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.arrayVersion.ArrayProducer;
import io.kimmking.kmq.core.arrayVersion.ArraymqBroker;
import io.kimmking.kmq.core.arrayVersion.ArraymqConsumer;
import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArraymqDemo {

    @SneakyThrows
    public static void main(String[] args) {

        final Lock lock = new ReentrantLock();
        final Condition notFull  = lock.newCondition();

        String topic = "kk.test";
        ArraymqBroker broker = new ArraymqBroker(lock, notFull);
        broker.createTopic(topic);

        ArraymqConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);
        final boolean[] flag = new boolean[2];
        flag[0] = true;
        flag[1] = true;
        Thread consumerThread = new Thread(() -> {
            while (flag[0]) {
                KmqMessage<Order> message = consumer.poll(100);
                if(null != message) {
                    System.out.println("消费者" + consumer.getConsumerID() + "本次消费到的数据: " + message.getBody());
                }
            }
            System.out.println("消费程序退出。");
        });
        consumerThread.start();

        ArrayProducer producer = broker.createProducer();
        for (int i = 0; i < 20; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new KmqMessage(null, order));
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");

        while (true) {
            char c = (char) System.in.read();

            if(c > 20) {
                flag[1] = producer.send(topic, new KmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            lock.lock();
            if(consumer.getCurrentConsumerOffset() == broker.getAmqMap().get(topic).getReadableIndex()-1){
                notFull.signal();
            }
            lock.unlock();

            if(!flag[1]){
                flag[0] = false;
                Thread.sleep(1000);
                System.out.println("当前数据容器已满，将退出主线程");
                System.exit(0);
            }

            if( c == 'q' || c == 'e') break;
        }

        flag[0] = false;

    }
}
