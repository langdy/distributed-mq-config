package com.yjl.distributed.mq.config.common.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;

/**
 * @author : zhaoyc
 * @date : 2016/12/10
 */
public class MacAddressUtils {
	private static final String NETWORK_INTERFACE_NAME;

	static {
		final String osName = System.getProperty("os.name").toLowerCase();
		String networkInterfaceName = "eth0";
		if (osName.contains("mac") && osName.indexOf("os") > 0) {
			networkInterfaceName = "en0";
		} else if (osName.contains("windows")) {
		} else if (osName.contains("linux")) {
			networkInterfaceName = "eth0";
		}
		NETWORK_INTERFACE_NAME = networkInterfaceName;
	}

	/**
	 * 得到当前系统mac地址
	 *
	 * @return 当前系统的mac地址 , 如果获取不到返回 {@code null },如果有并且有多个网卡,只会获取第一个.
	 */
	public static String getCurrentMacIP() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				if (NETWORK_INTERFACE_NAME.equals(networkInterface.getName())) {
					Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
					while (inetAddresses.hasMoreElements()) {
						InetAddress inetAddress = inetAddresses.nextElement();
						if (inetAddress instanceof Inet6Address) {
							continue;
						}
						return inetAddress.getHostAddress();
					}
					break;
				}
			}
		} catch (SocketException e) {
			LogManager.getLogger().debug(MacAddressUtils.class.getSimpleName(), e);
		}
		return null;
	}

}


