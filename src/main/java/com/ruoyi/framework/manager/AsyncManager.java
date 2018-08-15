package com.ruoyi.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 * 
 * @author liuhulu
 */
public class AsyncManager {
	// 操作延迟
	private final int OPERATE_DELAY_TIME = 10;
	// 异步操作此案城池
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
	// 单例
	private static AsyncManager me = new AsyncManager();
	public static AsyncManager me() {
		return me;
	}
	// 执行任务
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}
}
