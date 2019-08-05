package org.mirrentools.gateway.http;

import io.vertx.core.json.JsonObject;

/**
 * URL记录信息
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class OrionUrlRecord {
	/** 记录的id */
	private String id;
	/** 请求的URL */
	private String url;
	/** 描述信息 */
	private JsonObject metadata;

	public OrionUrlRecord(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}

	public OrionUrlRecord(String id, String url, JsonObject metadata) {
		super();
		this.id = id;
		this.url = url;
		this.metadata = metadata;
	}

	public String getId() {
		return id;
	}

	public OrionUrlRecord setId(String id) {
		this.id = id;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public OrionUrlRecord setUrl(String url) {
		this.url = url;
		return this;
	}

	public JsonObject getMetadata() {
		return metadata;
	}

	public OrionUrlRecord setMetadata(JsonObject metadata) {
		this.metadata = metadata;
		return this;
	}

}
