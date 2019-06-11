package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean;

/**
 * @author :      fangbingran
 * @aescription : 从机状态
 * @date :        2019/06/10  17:25
 */
public class SlaveStateBean {
    /**
     * 制冷区温度
     */
    private String refrigerationArea;
    /**
     * 制热区温度
     */
    private String heatingArea;
    /**
     * 光检状态,是否正常
     */
    private boolean photographicIsNormal;

    /**
     * 门控,是否关闭
     */
    private boolean gateIsStop;
    /**
     * 振动状态,是否正常
     */
    private boolean vibrateIsNormal;
    /**
     * 温控板连接,是否正常
     */
    private boolean temperatureControlBoardIsNormal;
    /**
     * 电机或者电磁锁,是否正常
     */
    private boolean electromagneticLockIsNormal;
    /**
     * 电源,是否正常
     */
    private boolean powerIsNormal;
    /**
     * 压缩机,是否正常
     */
    private boolean compressorIsNormal;
    /**
     * 风机,是否正常
     */
    private boolean fanIsNormal;
    /**
     * 压缩机,是否停止
     */
    private boolean compressorIsStop;
    /**
     * 风机,是否停止
     */
    private boolean fanIIsStop;
    /**
     * 窗玻璃,是否停止
     */
    private boolean windowGlassStop;
    /**
     * 制热模块,是否停止
     */
    private boolean heatingModuleStop;
    /**
     * LED灯,是否停止
     */
    private boolean ledLanternStop;
    /**
     * 柜子选择(0：弹簧柜  1：格子柜)
     */
    private String cabinetSelection;
    /**
     * 帮助键是否按下
     */
    private boolean helpKeyIsDown;
    /**
     * 升降台是否正常
     */
    private boolean liftingTableIsNormal;
    /**
     * 有故障的电机编号
     */
    private String faultyMotorNumber;
    /**
     * 人数统计
     */
    private String peopleCounting;

    public String getRefrigerationArea() {
        return refrigerationArea;
    }

    public void setRefrigerationArea(String refrigerationArea) {
        this.refrigerationArea = refrigerationArea;
    }

    public String getHeatingArea() {
        return heatingArea;
    }

    public void setHeatingArea(String heatingArea) {
        this.heatingArea = heatingArea;
    }

    public boolean isPhotographicIsNormal() {
        return photographicIsNormal;
    }

    public void setPhotographicIsNormal(boolean photographicIsNormal) {
        this.photographicIsNormal = photographicIsNormal;
    }

    public boolean isGateIsStop() {
        return gateIsStop;
    }

    public void setGateIsStop(boolean gateIsStop) {
        this.gateIsStop = gateIsStop;
    }

    public boolean isVibrateIsNormal() {
        return vibrateIsNormal;
    }

    public void setVibrateIsNormal(boolean vibrateIsNormal) {
        this.vibrateIsNormal = vibrateIsNormal;
    }

    public boolean isTemperatureControlBoardIsNormal() {
        return temperatureControlBoardIsNormal;
    }

    public void setTemperatureControlBoardIsNormal(boolean temperatureControlBoardIsNormal) {
        this.temperatureControlBoardIsNormal = temperatureControlBoardIsNormal;
    }

    public boolean isElectromagneticLockIsNormal() {
        return electromagneticLockIsNormal;
    }

    public void setElectromagneticLockIsNormal(boolean electromagneticLockIsNormal) {
        this.electromagneticLockIsNormal = electromagneticLockIsNormal;
    }

    public boolean isPowerIsNormal() {
        return powerIsNormal;
    }

    public void setPowerIsNormal(boolean powerIsNormal) {
        this.powerIsNormal = powerIsNormal;
    }

    public boolean isCompressorIsNormal() {
        return compressorIsNormal;
    }

    public void setCompressorIsNormal(boolean compressorIsNormal) {
        this.compressorIsNormal = compressorIsNormal;
    }

    public boolean isFanIsNormal() {
        return fanIsNormal;
    }

    public void setFanIsNormal(boolean fanIsNormal) {
        this.fanIsNormal = fanIsNormal;
    }

    public boolean isCompressorIsStop() {
        return compressorIsStop;
    }

    public void setCompressorIsStop(boolean compressorIsStop) {
        this.compressorIsStop = compressorIsStop;
    }

    public boolean isFanIIsStop() {
        return fanIIsStop;
    }

    public void setFanIIsStop(boolean fanIIsStop) {
        this.fanIIsStop = fanIIsStop;
    }

    public boolean isWindowGlassStop() {
        return windowGlassStop;
    }

    public void setWindowGlassStop(boolean windowGlassStop) {
        this.windowGlassStop = windowGlassStop;
    }

    public boolean isHeatingModuleStop() {
        return heatingModuleStop;
    }

    public void setHeatingModuleStop(boolean heatingModuleStop) {
        this.heatingModuleStop = heatingModuleStop;
    }

    public boolean isLedLanternStop() {
        return ledLanternStop;
    }

    public void setLedLanternStop(boolean ledLanternStop) {
        this.ledLanternStop = ledLanternStop;
    }

    public String getCabinetSelection() {
        return cabinetSelection;
    }

    public void setCabinetSelection(String cabinetSelection) {
        this.cabinetSelection = cabinetSelection;
    }

    public boolean isHelpKeyIsDown() {
        return helpKeyIsDown;
    }

    public void setHelpKeyIsDown(boolean helpKeyIsDown) {
        this.helpKeyIsDown = helpKeyIsDown;
    }

    public boolean isLiftingTableIsNormal() {
        return liftingTableIsNormal;
    }

    public void setLiftingTableIsNormal(boolean liftingTableIsNormal) {
        this.liftingTableIsNormal = liftingTableIsNormal;
    }

    public String getFaultyMotorNumber() {
        return faultyMotorNumber;
    }

    public void setFaultyMotorNumber(String faultyMotorNumber) {
        this.faultyMotorNumber = faultyMotorNumber;
    }

    public String getPeopleCounting() {
        return peopleCounting;
    }

    public void setPeopleCounting(String peopleCounting) {
        this.peopleCounting = peopleCounting;
    }

    @Override
    public String toString() {
        return "SlaveStateBean{" +
                "制冷区温度='" + refrigerationArea + '\'' +
                ", 制热区温度='" + heatingArea + '\'' +
                ", 光检状态,是否正常='" + photographicIsNormal + '\'' +
                ", 门控,是否关闭=" + gateIsStop +
                ", 振动状态,是否正常=" + vibrateIsNormal +
                ", 温控板连接,是否正常=" + temperatureControlBoardIsNormal +
                ", 电机或者电磁锁,是否正常=" + electromagneticLockIsNormal +
                ", 电源,是否正常=" + powerIsNormal +
                ",  压缩机,是否正常=" + compressorIsNormal +
                ", 风机,是否正常=" + fanIsNormal +
                ", 压缩机,是否停止=" + compressorIsStop +
                ", 风机,是否停止=" + fanIIsStop +
                ", 窗玻璃,是否停止=" + windowGlassStop +
                ", 制热模块,是否停止=" + heatingModuleStop +
                ", LED灯,是否停止=" + ledLanternStop +
                ", 柜子选择(0：弹簧柜  1：格子柜)='" + cabinetSelection + '\'' +
                ", 帮助键是否按下=" + helpKeyIsDown +
                ", 升降台是否正常=" + liftingTableIsNormal +
                ", 有故障的电机编号='" + faultyMotorNumber + '\'' +
                ", 人数统计='" + peopleCounting + '\'' +
                '}';
    }
}
