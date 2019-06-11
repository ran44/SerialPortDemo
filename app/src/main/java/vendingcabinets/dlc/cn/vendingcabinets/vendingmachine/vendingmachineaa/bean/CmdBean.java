package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;


import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.ByteUtil;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  09:13
 */
public class CmdBean {
    /***
     * 帧头
     * 机柜地址
     * 序号
     * 帧长度
     * 控制码
     * 数据域
     * 校验码
     * 帧尾*/
    /**
     * 帧头
     */
    String frameHeader;
    /**
     * 机柜地址
     */
    String cabinetAddress;
    /**
     * 序号
     */
    String number;
    /**
     * 帧长度
     */
    String frameLength;
    /**
     * 控制码
     */
    String command;
    /**
     * 数据域
     */
    String data;
    /**
     * 校验码
     */
    String CRC;
    /**
     * 帧尾
     */
    String frameTail;

    public String getFrameHeader() {
        return frameHeader;
    }

    public void setFrameHeader(String frameHeader) {
        this.frameHeader = frameHeader;
    }

    public String getCabinetAddress() {
        return cabinetAddress;
    }

    public void setCabinetAddress(String cabinetAddress) {
        this.cabinetAddress = cabinetAddress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrameLength() {
        return frameLength;
    }

    public void setFrameLength(String frameLength) {
        this.frameLength = frameLength;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

    public String getFrameTail() {
        return frameTail;
    }

    public void setFrameTail(String frameTail) {
        this.frameTail = frameTail;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 帧头
         */
        String frameHeader;
        /**
         * 机柜地址
         */
        String cabinetAddress;
        /**
         * 序号
         */
        String number;
        /**
         * 帧长度
         */
        String frameLength;
        /**
         * 控制码
         */
        String command;
        /**
         * 数据域
         */
        String data;
        /**
         * 校验码
         */
        String cRC;
        /**
         * 帧尾
         */
        String frameTail;

        public String getFrameHeader() {
            return frameHeader;
        }

        public Builder setFrameHeader(String frameHeader) {
            this.frameHeader = frameHeader;
            return this;
        }

        public String getCabinetAddress() {
            return cabinetAddress;
        }

        public Builder setCabinetAddress(String cabinetAddress) {
            this.cabinetAddress = cabinetAddress;
            return this;
        }

        public String getNumber() {
            return number;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public String getFrameLength() {
            return frameLength;
        }

        public Builder setFrameLength(String frameLength) {
            this.frameLength = frameLength;
            return this;
        }

        public String getCommand() {
            return command;
        }

        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }

        public String getData() {
            return data;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public String getCRC() {
            return cRC;
        }

        public Builder setCRC(String cRC) {
            this.cRC = cRC;
            return this;
        }

        public String getFrameTail() {
            return frameTail;
        }

        public Builder setFrameTail(String frameTail) {
            this.frameTail = frameTail;
            return this;
        }

        @Override
        public String toString() {
            return "CmdBean{" +
                    "帧头='" + frameHeader + '\'' +
                    ", 机柜地址='" + cabinetAddress + '\'' +
                    ", 序号='" + number + '\'' +
                    ", 帧长度='" + frameLength + '\'' +
                    ", 控制码='" + command + '\'' +
                    ", 数据域='" + data + '\'' +
                    ", 校验码(异或)='" + cRC + '\'' +
                    ", 帧尾(换行)='" + frameTail + '\'' +
                    '}';
        }

        public byte[] build() {
            /**按次序拼接, 帧头,机柜地址, 序号,帧长度, 控制码,数据域,校验码,帧尾*/
            String stringBuilder = frameHeader +
                    cabinetAddress +
                    number +
                    frameLength +
                    command +
                    data +
                    cRC +
                    frameTail;
            return ByteUtil.hexStr2bytes(stringBuilder);
        }
    }

    @Override
    public String toString() {
        return "CmdBean{" +
                "帧头='" + frameHeader + '\'' +
                ", 机柜地址='" + cabinetAddress + '\'' +
                ", 序号='" + number + '\'' +
                ", 帧长度='" + frameLength + '\'' +
                ", 控制码='" + command + '\'' +
                ", 数据域='" + data + '\'' +
                ", 校验码(异或)='" + CRC + '\'' +
                ", 帧尾(换行)='" + frameTail + '\'' +
                '}';
    }
}
