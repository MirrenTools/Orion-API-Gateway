package org.mirrentools.gateway.http;

import org.mirrentools.gateway.enums.ParameterType;
import org.mirrentools.gateway.http.enums.ParameterPosition;

/**
 * http1.x请求或响应的数据
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 * @version 创建时间：2019年8月2日 下午1:43:00
 *
 */
public class OrionHttpParameterModel {
	/** 从客户端那个位置取数据 */
	private ParameterPosition formPosition;
	/** 数据在客户端叫什么 */
	private String formName;
	/** 发送到后端那个位置 */
	private ParameterPosition toPosition;
	/** 发送到后端名称叫什么 */
	private String toName;
	/** 数据的类型 */
	private ParameterType type;
	/** 是否可以为空 */
	private boolean nullable;
	/** 是否为数组 */
	private boolean isArray;
	/** 默认值 */
	private String def;
	/** 数据检查 */
	private OrionParameterModelCheck check;

	public ParameterPosition getFormPosition() {
		return formPosition;
	}

	public OrionHttpParameterModel setFormPosition(ParameterPosition formPosition) {
		this.formPosition = formPosition;
		return this;
	}

	public String getFormName() {
		return formName;
	}

	public OrionHttpParameterModel setFormName(String formName) {
		this.formName = formName;
		return this;
	}

	public ParameterPosition getToPosition() {
		return toPosition;
	}

	public OrionHttpParameterModel setToPosition(ParameterPosition toPosition) {
		this.toPosition = toPosition;
		return this;
	}

	public String getToName() {
		return toName;
	}

	public OrionHttpParameterModel setToName(String toName) {
		this.toName = toName;
		return this;
	}

	public ParameterType getType() {
		return type;
	}

	public OrionHttpParameterModel setType(ParameterType type) {
		this.type = type;
		return this;
	}

	public boolean isNullable() {
		return nullable;
	}

	public OrionHttpParameterModel setNullable(boolean nullable) {
		this.nullable = nullable;
		return this;
	}

	public boolean isArray() {
		return isArray;
	}

	public OrionHttpParameterModel setArray(boolean isArray) {
		this.isArray = isArray;
		return this;
	}

	public String getDef() {
		return def;
	}

	public OrionHttpParameterModel setDef(String def) {
		this.def = def;
		return this;
	}

	public OrionParameterModelCheck getCheck() {
		return check;
	}

	public OrionHttpParameterModel setCheck(OrionParameterModelCheck check) {
		this.check = check;
		return this;
	}

}
