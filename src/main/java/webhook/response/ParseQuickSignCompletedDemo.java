package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.QuickSignCompleted;
import cn.signit.sdk.pojo.webhook.response.WebhookResponse;
import cn.signit.sdk.type.WebhookEventType;
import cn.signit.sdk.util.HmacSignatureBuilder;

/**
 * 易企签 Java SDK 解析Webhook响应的示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK解析Webhook响应，示例代码中的数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class ParseQuickSignCompletedDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"quickSignCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_0000016a575d67db02426e4f9f9b0001\",\"destination\":\"https://webhook.site/78cfb508-7a94-4728-94a5-31b758206466\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"url\\\":\\\"http://10.10.9.67:61112/WSID_LINK_0000016a575e18fa02426e4f9f9b0001/download-file?token=8f72ee3e374f42b1bedbad47d6ef9fcc\\\",\\\"invokeNo\\\":\\\"201904260958572484001690651001\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk1c4c0b8dd9558615c2d0aa352c31e0fe";
        String appId = "16a575d060b02426e4f9f9b7d81";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 16a575d060b02426e4f9f9b7d81:w2GAJZl+myuJ5l6n1Agczo5oNPhXit3yuI+3zp96PEq/Kt/N8oWcTt8G8v35PsCTNg5ztFEzh5ZQ/B4/gmCgkQ==";
        // webhook响应header中x-signit-nonce
        String nonce = "l0Y47YI9EQ36c21KV3U1TzKQ";
        // webhook响应header中x-signit-date
        String dataString = "Fri Apr 26 09:59:05 CST 2019";
        // webhook响应header中host
        String host = "webhook.site";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId, new HmacSignatureBuilder().scheme("https")
                .apiKey(appId)
                .apiSecret(appSecretKey.getBytes())
                .method("POST")
                .payload(personVerifyWebhookRespStr.getBytes())
                .contentType("application/json")
                .host(host)
                .resource("")
                .nonce(nonce)
                .date(dataString));
        System.out.println("\n\nthe result of hmac verify is " + verifyResult);

        // step2: 解析webhook响应数据
        WebhookResponse ente = SignitClient.parseWebhookResponse(personVerifyWebhookRespStr);
        System.out.println("\nwebhookResponse is :\n" + JSON.toJSONString(ente, true));

        // step3: 解析获取rawData
        switch (WebhookEventType.parse(ente.getEvent())) {
        // 企业实名认证提交
        case ENTERPRISE_VERIFICATION_SUBMITTED:
            break;
        // 企业实名认证初级完成
        case ENTERPRISE_VERIFICATION_PRIMARY_COMPLETED:
            break;
        // 企业实名认证已打款
        case ENTERPRISE_VERIFICATION_PAID:
            break;
        // 企业实名认证完成
        case ENTERPRISE_VERIFICATION_COMPLETED:
            break;
        // 信封流程完成
        case ENVELOPE_COMPLETED:
            break;
        // 信封流程启动
        case ENVELOPE_STARTED:
            break;
        // 参与者确认
        case PARTICIPANT_CONFIRMED:
            break;
        // 参与者拒绝
        case PARTICIPANT_REJECTED:
            break;
        //个人实名认证提交
        case PERSON_VERIFICATION_SUBMITTED:
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        // 参与者正在处理信封
        case PARTICIPANT_HANDLING:
            break;
        //快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            QuickSignCompleted rawData3 = (QuickSignCompleted) ente.rawDataAsBean();
            boolean s3 = rawData3.isSuccess();
            // 法2：
            QuickSignCompleted rawDat4 = ente.rawDataAsBean(QuickSignCompleted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawData3, true));
            break;
        default:
            break;
        }

    }
}
