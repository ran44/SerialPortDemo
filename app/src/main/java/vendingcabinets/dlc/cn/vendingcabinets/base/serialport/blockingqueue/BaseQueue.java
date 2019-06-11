package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/05  20:42
 */
public abstract class BaseQueue implements IQueue {
    private int priority = Priority.DEFAULT;
    private int sequence;

    @Override
    public void setPriority(@Priority int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }


    @Override
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getSequence() {
        return sequence;
    }


    @Override
    public String toString() {
        return "BaseQueue{" +
                "priority=" + priority +
                ", sequence=" + sequence +
                '}';
    }

    /**
     * <br> Description: compareTo(E)中传进来的E是另一个Task，如果当前Task比另一个Task更靠前就返回负数，如果比另一个Task靠后，那就返回正数，如果优先级相等，那就返回0。
     * <br> Author:     fangbingran
     * <br> Date:        2018/5/9 23:29
     */
    @Override
    public int compareTo(IQueue another) {
        final int me = this.getPriority();
        final int it = another.getPriority();
        return me == it ? this.getSequence() - another.getSequence() :
                it - me;
    }
}
