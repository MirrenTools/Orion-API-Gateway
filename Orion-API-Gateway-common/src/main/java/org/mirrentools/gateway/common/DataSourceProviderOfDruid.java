package org.mirrentools.gateway.common;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.spi.DataSourceProvider;

public class DataSourceProviderOfDruid extends DruidDataSource implements DataSourceProvider {
	private static final long serialVersionUID = 381971380983136762L;

	public int maximumPoolSize(DataSource dataSource, JsonObject config) throws SQLException {
		return config.getInteger("druid.maxActive", Integer.valueOf(20)).intValue();
	}

	public DataSource getDataSource(JsonObject config) throws SQLException {
		DruidDataSource ds = new DruidDataSource();
		Log4j2Filter log4j2 = new Log4j2Filter();
		ds.getProxyFilters().add(log4j2);
		ds.setUrl(config.getString("druid.url"));
		ds.setDriverClassName(config.getString("druid.driver"));
		ds.setUsername(config.getString("druid.username"));
		ds.setPassword(config.getString("druid.password"));
		ds.setMaxActive(config.getInteger("druid.maxActive", Integer.valueOf(20)).intValue());
		ds.setMinIdle(config.getInteger("druid.minIdle", Integer.valueOf(1)).intValue());
		ds.setInitialSize(config.getInteger("druid.druid", Integer.valueOf(1)).intValue());
		return ds;
	}

	public void close(DataSource dataSource) throws SQLException {
		if ((dataSource instanceof DataSourceProviderOfDruid))
			((DataSourceProviderOfDruid) dataSource).close();
	}
}