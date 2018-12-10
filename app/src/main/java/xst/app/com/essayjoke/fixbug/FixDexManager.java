package xst.app.com.essayjoke.fixbug;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by LiuZhaowei on 2018/12/6 0006.
 */
public class FixDexManager {
    private Context mContext;
    private File mDexDir;//应用可访问的目录

    public FixDexManager(Context context) {
        this.mContext = context;
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {
        //1.先获取已经运行的DexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();
        Object applicationDexElements = getDexElementByClassLoader(applicationClassLoader);


        //2.获取下载好的补丁的DexElement
        //2.1移动到系统能够访问的目录下 dex目录下 ClassLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }
        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            //return;
        }
        //copy文件
        copyFile(srcFile, destFile);


        //2.2ClassLoader读取fixDexPath路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        //修复
        for (File fixDexFile : fixDexFiles) {
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),//dex路径
                    optimizedDirectory,//解压路径
                    null,
                    applicationClassLoader);
            Object fixDexElements = getDexElementByClassLoader(fixDexClassLoader);
            //3.把补丁的DexElement 查到已经运行打DexElement的最前面
            //applicationClassLoader  数组合并fixDexElement 数组
            //合并完成
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);
        }
        //把合并的数组注入到applicationClassLoader的类中
        injectDexElements(applicationClassLoader, applicationDexElements);


    }

    /**
     * 把DexElements 注入到classLoader
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pathListField = classLoaderClass.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        Class<?> pathListClass = pathList.getClass();
        Field dexElementsField = pathListClass.getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 获取classLoader中的DexElement
     * @param classLoader ClassLoader
     */
    public static Object getDexElementByClassLoader(ClassLoader classLoader) throws Exception {
        Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pathListField = classLoaderClass.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        Class<?> pathListClass = pathList.getClass();
        Field dexElementsField = pathListClass.getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        Object dexElements = dexElementsField.get(pathList);

        return dexElements;
    }


    /**
     * 复制文件的方法
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    public void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 合并两个dexElements数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }


    /**
     * 加载全部的修复包
     */
    public void loadFixDex() {
        File[] dexFiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();
        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex"))
                fixDexFiles.add(dexFile);
        }
    }

}
