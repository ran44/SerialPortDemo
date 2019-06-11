package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  15:17
 */
public class SellingGoodsBean {
    private String motorNumber;
    /**
     * 0：失败   1：成功  2：出货失败，并提醒消费者取走取物口中的物品
     */
    private String result;

    public String getMotorNumber() {
        return motorNumber;
    }

    public void setMotorNumber(String motorNumber) {
        this.motorNumber = motorNumber;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
