package java0.conc0303;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {


    private static void meth_01(){
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                int result = sum(); //这是得到的返回值

                // 确保  拿到result 并输出
                System.out.println("异步计算结果为："+result);
            }
        });
        c.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static class meth_02_c extends Thread{
        private CountDownLatch latch;
        public meth_02_c(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            int sync = sum();
            System.out.println("异步计算结果为："+sync);
            latch.countDown();
        }
    }
    private static void meth_02() throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new meth_02_c(countDownLatch)).start();
        countDownLatch.await();
    }

    private static void meth_03() throws InterruptedException{
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                int result = sum(); //这是得到的返回值
                System.out.println("异步计算结果为："+result);
            }
        });
        c.start();
        c.join();
    }

    static class meth_04_c extends Thread{
        private CyclicBarrier cyc;
        public meth_04_c(CyclicBarrier cyc){
            this.cyc = cyc;
        }
        @Override
        public void run() {
            try {
                int sync = sum();
                System.out.println("异步计算结果为：" + sync);
                cyc.await();
            } catch (Exception e){
            }
        }
    }
    private static void meth_04() throws Exception{
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(new meth_04_c(cyclicBarrier)).start();
        cyclicBarrier.await();
    }



    
    public static void main(String[] args) throws Exception{
        
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        meth_01();
        meth_02();
        meth_03();
        meth_04();

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
