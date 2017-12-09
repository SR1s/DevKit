package com.roslab.devkit.ui;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 布局绑定基类
 * @author SR1s
 */

public class ViewBinding extends RecyclerView.ViewHolder {

    /**
     * 以传入的view作为根布局
     * @param root 上文所提的view
     */
    public ViewBinding(View root) {
        super(root);
    }

    /**
     * 以传入的container中的rootId的view作为根布局
     * @param container 上文所提的container
     * @param rootId 上文所提的rootId
     */
    public ViewBinding(View container, @IdRes int rootId) {
        this((View) findViewById(container, rootId));
    }

    /**
     * 由传入的inflater解析指定的layoutId，得到布局
     * @param inflater 布局解析器
     * @param parent 布局将附上的容器(可为null，尽量填上，避免RV的item的layout参数失效的问题)
     * @param layoutRes 布局id
     */
    public ViewBinding(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutRes) {
        super(inflater.inflate(layoutRes, parent, false));
    }

    /**
     * 获取binding对应的根布局
     * @return 根布局
     */
    public View getRoot() {
        return itemView;
    }

    /**
     * 辅助获取指定view的别名函数
     * @param idRes viewId
     * @param <T> view type
     * @return view
     */
    public <T> T $(@IdRes int idRes) {
        return findViewById(idRes);
    }

    /**
     * 辅助获取指定view
     * @param idRes viewId
     * @param <T> view type
     * @return view
     */
    public <T> T findViewById(@IdRes int idRes) {
        return findViewById(getRoot(), idRes);
    }

    /**
     * 从root里获取指定id的view
     * @param root root
     * @param id id
     * @param <T> view type
     * @return view
     */
    private static <T> T findViewById(View root, @IdRes int id) {
        return (T) root.findViewById(id);
    }
}
