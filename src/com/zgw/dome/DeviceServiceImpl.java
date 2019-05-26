/**
 * Copyright(C) 2017 Hangzhou Xiaozhu Technology Co., Ltd. All rights reserved.
 */
package com.zgw.dome;



import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DeviceServiceImpl {
    private  static Logger logger;
    private final static String RANK_COIN_TYPE = "RANK_COIN_TYPE";
    private final static String RANK_CHECK_OUT_TYPE = "RANK_CHECK_OUT_TYPE";

    private static AliMachineUploadProcess aliMachineUploadProcess;
    IStoreService iStoreService;
    private volatile boolean initializeFlag = false;

    private void initialize() {
        aliMachineUploadProcess = AliMachineUploadProcess.getInstance(iStoreService);
        aliMachineUploadProcess.setName("AliMachineUploadProcessThread");
        aliMachineUploadProcess.start();
    }


    public Map addDevice(Map inData) throws Exception {
        logger.info("绑定设备:" + inData);
        Map retMap = new HashMap<>();
        Integer storeId=0;
        String deviceNo="";
        Integer comboId;
        Integer isSupervise;    //该字段不传
        Integer deviceTypeId;//设备类型
        String deviceName;//设备名称
        Integer infoId;//设备型号ID
        JSONArray list;        //套餐信息list
        Integer isAutoRefund; //是否自动退币0：自动  1：手动',
        try {
            JSONObject job =  null;
            storeId = Integer.parseInt(job.getString("storeId"));
            deviceNo = job.getString("deviceNo");
            comboId = Integer.parseInt(job.getString("comboId"));
            deviceTypeId = Integer.parseInt(job.getString("deviceTypeId"));
            deviceName = job.get("devName") == null ? "" : job.getString("devName");
//            infoId = job.get("infoId") == null ? 0 : job.getInt("infoId");
////            isAutoRefund = job.get("isAutoRefund") == null ? 0 : job.getInt("isAutoRefund");
        } catch (Exception e) {
//            return handlerMsg(RETINFO.RET_CODE_ERROR, "输入数据不合法");
        }

        /*code...*/

        HashMap deviceMap = new HashMap();
        /*code...*/

        boolean isActive = deviceMap.get("active_time") != null;


        if (!isActive && Integer.parseInt(deviceMap.get("device_type").toString()) > 10) {
            //支付宝设备 第一次激活机具入驻
            if (!initializeFlag) {
                initializeFlag = true;
                initialize();
            }
            List storeInfo = execSqlQueryList("select id,name,location_code,addr from c_store_tbl where id=?", storeId);
            if (storeInfo.size() > 0) {
                Map storeInfoMap = (Map) storeInfo.get(0);
                AliMachineUploadBean machineUploadBean = new AliMachineUploadBean(Integer.parseInt(deviceMap.get("devid").toString()),
                        deviceNo, storeId, Long.parseLong(storeInfoMap.get("location_code").toString()), storeInfoMap.get("addr").toString());
                aliMachineUploadProcess.MachineUpLoad(machineUploadBean);
            }
        }
        /*code...*/
        return retMap;
    }

    private List execSqlQueryList(String s, Integer storeId) {
        return null;
    }
    /*code...*/
}
