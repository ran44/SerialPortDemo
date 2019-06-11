package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author :      fangbingran
 * @aescription : 它会一直处于阻塞状态，当队列中进入item的时候它会立刻有一个返回值，
 * @date :        2019/06/05  19:55
 */
public class AbstractBlockingQueue extends Thread {
    private BlockingQueue<IQueue> mBlockingQueue;
    private boolean isRunning = true;

    public AbstractBlockingQueue(BlockingQueue<IQueue> blockingDeque) {
        this.mBlockingQueue = blockingDeque;
    }

    public void quit() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {

        while (isRunning) {
            IQueue iQueue;
            try {
                // 叫下一个进来，没有就等着。
                iQueue = mBlockingQueue.take();
            } catch (InterruptedException e) {
                if (!isRunning) {
                    // 发生意外了，是下班状态的话就把服务关闭。
                    interrupt();
                    break; // 如果执行到break，后面的代码就无效了。
                }
                // 发生意外了，不是下班状态，那么Task服务继续等待。
                continue;
            }

            // 开始工作。
            iQueue.runTask();
        }
    }
}
