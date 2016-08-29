package org.wjh.solar.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.gps.CompassGps;
import org.springframework.beans.factory.InitializingBean;

public class CompassIndexBuilder implements InitializingBean {

    private static final Log logger = LogFactory.getLog(CompassIndexBuilder.class);

    // 是否需要建立索引，可被设置为false使本Builder失效.
    private boolean buildIndexOnStart = false;

    // 索引操作线程延时启动的时间，单位为秒
    private int lazyTime = 10;

    // Compass封装
    private CompassGps compassGps;

    public void setBuildIndexOnStart(boolean buildIndex) {
        this.buildIndexOnStart = buildIndex;
    }

    public void setLazyTime(int lazyTime) {
        this.lazyTime = lazyTime;
    }

    public void setCompassGps(CompassGps compassGps) {
        this.compassGps = compassGps;
    }

    /**
     * 实现<code>InitializingBean</code>接口，在完成注入后调用启动索引线程.
     * 
     * @see org.springframework.beans.factory.InitializingBeanafterPropertiesSet
     */
    public void afterPropertiesSet() throws Exception {
        logger.info("start to rebuildIndexOnStart!!!");
        Thread indexThread = new CompassIndexThread(compassGps);
        indexThread.setDaemon(true);
        indexThread.setName("Compass Indexer");
        indexThread.start();
        logger.info("end to rebuildIndexOnStart!!!");
    }

    public void reBuildIndex() throws Exception {
        logger.info("start to rebuildIndex!!!");
        Thread indexThread = new CompassIndexThread(compassGps);
        indexThread.setDaemon(true);
        indexThread.setName("Compass Indexer");
        indexThread.start();
        logger.info("end to rebuildIndex!!!");
    }

    public void flushIndex() throws Exception {
        logger.info("start to flushIndex!!!");
        Thread indexThread = new CompassIndexThread(compassGps);
        indexThread.setDaemon(true);
        indexThread.setName("Compass Indexer");
        indexThread.start();
        logger.info("end to flushIndex!!!");
    }

}
