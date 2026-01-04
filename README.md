### 基于SpringBoot + Vue的显卡售后服务系统

RMA维修管理、SN序列号追踪、显卡返修流程、质保期自动校验、维修进度查询、备件库存管理

##### 管理员端：全链路服务与资源调度
###### 用户/员工管理： 统一维护客户档案与技术人员权限，构建分工明确的组织架构，确保售后服务体系的高效运转。

###### 产品信息/序号： 建立显卡SN码唯一标识库，通过序列号精准追溯出厂日期与保修状态，实现资产的数字化管理。

###### 服务类型/公告： 自定义保修、付费维修等服务标准，并实时发布系统公告，确保售后政策信息透明、统一。

###### 预约/工单管理： 自动化处理客户预约请求并下达维修任务，实时监控工单流转节点，保障服务响应的及时性。

###### 维修信息/产品： 详细记录故障表现、维修方案及更换物料，通过数字化存档为产品质量改进提供数据支撑。

###### 缴费记录/评价： 实时核算维修费用并统计客户满意度反馈，通过财务与口碑的双重维度衡量售后服务质量。

##### 客户端：自助化进度追踪与交互
###### 个人信息维护： 客户自主管理联系方式与收货地址，确保售后取送货环节信息准确，提升沟通与物流效率。

###### 我的工单追踪： 实时查看报修订单的当前状态，从寄送、检测到维修完成，全程掌握售后进度，消除等待焦虑。

###### 维修信息查询： 在线查看显卡的故障诊断结果与具体维修详情，确保维修过程公开透明，提升客户信任度。

###### 缴费记录查询： 随时调取历史维修项目的费用清单与支付凭证，实现售后消费支出的清晰核对与便捷管理。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、Thymeleaf
前端：Vue、Apex、Antd、Axios
报表：Spread.js

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok 支付的话用支付宝的沙箱

管理员
1、用户管理 2、员工管理 3、评价管理 4、服务类型 5、预约管理 6、维修信息 7、产品信息 8、缴费记录 9、工单管理 10、公告信息 11.产品序号

客户
1.我的工单 2.个人信息 3.缴费记录 4.维修信息

维修员
1.我的任务 产品售后服务系统

#### 前台启动方式

安装所需文件 yarn install 
运行 yarn run dev

#### 后端启动方式

1.首先启动redis，进入redis目录终端。输入redis-server回车
2.导入sql文件，修改数据库与redis连接配置
3.idea中启动后端项目

#### 默认后台账户密码

[管理员]
admin
1234qwer

[客户]
fank
1234qwer

#### 项目截图

|  |  |
|---------------------|---------------------|
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518357211.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518576620.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518311784.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518556727.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518717323.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518541108.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518702060.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518511259.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518690300.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518500239.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518676593.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518486438.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518663262.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518469105.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518646498.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518451096.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518635083.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518434521.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518620153.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518408640.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518593573.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)


#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)


关键词： Java技术；产品售后；管理系统

#### 系统管理模块
该模块主要用于对系统进行管理，包括系统权限的设置，系统管理员的增加删除，密码的修改，用户的管理等。

系统权限是系统操作的先决条件。设计系统必须对使用系统的人作出分类，不同的人能够使用的权限是不同的，管理员权限不能随意授予，只能交给公司高层使用。普通用户使用的是普通权限。他们只能对自己的信息进行修改查询，不能对其他人进行查询修改，否则信息泄露，公司会变成一团糟。

### 服务信息管理模块
该模块主要是对消费者的信息进行管理维护，对客户信息进行数据建档,包括售后服务信息的添加、删除和修改。客户在购买之后，可以对客户信息进行登记录入系统，然后对客户进行跟踪服务，客户可对服务进行点评，提出建议，公司可不定时对客户发放问卷，进行满意度调查。

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
