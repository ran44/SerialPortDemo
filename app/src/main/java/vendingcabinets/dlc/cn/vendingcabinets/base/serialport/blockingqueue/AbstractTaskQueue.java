package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/05  19:56
 */
public class AbstractTaskQueue {
    /***AtomicInteger类，它的incrementAndGet()方法会每次递增1***/
    private AtomicInteger mAtomicInteger = new AtomicInteger();
    /**
     * 多个BlockingQueue。
     */

    private AbstractBlockingQueue[] mAbstractBlockingQueues;

    private BlockingQueue<IQueue> mTaskQueue;

    public AbstractTaskQueue(int size) {
        /**
         *  PriorityBlockingQueue所含对象的排序不是FIFO,而是依据对象的自然排序顺序或者是构造函数的Comparator决定的顺序.
         *
         */
        mTaskQueue = new PriorityBlockingQueue<>();
        mAbstractBlockingQueues = new AbstractBlockingQueue[size];

    }


    /**
     * 开始工作。
     */
    public void start() {
        stop();
        // 把各个BlockingQueue启动，开始工作。
        for (int i = 0; i < mAbstractBlockingQueues.length; i++) {
            mAbstractBlockingQueues[i] = new AbstractBlockingQueue(mTaskQueue);
            mAbstractBlockingQueues[i].start();
        }
    }

    /**
     * 统一各个BlockingQueue退出。
     */
    public void stop() {
        if (mAbstractBlockingQueues != null) {
            for (AbstractBlockingQueue taskExecutor : mAbstractBlockingQueues) {
                if (taskExecutor != null) {
                    taskExecutor.quit();
                }
            }
        }
    }

    /**
     * 添加一个任务。
     */
    public <T extends IQueue> int add(T iQueue) {
        if (!mTaskQueue.contains(iQueue)) {
            iQueue.setSequence(mAtomicInteger.incrementAndGet());
            mTaskQueue.add(iQueue);
        }
        // 返回排的队的人数，公开透明，让外面的人看的有多少人在等着办事。
        return mTaskQueue.size();
    }

    public int size() {
        return mTaskQueue.size();
    }
}
