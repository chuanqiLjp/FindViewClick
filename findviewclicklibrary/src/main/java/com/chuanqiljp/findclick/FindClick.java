package com.chuanqiljp.findclick;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/10/31.
 */
public class FindClick {
    private static final String TAG = "FindClick";

    private FindClick() {
    }

    /**
     * findViewById 和 setOnClickListener
     * 注意：setOnClickListener的响应方法为：onViewClicked()
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("( \"activity\" ) on a null object reference  ");
        }
        findViewId(activity);
        onClick(activity);
    }

    public static void bind(@NonNull Object target, @NonNull View source) {
        if (target == null || source == null) {
            throw new NullPointerException("( \"target\" or \"source\" ) on a null object reference  ");
        }
        findViewId(target, source);
        onClick(target, source);
    }

    private static void onClick(final Object target, final View source) {
        Class<?> clazz = target.getClass();
        Method method = null;
        try {
            method = clazz.getMethod("onViewClicked", View.class);//获取到指定的方法
        } catch (NoSuchMethodException e) {
            log(e);
        }
        if (method == null) {
            try {
                method = clazz.getMethod("onViewClicked");//获取到指定的方法
            } catch (NoSuchMethodException e) {
                log(e);
            }
        }
        if (method == null) {
            return;
        }
        method.setAccessible(true);
        OnClick onClick = method.getAnnotation(OnClick.class);
        //  该方法上是否有OnClick注解
        if (onClick != null) {
            // 获取OnClick里面所有的值
            int[] viewIds = onClick.value();// @OnClick({R.id.text_view,R.id.button})
            for (int viewId : viewIds) {
                if (viewId == View.NO_ID) {
                    continue;
                }
                // 先findViewById
                final View view = source.findViewById(viewId);
                // 后设置setOnclick
                final Method finalMethod = method;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            finalMethod.invoke(target, view);// 反射调用原来配置了OnClick的方法,调用有参的方法 view 代表当前点击的View
                        } catch (Exception e) {
                            log(e);
                            try {
                                finalMethod.invoke(target);
                            } catch (IllegalAccessException e1) {
                                log(e1);
                            } catch (InvocationTargetException e1) {
                                log(e1);
                            }
                        }
                    }
                });
            }
//            }
        }
    }

    private static void findViewId(Object target, View source) {
        Class<?> obj = target.getClass();
        Field[] fields = obj.getDeclaredFields();
        if (fields == null) return;
        for (Field field : fields) {
            // 获取字段的注解，如果没有ViewInject注解，则返回null
            BindView viewInject = field.getAnnotation(BindView.class);
            if (viewInject != null) {
                int viewId = viewInject.value(); // 获取字段注解的参数，这就是我们传进去控件Id
                if (viewId != -1) {
                    View resView = source.findViewById(viewId);
//                        // 获取类中的findViewById方法，参数为int
//                        Method method = source.getMethod("findViewById", int.class);
//                        // 执行该方法，返回一个Object类型的View实例
//                        Object resView = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    try {
                        field.set(target, resView);// 把字段的值设置为该View的实例
                    } catch (IllegalAccessException e) {
                        log(e);
                    }

                }
            }
        }
    }


    private static void findViewId(Activity activity) {
        Class<? extends Activity> object = activity.getClass(); // 获取activity的Class
        Field[] fields = object.getDeclaredFields(); // 通过Class获取activity的所有字段
        if (fields == null) return;
        for (Field field : fields) { // 遍历所有字段
            // 获取字段的注解，如果没有ViewInject注解，则返回null
            BindView viewInject = field.getAnnotation(BindView.class);
            if (viewInject != null) {
                int viewId = viewInject.value(); // 获取字段注解的参数，这就是我们传进去控件Id
                if (viewId != -1) {
                    try {
                        // 获取类中的findViewById方法，参数为int
                        Method method = object.getMethod("findViewById", int.class);
                        // 执行该方法，返回一个Object类型的View实例
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        // 把字段的值设置为该View的实例
                        field.set(activity, resView);
                    } catch (NoSuchMethodException e) {
                        log(e);
                    } catch (IllegalAccessException e) {
                        log(e);
                    } catch (InvocationTargetException e) {
                        log(e);
                    }
                }
            }
        }
    }

    /**
     * setOnClickListener
     *
     * @param activity
     */
    private static void onClick(final Activity activity) {
        Class<?> clazz = activity.getClass();
        Method method = null;
        try {
            method = clazz.getMethod("onViewClicked", View.class);//获取到指定的方法
        } catch (NoSuchMethodException e) {
            log(e);
        }
        if (method == null) {
            try {
                method = clazz.getMethod("onViewClicked");//获取到指定的方法
            } catch (NoSuchMethodException e) {
                log(e);
            }
        }
        if (method == null) {
            return;
        }
        method.setAccessible(true);
        OnClick onClick = method.getAnnotation(OnClick.class);
        //  该方法上是否有OnClick注解
        if (onClick != null) {
            // 获取OnClick里面所有的值
            int[] viewIds = onClick.value();// @OnClick({R.id.text_view,R.id.button})
            for (int viewId : viewIds) {
                if (viewId == View.NO_ID) {
                    continue;
                }
                // 先findViewById
                final View view = activity.findViewById(viewId);
                // 后设置setOnclick
                final Method finalMethod = method;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            finalMethod.invoke(activity, view);// 反射调用原来配置了OnClick的方法,调用有参的方法 view 代表当前点击的View
                        } catch (Exception e) {
                            log(e);
                            try {
                                finalMethod.invoke(activity);
                            } catch (IllegalAccessException e1) {
                                log(e1);
                            } catch (InvocationTargetException e1) {
                                log(e1);
                            }
                        }
                    }
                });
            }
//            }
        }
    }

    private static void log(Exception e) {
        Log.w(TAG, e.toString());
    }
}