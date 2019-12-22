package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.CountersignatureData;
import cn.signit.sdk.pojo.CountersignaturePosition;
import cn.signit.sdk.pojo.CountersignaturePosition.KeywordPositionBuilder;
import cn.signit.sdk.pojo.CountersignatureSigner;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.FileData;
import cn.signit.sdk.pojo.OauthData;
import cn.signit.sdk.pojo.SignSize;
import cn.signit.sdk.pojo.WriteData;
import cn.signit.sdk.pojo.request.CountersignatureSignRequest;
import cn.signit.sdk.pojo.response.CountersignatureSignResponse;
import cn.signit.sdk.type.AcceptDataType;
import cn.signit.sdk.type.Direction;
import cn.signit.sdk.type.TokenType;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 快捷会签调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK快速进行会签签署，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 
 */
public class CountersignatureSignDemo {
    public static void main(String[] args) {
        String appSecretKey = "sk5f9e87967150fe485d5f047cc300c2ce";
        String appId = "16aa4db191c3eebbcfde3dd95a1";
        // 测试环境，生产环境不用设置url
        String appUrl = "http://112.44.251.136:2576/v1/open/signatures/countersignature-sign";

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        // 测试环境需要手动设置oauthUrl，生产环境不用设置
        client.setOauthUrl("http://112.44.251.136:2576/v1/oauth/oauth/token");
        // step2: 使用SDK封装快捷会签请求
        CountersignatureSignRequest request = countersigatureSignParams();
        System.out.println("\nrequest is:\n\n " + JSON.toJSONString(request, true));
        // step3: 执行请求,获得响应
        CountersignatureSignResponse response = null;
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

    public static CountersignatureSignRequest countersigatureSignParams() {
        return CountersignatureSignRequest.builder()
                .customTag("this_is_test_1")
                .acceptDataType(AcceptDataType.URL)
                .fileData(FileData.builder()
                        .url("https://github.com/signit-wesign/java-sdk-sample/raw/master/demoData/countersignatureSign.docx"))
                // 设置签署区域
                .position(CountersignaturePosition.builder()
                        .columnSpace(10f)
                        .rowSpace(5f)
                        .keywordPosition(new KeywordPositionBuilder().width(300f)
                                .height(100f)
                                .direction(Direction.RIGHT)
                                .keyword("联合签名：")
                                .index(1)
                                .yOffset(75f)
                                .xOffset(10f)))
                // 设置了3个签名
                .sign(CountersignatureData.builder()
                        .writeData(WriteData.builder()
                                .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/lisi.png")
                                .name("李四"))
                        .size(SignSize.builder()
                                .height(20f)
                                .width(35f))
                        .sequence(3)
                        .signer(CountersignatureSigner.builder()
                                .withName("李四")
                                .withContact("13281526649")),
                        CountersignatureData.builder()
                                .writeData(WriteData.builder()
                                        .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/wangwu.png")
                                        .name("王五"))
                                .size(new SignSize(20f, 35f))
                                .sequence(2)
                                .signer(CountersignatureSigner.builder()
                                        .withName("王五")
                                        .withContact("13281626649")),
                        CountersignatureData.builder()
                                .writeData(WriteData.builder()
                                        .url("https://raw.githubusercontent.com/signit-wesign/java-sdk-sample/master/demoData/zhangsan.png")
                                        .name("张三"))
                                .size(new SignSize(20f, 35f))
                                .sequence(1)
                                .signer(CountersignatureSigner.builder()
                                        .withName("张三")
                                        .withContact("13654813641")))
                .build();
    }
}
