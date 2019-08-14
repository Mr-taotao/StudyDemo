package com.example.chtlei.mydemo.deviceinfo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import com.example.chtlei.mydemo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chtlei on 18-10-9.
 */

public class GetDeviceInfosActivity extends Activity {
    private static final String TAG = "GetDeviceInfosActivity";
    private ListView listView;
    private DeviceListAdapter deviceListAdapter;
    private List<DeviceItem> listItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_device_infos);

        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initListItem();

        initView();
    }

    private void initView(){
        listView = findViewById(R.id.my_list_view);

        deviceListAdapter = new DeviceListAdapter(this,listItem);
        listView.setAdapter(deviceListAdapter);
    }

    private void initListItem(){
        listItem = new ArrayList<>();

        DeviceItem deviceManufacturer = new DeviceItem("生产商家",DeviceInfoUtil.getDeviceManufacturer());
        listItem.add(deviceManufacturer);

        DeviceItem deviceProduct = new DeviceItem("产品名称",DeviceInfoUtil.getDeviceProduct());
        listItem.add(deviceProduct);

        DeviceItem deviceBrand = new DeviceItem("手机品牌",DeviceInfoUtil.getDeviceBrand());
        listItem.add(deviceBrand);

        DeviceItem deviceModel = new DeviceItem("手机型号",DeviceInfoUtil.getDeviceModel());
        listItem.add(deviceModel);

        DeviceItem deviceBoard = new DeviceItem("手机主板名",DeviceInfoUtil.getDeviceBoard());
        listItem.add(deviceBoard);

        DeviceItem deviceDevice = new DeviceItem("设备名",DeviceInfoUtil.getDeviceDevice());
        listItem.add(deviceDevice);

        DeviceItem deviceSerial = new DeviceItem("手机硬件序列号",DeviceInfoUtil.getDeviceSerial());
        listItem.add(deviceSerial);

        DeviceItem deviceSDK = new DeviceItem("Android 系统SDK",DeviceInfoUtil.getDeviceSDK() + "");
        listItem.add(deviceSDK);

        DeviceItem deviceAndroidVersion = new DeviceItem("获得Android 版本",DeviceInfoUtil.getDeviceAndroidVersion());
        listItem.add(deviceAndroidVersion);

        DeviceItem deviceDefaultLanguage = new DeviceItem("当前手机系统语言",DeviceInfoUtil.getDeviceDefaultLanguage());
        listItem.add(deviceDefaultLanguage);

        DeviceItem deviceSimOperatorName = new DeviceItem("手机运营商",DeviceInfoUtil.getDeviceSimOperatorName(this));
        listItem.add(deviceSimOperatorName);

        DeviceItem deviceWidth = new DeviceItem("设备宽度",DeviceInfoUtil.getDeviceWidth(this) + "");
        listItem.add(deviceWidth);

        DeviceItem deviceHeight = new DeviceItem("设备高度",DeviceInfoUtil.getDeviceHeight(this) + "");
        listItem.add(deviceHeight);

        DeviceItem deviceIMEI = new DeviceItem("IMEI",DeviceInfoUtil.getIMEI(this));
        listItem.add(deviceIMEI);

        DeviceItem devicePhoneNumber = new DeviceItem("手机号",DeviceInfoUtil.getPhoneNumber(this));
        listItem.add(devicePhoneNumber);

        DeviceItem deviceDip = new DeviceItem("手机分辨率",DeviceInfoUtil.getDeviceDip(this) + "");
        listItem.add(deviceDip);

        DeviceItem deviceStorageInfoINI = new DeviceItem("内置存储",DeviceInfoUtil.getDeviceStorageInfo(this,DeviceInfoUtil.INTERNAL_STORAGE));
        listItem.add(deviceStorageInfoINI);

        DeviceItem deviceStorageInfoEXT = new DeviceItem("外置存储",DeviceInfoUtil.getDeviceStorageInfo(this,DeviceInfoUtil.EXTERNAL_STORAGE));
        listItem.add(deviceStorageInfoEXT);

        DeviceItem deviceRAM = new DeviceItem("RAM存储",DeviceInfoUtil.getDeviceRAMInfo(this));
        listItem.add(deviceRAM);
    }

    private void checkPermission(){
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE};
            requestPermissions(permissions,1);
        }
    }
}
