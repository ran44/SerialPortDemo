package vendingcabinets.dlc.cn.vendingcabinets;

import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.fbrtest.R;
import serialtool.activity.base.BaseActivity;
import serialtool.comn.Device;
import serialtool.comn.SerialPortManager;
import serialtool.util.AllCapTransformationMethod;
import serialtool.util.ByteUtil;
import serialtool.util.PrefHelper;
import serialtool.util.ToastUtil;
import serialtool.util.constant.PreferenceKeys;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.VendingMachineAACmdFactory;

public class TestSendActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner_devices)
    Spinner mSpinnerDevices;
    @BindView(R.id.spinner_baudrate)
    Spinner mSpinnerBaudrate;
    @BindView(R.id.btn_open_device)
    Button mBtnOpenDevice;
    @BindView(R.id.btn_send_data)
    Button mBtnSendData;
    @BindView(R.id.et_data)
    EditText mEtData;

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;

    private boolean mOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEtData.setTransformationMethod(new AllCapTransformationMethod(true));

        initDevice();
        initSpinners();
        updateViewState(mOpened);
    }

    @Override
    protected void onDestroy() {
        SerialPortManager.instance().close();
        super.onDestroy();
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_test;
    }

    /**
     * 初始化设备列表
     */
    private void initDevice() {

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // 设备
        mDevices = serialPortFinder.getAllDevicesPath();
        if (mDevices.length == 0) {
            mDevices = new String[] {
                getString(R.string.no_serial_device)
            };
        }
        // 波特率
        mBaudrates = getResources().getStringArray(R.array.baudrates);

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);

        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);
    }

    /**
     * 初始化下拉选项
     */
    private void initSpinners() {

        ArrayAdapter<String> deviceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_default_item, mDevices);
        deviceAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerDevices.setAdapter(deviceAdapter);
        mSpinnerDevices.setOnItemSelectedListener(this);

        ArrayAdapter<String> baudrateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_default_item, mBaudrates);
        baudrateAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerBaudrate.setAdapter(baudrateAdapter);
        mSpinnerBaudrate.setOnItemSelectedListener(this);

        mSpinnerDevices.setSelection(mDeviceIndex);
        mSpinnerBaudrate.setSelection(mBaudrateIndex);
    }

    @OnClick({ R.id.btn_open_device, R.id.btn_send_data, R.id.btn_1, R.id.btn_2 })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_device:
                switchSerialPort();
                break;
            case R.id.btn_send_data:
                sendData();
                break;
            case R.id.btn_1:
                byte[] data = VendingMachineAACmdFactory.responseFrame(1, 1);
                mEtData.setText(ByteUtil.bytes2HexStr(data));
                break;
            case R.id.btn_2:
                byte[] allData = VendingMachineAACmdFactory.testingSingleDrivers(1, 1);
                mEtData.setText(ByteUtil.bytes2HexStr(allData));
                break;
                default:
        }
    }

    private void sendData() {

        String text = mEtData.getText().toString().trim();
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "无效数据");
            return;
        }

        SerialPortManager.instance().sendCommand(text);
    }

    /**
     * 打开或关闭串口
     */
    private void switchSerialPort() {
        if (mOpened) {
            SerialPortManager.instance().close();
            mOpened = false;
        } else {

            // 保存配置
            PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex);
            PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex);

            mOpened = SerialPortManager.instance().open(mDevice) != null;
            if (mOpened) {
                ToastUtil.showOne(this, "成功打开串口");
            } else {
                ToastUtil.showOne(this, "打开串口失败");
            }
        }
        updateViewState(mOpened);
    }

    /**
     * 更新视图状态
     *
     * @param isSerialPortOpened
     */
    private void updateViewState(boolean isSerialPortOpened) {

        int stringRes = isSerialPortOpened ? R.string.close_serial_port : R.string.open_serial_port;

        mBtnOpenDevice.setText(stringRes);

        mSpinnerDevices.setEnabled(!isSerialPortOpened);
        mSpinnerBaudrate.setEnabled(!isSerialPortOpened);
        mBtnSendData.setEnabled(isSerialPortOpened);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Spinner 选择监听
        switch (parent.getId()) {
            case R.id.spinner_devices:
                mDeviceIndex = position;
                mDevice.setPath(mDevices[mDeviceIndex]);
                break;
            case R.id.spinner_baudrate:
                mBaudrateIndex = position;
                mDevice.setBaudrate(mBaudrates[mBaudrateIndex]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // 空实现
    }
}
