package org.mirrentools.gateway.common;

/**
 * 用于存储数据库列名,与列名对应的值
 * 
 * @author github.shenzhenMirren
 *
 */
public class SqlPropertyValue<T> {
	private String name;
	private T value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public SqlPropertyValue(String name, T value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "PropertyValue [name=" + name + ", value=" + value + "]";
	}

}
