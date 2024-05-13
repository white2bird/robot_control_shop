package com.it.util;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 
 * @date 2024/4/8 10:45
 */
public class MacUtil {

    public static void main(String[] args) {
        System.out.println(getMac());
    }

    /**
     * 获取本机 mac 地址集合
     *
     * @return mac 地址集合
     */
    public static String getMac() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Optional.ofNullable(networkInterface.getHardwareAddress())
                        .ifPresent(mac -> list.add(format(mac)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.stream().distinct().sorted().collect(Collectors.joining(";"));
    }

    /**
     * 将 mac 字节数组格式化为全大写并且使用 - 作为分隔符的字符串
     *
     * @param mac 获取到的 mac 字节数组
     * @return 格式化后的 mac 地址
     */
    private static String format(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte b : mac) {
            sb.append(String.format("%02X", b)).append("-");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}


