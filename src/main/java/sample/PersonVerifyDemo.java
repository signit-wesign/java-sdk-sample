package sample;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.IdCardImage;
import cn.signit.sdk.pojo.IdCardImageData;
import cn.signit.sdk.pojo.OauthData;
import cn.signit.sdk.pojo.request.PersonVerifyRequest;
import cn.signit.sdk.pojo.response.PersonVerifyResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.IdCardType;
import cn.signit.sdk.type.ImageCode;
import cn.signit.sdk.type.PersonAuthType;
import cn.signit.sdk.type.TokenType;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 企业实名认证调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行个人实名认证，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class PersonVerifyDemo {
    public static void main(String[] args) {
        String appSecretKey = "sk34acd7f913696b965288f1aabbcf19ad";
        String appId = "16a543b48463eebbcfde3dd32d1";// 国信易企签科技有限公司
        // String appUrl = "https://open.signit.cn/v1/open/verifications/enterprise";
        String appUrl = "http://112.44.251.136:2576/v1/open/verifications/person";//生产环境使用上面的链接

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        // 测试环境需要手动设置oauthUrl，生产环境不用设置
        client.setOauthUrl("http://112.44.251.136:2576/v1/oauth/oauth/token");
        // step2: 使用SDK封装实名认证请求
        PersonVerifyRequest request = verifyPersonParam();
        System.out.println("\nrequest is:\n\n " + JSON.toJSONString(request, true));
        // step3: 执行请求,获得响应
        PersonVerifyResponse response = null;
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

    static PersonVerifyRequest verifyPersonParam() {
        return PersonVerifyRequest.builder()
                .name("张波")
                .idCardNo("511112199409113012")
                .idCardType(IdCardType.SECOND_GENERATION_IDCARD)
                .phone("18380581554")
                .authModes(PersonAuthType.PHONE_AUTH)
                .idCardImages(IdCardImage.builder()
                        .imageName("法人身份证正面照")
                        .imageCode(ImageCode.ID_CARD_BACK)
                        .imageData(IdCardImageData.builder()
                                .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E8%BA%AB%E4%BB%BD%E8%AF%81%E6%AD%A3%E9%9D%A2%E6%BC%94%E7%A4%BA%E5%9B%BE%E7%89%87.jpeg")),
                        IdCardImage.builder()
                                .imageName("法人身份证正面照")
                                .imageCode(ImageCode.ID_CARD_FRONT)
                                .imageData(IdCardImageData.builder()
                                        .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E8%BA%AB%E4%BB%BD%E8%AF%81%E8%83%8C%E9%9D%A2%E6%BC%94%E7%A4%BA%E5%9B%BE%E7%89%87.jpeg")),
                        IdCardImage.builder()
                                .imageName("手持身份证人像面")
                                .imageCode(ImageCode.PERSON_HANDHELD_ID_CARD_BACK)
                                .imageData(IdCardImageData.builder()
                                        .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E6%89%8B%E6%8C%81%E8%BA%AB%E4%BB%BD%E8%AF%81%E4%BA%BA%E5%83%8F%E9%9D%A2%E7%A4%BA%E4%BE%8B%E5%9B%BE%E7%89%87.jpg")))
                .returnUrl("https://www.baidu.com")
                .acceptDataType(AcceptDataType.URL)
                .customTag("1 tag" + new Date().toString())
                .build();
    }
}
