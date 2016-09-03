package org.wjh.solar.lock;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ZookeeperUtils implements BeanFactoryAware {

    private static final Log log = LogFactory.getLog(ZookeeperUtils.class);

    private static ZookeeperUtils S_INSTANCE = null;
    private static CuratorFramework S_ZOOKEEPER = null;

    private String connectStr = null;
    private int sessionTimeout = 60000;
    private int baseSleepTimeMs = 60000;
    private int maxTryCount = 3;
    private int connectionTimeout = 15000;

    public String getConnectStr() {
        return connectStr;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxTryCount() {
        return maxTryCount;
    }

    public void setMaxTryCount(int maxTryCount) {
        this.maxTryCount = maxTryCount;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public static void resetZookeeperInstance() {
    }

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        S_INSTANCE = factory.getBean("zookeeperUtils", ZookeeperUtils.class);
    }

    public static CuratorFramework getZooKeeper() throws IOException, KeeperException, InterruptedException {
        if (S_ZOOKEEPER != null) {
            return S_ZOOKEEPER;
        }
        synchronized (ZookeeperUtils.class) {
            if (S_ZOOKEEPER != null) {
                return S_ZOOKEEPER;
            }
            CuratorFramework client = CuratorFrameworkFactory
                    .builder()
                    .connectString(S_INSTANCE.connectStr)
                    .sessionTimeoutMs(S_INSTANCE.sessionTimeout)
                    .connectionTimeoutMs(S_INSTANCE.connectionTimeout)
                    .retryPolicy(new ExponentialBackoffRetry(S_INSTANCE.baseSleepTimeMs, S_INSTANCE.maxTryCount))
                    .build();
            if (client != null) {
                client.start();
                S_ZOOKEEPER = client;
            } else {
                throw new RuntimeException("zookeeper create fail.connectUrl:" + S_INSTANCE.connectStr);
            }
            log.info("zookeeper create successfully .connectUrl:" + S_INSTANCE.connectStr);
            return S_ZOOKEEPER;
        }
    }

}
