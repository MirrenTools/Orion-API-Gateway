package org.mirrentools.gateway;

/**
 * 该异常用于结束请求,当处理器接收到失败就会结束下一步流程并响应相应的数据
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午2:11:10
 *
 */
public class OrionProcessEndException extends RuntimeException {
	private static final long serialVersionUID = 5183431284140529491L;

	public OrionProcessEndException(String message) {
		super(message);
	}

	public OrionProcessEndException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrionProcessEndException(Throwable cause) {
		super(cause);
	}

	public OrionProcessEndException(String message, boolean noStackTrace) {
		super(message, null, !noStackTrace, !noStackTrace);
	}

	public OrionProcessEndException(Throwable cause, boolean noStackTrace) {
		super(null, cause, !noStackTrace, !noStackTrace);
	}

}
