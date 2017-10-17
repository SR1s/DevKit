package com.roalab.devkit.ui;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Ui控制器基类
 * @author SR1
 */

public class UiController<EventType extends UiEvent, UiOperationInterface extends UiOperation> {

    private static final String TAG = "UiController";

    /**
     * 共享的主线程Handler
     */
    private static final Handler sUiHandler = new Handler(Looper.getMainLooper());

    /**
     * Ui操作分发
     */
    private final UiOperationInterface mDispatcher;

    /**
     * 事件响应处理
     */
    private EventType mEventHandler;

    public UiController(Class<UiOperationInterface> operationInterface) {
        mDispatcher = makeDispatcher(operationInterface);
    }

    /**
     * 设置事件响应处理器
     * @param handler 处理器
     */
    public void setEventHandler(EventType handler) {
        mEventHandler = handler;
    }

    /**
     * 判断是否存在事件响应处理器
     * @return 存在true, 不存在false
     */
    public boolean isHandlerExist() {
        return mEventHandler != null;
    }

    /**
     * 取得事件响应处理器
     * @return 处理器
     */
    public EventType getHandler() {
        return mEventHandler;
    }

    /**
     * 分发Ui操作事件到主线程
     * @return
     */
    public UiOperationInterface onUiThread() {
        return mDispatcher;
    }

    /**
     * 构造分发器
     * @param operationInterface ui操作接口
     * @return 分发器
     */
    @SuppressWarnings("unchecked")
    private UiOperationInterface makeDispatcher(Class<UiOperationInterface> operationInterface) {
        return (UiOperationInterface) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{
                        operationInterface
                }, (Object proxy, final Method method, final Object[] args) -> {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        method.invoke(this, args);
                    } else {
                        sUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    method.invoke(this, args);
                                } catch (Exception ex) {
                                    Log.w(TAG, "invoke method: " + method.getName() + " fail with args: " + Arrays.toString(args), ex);
                                }
                            }
                        });
                    }
                    return null;
                }
        );
    }

}
