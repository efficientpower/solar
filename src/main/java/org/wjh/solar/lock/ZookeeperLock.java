package org.wjh.solar.lock;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class ZookeeperLock {

    private static final Log log = LogFactory.getLog(ZookeeperLock.class);

    public String LOCK_OK = "OK";
    private String canonicPath;
    private InterProcessMutex lock;

    public ZookeeperLock(InterProcessMutex lock, String canonicPath) {
        this.lock = lock;
        this.canonicPath = canonicPath;
    }

    /**
     * 默认获取锁,返回值只是为了适配原有接口, 没有实际作用.
     * 
     * @return
     */
    public String lock() {
        try {
            lock.acquire();
            return LOCK_OK;
        } catch (Exception e) {
            log.error("zookeeper.create error " + canonicPath, e);
            throw new RuntimeException("this should not happened for zookeeper", e);
        }
    }

    /**
     * 设置超时时间的获取锁接口, 根据返回值判断是否成功.
     *
     * @param timeout 超时时间, 单位为秒
     * @return 是否获取成功.
     */
    public boolean lock(int timeout) {
        try {
            return lock.acquire(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("zookeeper.create error " + canonicPath, e);
            throw new RuntimeException("this should not happened for zookeeper", e);
        }
    }

    /**
     * 默认释放锁
     */
    public void unlock() {
        try {
            lock.release();
        } catch (Exception e) {
            log.error("zookeeper. unlock error " + canonicPath, e);
            throw new RuntimeException("unlock error ,this should not happened for zookeeper", e);
        }
    }

    /**
     * 加参数的释放锁版本, 参数实际上没用; 只是兼容原有接口.
     * 
     * @param lockNode
     */
    public void unlock(String lockNode) {
        this.unlock();
    }
}
