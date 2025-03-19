**公共字段填充** ：利用AOP进行填充

**编辑员工和更改员工部分实现思路**：重新创建一个新的employ对象，把要更改的属性填充进这个新的对象，在xml配置中写sql时，判断新的对象属性是否为空，如果不为空，则更新对应的属性值

**新增菜品**：菜品中关联的有菜品和口味两张表，dishserviceImpl中包括两个mapper接口，一个是新增菜品的mapper，一个是插入flavor的mapper，先插入新的菜品，然后返回插入菜品的id值，具体如图，这样就可以得到菜品的id返回值，然后再赋值给口味中的id值，确保不为null，之后再把元素插入到口味这张表。

![image-20250315212706903](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503152127013.png)

##### 删除菜品：

![image-20250315225244833](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503152252871.png)

要处理带逗号的字符，可以自己切片处理，也可以交给mvc框架处理，加上@requestparam即可，路径上带参数的情况

![image-20250315225004212](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503152250268.png)

![image-20250315225106688](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503152251728.png)



##### 根据ID来查询菜品

![image-20250317213354729](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172133795.png)

采用封装到VO对象当中, 注意

![image-20250317213521922](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172135961.png)



##### 修改菜品

这个跟新增菜品的思路差不多,调用的update方法,同样要写xml配置的sql,但这个是修改的是dish,不包括flavor,flavor的修改,思路如下:直接删除原flavor,然后在dto对象里得到新的flavor数组,然后覆盖掉

![image-20250317214636477](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172146544.png)

##### 在java中操作redis

方法具体有以下5种

![image-20250317221337429](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172213482.png)



首先要在application.yml中配置redist的相关数据

![image-20250317221534579](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172215611.png)

再在config文件中创建redistemplate配置文件，设置序列化器是为了防止key是中文字符，导致乱码

![image-20250317221614315](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172216369.png)





操作字符串的类型

![image-20250317222038760](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172220809.png)

操作hash类型的数据

![image-20250317222238017](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172222074.png)

##### 店铺营业状态开发

![image-20250317222924956](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172229004.png)

![image-20250317223346663](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172233739.png)

![image-20250317223845806](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172238869.png)





两个controller名字相同，加载到spring容器中会发生冲突，所以需要指定不同的名字

![image-20250317224540456](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172245497.png)

![image-20250317224728971](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503172247007.png)





#### 添加购物车代码

思路:前端传过来的就三个字段值,一个dishID,一个套餐id,一个数量,dishID和套餐ID只能传过来一个,不能同时传过来,

如果已经存在,则同时传过来



用动态sql,如果字段有值,则更改

![image-20250319085055876](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503190850986.png)

![image-20250319090218094](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503190902206.png)

![image-20250319092953450](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503190929530.png)

![image-20250319101335951](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191013043.png)







#### 用户下单

![image-20250319111756064](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191117166.png)

![image-20250319111837232](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191118302.png)

![image-20250319112001312](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191120411.png)

![image-20250319113702487](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191137565.png)

![image-20250319114220985](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191142068.png)

![image-20250319114450408](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191144482.png)

[Day08-08-用户下单_代码开发2_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1TP411v7v6?spm_id_from=333.788.player.switch&vd_source=beaa4cde13a4cd09b5d2d2aeeee617a4&p=112)****

![image-20250319121013492](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191210604.png)

注意方法上要加上事务注解,因为这关联到两张表orders和orderdetail,如果第一张表成功插入了,但第二张表插入失败了, 这时候数据库就不能保持一致,会有脏数据,这时候选择加上事务注解保持一致性.



#### SpringTask订单状态处理

![image-20250319132015426](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191320613.png)

![image-20250319132031200](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191320410.png)

![image-20250319133107513](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191331583.png)

![image-20250319134715339](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191347404.png)

![image-20250319134454644](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191344743.png)

#### websocket技术

![image-20250319190431831](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503191904918.png)





#### 导出运营数据Excel报表

![image-20250319203827679](https://typora-oss-picgo.oss-cn-beijing.aliyuncs.com/202503192038757.png)

 
