package jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 第一周作业
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 *
 * @author 王杰
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> helloClass = new MyClassLoader().findClass("Hello");
        Object helloObj = helloClass.newInstance();
        Method helloMethod = helloClass.getDeclaredMethod("hello");
        helloMethod.invoke(helloObj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = this.getClass().getClassLoader().getResource("Hello.xlass").getFile();
        byte[] bytes = readXlassFile(filePath);
        return defineClass(name, bytes, 0, bytes.length);

    }

    /**
     * 读取ȡHello.xlass文件并解码(x=255-x)
     *
     * @param fileName
     * @return
     */
    public byte[] readXlassFile(String fileName) throws ClassNotFoundException {
        File file = new File(fileName);
        byte[] dataArray = new byte[(int) file.length()];
        try (InputStream inputStream = new FileInputStream(file)) {
            int temByte;
            int index = 0;
            while ((temByte = inputStream.read()) != -1) {
                int data = 255 - temByte;
                dataArray[index++] = (byte) data;
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(fileName, e);
        }

        return dataArray;
    }
}
