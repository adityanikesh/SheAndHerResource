package com.cvs.avocado.utils;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtil {

	public static boolean InetAddressValidator(String address) {
		try {
			InetAddress a = InetAddress.getByName(address);
			if(a.isLoopbackAddress() || a.isAnyLocalAddress()) {
				return false;
			}else {
				return true;
			}
		}catch (UnknownHostException ex) {
			return false;
		}
	}
	
	public static int InetAddressType (String address) {
		try {
			InetAddress a = InetAddress.getByName(address);
			if(a instanceof Inet4Address) {
				return 4;
			} else if(a instanceof Inet6Address) {
				return 6;
			}else {
				return 0;
			}
		} catch (UnknownHostException ex) {
			return 0;
		}
	}
	

	public static BigInteger InetAddressToInteger(String address) throws UnknownHostException {
		InetAddress a = InetAddress.getByName(address);
		byte[] bytes = a.getAddress();
		return new BigInteger(1, bytes);
	}
}