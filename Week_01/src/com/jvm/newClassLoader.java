package com.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class newClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class cl = new newClassLoader().findClass("Hello");
            Method method = cl.getMethod("hello", null);
            method.invoke(cl.newInstance(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream is = null;
        byte[] bytes = null;
        try {
            File file = new File("src/resource/Hello.xlass");
            is = new FileInputStream(file);
            int size = is.available();
            bytes = new byte[size];
            int temp;
            for (int i=0;i<size;i++) {
                temp = is.read();
                bytes[i] = (byte) (255-temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(is != null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return defineClass(name,bytes,0,bytes.length);
        }
    }


}