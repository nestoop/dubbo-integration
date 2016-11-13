package com.example.mq;

import cn.inspiry.dm.business.device.vo.DeviceUpgradeVO;
import cn.inspiry.dm.ppcp.PpcpUpgradeService;
import cn.inspiry.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by botter
 *
 * @Date 11/11/16.
 * @description
 */
@Service
@RabbitListener(queues = RabbitMQConfig.MQ_UPGRADE_QUEUE)
public class ReceiverUpgradeMessageService {

    @Autowired
    private PpcpUpgradeService ppcpUpgradeService;

    @RabbitHandler
    public void receiverUpgradeMessage (Object message){
        Message upgradeMessage = (Message) message;

        System.out.println("upgrade message :" + new String(upgradeMessage.getBody()));
        String messageBodyJSON = new String(upgradeMessage.getBody());
        if (StringUtil.isEmpty(messageBodyJSON)) {
            return;
        }

        DeviceUpgradeVO deviceUpgradeVO = JSON.parseObject(messageBodyJSON, DeviceUpgradeVO.class);
        String appName = deviceUpgradeVO.getAppName();
        String appVersion = deviceUpgradeVO.getAppVersion();
        for (String serialNum : deviceUpgradeVO.getSerialNumList()) {
            ppcpUpgradeService.sendMsgPutSystemVersion(serialNum, appName, appVersion);
        }
    }
}
