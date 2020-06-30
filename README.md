# 酒店管理系统

## 作业说明
1. 自主实现第三阶段需求。
2. 完成第三阶段的需求用例文档，需求规格文档，体系结构文档，详细设计文档。
3. 自行完成相关单元测试和Web功能测试，提交测试报告。

后端springboot+mybatis 

前端vue+antdv

## 项目启动前

1. 运行`/sql/hotel.sql`

2. 修改`src/main/resources/application.yml`中的datasource

3. 配置`/front_end/src/utils/oss.js`中的阿里云oss配置

	```javascript
	const client = new oss({
		// 此处填写阿里云oss信息
	    accessKeyId: '',
	    accessKeySecret: '',
	    region: 'oss-cn-beijing', //
	    bucket: 'nju-hotel',//
	    host:'nju-hotel.oss-cn-beijing.aliyuncs.com'
	})
	```

## 预设数据

运行`/sql/hotel.sql`后系统内置数据如下

### 用户

| 类型         | 账户         | 密码   |
| ------------ | ------------ | ------ |
| 客户         | 111@qq.com   | 123456 |
| 客户         | 123@qq.com   | 123456 |
| 酒店工作人员 | 333@qq.com   | 123456 |
| 管理员       | 222@qq.com   | 123456 |
| 网站工作人员 | 444@qq.com   | 123456 |
| 酒店工作人员 | chuan@qq.com | 123456 |

> 333@qq.com管了三个酒店，其实这实际上是不存在的，一个酒店工作人员只能管一个酒店，这里是方便测试
>
> chuan@qq.com是真实的情况

### 酒店

| 名称     | 星级 | 区域                 |
| -------- | ---- | -------------------- |
| 汉庭酒店 | 4    | 江苏省 南京市 栖霞区 |
| 儒家酒店 | 4    | 江苏省 南京市 鼓楼区 |
| 桂圆酒店 | 5    | 江苏省 南京市 栖霞区 |
| 川骚酒店 | 3    | 江苏省 南京市 栖霞区 |

> 只有第一个酒店有房间和评价，其他需要自己添加