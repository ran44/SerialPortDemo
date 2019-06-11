package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  19:35
 */
public class TemperatureBean {
    private String probeNumber;
    private String actualTemperature;

    public String getProbeNumber() {
        return probeNumber;
    }

    public void setProbeNumber(String probeNumber) {
        this.probeNumber = probeNumber;
    }

    public String getActualTemperature() {
        return actualTemperature;
    }

    public void setActualTemperature(String actualTemperature) {
        this.actualTemperature = actualTemperature;
    }

    @Override
    public String toString() {
        return "TemperatureBean{" +
                "探头编号='" + probeNumber + '\'' +
                ", 实际温度='" + actualTemperature + '\'' +
                '}';
    }
}
