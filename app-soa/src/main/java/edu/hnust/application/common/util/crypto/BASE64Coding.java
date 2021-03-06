package edu.hnust.application.common.util.crypto;

import java.nio.ByteBuffer;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64的工具类
 * @author  Jason Zeng(2079)
 * @version  [版本号, Apr 1, 2017]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("restriction")
public class BASE64Coding {
	public BASE64Coding() {
	}

	/**
	 * 按系统默认编码encode该字符串
	 * 
	 * @param s
	 * @return String
	 */
	public static String encode(String s) {
		return new BASE64Encoder().encode(s.getBytes());
	}

	/**
	 * 对字节数组进行encode
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * 对ByteBuffer进行encode
	 * 
	 * @param buf
	 * @return String
	 */
	public static String encode(ByteBuffer buf) {
		return new BASE64Encoder().encode(buf);
	}

	/**
	 * 对BASE64的字符串进行decode，若decode失败，则返回null
	 * 
	 * @param str
	 * @return byte[]
	 */
	public static byte[] decode(String str) {
		try {
			return new BASE64Decoder().decodeBuffer(str);
		} catch (Exception e) {
			return null;
		}
	}
}
