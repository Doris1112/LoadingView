package com.android.loadingview.liquid.util;

/**
 * 盛水事件
 */
public interface PourFinishListener {
	
	/**
	 * 完成
	 */
	void onPourFinish();

	/**
	 * 进度改变
	 */
    void onProgressUpdate(float progress);

}
