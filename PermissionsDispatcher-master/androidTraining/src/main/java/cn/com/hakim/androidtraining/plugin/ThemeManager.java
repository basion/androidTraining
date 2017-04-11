package cn.com.hakim.androidtraining.plugin;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ThemeManager {
    private static String currentSkin;
    /**
     * 检查是否有新皮肤
     *
     * @return
     */
    private boolean hasNewTheme() {
        return true;
    }

    /**
     * 下载皮肤
     */
    private void syncTheme() {

    }

    /**
     * 获取皮肤列表
     *
     * @return
     */
    protected List<String> getThemeList() {
        return null;
    }

    /**
     * 删除指定皮肤
     *
     * @param name
     * @return
     */
    protected boolean deleteTheme(String name) {
        return true;
    }

    /**
     * 切换皮肤
     *
     * @param sourceName
     */
    public void changeSkin(String sourceName) {

    }

    public interface IChangeSkin {
        void onSyncSuccess();
    }
}
