package lzw.app.com.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lzw.app.com.framelibrary.skin.attr.SkinAttr;
import lzw.app.com.framelibrary.skin.attr.SkinType;


/**
 * Created by LiuZhaowei on 2018/12/9 0009.
 * 皮肤属性解析支持类
 */
public class SkinAttrSupport {
    private final static String TAG = "tag";

    /**
     * 获取SkinAttr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        //解析 background src textColor
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attrLength = attrs.getAttributeCount();
        for (int index = 0; index < attrLength; index++) {
            //获取名称
            String attrName = attrs.getAttributeName(index);
            String attrValue = attrs.getAttributeValue(index);
            Log.e(TAG,"attrName:"+attrName+ "attrValue:"+attrValue);
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //资源名称  目前只有attrValue
                String resName = getResName(context, attrValue);

                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }

            //  Log.e(TAG,attrName+":"+attrValue);
            //只获取重要的属性
        }
        return skinAttrs;
    }

    /**
     * 获取资源的名称
     *
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {


        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);

        }
        return null;
    }

    /**
     * 通过名称,获取SkinType
     *
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }

}
