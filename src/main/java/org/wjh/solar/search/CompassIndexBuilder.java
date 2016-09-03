package org.wjh.solar.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.gps.CompassGps;
import org.springframework.beans.factory.InitializingBean;

public class CompassIndexBuilder implements InitializingBean {

    private static final Log logger = LogFactory.getLog(CompassIndexBuilder.class);

    // Compass封装
    private CompassGps compassGps;

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
        indexThread.setName("CompassIndexer");
        indexThread.start();
        logger.info("end to rebuildIndexOnStart!!!");
    }

    public void reBuildIndex() throws Exception {
        logger.info("start to rebuildIndex!!!");
        Thread indexThread = new CompassIndexThread(compassGps);
        indexThread.setDaemon(true);
        indexThread.setName("CompassIndexer");
        indexThread.start();
        logger.info("end to rebuildIndex!!!");
    }

    public void flushIndex() throws Exception {
        logger.info("start to flushIndex!!!");
        Thread indexThread = new CompassIndexThread(compassGps);
        indexThread.setDaemon(true);
        indexThread.setName("CompassIndexer");
        indexThread.start();
        logger.info("end to flushIndex!!!");
    }

}
