package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.EnterpriseAgent;
import cn.signit.sdk.pojo.EnterpriseBankCardInfo;
import cn.signit.sdk.pojo.EnterpriseLegalPerson;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.IdCardImage;
import cn.signit.sdk.pojo.IdCardImageData;
import cn.signit.sdk.pojo.request.EnterpriseVerifyRequest;
import cn.signit.sdk.pojo.response.EnterpriseVerifyResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.EnterpriseAuthType;
import cn.signit.sdk.type.IdCardType;
import cn.signit.sdk.type.ImageCode;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 企业实名认证调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行企业实名认证，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，appUrl均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class EnterpriseVerifyDemo {
    public static void main(String[] args) throws SignitException {
        String appSecretKey = "sk34acd7f913696b965288f1aabbcf19ad";
        String appId = "16a543b48463eebbcfde3dd32d1";
        String appUrl = "http://112.44.251.136:2576/v1/open/verifications/enterprise";

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        // 测试环境需要手动设置oauthUrl，生产环境不用设置
        client.setOauthUrl("http://112.44.251.136:2576/v1/oauth/oauth/token");
        // step2: 使用SDK封装实名认证请求
        EnterpriseVerifyRequest request = verifyUseLegelPersonWithLeastParams();
        System.out.println("\nrequest is:\n\n " + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        EnterpriseVerifyResponse response = null;
        try {
            response = client.execute(request);
        } catch (SignitException e) {
            if (FastjsonDecoder.isValidJson(e.getMessage())) {
                ErrorResp error = FastjsonDecoder.decodeAsBean(e.getMessage(), ErrorResp.class);
                System.out.println("\nerror rmresponse is:\n" + JSON.toJSONString(error, true));
            } else {
                System.out.println("\nerror rmresponse is:\n" + e.getMessage());
            }
        }
        System.out.println("\nresponse is:\n" + JSON.toJSONString(response, true));

    }

    public static EnterpriseVerifyRequest verifyUseLegelPersonWithLeastParams() {
        return EnterpriseVerifyRequest.builder()
                .name("河南韩起电气设备有限公司")
                .authType(EnterpriseAuthType.LEGAL_PERSON)
                .legalPerson(EnterpriseLegalPerson.builder()
                        .name("张宇")
                        .idCardNo("511324199110124864")
                        .idCardType(IdCardType.SECOND_GENERATION_IDCARD)
                        .phone("13281522860"))
                .unifiedSocialCode("91410103341757802J")
                .businessLicenceImage(IdCardImage.builder()
                        .imageName("营业执照演示图片.jpg")
                        .imageCode(ImageCode.BUSINESS_LICENCE)
                        .imageData(IdCardImageData.builder()
                                .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E8%90%A5%E4%B8%9A%E6%89%A7%E7%85%A7%E6%BC%94%E7%A4%BA%E5%9B%BE%E7%89%87.jpg")))
                .bankCardInfo(EnterpriseBankCardInfo.builder()
                        .bankCardNo("6228480489080786572")
                        .bankBranch("绵阳市涪城区西科大支行")
                        .bank("中国农业银行"))
                .returnUrl("https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .acceptDataType(AcceptDataType.URL)
                .customTag("hello world legal person, this is test")
                .build();
    }
    

    public static EnterpriseVerifyRequest verifyUseAgentWithLeastParams() {
        return EnterpriseVerifyRequest.builder()
                .name("河南韩起电气设备有限公司")
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
                                        .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E5%A7%94%E6%89%98%E4%B9%A6%E6%BC%94%E7%A4%BA%E7%A4%BA%E4%BE%8B.docx")))
                )
                .unifiedSocialCode("91510700595072782J")
                .businessLicenceImage(IdCardImage.builder()
                        .imageName("营业执照演示图片.jpg")
                        .imageCode(ImageCode.BUSINESS_LICENCE)
                        .imageData(IdCardImageData.builder()
                                .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/%E8%90%A5%E4%B8%9A%E6%89%A7%E7%85%A7%E6%BC%94%E7%A4%BA%E5%9B%BE%E7%89%87.jpg")))
                .bankCardInfo(EnterpriseBankCardInfo.builder()
                        .bankCardNo("6228480489080786572")
                        .bankBranch("绵阳市涪城区西科大支行")
                        .bank("中国农业银行"))
                .returnUrl("https://webhook.site/dd1d048e-c07d-4f5e-bfd2-5e381eccde06")
                .acceptDataType(AcceptDataType.URL)
                .customTag("hello world legal People, this is test")
                .build();
    }

}
