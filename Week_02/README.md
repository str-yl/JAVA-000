学习笔记

第一题：写一段代码，使用HttpClient或OkHttp访问http://localhost:8081,代码提交到GitHub
package jktime;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

public class test {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static String httpGet(String url){
        String getData ;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            getData = response.body().string();

        }catch (Exception e){
            System.out.println("【发送 GET 请求出现异常】！" + e.getMessage());
            return "-1";
        }
        return getData;
    }

    public static String httpPost(String url, String json){
        String postData ;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            postData = response.body().string();
        }catch (Exception e){
            System.out.println("【发送 POST 请求出现异常】！" + e.getMessage());
            return "-1";
        }
        return postData;
    }

    public static void main(String[] args) {
        String loadJSON = httpGet("http://localhost:8801");
        System.out.println(loadJSON);

        JSONObject json=new JSONObject();
        json.put("name","admin");
        json.put("pwd","admin");
        String postJSON = httpPost("http://localhost:8801",json.toString());
    }
}

第二题：结合第一题和第二题对不同的GC进行总结：
1）串行GC一般使用的CPU单核的主机环境下或者业务数据很小的情况下使用；
2）并行GC充分利用了多核CPU进行并发垃圾回收，在GC表现上优于串行GC；
3）CMS在并行垃圾回收的基础上将回收过程细分成了若干个子阶段，该GC适用在堆内存小于16GB的场景下；
4）G1将堆内存切分为一个个的region，年轻代，老年代在堆内存中的位置并不是不变的，它适用于堆内存大于16GB以上的场景，同时可以设置期望的STW时间，G1本身会最大努力地去满足设置的停顿时间；