package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.EnterpriseAgent;
import cn.signit.sdk.pojo.EnterpriseBankCardInfo;
import cn.signit.sdk.pojo.EnterpriseLegalPerson;
import cn.signit.sdk.pojo.IdCardImage;
import cn.signit.sdk.pojo.IdCardImageData;
import cn.signit.sdk.pojo.request.EnterpriseVerifyRequest;
import cn.signit.sdk.pojo.response.EnterpriseVerifyResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.EnterpriseAuthType;
import cn.signit.sdk.type.IdCardType;
import cn.signit.sdk.type.ImageCode;

/**
 * 易企签 Java SDK 企业实名认证调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行企业实名认证，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class EnterpriseVerifyDemo {
    public static void main(String[] args) throws SignitException {
        // https://webhook.site
        // webhook 地址 https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06
        String appSecretKey = "sk23881d0f62799a8a2353c14258136a96";
        String appId = "167aba734840242ac1300069ed1";
        // String appUrl = "http://localhost:7830/verifications/enterprise";
        // String appUrl =
        // "http://112.44.251.136:2576/v1/open/verifications/enterprise";
        String appUrl = "http://10.10.9.70/v1/open/verifications/enterpise";

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        // 测试环境需要手动设置oauthUrl，生产环境不用设置
        // client.setOauthUrl("http://10.10.9.147:2576/v1/oauth/oauth/token");
        client.setOauthUrl("http://10.10.9.70:2576/v1/oauth/oauth/token");
        // step2: 使用SDK封装实名认证请求
        EnterpriseVerifyRequest request = verifyUseLegelPersonWithLeastParams();
        System.out.println("\nrequest is:\n\n " + JSON.toJSONString(request, true));
        // step3: 执行请求,获得响应
        EnterpriseVerifyResponse response = client.execute(request);
        System.out.println("\nresponse is:\n" + JSON.toJSONString(response, true));

    }

    public static EnterpriseVerifyRequest verifyUseLegelPersonWithLeastParams() {
        return EnterpriseVerifyRequest.builder()
                .name("王五的最后一次真的真的真的是呀")
                .authType(EnterpriseAuthType.LEGAL_PERSON)
                .legalPerson(EnterpriseLegalPerson.builder()
                        .name("王五")
                        .idCardNo("511112199409113015")
                        .idCardType(IdCardType.SECOND_GENERATION_IDCARD)
                        .phone("18583506019")
                // .idCardImages(IdCardImage.builder()
                // .imageName("法人身份证正面照")
                // .imageCode(ImageCode.AGENT_ID_CARD_FRONT)
                // .imageData(IdCardImageData.builder()
                // .url("https://raw.githubusercontent.com/zhangbo1416694870/file-system/master/zhangbo.png")),
                // IdCardImage.builder()
                // .imageName("法人身份证正面照")
                // .imageCode(ImageCode.AGENT_ID_CARD_FRONT)
                // .imageData(IdCardImageData.builder()
                // .url("https://raw.githubusercontent.com/zhangbo1416694870/file-system/master/zhangbo.png")))
                )
                .unifiedSocialCode("91510700595072782J")
                .businessLicenceImage(IdCardImage.builder()
                        .imageName("营业执照图片")
                        .imageCode(ImageCode.BUSINESS_LICENCE)
                        .imageData(IdCardImageData.builder()
                                .url("https://raw.githubusercontent.com/zhangbo1416694870/file-system/master/zhangbo.png")))
                .bankCardInfo(EnterpriseBankCardInfo.builder()
                        .bankCardNo("6228480489080786572")
                        .bankBranch("绵阳市涪城区西科大支行")
                        .bank("中国农业银行"))
                .returnUrl("https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .acceptDataType(AcceptDataType.URL)
                .customTag("hello world legal People:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .build();

    }

    public static EnterpriseVerifyRequest verifyUseAgentWithLeastParams() {
        return EnterpriseVerifyRequest.builder()
                .name("王五的企业")
                .authType(EnterpriseAuthType.AGENT)
                .agent(EnterpriseAgent.builder()
                        .name("王五")
                        .idCardNo("510184199111217311")
                        .idCardType(IdCardType.SECOND_GENERATION_IDCARD)
                        .phone("13281522860")
                        .trustInstrumentImage(IdCardImage.builder()
                                .imageName("委托书照片")
                                .imageCode(ImageCode.AGENT_TRUST)
                                .imageData(IdCardImageData.builder()
                                        .url("http://img53.chem17.com/2/20130718/635097412512148201680.jpg")))
                // .idCardImages(IdCardImage.builder()
                // .imageName("经办人身份证正面照")
                // .imageCode(ImageCode.AGENT_ID_CARD_FRONT)
                // .imageData(IdCardImageData.builder()
                // .url("http://5b0988e595225.cdn.sohucs.com/images/20181127/433e8249175e4ac9ae0314ad9f6b6ac1.jpeg")),
                // IdCardImage.builder()
                // .imageName("经办人身份证正面照")
                // .imageCode(ImageCode.AGENT_ID_CARD_BACK)
                // .imageData(IdCardImageData.builder()
                // .url("http://img.mp.sohu.com/q_70,c_zoom,w_640/upload/20170704/83902139496349e2a6e88d2486150248_th.jpg")))
                )
                .unifiedSocialCode("91510700595072782J")
                .businessLicenceImage(IdCardImage.builder()
                        .imageName("营业执照图片")
                        .imageCode(ImageCode.BUSINESS_LICENCE)
                        .imageData(IdCardImageData.builder()
                                .url("https://raw.githubusercontent.com/zhangbo1416694870/file-system/master/zhangbo.png")))
                .bankCardInfo(EnterpriseBankCardInfo.builder()
                        .bankCardNo("6228480489080786572")
                        .bankBranch("绵阳市涪城区西科大支行")
                        .bank("中国农业银行"))
                .returnUrl("https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .acceptDataType(AcceptDataType.URL)
                .customTag("hello world agent:https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .build();

    }

}
