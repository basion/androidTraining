package cn.com.hakim.androidtraining.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/1/5.
 */

public class CAppBarLayout extends AppBarLayout {
    private static final String TAG = "CAppBarLayout";

    public CAppBarLayout(Context context) {
        super(context);
    }

    public CAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    private int mTotalScrollRange = INVALID_SCROLL_RANGE;
//    private int mDownPreScrollRange = INVALID_SCROLL_RANGE;
//    private int mDownScrollRange = INVALID_SCROLL_RANGE;
    public void getDownRange() {
        int mDownScrollRange = (int) getFieldValue(this,"mDownScrollRange");
        Log.e(TAG,"mDownScrollRange = "+mDownScrollRange);
        int mTotalScrollRange = (int) getFieldValue(this,"mTotalScrollRange");
        Log.e(TAG,"mTotalScrollRange = "+mTotalScrollRange);
        int mDownPreScrollRange = (int) getFieldValue(this,"mDownPreScrollRange");
        Log.e(TAG,"mDownPreScrollRange = "+mDownPreScrollRange);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    public static Field getDeclaredField(Object object, String fieldName){
        Field field = null ;

        Class<?> clazz = object.getClass() ;

        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName) ;
                return field ;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return null;
    }
    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */

    public static Object getFieldValue(Object object, String fieldName){

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName) ;

        //抑制Java对其的检查
        field.setAccessible(true) ;

        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object) ;

        } catch(Exception e) {
            e.printStackTrace() ;
        }

        return null;
    }
}
