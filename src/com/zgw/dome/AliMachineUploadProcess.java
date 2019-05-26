package com.zgw.dome;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AntMerchantExpandAutomatApplyUploadModel;
import com.alipay.api.domain.SmartAddressInfo;
import com.alipay.api.request.AntMerchantExpandAutomatApplyUploadRequest;
import com.alipay.api.response.AntMerchantExpandAutomatApplyUploadResponse;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 〈ali机具入驻〉
 * @author gw.Zeng
 * @create 2019/5/23
 * @since 1.0.0
 */
public class AliMachineUploadProcess extends Thread {
    private Logger logger = Logger.getLogger(AliMachineUploadProcess.class);
    private  IStoreService iStoreService;

    private static AliMachineUploadProcess processIntance;
    private static boolean initialized =false;
    private static   LinkedBlockingQueue<AliMachineUploadBean> deviceInfo = new LinkedBlockingQueue<>();

    public AliMachineUploadProcess(IStoreService iStoreService) {
        this.iStoreService = iStoreService;
       synchronized (AliMachineUploadProcess.class){
           if (initialized ==false){
               initialized =!false;
           }else {
               throw new RuntimeException("已初始化单例");
           }
       }
    }
    @Override
    public void run() {
        while (true){
            try {
                AliMachineUploadBean deviceInfo = this.deviceInfo.take();
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "",
                       "","json","GBK","","RSA2");
                AntMerchantExpandAutomatApplyUploadRequest request = new AntMerchantExpandAutomatApplyUploadRequest();
                AntMerchantExpandAutomatApplyUploadModel model = new AntMerchantExpandAutomatApplyUploadModel();
//                model.setTerminalId(deviceInfo.getDeviceNo());
                model.setMerchantUserId("2088621196133228");
                model.setProductUserId("2088621196133228");
                model.setMachineType("ROCKING_MACHINE");
                model.setMachineCooperationType("COOPERATION_REFORM");
                model.setMachineDeliveryDate(new Date());
                model.setMachineName("***科技");
                SmartAddressInfo deliveryAddress = new SmartAddressInfo();
                deliveryAddress.setProvinceCode(330000L);
                deliveryAddress.setAreaCode(330110L);
                deliveryAddress.setCityCode(330100L);
                deliveryAddress.setMachineAddress("浙江省杭州市余杭区***********");
                model.setDeliveryAddress(deliveryAddress);
                SmartAddressInfo addressInfo = new SmartAddressInfo();
//                addressInfo.setProvinceCode(deviceInfo.getProvinceCode());
//                addressInfo.setAreaCode(deviceInfo.getAreaCode());
//                addressInfo.setCityCode(deviceInfo.getCityCode());
//                addressInfo.setMachineAddress(deviceInfo.getMachineAddress());
                model.setPointPosition(addressInfo);
                model.setMerchantUserType("ISV");
                request.setBizModel(model);
//                logger.info("机具入驻  入参model："+ JsonHelper.bean2json(model));
                AntMerchantExpandAutomatApplyUploadResponse response = alipayClient.execute(request);
//                logger.info("机具入驻  出参response："+ JsonHelper.bean2json(response));
                if(response.isSuccess()){
//                    iStoreService.setDeviceUpload(deviceInfo.getDeviceNo());
                    logger.info("机具入驻   成功：" );
                } else {
                    logger.info("机具入驻   失败：" );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }

    }
    public void MachineUpLoad(AliMachineUploadBean machineUploadBean) {
        deviceInfo.add(machineUploadBean);
    }
    public static  final  AliMachineUploadProcess getInstance(IStoreService iStoreService){
        if(processIntance == null){
            return new  AliMachineUploadProcess( iStoreService) ;
        }else {
            return processIntance;
        }

    }

}
