package vendingcabinets.dlc.cn.vendingcabinets;

import android.os.SystemClock;
import android.util.Log;

import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.BaseQueue;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/05  20:40
 */
public class Task2 extends BaseQueue {
    private String tag;

    public Task2(String tag, int priority) {
        this.tag = tag;
        setPriority(priority);
    }

    @Override
    public void runTask() {
        SystemClock.sleep(3000);
        String name = "";
        Thread[] findAllThreads = findAllThreads();
        int cnt = 0;
        for (Thread t : findAllThreads) {
            name = name + "\n" + t.getName();
        }
        System.out.println("name=" + name);
        Log.d("BaseQueue", toString() + "--" + tag + "---name=" + name);
    }

    private Thread[] findAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;

        /* 遍历线程组树，获取根线程组 */
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        /* 激活的线程数加倍 */
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];

        /* 获取根线程组的所有线程 */
        int actualSize = topGroup.enumerate(slackList);
        /* copy into a list that is the exact size */
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return (list);
    }


}
