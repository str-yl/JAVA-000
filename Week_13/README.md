学习笔记
实现点：
           
第二个版本：

主要实现点概括：

0、使用数组充当消息队列MQ；

1、首先Arraymq对象新增变量标记当前topic的最大可读位置，readableIndex；

2、消费者Consumer里新增该消费者消费这个topic的当前偏移量currentConsumerOffset；

3、生产消息主要操作就是queue[this.readableIndex+1] = message;
即当前最大可读位置的下一个位置set生产的数据；然后readableIndex自增1；

4、消费消息主要是读取当前偏移量的下一个位置的数据return amq.getQueue()[currentConsumerOffset + 1];
然后当前偏移量自增1；

5、消费消息时需要主要当消费到最大可读位置时，当前消费进程设置为wait状态，当有
新的数据生成到topic中之后，在唤醒消费线程继续消费；使用Lock+Condition来操作消费进程；

6、当最大可读位置和数组长度一致时，退出主线程，即停止生产数据操作，同时让消费线程也退出；

具体的实现代码见io.kimmking.kmq.core.arrayVersion包和io.kimmking.kmq.demo.ArraymqDemo类;