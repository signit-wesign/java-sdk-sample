package sample;

import cn.signit.sdk.pojo.response.IdentityVerifyResoponse;
import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.OauthData;
import cn.signit.sdk.pojo.request.PersonVerifyRequest;
import cn.signit.sdk.pojo.request.PhoneVerifyRequest;
import cn.signit.sdk.pojo.response.PersonVerifyResponse;
import cn.signit.sdk.type.TokenType;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 企业实名认证调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行个人实名认证，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class PhoneVerifyDemo {
    public static void main(String[] args) {
        String appSecretKey = "sk01ab341ddff3c856de88ef2b57554fc9";
        String appId = "18160e08f5422aac68e38ec5c51";// 国信易企签科技有限公司
        // String appUrl = "https://open.signit.cn/v1/open/verifications/person";
        String appUrl = "https://wesign-prod-open.signit.vip:10443/v1/open/authentications/phone-authentication/verify";//生产环境使用上面的链接

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        // 测试环境需要手动设置oauthUrl，生产环境不用设置
        client.setOauthUrl("https://wesign-prod-open.signit.vip:10443/v1/oauth/oauth/token");
        // step2: 使用SDK封装实名认证请求
        PhoneVerifyRequest request = verifyPhoneParam();
        System.out.println("\nrequest is:\n\n " + JSON.toJSONString(request, true));
        // step3: 执行请求,获得响应
        IdentityVerifyResoponse response = null;
        try {
            OauthData oauth = client.getOauthData(appId, appSecretKey, TokenType.CLIENT_CREDENTIALS, true);
            response = client.execute(request);
        } catch (SignitException e) {
            ErrorResp errorResp = null;
            if (FastjsonDecoder.isValidJson(e.getMessage())) {
                errorResp = FastjsonDecoder.decodeAsBean(e.getMessage(), ErrorResp.class);
                System.out.println("\nerror response is:\n" + JSON.toJSONString(errorResp, true));
            } else {
                System.out.println("\nerror response is:\n" + e.getMessage());
            }
        }
        System.out.println("\nresponse is:\n" + JSON.toJSONString(response, true));
    }

    static PhoneVerifyRequest verifyPhoneParam() {
        PhoneVerifyRequest phoneVerifyRequest = new PhoneVerifyRequest();
        phoneVerifyRequest.setPhone("15308082360");
        phoneVerifyRequest.setName("阿拉啦");
        phoneVerifyRequest.setIdCardNo("411322199502280317");
        phoneVerifyRequest.setEnableNumberPortability(true);
        return phoneVerifyRequest;
    }

}
