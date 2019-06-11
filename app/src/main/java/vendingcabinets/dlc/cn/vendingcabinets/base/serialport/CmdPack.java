package vendingcabinets.dlc.cn.vendingcabinets.base.serialport;

/**
 * @author :      fangbingran
 * @aescription : 命令包
 * @date :        2019/06/06  12:02
 */
public class CmdPack {
    private byte[] sendData;
    /**
     * 校验结果的命令码
     */
    private byte checkCommand;
    /**
     * 发送数据超时时间,默认3000ms秒
     */
    private int sendOutTime = 3000;
    /**
     * 等待下一条数据超时时间默认300ms
     */
    private int waitOutTime = 300;

    public byte[] getSendData() {
        return sendData;
    }

    public int getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(int sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public int getWaitOutTime() {
        return waitOutTime;
    }

    public void setWaitOutTime(int waitOutTime) {
        this.waitOutTime = waitOutTime;
    }

    public void setSendData(byte[] sendData) {
        this.sendData = sendData;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public byte getCheckCommand() {
        return checkCommand;
    }

    public void setCheckCommand(byte checkCommand) {
        this.checkCommand = checkCommand;
    }


    public static class Builder {
        /**
         * 校验结果的命令码
         */
        private byte checkCommand;
        private byte[] sendData;
        /**
         * 发送数据超时时间,默认3000ms秒
         */
        private int sendOutTime = 3000;
        /**
         * 等待下一条数据超时时间默认300ms
         */
        private int waitOutTime = 300;

        public int getSendOutTime() {
            return sendOutTime;
        }

        public void setSendOutTime(int sendOutTime) {
            this.sendOutTime = sendOutTime;
        }

        public int getWaitOutTime() {
            return waitOutTime;
        }

        public void setWaitOutTime(int waitOutTime) {
            this.waitOutTime = waitOutTime;
        }

        public byte[] getSendData() {
            return sendData;
        }

        public Builder setSendData(byte[] sendData) {
            this.sendData = sendData;
            return this;
        }

        public byte getCheckCommand() {
            return checkCommand;
        }

        public void setCheckCommand(byte checkCommand) {
            this.checkCommand = checkCommand;
        }
    }
}
