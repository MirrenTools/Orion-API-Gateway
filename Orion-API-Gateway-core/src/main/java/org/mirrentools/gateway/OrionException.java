package org.mirrentools.gateway;

/**
 * 通用异常类
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午2:11:10
 *
 */
public class OrionException extends RuntimeException {
	private static final long serialVersionUID = 5183431284140529491L;

	public OrionException(String message) {
		super(message);
	}

	public OrionException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrionException(Throwable cause) {
		super(cause);
	}

	public OrionException(String message, boolean noStackTrace) {
		super(message, null, !noStackTrace, !noStackTrace);
	}

	public OrionException(Throwable cause, boolean noStackTrace) {
		super(null, cause, !noStackTrace, !noStackTrace);
	}

}
