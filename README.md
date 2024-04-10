### 基于SpringBoot + Vue的显卡售后服务系统

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
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1710518593573.jpg) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解`

#### 其它资源

[2023年答辩顺利通过](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年答辩通过率100%](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)


#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

More info: [悲伤的橘子树](https://berserker287.github.io/)


关键词： Java技术；产品售后；管理系统

#### 系统管理模块
该模块主要用于对系统进行管理，包括系统权限的设置，系统管理员的增加删除，密码的修改，用户的管理等。

系统权限是系统操作的先决条件。设计系统必须对使用系统的人作出分类，不同的人能够使用的权限是不同的，管理员权限不能随意授予，只能交给公司高层使用。普通用户使用的是普通权限。他们只能对自己的信息进行修改查询，不能对其他人进行查询修改，否则信息泄露，公司会变成一团糟。

### 服务信息管理模块
该模块主要是对消费者的信息进行管理维护，对客户信息进行数据建档,包括售后服务信息的添加、删除和修改。客户在购买之后，可以对客户信息进行登记录入系统，然后对客户进行跟踪服务，客户可对服务进行点评，提出建议，公司可不定时对客户发放问卷，进行满意度调查。

