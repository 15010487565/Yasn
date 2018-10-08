package com.yasn.purchase.threelevelganged;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public abstract class BaseThreeActivity extends BaseYasnActivity implements AdapterView.OnItemClickListener, OnWheelChangedListener{

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    protected String[] mProvinceId;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mCitisIdMap = new HashMap<String, String[]>();

    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictIdMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    protected String mCurrentProviceId;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    protected String mCurrentCityId;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";
    protected String mCurrentDistrictId;

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    protected LinearLayout address_select;

    protected void setUpViews() {
        address_select = (LinearLayout) findViewById(R.id.ll_address_select);

        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        setUpListener();
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        //
        TextView btn_confirm = (TextView) findViewById(R.id.btn_confirm);
        TextView btn_off = (TextView) findViewById(R.id.btn_off);
        btn_confirm.setOnClickListener(this);
        btn_off.setOnClickListener(this);
    }
    protected void setUpData(List<CityListAllModel.ListRegionsBean> provinceList) {
        initProvinceDatas(provinceList);
//        Log.e("TAG_城市列表","mProvinceDatas="+mProvinceDatas.length);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        if (mProvinceDatas != null) {
            updateCities();
            updateAreas();
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    protected void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        Log.e("TAG_","pCurrent="+pCurrent);
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        mCurrentCityId = mCitisIdMap.get(mCurrentProviceName)[pCurrent];
        Log.e("TAG_省市区","市ID="+mCurrentCityId);
        mViewDistrict.setVisibility(View.VISIBLE);
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
//        Log.e("TAG_城市列表","areas="+areas.length);
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);

        if (mDistrictDatasMap == null || mDistrictDatasMap.size() == 0) {
            mViewDistrict.setVisibility(View.GONE);
        } else {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            mCurrentDistrictId = mDistrictIdMap.get(mCurrentCityName)[0];
            Log.e("TAG_省市区","区ID="+mCurrentDistrictId);
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    protected void updateCities() {
        String[] cities = null;
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        mCurrentProviceId = mProvinceId[pCurrent];
        Log.e("TAG_省市区","省ID="+mCurrentProviceId);
        if (mCitisDatasMap == null) {
            cities = new String[]{""};
        } else {
            cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
        }
//        Log.e("TAG_城市列表","cities="+cities.length);
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

//    public boolean checkupPermissions(String... permissions) {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            PermissionsActivity.startActivityForResult(this, PERMISSIONS_GRANTED, permissions);
//            return false;
//        } else {
//            Log.e("TAG_获取列表", "已执行");
//            Map<String, Object> params = new HashMap<String, Object>();
//            //获取城市雷列表
//            params.put("uid", "1");
//            okHttpGet(101, GlobalParam.ALLCITYLIST, params);
//            return true;
//        }
//
//    }

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            File path = new File(Environment.getExternalStorageDirectory(),
                    "province_data.xml");
            FileInputStream input = new FileInputStream(path);
            //        AssetManager asset = getAssets();
//            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && provinceList.size() > 0) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    if (districtList != null && !districtList.isEmpty()) {
                        mCurrentDistrictName = districtList.get(0).getName();
                        mCurrentZipCode = districtList.get(0).getZipcode();
                    }
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                ProvinceModel provinceModel = provinceList.get(i);
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    if (districtList != null && districtList.size() > 0) {
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                        for (int k = 0; k < districtList.size(); k++) {
                            // 遍历市下面所有区/县的数据
                            DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(),1);
                            // 区/县对于的邮编，保存到mZipcodeDatasMap
                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            distrinctArray[k] = districtModel;
                            distrinctNameArray[k] = districtModel.getName();
                        }
                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    protected void initProvinceDatas(List<CityListAllModel.ListRegionsBean> provinceList) {

        try {
            //*/ 初始化默认选中的省、市、区
//            initDefault(provinceList);
            //*/
            mProvinceDatas = new String[provinceList.size()];
            mProvinceId = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                CityListAllModel.ListRegionsBean listRegionsBean = provinceList.get(i);
                mProvinceDatas[i] = listRegionsBean.getLocal_name();
                mProvinceId[i] = listRegionsBean.getRegion_id()+"";
//                Log.e("TAG_省市区map","mProvinceId="+mProvinceId.toString());
                List<CityListAllModel.ListRegionsBean.CityBean> cityList = provinceList.get(i).getCity();
                String[] cityNames = new String[cityList.size()];
                String[] cityIds = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    CityListAllModel.ListRegionsBean.CityBean cityBean = cityList.get(j);
                    cityNames[j] = cityBean.getLocal_name();
                    cityIds[j] = cityBean.getRegion_id()+"";
                    List<CityListAllModel.ListRegionsBean.CityBean.AreaBean> districtList = cityList.get(j).getArea();
                    if (districtList != null && districtList.size() > 0) {
                        String[] distrinctNameArray = new String[districtList.size()];
                        String[] distrinctIds = new String[districtList.size()];
                        for (int k = 0; k < districtList.size(); k++) {
                            // 遍历市下面所有区/县的数据
                            CityListAllModel.ListRegionsBean.CityBean.AreaBean areaBean = districtList.get(k);
                            String local_name = areaBean.getLocal_name();
//                            int region_id = areaBean.getRegion_id();
//                            DistrictModel districtModel = new DistrictModel(local_name, region_id);
                            // 区/县对于的邮编，保存到mZipcodeDatasMap
//                            mZipcodeDatasMap.put(local_name, districtList.get(k).getRegion_id()+"");
//                            distrinctArray[k] = districtModel;
                            distrinctNameArray[k] = local_name;
                            int region_id = areaBean.getRegion_id();
                            distrinctIds[k] = region_id+"";
                        }
                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictIdMap.put(cityNames[j], distrinctIds);
//                        Log.e("TAG_省市区map","mDistrictIdMap="+mDistrictIdMap.toString());
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisIdMap.put(mProvinceDatas[i],cityIds);
//                Log.e("TAG_省市区map","mCitisIdMap="+mCitisIdMap.toString());
                mCitisDatasMap.put(mProvinceDatas[i], cityNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void initDefault(List<CityListAllModel.ListRegionsBean> provinceList) {
        if (provinceList != null && provinceList.size() > 0) {
            CityListAllModel.ListRegionsBean provincedataBean = provinceList.get(0);
            mCurrentProviceName = provincedataBean.getLocal_name();
            mCurrentProviceId = provincedataBean.getRegion_id()+"";
            Log.e("TAG_省市区","初始省ID="+mCurrentProviceId);
            List<CityListAllModel.ListRegionsBean.CityBean> cityList = provincedataBean.getCity();
            if (cityList != null && !cityList.isEmpty()) {
                CityListAllModel.ListRegionsBean.CityBean cityBean = cityList.get(0);
                mCurrentCityName = cityBean.getLocal_name();
                mCurrentCityId = cityBean.getRegion_id()+"";
                Log.e("TAG_省市区","初始市ID="+mCurrentCityId);
                List<CityListAllModel.ListRegionsBean.CityBean.AreaBean> districtList = cityBean.getArea();
                if (districtList != null && !districtList.isEmpty()) {
                    CityListAllModel.ListRegionsBean.CityBean.AreaBean districtModel = districtList.get(0);
                    mCurrentDistrictName = districtModel.getLocal_name();
                    mCurrentDistrictId = districtModel.getRegion_id()+"";
                    Log.e("TAG_省市区","初始区ID="+mCurrentDistrictId);
//                        mCurrentZipCode = districtModel.getZipcode();
                }
            }
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
//        if (returnCode == 200) {
//            switch (requestCode) {
//
//                case 101:
//                    try {
//                        CityListAllModel cityallinfo = JSON.parseObject(returnData, CityListAllModel.class);
//                        creatCityList(cityallinfo);
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            int REQUEST_CODE_CONTACT = 101;
//                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                            //验证是否许可权限
//                            for (String str : permissions) {
//                                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                                    //申请权限
//                                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                                    return;
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        } else {
//            ToastUtil.showToast(returnMsg);
//        }
    }

//    @Override
//    public void onCancelResult() {
//
//    }
//
//    @Override
//    public void onErrorResult(int errorCode, IOException errorExcep) {
//
//    }
//
//    @Override
//    public void onParseErrorResult(int errorCode) {
//
//    }
//
//    @Override
//    public void onFinishResult() {
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        Log.e("TAG_读写权限", "requestCode=" + requestCode);
//        switch (requestCode) {
//            case PERMISSIONS_GRANTED:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    //获取城市雷列表
//                    params.put("uid", "1");
//                    okHttpGet(101, GlobalParam.ALLCITYLIST, params);
//                } else {
//                    // Permission Denied
//                    ToastUtil.showToast("请允许相关权限再进行下一步操作！");
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

//    public void creatCityList(CityListAllModel cityallinfo) {
//        try {
//
//            File file = new File(Environment.getExternalStorageDirectory(),
//                    "province_data.xml");
//            if (file.exists()) {
//                file.delete();
//            } else {
//                file.mkdir(); //如果不存在则创建
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            // 获得一个序列化工具
//            XmlSerializer serializer = Xml.newSerializer();
//            serializer.setOutput(fos, "utf-8");
//            // 设置文件头
//            serializer.startDocument("utf-8", true);
//            serializer.startTag(null, "root");
//
//            //省集合
//            List<CityListAllModel.DataBean> provincedata = cityallinfo.getData();
//            if (provincedata != null && provincedata.size() > 0) {
//                for (int i = 0, j = provincedata.size(); i < j; i++) {
//                    CityListAllModel.DataBean provincedataBean = provincedata.get(i);
//                    //省名字
//                    String proviceName = provincedataBean.getProviceName();
//                    serializer.startTag(null, "province");
//                    serializer.attribute(null, "name", proviceName);
//                    //市集合
//                    List<CityListAllModel.DataBean.CityBean> citydata = provincedataBean.getCity();
//                    if (citydata != null && citydata.size() > 0) {
//                        for (int k = 0, l = citydata.size(); k < l; k++) {
//                            CityListAllModel.DataBean.CityBean cityBean = citydata.get(k);
//                            //市名字
//                            String cityName = cityBean.getCityName();
//                            serializer.startTag(null, "city");
//                            serializer.attribute(null, "name", cityName);
//                            //区集合
//                            List<CityListAllModel.DataBean.CityBean.AreaBean> area = cityBean.getArea();
//                            if (area != null && area.size() > 0) {
//                                for (int m = 0, n = area.size(); m < n; m++) {
//                                    //区名字
//                                    CityListAllModel.DataBean.CityBean.AreaBean areaBean = area.get(m);
//                                    String areaName = areaBean.getAreaName();
//                                    serializer.startTag(null, "district");
//                                    serializer.attribute(null, "name", areaName);
//                                    serializer.attribute(null, "zipcode",
//                                            String.valueOf(i) + String.valueOf(k) + "000");
//                                    serializer.endTag(null, "district");
//                                }
//                            }
//                            serializer.endTag(null, "city");
//                        }
//                    }
//                    serializer.endTag(null, "province");
//                }
//            }
//            serializer.endTag(null, "root");
//            serializer.endDocument();
//            fos.close();
//            Log.e("TAG_城市列表", "写入成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TAG_城市列表", "写入失败");
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        adapter.setSeclection(i);
//        adapter.notifyDataSetChanged();
        if (i == 0) {
//            Intent intent = new Intent(this, MeInfoActivity.class);
//            intent.putExtra("MEINFOHEAD",image_head);
//            intent.putExtra("NICKNAME",nickname);
//            intent.putExtra("SEX",sex);
//            intent.putExtra("USERBIRTHDAY",userbirthday);
//            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        } else if (i == 1) {
//            startActivity(new Intent(this, MyPetActivity.class));
        } else if (i == 2) {
//            startActivity(new Intent(this, MyPetIntegralTaskActivty.class));
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
           mCurrentDistrictId = mDistrictIdMap.get(mCurrentCityName)[newValue];
            Log.e("TAG_省市区","区ID="+mCurrentDistrictId);
        }
    }
}
