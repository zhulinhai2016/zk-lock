package com.zlh.zk.lockdemo;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @Author: ZhuLinHai
 **/
public class ZkLockSerializer implements ZkSerializer {

    /**
     * 默认data都是 String 类型的数据
     * @param data
     * @return
     * @throws ZkMarshallingError 返回一个0
     */
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
        try {
            String d = (String) data;
            return d.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 默认都是将bytes转为 String 类型的数据
     * @param bytes
     * @return
     * @throws ZkMarshallingError 返回一个"" 空数据
     */
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes,StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
