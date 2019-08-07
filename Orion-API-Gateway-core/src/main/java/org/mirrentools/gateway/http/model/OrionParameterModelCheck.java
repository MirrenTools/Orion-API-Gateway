package org.mirrentools.gateway.http.model;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 参数检查
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
public class OrionParameterModelCheck {
	/** 字符串的最小长度 */
	private Long minLength;
	/** 字符串的最大长度 */
	private Long maxLength;
	/** 数值的最小值 */
	private Number minValue;
	/** 数值的最大值 */
	private Number maxValue;
	/** 正则表达式 */
	private String regex;
	/** 枚举类 */
	private List<String> enums;

	/**
	 * 将对象转换为json
	 * 
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		if (minLength != null) {
			json.put("minLength", this.minLength);
		}
		if (maxLength != null) {
			json.put("maxLength", this.maxLength);
		}
		if (minValue != null) {
			json.put("minValue", this.minValue);
		}
		if (maxValue != null) {
			json.put("maxValue", this.maxValue);
		}
		if (regex != null) {
			json.put("regex", this.regex);
		}
		if (enums != null) {
			json.put("enums", enums);
		}
		return json;
	}

	/**
	 * 通过json实例化一个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static OrionParameterModelCheck fromJson(JsonObject obj) {
		if (obj == null) {
			return null;
		}
		OrionParameterModelCheck option = new OrionParameterModelCheck();
		if (obj.getValue("minLength") instanceof Number) {
			option.setMinLength(((Number) obj.getValue("minLength")).longValue());
		}
		if (obj.getValue("maxLength") instanceof Number) {
			option.setMaxLength(((Number) obj.getValue("maxLength")).longValue());
		}
		if (obj.getValue("minValue") instanceof Number) {
			option.setMinValue((Number) obj.getValue("minValue"));
		}
		if (obj.getValue("maxValue") instanceof Number) {
			option.setMaxValue((Number) obj.getValue("maxValue"));
		}
		if (obj.getValue("regex") instanceof String) {
			option.setRegex(obj.getString("regex"));
		}
		if (obj.getValue("enums") instanceof JsonArray) {
			List<String> list = new ArrayList<>();
			obj.getJsonArray("enums").forEach(va -> {
				if (va instanceof String) {
					list.add(va.toString());
				}
			});
			option.setEnums(list);
		}
		return option;
	}

	public OrionParameterModelCheck() {
		super();
	}

	public OrionParameterModelCheck(Long minLength, Long maxLength, Number minValue, Number maxValue, String regex, List<String> enums) {
		super();
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.regex = regex;
		this.enums = enums;
	}

	public Long getMinLength() {
		return minLength;
	}

	public OrionParameterModelCheck setMinLength(Long minLength) {
		this.minLength = minLength;
		return this;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public OrionParameterModelCheck setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
		return this;
	}

	public Number getMinValue() {
		return minValue;
	}

	public OrionParameterModelCheck setMinValue(Number minValue) {
		this.minValue = minValue;
		return this;
	}

	public Number getMaxValue() {
		return maxValue;
	}

	public OrionParameterModelCheck setMaxValue(Number maxValue) {
		this.maxValue = maxValue;
		return this;
	}

	public String getRegex() {
		return regex;
	}

	public OrionParameterModelCheck setRegex(String regex) {
		this.regex = regex;
		return this;
	}

	public List<String> getEnums() {
		return enums;
	}

	public OrionParameterModelCheck setEnums(List<String> enums) {
		this.enums = enums;
		return this;
	}

	@Override
	public String toString() {
		return "OrionParameterModelCheck [minLength=" + minLength + ", maxLength=" + maxLength + ", minValue=" + minValue + ", maxValue=" + maxValue + ", regex=" + regex + ", enums=" + enums + "]";
	}
}
