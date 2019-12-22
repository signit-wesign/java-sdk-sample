package webhook.response;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.pojo.webhook.response.CountersignatureSignCompleted;
import cn.signit.sdk.pojo.webhook.response.EnterpriseVerificationCompleted;
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
public class ParseCountersignatureSignCompletedDemo {
    public static void main(String[] args) {

        String personVerifyWebhookRespStr = "{\"event\":\"countersignatureSignCompleted\",\"target\":{\"webhookWsid\":\"WSID_HOOK_0000016aa4e050ad3eebbcfde3dd0001\",\"destination\":\"http://10.10.9.182:8084/3becd244-9f02-4d0b-9d1d-a53f127c0f7f\"},\"rawData\":\"{\\\"code\\\":\\\"100550000\\\",\\\"message\\\":\\\"\\\\u8BF7\\\\u6C42\\\\u6210\\\\u529F\\\",\\\"customTag\\\":\\\"this_is_test_1\\\",\\\"invokeNo\\\":\\\"201909191641132796001724472001\\\",\\\"url\\\":\\\"http://112.44.251.136:61112/WSID_LINK_0000016d48aedd94fe56062cd8770001/download-file?token=25f253a0c672490bb1ef56fcb232bcbb\\\",\\\"links\\\":[]}\",\"needCallBack\":false}";
        String appSecretKey = "sk5f9e87967150fe485d5f047cc300c2ce";
        String appId = "16aa4db191c3eebbcfde3dd95a1";
        // webhook响应header中x-signit-signature
        String signitSignature = "HmacSHA512 16aa4db191c3eebbcfde3dd95a1:jX8jE3dN521R+7CSu1TG0x6gytL1P1pobpGg6/TY39kJJh7zhYhg2QUMHbrr59z18xnqDbR8GsbwtsygYUntQQ==";
        // webhook响应header中x-signit-nonce
        String nonce = "316Ho3Kfw4SX6r53Fk60385C";
        // webhook响应header中x-signit-date
        String dataString = "Thu Sep 19 16:32:22 CST 2019";
        // webhook响应header中host
        String host = "10.10.9.182:8084";

        // step1: 客户端验证服务端
        Boolean verifyResult = SignitClient.verify(signitSignature, appId, new HmacSignatureBuilder().scheme("http")
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
        // 个人实名认证提交
        case PERSON_VERIFICATION_SUBMITTED:
            break;
        // 个人实名认证完成
        case PERSON_VERIFICATION_COMPLETED:
            break;
        // 参与者正在处理信封
        case PARTICIPANT_HANDLING:
            break;
        // 快捷签署完成事件
        case QUICK_SIGN_COMPLETED:
            break;
        // 会签签署完成
        case COUNTERSIGNATURE_SIGN_COMPLETED:
            // ps:rawData的命名方式为事件名称转换的大驼峰命名，数据所在包为cn.signit.sdk.pojo.webhook.response，其获取的2种方式如下：
            CountersignatureSignCompleted rawData1 = (CountersignatureSignCompleted) ente.rawDataAsBean();
            boolean s = rawData1.isSuccess();
            // 法2：
            CountersignatureSignCompleted rawDat2 = ente.rawDataAsBean(CountersignatureSignCompleted.class);
            System.out.println("\nwebhookResponse rawData is :\n" + JSON.toJSONString(rawDat2, true));
        default:
            break;
        }

    }
}
