package com.cgy.chengy.demofactory.popup;

import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by ChenGY on 2019-02-22.
 */
public class PopupAniUtil {

    /**
     * 初始化平移动画
     */
    public static void initTranslateAnimate(BasePopupWindow basePopupWindow, boolean isHasAnchorView) {
        int gravity = basePopupWindow.getPopupGravity();
        float in_fromX = 0;
        float in_toX = 0;
        float in_fromY = 0;
        float in_toY = 0;

        float exit_fromX = 0;
        float exit_toX = 0;
        float exit_fromY = 0;
        float exit_toY = 0;

        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
            case Gravity.START:
                in_fromX = isHasAnchorView ? 1f : -1f;
                exit_toX = isHasAnchorView ? 1f : -1f;
                break;
            case Gravity.RIGHT:
            case Gravity.END:
                in_fromX = isHasAnchorView ? -1f : 1f;
                exit_toX = isHasAnchorView ? -1f : 1f;
                break;
            case Gravity.CENTER_HORIZONTAL:
                break;
            default:
                break;
        }
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                in_fromY = isHasAnchorView ? 1f : -1f;
                exit_toY = isHasAnchorView ? 1f : -1f;
                break;
            case Gravity.BOTTOM:
                in_fromY = isHasAnchorView ? -1f : 1f;
                exit_toY = isHasAnchorView ? -1f : 1f;
                break;
            case Gravity.CENTER_VERTICAL:
                break;
            default:
                break;
        }
        basePopupWindow.setShowAnimation(createTranslateAnimate(in_fromX, in_toX, in_fromY, in_toY));
        basePopupWindow.setDismissAnimation(createTranslateAnimate(exit_fromX, exit_toX, exit_fromY, exit_toY));
    }

    /**
     * 初始化平移动画for快速
     */
    public static Animation initTranslateAnimateQuick(int gravity, boolean isHasAnchorView, boolean isIn) {
        float in_fromX = 0;
        float in_toX = 0;
        float in_fromY = 0;
        float in_toY = 0;

        float exit_fromX = 0;
        float exit_toX = 0;
        float exit_fromY = 0;
        float exit_toY = 0;

        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
            case Gravity.START:
                in_fromX = isHasAnchorView ? 1f : -1f;
                exit_toX = isHasAnchorView ? 1f : -1f;
                break;
            case Gravity.RIGHT:
            case Gravity.END:
                in_fromX = isHasAnchorView ? -1f : 1f;
                exit_toX = isHasAnchorView ? -1f : 1f;
                break;
            case Gravity.CENTER_HORIZONTAL:
                break;
            default:
                break;
        }
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                in_fromY = isHasAnchorView ? 1f : -1f;
                exit_toY = isHasAnchorView ? 1f : -1f;
                break;
            case Gravity.BOTTOM:
                in_fromY = isHasAnchorView ? -1f : 1f;
                exit_toY = isHasAnchorView ? -1f : 1f;
                break;
            case Gravity.CENTER_VERTICAL:
                break;
            default:
                break;
        }
        return isIn ? createTranslateAnimate(in_fromX, in_toX, in_fromY, in_toY)
                : createTranslateAnimate(exit_fromX, exit_toX, exit_fromY, exit_toY);
    }

    private static Animation createTranslateAnimate(float fromX, float toX, float fromY, float toY) {
        Animation result = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                fromX,
                Animation.RELATIVE_TO_PARENT,
                toX,
                Animation.RELATIVE_TO_PARENT,
                fromY,
                Animation.RELATIVE_TO_PARENT,
                toY);
        result.setDuration(500);
        return result;
    }
}
