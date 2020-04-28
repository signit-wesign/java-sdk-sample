package sample;

import com.alibaba.fastjson.JSON;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.request.EntrustSignProcess2WeSignRequest;
import cn.signit.sdk.pojo.response.EntrustSignProcess2WeSignResponse;
import cn.signit.sdk.util.FastjsonDecoder;

/**
 * 易企签 Java SDK 托管签署流程到易企签调用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行启动信封，示例代码中的姓名、手机、邮箱、签名文件、手写签名数据以及印章数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class EntrustSignProcess2WeSignDemo {

    public static void main(String[] args) {
        String appSecretKey = "sk3847e93a9e3835e6e42a4944ae979308";
        String appId = "171ba7cc03600ff1aa995d134a1";

        // "https://open.signit.cn/v1/open/envelopes/{envelope-wsid}/entrust";
        String appUrl = "http://112.44.251.136:2576/v1/open/envelopes/WSID_ENVE_00000171c0838ae800ff1aa995d10001/entrust";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://112.44.251.136:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装托管签署流程到易企签请求
        EntrustSignProcess2WeSignRequest request = getEntrustSignProcess2WeSignParam();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        EntrustSignProcess2WeSignResponse response = null;
        try {
            response = client.execute(request);
        } catch (SignitException e) {
            ErrorResp errorResp = null;
            if (FastjsonDecoder.isValidJson(e.getMessage())) {
                errorResp = FastjsonDecoder.decodeAsBean(e.getMessage(), ErrorResp.class);
            }
            System.out.println("\nerror response is:\n" + JSON.toJSONString(errorResp, true));
        }
        System.out.println("\nresponse is:\n" + JSON.toJSONString(response, true));
    }

    // 使用SDK封装托管签署流程到易企签请求
    private static EntrustSignProcess2WeSignRequest getEntrustSignProcess2WeSignParam() {
        return EntrustSignProcess2WeSignRequest.builder()
                .depth(0)
                .build();
    }

}
