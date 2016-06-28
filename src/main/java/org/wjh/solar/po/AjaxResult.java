package org.wjh.solar.po;

import java.util.HashMap;

/**
 * 返回结果封装
 * 
 * @author wangjihui
 *
 * @param <T>
 */
public class AjaxResult<T> extends HashMap<String, Object> {

	private static final long serialVersionUID = 7478643224347185847L;

	public AjaxResult() {
		put("code", MobileCode.OK.intValue());
	}

	public AjaxResult(int code, String msg) {
		setFlag(code);
		setMessage(msg);
	}

	public AjaxResult(MobileCode mc) {
		setFlag(mc.intValue());
		setMessage(mc.getMessage());
	}

	public void setFlag(int val) {
		put("code", val);
	}

	public void setMessage(String val) {
		put("message", val);
	}

	public void setData(T data) {
		put("data", data);
	}

	public void setCursor(String cursor) {
		put("cursor", cursor);
	}

	/**
	 * 状态码
	 * 
	 * @author wangjihui
	 *
	 */
	public static enum MobileCode {
		/** 成功 200 */
		OK("处理完成", 200),
		/** 处理失败,包括所有的失败情况 1 */
		FAIL("失败", 1),
		/** 参数异常400 */
		PARAM_EXCEPTION("参数异常", 400),
		/** 未登录，登录失败 401 */
		UNAUTHORIZED("未授权", 401),
		/** 用户禁言 :用户得到了授权，但访问是被禁止的,没有权限 */
		FORBIDDEN("您已经被禁言", 403),
		/** 访问对象不存在（或者对于用户不可见） */
		NOT_FOUND("您访问的对象不存在", 404),
		/** 服务器异常：500 */
		SERVER_ERROR("服务器异常", 500),
		/** 服务不可用 503 */
		SERVICE_UNAVAILABLE("服务不可用", 503);

		private final int code;
		private final String msg;

		MobileCode(String msg, int code) {
			this.msg = msg;
			this.code = code;
		}

		public int intValue() {
			return code;
		}

		public String getMessage() {
			return msg;
		}

	}
}
