package com.example.lab.android.nuc.law_analysis.communication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lab.android.nuc.law_analysis.R;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private MyLocationConfiguration.LocationMode mLocationMode;
    private TextView positionText;
    /*自定义图标*/
    private BitmapDescriptor mIconLocation;
    private float mCurrentX;
    private boolean isLastTrackExist = true;
    //让地图显示出来
    private MapView mapView;
    //移动到我的位置
    private BaiduMap baiduMap;
    private boolean isFirstLacate = true;

    private StringBuilder currentPosition = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让地图显示出来.这一步要写在setContentView之前
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        //让地图显示出来.这一步要写在setContentView之后
        mapView = (MapView) findViewById( R.id.bmapView);
        //移动到我的位置
        baiduMap = mapView.getMap();
        baiduMap.setMapType( BaiduMap.MAP_TYPE_NORMAL );
        View child = mapView.getChildAt( 1 );
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility( View.INVISIBLE );
        }
        //首先创建了一个LocationClient示例，并且调用getApplicationContext()获取一个全局的Context参数并传入
        mLocationClient = new LocationClient(getApplicationContext());
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo( 18.0f );
        baiduMap.setMapStatus( msu );

        //显示我的位置功能开启
        baiduMap.setMyLocationEnabled(true);
        //首先创建了一个LocationClient示例，并且调用getApplicationContext()获取一个全局的Context参数并传入
        mLocationClient = new LocationClient(getApplicationContext());
        //调用LocationClient的registerLocationListener方法来注册一个定位监听器
        mLocationClient.registerLocationListener(new MyLocationListener());
        positionText = (TextView) findViewById(R.id.position_text_view);


        //由于要一次性申请3个权限，所以首先创建一个空的List集合
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this,Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this,Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            //然后调用requestPermissions方法一次性申请
            ActivityCompat.requestPermissions(LocationActivity.this,permissions,1);
        }else {
            requestLocation();
        }
    }

    //新建方法调用start()方法开始定位
    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }


    //5秒更新一下位置
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
//        //只使用GPS进行定位
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //更简单易懂的定位显示
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    //设置让mapView随着Activity的生命周期开启跟关闭
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //摧毁活动之后不再定位，减少手机电量的消耗
        mLocationClient.stop();
        mapView.onDestroy();

        //显示我的位置功能关闭
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if (grantResults.length > 0){
                    //循环遍历
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            currentPosition.append( "我现在的位置是 : " );
            currentPosition.append("").append(bdLocation.getCountry()).append("  ");
            currentPosition.append("").append(bdLocation.getProvince()).append("  ");
            currentPosition.append("").append(bdLocation.getCity()).append("  ");
            currentPosition.append( "" ).append( bdLocation.getDistrict() ).append( "  " );
            currentPosition.append("").append(bdLocation.getStreet()).append("  ");

            currentPosition.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");
            }else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络");
            }
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation ||
                    bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                navigateTo(bdLocation);
            }
            positionText.setText(currentPosition);
        }
    }

    private void navigateTo(BDLocation location){
        //先是将BDLocation内的经纬度封装到LatLng里面
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());

        //这里将缩放级别改为了16倍
        MapStatusUpdate update =  MapStatusUpdateFactory.zoomTo(16);


        //接着将返回的MapStatusUpdate对象作为参数传入到baiduMap的animateMapStatus()方法中
        baiduMap.animateMapStatus(update);

        //然后调用MapStatusUpdateFactory的newLatLng()方法将LatLng对象传入
        update = MapStatusUpdateFactory.newLatLng(ll);

        //再次将返回的MapStatusUpdate对象作为参数传入到baiduMap的animateMapStatus()方法中
        baiduMap.animateMapStatus(update);

        //MyLocationData.Builder该类使用来封装设备所在位置的
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();

        //设置它的经纬度信息
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());

        //调用locationBuilder的build()方法，生成MyLocationData实例
        MyLocationData locationData = locationBuilder.build();

        //然后将该实例传入到baiduMap的setMyLocationData()方法中,就可以显示设备当前所在的位置了
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra( ChatActivity.CHAT_LOCATION,currentPosition.toString() );
        setResult( RESULT_OK,intent );
        super.onBackPressed();
        finish();
    }
}
