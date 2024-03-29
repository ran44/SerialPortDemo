package vendingcabinets.dlc.cn.vendingcabinets;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.dlc.fbrtest.R;

import java.util.List;

import vendingcabinets.dlc.cn.vendingcabinets.base.ICommonCallback;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.VendingMachineAAProxy;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.SellingGoodsBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.SlaveStateBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TemperatureBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TestingCabinetDriveBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TestingSingleBean;

public class TestActivity extends CustomAdaptActivity {
    //选择地址
    private Spinner spacer;
    //电机编号
    private TextView et_number;
    //应答帧选择命令码
    private Spinner spacer2;
    //探头编号
    private Spinner spacer3;
    //最小值
    private EditText et_min;
    //最大值
    private EditText et_max;
    //是否开启光检
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //应答帧
                //                Toast.makeText(TestActivity.this, spac    er.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                int number = Integer.parseInt(spacer2.getSelectedItem().toString());
                VendingMachineAAProxy.get().responseFrame(Priority.DEFAULT, cabinetAddress, number, new ICommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d("DLCLog:", "应答帧:" + FastJsonUtil.toJSONString(s));
                    }

                    @Override
                    public void onFailed(DLCException dLCException) {
                        Log.d("DLCLog:", "应答帧:" + dLCException.toString());
                    }
                });
            }
        });
        findViewById(R.id.button_testing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试单个驱动单元
                //                Toast.makeText(TestActivity.this, spacer.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                int number = Integer.parseInt(et_number.getText().toString());
                VendingMachineAAProxy.get()
                        .testingSingleDrivers(Priority.DEFAULT, cabinetAddress, number, new ICommonCallback<TestingSingleBean>() {

                            @Override
                            public void onSuccess(TestingSingleBean testingSingleBean) {
                                Log.d("DLCLog:", "测试单个驱动单元:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {
                                Log.d("DLCLog:", "测试单个驱动单元:" + dLCException.toString());
                            }
                        });
            }
        });
        findViewById(R.id.button_testing_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试整柜驱动单元
                //                Toast.makeText(TestActivity.this, spacer.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                VendingMachineAAProxy.get().testingCabinetDrive(Priority.DEFAULT, cabinetAddress, new ICommonCallback<String>() {

                    @Override
                    public void onSuccess(String testingSingleBean) {
                        Log.d("DLCLog:", "测试整柜驱动单元:" + FastJsonUtil.toJSONString(testingSingleBean));
                    }

                    @Override
                    public void onFailed(DLCException dLCException) {
                        Log.d("DLCLog:", "测试整柜驱动单元:" + dLCException.toString());
                    }
                });
            }
        });
        findViewById(R.id.button_cargoTrackScanning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                //货道扫描
                VendingMachineAAProxy.get()
                        .cargoTrackScanning(Priority.DEFAULT, 10, cabinetAddress, new ICommonCallback<List<TestingCabinetDriveBean>>() {

                            @Override
                            public void onSuccess(List<TestingCabinetDriveBean> testingSingleBean) {
                                Log.d("DLCLog:", "货道扫描:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {
                                Log.d("DLCLog:", "货道扫描:" + dLCException.toString());
                            }
                        });
            }
        });
        findViewById(R.id.button_sellingGoods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                //出售商品
                int number = Integer.parseInt(et_number.getText().toString());
                VendingMachineAAProxy.get()
                        .sellingGoods(Priority.DEFAULT, cabinetAddress, number, new ICommonCallback<SellingGoodsBean>() {

                            @Override
                            public void onSuccess(SellingGoodsBean testingSingleBean) {
                                Log.d("DLCLog:", "出售商品:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {

                                Log.d("DLCLog:", "出售商品:" + dLCException.toString());
                            }
                        });
            }
        });
        findViewById(R.id.button_slaveStateQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从机状态查询
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                VendingMachineAAProxy.get().slaveStateQuery(Priority.DEFAULT, cabinetAddress, new ICommonCallback<SlaveStateBean>() {

                    @Override
                    public void onSuccess(SlaveStateBean testingSingleBean) {
                        Log.d("DLCLog:", "从机状态查询:" + FastJsonUtil.toJSONString(testingSingleBean));
                    }

                    @Override
                    public void onFailed(DLCException dLCException) {
                        Log.d("DLCLog:", "从机状态查询:" + dLCException.toString());
                    }
                });
            }
        });
        findViewById(R.id.button_setTemperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = Integer.parseInt(et_max.getText().toString());
                int min = Integer.parseInt(et_min.getText().toString());
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                int probeNumber = Integer.parseInt(spacer3.getSelectedItem().toString());
                //温度设置
                VendingMachineAAProxy.get()
                        .setTemperature(Priority.DEFAULT, cabinetAddress, min, max, probeNumber, new ICommonCallback<String>() {

                            @Override
                            public void onSuccess(String testingSingleBean) {
                                Log.d("DLCLog:", "温度设置:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {
                                Log.d("DLCLog:", "温度设置:" + dLCException.toString());
                            }
                        });
            }
        });
        findViewById(R.id.button_temperatureQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //温度查询
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                int probeNumber = Integer.parseInt(spacer3.getSelectedItem().toString());
                VendingMachineAAProxy.get()
                        .temperatureQuery(Priority.DEFAULT, cabinetAddress, probeNumber, new ICommonCallback<TemperatureBean>() {

                            @Override
                            public void onSuccess(TemperatureBean testingSingleBean) {
                                Log.d("DLCLog:", "温度查询:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {
                                Log.d("DLCLog:", "温度查询:" + dLCException.toString());
                            }
                        });
            }
        });
        findViewById(R.id.button_firmwareVersionQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //固件版本查询
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                VendingMachineAAProxy.get().firmwareVersionQuery(Priority.DEFAULT, cabinetAddress, new ICommonCallback<String>() {

                    @Override
                    public void onSuccess(String testingSingleBean) {
                        Log.d("DLCLog:", "固件版本查询:" + FastJsonUtil.toJSONString(testingSingleBean));
                    }

                    @Override
                    public void onFailed(DLCException dLCException) {
                        Log.d("DLCLog:", "固件版本查询:" + dLCException.toString());
                    }
                });
            }
        });
        findViewById(R.id.button_photometricControl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //光检控制
                int cabinetAddress = Integer.parseInt(spacer.getSelectedItem().toString());
                VendingMachineAAProxy.get()
                        .photometricControl(Priority.DEFAULT, cabinetAddress, checkbox.isChecked(), new ICommonCallback<Boolean>() {

                            @Override
                            public void onSuccess(Boolean testingSingleBean) {
                                Log.d("DLCLog:", "光检控制:" + FastJsonUtil.toJSONString(testingSingleBean));
                            }

                            @Override
                            public void onFailed(DLCException dLCException) {
                                Log.d("DLCLog:", "光检控制:" + dLCException.toString());
                            }
                        });
            }
        });
        spacer = findViewById(R.id.spacer);
        et_number = findViewById(R.id.et_number);
        spacer2 = findViewById(R.id.spacer2);
        spacer3 = findViewById(R.id.spacer3);
        et_min = findViewById(R.id.et_min);
        et_max = findViewById(R.id.et_max);
        checkbox = findViewById(R.id.checkbox);
        VendingMachineAAProxy.get().init(this.getApplicationContext(), "/dev/ttyS0", 115200, true);
    }

    private void Toast(String value) {
        Toast.makeText(TestActivity.this, value, Toast.LENGTH_SHORT).show();
    }
}
