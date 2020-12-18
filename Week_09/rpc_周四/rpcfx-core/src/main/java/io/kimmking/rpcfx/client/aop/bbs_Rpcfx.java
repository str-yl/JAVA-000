package io.kimmking.rpcfx.client.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class bbs_Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(final Class<T> serviceClass) throws Exception {

        // 0. 替换动态代理 -> AOP
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
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
    }

    public interface urlInterceptor {

        void setUrl(String url);
    }

    public static class bbsClassAdvisor {
        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        @RuntimeType
        public static Object wesd(@Origin Method method, @AllArguments Object[] arguments, @FieldValue(value = "url") String url) {

//                System.out.println("Enter " + method.getName() + " with arguments: " + Arrays.toString(arguments));
                System.out.println("url: " + url);
                RpcfxRequest request = new RpcfxRequest();
                request.setServiceClass(method.getDeclaringClass().getName());
                request.setMethod(method.getName());
                request.setParams(arguments);
                RpcfxResponse response = null;
                try {
                    response = post(request, url);
                } finally {
                    return JSON.parse(response.getResult().toString());
                }
                // 这里判断response.status，处理异常
                // 考虑封装一个全局的RpcfxException

//                return JSON.parse(response.getResult().toString());
        }

        private static RpcfxResponse post(RpcfxRequest req, String url) throws Exception {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json: " + reqJson);

            // 1.可以复用client
//            OkHttpClient client = new OkHttpClient();
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(JSONTYPE, reqJson))
//                    .build();
//            String respJson = client.newCall(request).execute().body().string();

            // 2.尝试使用httpclient或者netty client
            NettyClient client = new NettyClient("localhost", 8080, reqJson);
            String respJson = client.start();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
