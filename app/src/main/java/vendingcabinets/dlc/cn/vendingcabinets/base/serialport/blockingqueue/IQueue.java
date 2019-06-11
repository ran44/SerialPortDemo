package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue;

/**
 * @author :      fangbingran
 * @aescription :队列接口
 * @date :        2019/06/05  19:37
 */
public interface IQueue extends Comparable<IQueue> {
    /**
     * 开始工作
     */
    void runTask();

    /**
     * 设置等级
     */
    void setPriority(@Priority int priority);

    int getPriority();

    /**
     * 一个序列标记
     */
    void setSequence(int sequence);

    /**
     * 获取序列标记
     */
    int getSequence();


}
