package com.example.observekeyboarddemo;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

class KeyBoardHelper {
    public interface OnKeyboardChangeListener {
        void keyBoardShow();

        void keyBoardHide();
    }

    /**
     * 需要用一个对象来包装height。
     * 由于height不再是类的成员变量，而仅仅是方法中的局部变量，需要加final修饰才能对匿名内部类（OnGlobalLayoutListener）可见，
     * 而一但加上final变只能访问而不能二次赋值（所以需要用对象包装起来，通过访问对象的变量来实现赋值）。
     */
    private static class HeightWrapper {
        int height;
    }

    public static void setListener(Activity activity, final OnKeyboardChangeListener listener) {
        final View rootView = activity.getWindow().getDecorView();
        final HeightWrapper wrapper = new HeightWrapper();
        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        rootView.getWindowVisibleDisplayFrame(r);
                        int height = r.height();
                        if (wrapper.height == 0) {
                            wrapper.height = height;
                        } else {
                            int diff = wrapper.height - height;
                            if (diff > 200) {
                                if (listener != null) {
                                    listener.keyBoardShow();
                                }
                                wrapper.height = height;
                            } else if (diff < -200) {
                                if (listener != null) {
                                    listener.keyBoardHide();
                                }
                                wrapper.height = height;
                            }
                        }
                    }
                });
    }
}
