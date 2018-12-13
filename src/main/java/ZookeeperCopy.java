import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author chenyuwei
 * @date 2018/12/11
 * zookeeper数据复制工具
 * 启动参数1为来源zk地址
 * 启动参数2为目标zk地址
 * 启动参数3为需要复制的路径，此路径下的子节点都会被复制
 */
public class ZookeeperCopy {

    /**
     * 来源zk客户端
     */
    private static ZooKeeper sourceZK;

    /**
     * 目标zk客户端
     */
    private static ZooKeeper targetZK;

    /**
     * 来源zk地址
     */
    private static String sourceZKAddress;

    /**
     * 目标zk地址
     */
    private static String targetZKAddress;

    public static void main(String[] args) throws Exception {
        sourceZKAddress = args[0];
        targetZKAddress = args[1];

        //目标复制路径
        String nodePath = args[2];
        if (sourceZK == null) {
            sourceZK = getSourceZK();
        }
        if (targetZK == null) {
            targetZK = getTargetZK();
        }

        if (targetZK.exists(nodePath, false) == null) {
            byte[] data = sourceZK.getData(nodePath , false, new Stat());
            targetZK.create(nodePath, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        getNodes(nodePath);
        System.out.println("数据复制完成");
    }

    private static ZooKeeper getTargetZK() throws Exception {
        if (targetZK == null) {
            targetZK = new ZooKeeper(targetZKAddress, 3000,
                    new Watcher() {
                        // 监控所有被触发的事件
                        @Override
                        public void process(WatchedEvent event) {

                        }

                    });
        }
        return targetZK;
    }

    private static ZooKeeper getSourceZK() throws Exception {

        if (sourceZK == null) {
            sourceZK = new ZooKeeper(sourceZKAddress, 3000,
                    new Watcher() {
                        // 监控所有被触发的事件
                        @Override
                        public void process(WatchedEvent event) {

                        }

                    });
        }
        return sourceZK;
    }

    private static void getNodes(String nodePath) throws Exception {
        List<String> list = sourceZK.getChildren(nodePath, false);
        for (String str : list) {
            Stat exists = targetZK.exists(nodePath + "/" + str, false);
            if (exists == null) {
                System.out.println("正在创建" + str);
                byte[] data = sourceZK.getData(nodePath + "/" + str, false, new Stat());
                targetZK.create(nodePath + "/" + str, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            getNodes(nodePath + "/" + str);
        }

    }
}
