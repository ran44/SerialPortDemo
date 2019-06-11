package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;

import java.util.List;

import vendingcabinets.dlc.cn.vendingcabinets.FastJsonUtil;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  16:20
 */
public class TestingCabinetDriveBean implements Comparable<TestingCabinetDriveBean> {
    private int cargoWayId;
    private List<TestingCabinetDrive> list;

    public List<TestingCabinetDrive> getList() {
        return list;
    }

    public void setList(List<TestingCabinetDrive> list) {
        this.list = list;
    }

    public int getCargoWayId() {
        return cargoWayId;
    }

    public void setCargoWayId(int cargoWayId) {
        this.cargoWayId = cargoWayId;
    }

    public static class TestingCabinetDrive implements Comparable<TestingCabinetDrive>

    {
        private int motorNumber;
        private boolean isExist = false;

        public boolean isExist() {
            return isExist;
        }

        public void setExist(boolean exist) {
            isExist = exist;
        }

        public int getMotorNumber() {
            return motorNumber;
        }

        public void setMotorNumber(int motorNumber) {
            this.motorNumber = motorNumber;
        }

        @Override
        public int compareTo(TestingCabinetDrive another) {
            final int me = this.getMotorNumber();
            final int it = another.getMotorNumber();
            return it - me;
        }
    }

    @Override
    public int compareTo(TestingCabinetDriveBean another) {
        final int me = this.getCargoWayId();
        final int it = another.getCargoWayId();
        return it - me;
    }

    @Override
    public String toString() {
        return "TestingCabinetDriveBean{" +
                "cargoWayId=" + cargoWayId +
                ", list=" + FastJsonUtil.toJSONString(list) +
                '}';
    }
}
