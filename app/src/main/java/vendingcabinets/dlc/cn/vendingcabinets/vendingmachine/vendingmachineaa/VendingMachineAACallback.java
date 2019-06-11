package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa;


import java.nio.ByteBuffer;

import vendingcabinets.dlc.cn.vendingcabinets.DLCLog;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.BaseDataCallback;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.DataPack;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.ByteUtil;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.CheckUtil;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  17:00
 */
public class VendingMachineAACallback implements BaseDataCallback {
    private final ByteBuffer mByteBuffer;
    //帧头+机柜地址+ 序号+帧长度+ 控制码+ 数据域+校验码+帧尾（所有命令格式）
    //1、帧长度包括机柜地址到校验的所有字节，不包含帧头帧尾。
    //2/不同的从机如果使用同一个序号变量可能会使从机产在前后两条指令的序号重复
    //主机空闲时应按200-400MS一次的频率不断对从机进行状态轮询。从机应当在最早100US后，最迟100MS后进行回应。
    //主机要对从机发出指令后，从机如果空闲应当立即回应ACK，表示接到指令，并开始执行。执行中，主机可不断以相同的命令格式查询从机的状态，
    // 从机如果还没执行完成则回应BUSY,如果执行完成，以回复相应的执行状态数据,注意序号不能改变


    public VendingMachineAACallback() {
        mByteBuffer = ByteBuffer.allocate(1024);
        mByteBuffer.clear();
    }

    @Override
    public DataPack checkData(byte[] received, int size) {
        //从received数组中的0到0+length区域读取数据并使用相对写写入此byteBuffer
        mByteBuffer.put(received, 0, size);
        //limit = position;position = 0;mark = -1;
        // 将一个处于存数据状态的缓冲区变为一个处于准备取数据的状态
        mByteBuffer.flip();
        int readable;
        while ((readable = mByteBuffer.remaining()) >= VendingMachineAAProtocol.MIN_PACK_LEN) {
            //标记一下当前位置,调用mark()来设置mark=position，再调用reset()可以让position恢复到标记的位置
            mByteBuffer.mark();
            int frameStart = mByteBuffer.position();
            byte head = mByteBuffer.get();
            //校验头部
            if (head != VendingMachineAAProtocol.FRAME_HEADER) {
                continue;
            }
            //校验数据域长度,数据域在第6个位置
            byte[] len = new byte[mByteBuffer.get(frameStart + VendingMachineAAProtocol.DATA_LEN_INDEX)];
            //数据域长度
            int dataLen = ByteUtil.byteToInt(len);
            // 总数据长度(实际长度=最小长度(不包含数据域长度)+实际数据长度)
            int total = VendingMachineAAProtocol.MIN_PACK_LEN + dataLen;
            //当前长度小于实际数据总长度,
            if (readable < total) {
                //重置处理的位置,跳出循环
                mByteBuffer.reset();
                break;
            }
            //回到头
            mByteBuffer.reset();
            //获取整个包
            byte[] allPack = new byte[total];
            mByteBuffer.get(allPack);
            int oXRLen = VendingMachineAAProtocol.OXR_LEN + VendingMachineAAProtocol.FRAME_TAIL_LEN;
            //对当前数据进行异或,(不包含帧头，帧尾,和本身)帧头和帧尾,异或各一个字节,所以异或的数据是第二位到倒数第三位
            String dataToXOR = CheckUtil.getXOR(ByteUtil.getBytes(allPack, 1, total - oXRLen));
            //获取数据包中的异或值

            String currentXOR = ByteUtil.bytes2HexStr(allPack,
                    total - oXRLen, 1);
            if (dataToXOR.equalsIgnoreCase(currentXOR)) {
                //获取数据码,根据数据,数据码在第六位,所以从5算起
                byte[] data = ByteUtil.getBytes(allPack, 5, dataLen);
                byte command = allPack[4];
                return new DataPack(allPack, data, command);
            } else {
                DLCLog.d("异或不相同,dataToXOR:" + dataToXOR + "--currentXOR:" + currentXOR);
                //不一致回调这次帧头之后
                mByteBuffer.position(frameStart + 1);
            }

        }
        //最后清掉之前处理过的不合适的数据
        mByteBuffer.compact();
        return null;
    }
}
