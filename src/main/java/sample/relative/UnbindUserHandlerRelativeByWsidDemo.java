package sample.relative;

import cn.signit.sdk.SignitClient;
import cn.signit.sdk.SignitException;
import cn.signit.sdk.pojo.ErrorResp;
import cn.signit.sdk.pojo.request.relative.UserHandlerRelativeRequest;
import cn.signit.sdk.pojo.response.relative.UserHandlerRelativeResponse;
import cn.signit.sdk.util.FastjsonDecoder;
import com.alibaba.fastjson.JSON;

/**
 * 易企签 Java SDK 根据签署平台相对方企业账户ID和相对方个人账户ID解除绑定关系用示例代码</br>
 * 本示例代码仅展示了如何使用易企签 Java
 * SDK进行根据签署平台相对方企业账户ID和相对方个人账户ID解除绑定关系，示例代码中的相对方账户ID数据均为虚拟数据，实际运行时需要根据具体需求设置相关数据</br>
 * 示例代码中的appId，appSecretKey，url均为测试环境参数，实际运行时需要将相关参数修改为生产环境参数
 * 更多信息详见：https://github.com/signit-wesign/java-sdk-sample/tree/master/src/main/java/sample
 */
public class UnbindUserHandlerRelativeByWsidDemo {
    public static void main(String[] args) throws SignitException {
        String appSecretKey = "skc8959ea29ec6fefdc3d7e3e158dc6e77";
        String appId = "186a6bbd10500ff63263cdd5781";

        String appUrl = "http://10.10.1.39:2576/v1/open/organization-relatives/WSID_UERE_00000186a6bc656f5ed9b8df90b4301d/person-relatives/WSID_UPRE_00000186a6a3ccd15ed9b8df90b4301d/unbind";// 测试环境使用的地址，生产环境时，应该使用上面一个appUrl

        // step1: 初始化易企签开放平台客户端
        SignitClient client = new SignitClient(appId, appSecretKey, appUrl);
        client.setOauthUrl("http://10.10.1.39:2576/v1/oauth/oauth/token");// 测试环境需要手动设置oauthUrl，生产环境不用设置

        // step2: 使用SDK封装请求数据
        UserHandlerRelativeRequest request = unbindUserHandlerRelative();
        System.out.println("request is :\n" + JSON.toJSONString(request, true));

        // step3: 执行请求,获得响应
        UserHandlerRelativeResponse response = null;
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

    // 解除相对方账户绑定关系
    public static UserHandlerRelativeRequest unbindUserHandlerRelative() {
        return new UserHandlerRelativeRequest();
    }

}
