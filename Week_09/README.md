学习笔记   
周四的必做作业：     
1、使用ByteBuddy实现动态代理：    
return new ByteBuddy()    
    .subclass(serviceClass)    
    .defineField("url", String.class, Modifier.PUBLIC)
    .implement(urlInterceptor.class).intercept(FieldAccessor.ofBeanProperty())
    .method(ElementMatchers.nameStartsWith("find"))
    .intercept(MethodDelegation.to(bbsClassAdvisor.class))
    .make()
    .load(serviceClass.getClassLoader())
    .getLoaded()
    .newInstance();  
具体的代码请见io.kimmking.rpcfx.client.aop.bbs_Rpcfx类

2、使用NettyClient替代OkHttpClient：   
NettyClient client = new NettyClient("localhost", 8080, reqJson);   
String respJson = client.start();
System.out.println("resp json: " + respJson);    
具体的代码请见io.kimmking.rpcfx.client.aop.NettyClient和
io.kimmking.rpcfx.client.aop.NettyClientHandler

周六的必做作业还没完全做完，本周末做完之后提交。
