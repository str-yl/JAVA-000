学习笔记
第一题；使用spring里的多种方式实现对象的自动注入：
答案：
<!--第一种对象自动注入方式-->
    <bean id="student123"
          class="io.kimmking.spring01.Student">
        <property name="id" value="123" />
        <property name="name" value="KK123" />
    </bean>
    <!--第二种使用静态工厂模式注入-->
    <bean id="student222" class="com.stryl.getBean.d_Bean_factoy" factory-method="getStudent_d">
    </bean>
    <!--第三种使用@Autowired注解注入-->
    <!--第四种使用@Resource注解注入-->
    <!--第五种使用@Inject注解注入-->

第二题：自定义Student，kclass，school的自动配置和starter
请见com.stryl包下的代码文件

第三题：联系使用jdbc的原生接口和hikari连接池
请见jdbc.test包下的代码文件