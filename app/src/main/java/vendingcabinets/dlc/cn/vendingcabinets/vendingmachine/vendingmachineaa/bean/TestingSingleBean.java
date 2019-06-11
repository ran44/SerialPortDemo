package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  15:17
 */
public class TestingSingleBean {
    private String motorNumber;
    /**
     * 0：测试失败  1：测试成功  2：电机故障
     */
    private String testResult;

    public String getMotorNumber() {
        return motorNumber;
    }

    public void setMotorNumber(String motorNumber) {
        this.motorNumber = motorNumber;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}
