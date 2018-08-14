
# 易企签 Java SDK 示例

易企签Java sdk调用示例，包括**快捷签署**等功能

# 环境要求
Java 1.6 or later.

# 使用前准备
### 1、申请应用
申请应用需要已经**实名认证**过的**企业账号**，如尚未拥有账号，请前往[https://www.signit.cn](https://www.signit.cn) 注册申请并进行认证。

已经拥有账号的用户请[登录](https://app.signit.cn/login)后切换到企业账号下，从"企业"的"开放API"中进入开发者中心创建应用。

### 2、设置webhook地址
sdk中部分功能需要依托webhook传递数据，所以需要设置一个webhook地址。如果webhook地址测试不通过，服务端将无法返回处理后的数据给客户端。

为了方便开发者进行调试，可以访问[https://request.worktile.com](https://request.worktile.com)生成URL地址作为webhook地址，如[https://request.worktile.com/B1pD7CwHX](https://request.worktile.com/B1pD7CwHX)，调用应用功能如快捷签署成功后，前往[https://request.worktile.com/B1pD7CwHX/inspect](https://request.worktile.com/B1pD7CwHX/inspect)查看接收到的webhook信息

# 安装方式


### Maven

	<dependency>
		<groupId>cn.signit.sdk</groupId>
		<artifactId>signit-java-sdk</artifactId>
		<version>1.0.1</version>
	</dependency>

### 源码
如需使用源码，请前往[易企签官方github](https://github.com/signit-wesign/java-sdk)下载



# 如何使用
### 1、快捷签署

无需将文件上传到易企签平台，只需要在sdk中设置待签名的文件以及签名数据，调用快捷签署接口，即可在开放平台设置的webhook地址中的接收到签名后的文件

[快捷签署调用示例](https://github.com/signit-wesign/java-sdk-sample/blob/master/src/main/java/sample/QuickSignatureDemo.java)
