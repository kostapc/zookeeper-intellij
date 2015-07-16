package org.mvnsearch.intellij.plugin.zookeeper;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.junit.Assert;

import java.util.List;

/**
 * Created by bartek on 14.07.15.
 */
public class ZkLogin {

    public static void main(String[] atgfs) {

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        CuratorFramework client = builder
                .connectString("localhost:2181")
                .authorization("digest", "fabric:admin".getBytes())

                .retryPolicy(new RetryOneTime(1))
                .build();
        client.start();
        try {
            ACL acl = new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.AUTH_IDS);
            List<ACL> aclList = Lists.newArrayList(acl);
//            client.create().withACL(aclList).forPath("/fabric", "test".getBytes());

            List<ACL> ac2l = client.getACL().forPath("/fabric");
            System.out.println(ac2l);
            List<String> children = client.getChildren().forPath("/fabric");
            System.out.println(children);
            byte[]      data = client.getData().forPath("/fabric");
            System.out.println(new String(data));
            client.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
