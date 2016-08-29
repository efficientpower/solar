package org.wjh.solar.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.gps.CompassGps;

public class CompassIndexThread extends Thread {

    private Log log = LogFactory.getLog(CompassIndexThread.class);

    // 索引操作线程延时启动的时间，单位为秒
    private int lazyTime = 10;

    private CompassGps compassGps;

    public CompassIndexThread(CompassGps compassGps) {
        this.compassGps = compassGps;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(lazyTime * 1000);
            log.info("begin MOVIELIB compass index...");
            long beginTime = System.currentTimeMillis();
            // 重建索引.如果compass实体中定义的索引文件已存在，索引过程中会建立临时索引，索引完成后再进行覆盖.
            compassGps.index();
            long costTime = System.currentTimeMillis() - beginTime;
            log.info("MOVIELIB compss index finished. use " + costTime + " milliseconds");
        } catch (InterruptedException e) {
            log.error("索引线程被中断：" + e.getMessage(), e);
        }
    }
}
