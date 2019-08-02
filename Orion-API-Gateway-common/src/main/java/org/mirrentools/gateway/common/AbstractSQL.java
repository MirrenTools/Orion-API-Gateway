package org.mirrentools.gateway.common;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;

/**
 * MySQL数据库操作语句
 * 
 * @author github.shenzhenMirren
 *
 */
public abstract class AbstractSQL<T> {

	private final Logger LOG = LogManager.getLogger(AbstractSQL.class);

	// ====================================
	// =============抽象方法区===============
	// ====================================
	/**
	 * 表的名字
	 * 
	 * @return
	 */
	protected abstract String tableName();

	/**
	 * 表的主键
	 * 
	 * @return
	 */
	protected abstract String primaryId();

	/**
	 * 表的所有列名
	 * 
	 * @return
	 */
	protected abstract String columns();

	/**
	 * 表的所有列名与列名对应的值
	 * 
	 * @return
	 */
	protected abstract List<SqlPropertyValue<?>> propertyValue(T obj);

	// ====================================
	// =============通用方法区===============
	// ====================================
	/**
	 * 执行查询
	 * 
	 * @param qp
	 *          SQL语句与参数
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果
	 */
	public void queryExecuteAsObj(SqlAndParams qp, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		queryExecute(qp, conn, query -> {
			if (query.succeeded()) {
				List<JsonObject> rows = query.result().getRows();
				if (rows != null && rows.size() > 0) {
					handler.handle(Future.succeededFuture(rows.get(0)));
				} else {
					handler.handle(Future.succeededFuture());
				}
			} else {
				handler.handle(Future.failedFuture(query.cause()));
			}
		});
	}
	/**
	 * 执行查询
	 * 
	 * @param qp
	 *          SQL语句与参数
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果
	 */
	public void queryExecuteAsList(SqlAndParams qp, SQLConnection conn, Handler<AsyncResult<List<JsonObject>>> handler) {
		queryExecute(qp, conn, query -> {
			if (query.succeeded()) {
				List<JsonObject> rows = query.result().getRows();
				handler.handle(Future.succeededFuture(rows));
			} else {
				handler.handle(Future.failedFuture(query.cause()));
			}
		});
	}

	/**
	 * 执行查询
	 * 
	 * @param qp
	 *          SQL语句与参数
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果
	 */
	public void queryExecute(SqlAndParams qp, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler) {
		Promise<ResultSet> promise = Promise.promise();
		Future<ResultSet> fut = promise.future();
		fut.setHandler(query -> {
			if (query.succeeded()) {
				conn.close(close -> {
					if (close.failed()) {
						handler.handle(Future.failedFuture(close.cause()));
					} else {
						handler.handle(Future.succeededFuture(query.result()));
					}
				});
			} else {
				conn.close(close -> {
					if (close.failed()) {
						handler.handle(Future.failedFuture(close.cause()));
					} else {
						handler.handle(Future.failedFuture(query.cause()));
					}
				});
			}
		});
		if (qp.getParams() == null) {
			conn.query(qp.getSql(), promise);
		} else {
			conn.queryWithParams(qp.getSql(), qp.getParams(), promise);
		}
	}
	/**
	 * 执行更新等操作得到受影响的行数
	 * 
	 * @param qp
	 *          SQL语句与参数
	 * @param conn
	 *          数据库连接
	 * @param handler
	 */
	public void updateExecuteResult(SqlAndParams qp, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateExecute(qp, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		});
	}

	/**
	 * 执行更新等操作
	 * 
	 * @param qp
	 *          SQL语句与参数
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果
	 */
	public void updateExecute(SqlAndParams qp, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler) {
		Promise<UpdateResult> promise = Promise.<UpdateResult>promise();
		Future<UpdateResult> fut = promise.future();
		fut.setHandler(query -> {
			if (query.succeeded()) {
				conn.close(close -> {
					if (close.failed()) {
						handler.handle(Future.failedFuture(close.cause()));
					} else {
						handler.handle(Future.succeededFuture(query.result()));
					}
				});
			} else {
				conn.close(close -> {
					if (close.failed()) {
						handler.handle(Future.failedFuture(close.cause()));
					} else {
						handler.handle(Future.failedFuture(query.cause()));
					}
				});
			}
		});
		if (qp.getParams() == null) {
			conn.update(qp.getSql(), promise);
		} else {
			conn.updateWithParams(qp.getSql(), qp.getParams(), promise);
		}
	}

	// ====================================
	// ==============主方法区================
	// ====================================

	/**
	 * 获得数据库总行数SQL语句<br>
	 * 返回:sql
	 * 
	 * @return
	 * @since 1.0.2
	 */
	public SqlAndParams getCount() {
		return getCount(null);
	}
	/**
	 * 获得数据总行数SQL语句<br>
	 * 
	 * @param assist
	 * @return 返回:sql or sql与params
	 */
	public SqlAndParams getCount(SqlAssist assist) {
		StringBuilder sql = new StringBuilder(String.format("select count(*) from %s", tableName()));
		JsonArray params = null;
		if (assist != null) {
			if (assist.getCondition() != null && assist.getCondition().size() > 0) {
				List<SqlWhereCondition<?>> where = assist.getCondition();
				params = new JsonArray();
				sql.append(" where " + where.get(0).getRequire());
				if (where.get(0).getValue() != null) {
					params.add(where.get(0).getValue());
				}
				if (where.get(0).getValues() != null) {
					for (Object value : where.get(0).getValues()) {
						params.add(value);
					}
				}
				for (int i = 1; i < where.size(); i++) {
					sql.append(where.get(i).getRequire());
					if (where.get(i).getValue() != null) {
						params.add(where.get(i).getValue());
					}
					if (where.get(i).getValues() != null) {
						for (Object value : where.get(i).getValues()) {
							params.add(value);
						}
					}
				}
			}
		}
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCountSQL : " + result.toString());
		}
		return result;
	}

	/**
	 * 
	 * @param assist
	 * @param conn
	 * @param handler
	 * @since 1.0.2
	 */
	public void getCount(SQLConnection conn, Handler<AsyncResult<Long>> handler) {
		getCount(null, conn, handler);
	}
	/**
	 * 
	 * @param assist
	 * @param conn
	 * @param handler
	 * @since 1.0.2
	 */
	public void getCount(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<Long>> handler) {
		getCount(assist, conn, set -> {
			if (set.succeeded()) {
				List<JsonArray> rows = set.result().getResults();
				if (rows != null && rows.size() >= 0) {
					Object value = rows.get(0).getValue(0);
					if (value instanceof Number) {
						handler.handle(Future.succeededFuture(((Number) value).longValue()));
					} else {
						handler.handle(Future.succeededFuture(0L));
					}
				} else {
					handler.handle(Future.succeededFuture(0L));
				}
			} else {
				handler.handle(Future.failedFuture(set.cause()));
			}
		}, null);
	}
	/**
	 * 
	 * @param assist
	 *          查询工具,如果没有可以为null
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果为{@link AsyncResult<ResultSet>}
	 * @param nullable
	 *          用户重载方法没有实际作用传入null便可
	 * @since 1.0.2
	 */
	public void getCount(SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		getCount(null, conn, handler, nullable);
	}
	/**
	 * 
	 * @param assist
	 *          查询工具,如果没有可以为null
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果为{@link AsyncResult<ResultSet>}
	 * @param nullable
	 *          用户重载方法没有实际作用传入null便可
	 * @since 1.0.2
	 */
	public void getCount(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		SqlAndParams qp = getCount(assist);
		queryExecute(qp, conn, handler);
	}

	/**
	 * 获得查询全部数据SQL语句与参数<br>
	 * 
	 * @return 返回:sql
	 */
	public SqlAndParams selectAll() {
		return selectAll(null);
	}

	/**
	 * 获得查询全部数据SQL语句与参数<br>
	 * 
	 * @param assist
	 * @return 返回:sql or sql与params
	 */
	public SqlAndParams selectAll(SqlAssist assist) {
		// 如果Assist为空返回默认默认查询语句,反则根据Assist生成语句sql语句
		if (assist == null) {
			SqlAndParams result = new SqlAndParams(String.format("select %s from %s ", columns(), tableName()));
			if (LOG.isDebugEnabled()) {
				LOG.debug("SelectAllSQL : " + result.toString());
			}
			return result;
		} else {
			String distinct = assist.getDistinct() == null ? "" : assist.getDistinct();// 去重语句
			String column = assist.getResultColumn() == null ? columns() : assist.getResultColumn();// 表的列名
			// 初始化SQL语句
			StringBuilder sql = new StringBuilder(String.format("select %s %s from %s", distinct, column, tableName()));
			JsonArray params = null;// 参数
			if (!assist.isOptimizePage() || (assist.getRowSize() == null && assist.getStartRow() == null) || primaryId() == null) {
				if (assist.getCondition() != null && assist.getCondition().size() > 0) {
					List<SqlWhereCondition<?>> where = assist.getCondition();
					params = new JsonArray();
					sql.append(" where " + where.get(0).getRequire());
					if (where.get(0).getValue() != null) {
						params.add(where.get(0).getValue());
					}
					if (where.get(0).getValues() != null) {
						for (Object value : where.get(0).getValues()) {
							params.add(value);
						}
					}
					for (int i = 1; i < where.size(); i++) {
						sql.append(where.get(i).getRequire());
						if (where.get(i).getValue() != null) {
							params.add(where.get(i).getValue());
						}
						if (where.get(i).getValues() != null) {
							for (Object value : where.get(i).getValues()) {
								params.add(value);
							}
						}
					}
				}
				if (assist.getOrder() != null) {
					sql.append(assist.getOrder());
				}
				if (assist.getRowSize() != null || assist.getStartRow() != null) {
					if (params == null) {
						params = new JsonArray();
					}
					if (assist.getStartRow() == null) {
						sql.append(" LIMIT ?");
						params.add(assist.getRowSize());
					} else {
						sql.append(" LIMIT ?,?");
						params.add(assist.getStartRow()).add(assist.getRowSize());
					}
				}
			} else {
				// 添加分页语句
				sql.append(String.format(" inner join ( select %s as tmp_id from %s ", primaryId(), tableName()));
				if (assist.getCondition() != null && assist.getCondition().size() > 0) {
					List<SqlWhereCondition<?>> where = assist.getCondition();
					params = new JsonArray();
					sql.append(" where " + where.get(0).getRequire());
					if (where.get(0).getValue() != null) {
						params.add(where.get(0).getValue());
					}
					if (where.get(0).getValues() != null) {
						for (Object value : where.get(0).getValues()) {
							params.add(value);
						}
					}
					for (int i = 1; i < where.size(); i++) {
						sql.append(where.get(i).getRequire());
						if (where.get(i).getValue() != null) {
							params.add(where.get(i).getValue());
						}
						if (where.get(i).getValues() != null) {
							for (Object value : where.get(i).getValues()) {
								params.add(value);
							}
						}
					}
				}
				if (assist.getOrder() != null) {
					sql.append(assist.getOrder());
				}
				if (params == null) {
					params = new JsonArray();
				}
				if (assist.getStartRow() == null) {
					sql.append("LIMIT ?");
					params.add(assist.getRowSize());
				} else {
					sql.append("LIMIT ?,?");
					params.add(assist.getStartRow()).add(assist.getRowSize());
				}

				sql.append(String.format(" ) as tmp on tmp.tmp_id=%s.%s ", tableName(), primaryId()));
			}
			SqlAndParams result = new SqlAndParams(sql.toString(), params);
			if (LOG.isDebugEnabled()) {
				LOG.debug("SelectAllSQL : " + result.toString());
			}
			return result;
		}
	}
	/**
	 * 查询所有数据
	 * 
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果集
	 */
	public void selectAll(SQLConnection conn, Handler<AsyncResult<List<JsonObject>>> handler) {
		selectAll(null, conn, handler);
	}
	/**
	 * 通过查询工具查询所有数据
	 * 
	 * @param assist
	 *          查询工具帮助类
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          数据库连接
	 */
	public void selectAll(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<List<JsonObject>>> handler) {
		selectAll(assist, conn, set -> {
			if (set.succeeded()) {
				List<JsonObject> rows = set.result().getRows();
				handler.handle(Future.succeededFuture(rows));
			} else {
				handler.handle(Future.failedFuture(set.cause()));
			}
		}, null);
	}
	/**
	 * 查询所有数据
	 * 
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果集
	 * @param nullable
	 *          没有任何作用传null就可以了,用于重载方法而已
	 */
	public void selectAll(SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		selectAll(null, conn, handler, nullable);
	}
	/**
	 * 通过查询工具查询所有数据
	 * 
	 * @param assist
	 *          查询工具帮助类
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果集
	 * @param nullable
	 *          没有任何作用传null就可以了,用于重载方法而已
	 */
	public void selectAll(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		SqlAndParams qp = selectAll(assist);
		queryExecute(qp, conn, handler);
	}

	/**
	 * 获得分页查询的SQL语句与参数<br>
	 * 
	 * @param assist
	 * @return 返回: batchSql or batchSql与batchParams
	 */
	public SqlAndParams selectAllByPage(SqlAssist assist) {
		SqlAndParams count = getCount(assist);// 获得数据总行数
		SqlAndParams selectAll = selectAll(assist);// 获得数据
		SqlAndParams result = new SqlAndParams();
		result.addSqlAndParams(count, selectAll);
		return result;
	}

	/**
	 * 获得分页查询的结果
	 * 
	 * @param assist
	 *          查询工具帮助类,如果assist为空或者分页数据为空 startRow默认值0,rowSize默认30;
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果: {count:Long数据总行数,默认值=0,data:JsonArray<JsonObject>数据默认值=new
	 *          JsonArray()}
	 */
	public void selectAllByPage(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		selectAllByPage("count", "data", assist, conn, handler);
	}
	/**
	 * 获得分页查询的结果
	 * 
	 * @param countName
	 *          数据总行数的key名字 ,值默认=0
	 * @param dataName
	 *          数据的key名字 , data默认=new JsonArray
	 * @param assist
	 *          SQL帮助类
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果
	 */
	public void selectAllByPage(String countName, String dataName, SqlAssist assist, SQLConnection conn,
			Handler<AsyncResult<JsonObject>> handler) {
		SqlAndParams cqp = getCount(assist);
		Future.<Long>future(count -> {
			conn.queryWithParams(cqp.getSql(), cqp.getParams(), set -> {
				if (set.succeeded()) {
					List<JsonArray> rows = set.result().getResults();
					long result = 0;
					if (rows != null && rows.size() >= 0) {
						Object value = rows.get(0).getValue(0);
						if (value instanceof Number) {
							result = ((Number) value).longValue();
						}
					}
					count.complete(result);
				} else {
					count.fail(set.cause());
				}
			});
		}).compose(count -> Future.<JsonObject>future(find -> {
			JsonObject result = new JsonObject();
			result.put(countName, count);
			if (count == 0) {
				result.put(dataName, new JsonArray());
				find.complete(result);
			} else {
				SqlAndParams allqp;
				if (assist == null) {
					allqp = selectAll(new SqlAssist().setRowSize(30));
				} else if (assist.getRowSize() == null) {
					assist.setRowSize(30);
					allqp = selectAll(assist);
				} else {
					allqp = selectAll(assist);
				}
				conn.queryWithParams(allqp.getSql(), allqp.getParams(), set -> {
					if (set.succeeded()) {
						List<JsonObject> rows = set.result().getRows();
						result.put(dataName, new JsonArray(rows));
						conn.close(close -> {
							if (close.failed()) {
								find.fail(close.cause());
							} else {
								find.complete(result);
							}
						});
					} else {
						conn.close(close -> {
							if (close.failed()) {
								find.fail(close.cause());
							} else {
								find.fail(set.cause());
							}
						});
					}
				});
			}
		})).setHandler(handler);
	}

	/**
	 * 通过主键查询一个对象<br>
	 * 
	 * @param primaryKey
	 *          主键的值
	 * @return 返回:sql or sql与params
	 */
	public <S> SqlAndParams selectById(S primaryValue) {
		return selectById(primaryValue, null);
	}

	/**
	 * 通过主键查询一个对象<br>
	 * 返回:sql or sql与params
	 * 
	 * @param primaryKey
	 *          主键的值
	 * @param resultColumns
	 *          指定返回列 格式 [table.]列名 [as 类的属性名字],...
	 * @return
	 */
	public <S> SqlAndParams selectById(S primaryValue, String resultColumns) {
		String sql = String.format("select %s from %s where %s = ? ", resultColumns == null ? columns() : resultColumns, tableName(),
				primaryId());
		JsonArray params = new JsonArray();
		params.add(primaryValue);
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectByIdSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 通过ID查询出数据
	 * 
	 * @param primaryValue
	 *          主键值
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果:如果查询得到返回JsonObject如果查询不到返回null
	 */
	public <S> void selectById(S primaryValue, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		selectById(primaryValue, null, conn, handler);
	}

	/**
	 * 通过ID查询出数据,并自定义返回列
	 * 
	 * @param primaryValue
	 *          主键值
	 * @param resultColumns
	 *          自定义返回列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果:如果查询得到返回JsonObject如果查询不到返回null
	 */
	public <S> void selectById(S primaryValue, String resultColumns, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		selectById(primaryValue, resultColumns, conn, set -> {
			if (set.succeeded()) {
				List<JsonObject> rows = set.result().getRows();
				if (rows != null && rows.size() > 0) {
					handler.handle(Future.succeededFuture(rows.get(0)));
				} else {
					handler.handle(Future.succeededFuture());
				}
			} else {
				handler.handle(Future.failedFuture(set.cause()));
			}
		}, null);
	}

	/**
	 * 通过ID查询出数据
	 * 
	 * @param primaryValue
	 *          主键值
	 * @param conn
	 *          自定义返回列
	 * @param handler
	 *          返回结果:ResultSet
	 * @param nullable
	 *          没有实际用处只是重载用,可以传null
	 */
	public <S> void selectById(S primaryValue, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		selectById(primaryValue, null, conn, handler, nullable);
	}
	/**
	 * 通过ID查询出数据,并自定义返回列
	 * 
	 * @param primaryValue
	 *          主键值
	 * @param resultColumns
	 *          自定义返回列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果:ResultSet
	 * @param nullable
	 *          没有实际用处只是重载用,可以传null
	 */
	public <S> void selectById(S primaryValue, String resultColumns, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler,
			Boolean nullable) {
		SqlAndParams qp;
		if (resultColumns == null) {
			qp = selectById(primaryValue);
		} else {
			qp = selectById(primaryValue, resultColumns);
		}
		queryExecute(qp, conn, handler);
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @return 返回sql 或 sql与params
	 */
	public SqlAndParams selectByObj(T obj) {
		return selectByObj(obj, null);
	}

	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param resultColumns
	 *          自定义返回列
	 * @return 返回sql 或 sql与params
	 */
	public SqlAndParams selectByObj(T obj, String resultColumns) {
		return selectByObj(obj, resultColumns, false);
	}

	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param resultColumns
	 *          自定义返回列
	 * @param single
	 *          是否支取一条数据true支取一条,false取全部
	 * @return 返回sql 或 sql与params
	 * 
	 */
	public SqlAndParams selectByObj(T obj, String resultColumns, boolean single) {
		StringBuilder sql = new StringBuilder(
				String.format("select %s from %s ", resultColumns == null ? columns() : resultColumns, tableName()));
		JsonArray params = null;
		boolean isFrist = true;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getValue() != null) {
				if (isFrist) {
					params = new JsonArray();
					sql.append(String.format("where %s = ? ", pv.getName()));
					params.add(pv.getValue());
					isFrist = false;
				} else {
					sql.append(String.format("and %s = ? ", pv.getName()));
					params.add(pv.getValue());
				}
			}
		}
		if (single) {
			sql.append(" LIMIT 1");
		}
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("selectByObjSQL : " + result.toString());
		}
		return result;
	}

	/**
	 * 将对象属性不为null的属性作为条件查询出数据,只取查询出来的第一条数据
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果:如果存在返回JsonObject,不存在返回null
	 */
	public void selectSingleByObj(T obj, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		selectSingleByObj(obj, null, conn, handler);
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据,只取查询出来的第一条数据
	 * 
	 * @param obj
	 *          对象
	 * @param resultColumns
	 *          自定义返回列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果:如果存在返回JsonObject,不存在返回null
	 */
	public void selectSingleByObj(T obj, String resultColumns, SQLConnection conn, Handler<AsyncResult<JsonObject>> handler) {
		SqlAndParams qp = selectByObj(obj, resultColumns, true);
		queryExecute(qp, conn, set -> {
			if (set.succeeded()) {
				List<JsonObject> rows = set.result().getRows();
				JsonObject result = null;
				if (rows != null && rows.size() > 0) {
					result = rows.get(0);
				}
				handler.handle(Future.succeededFuture(result));
			} else {
				handler.handle(Future.failedFuture(set.cause()));
			}
		});
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果集
	 */
	public void selectByObj(T obj, SQLConnection conn, Handler<AsyncResult<List<JsonObject>>> handler) {
		selectByObj(obj, null, conn, handler);
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param resultColumns
	 *          自定义返回列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果集
	 */
	public void selectByObj(T obj, String resultColumns, SQLConnection conn, Handler<AsyncResult<List<JsonObject>>> handler) {
		selectByObj(obj, resultColumns, conn, set -> {
			if (set.succeeded()) {
				List<JsonObject> rows = set.result().getRows();
				handler.handle(Future.succeededFuture(rows));
			} else {
				handler.handle(Future.failedFuture(set.cause()));
			}
		}, null);
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果集
	 */
	public void selectByObj(T obj, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		selectByObj(obj, null, conn, handler, nullable);
	}
	/**
	 * 将对象属性不为null的属性作为条件查询出数据
	 * 
	 * @param obj
	 *          对象
	 * @param resultColumns
	 *          自定义返回列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回结果集
	 */
	public void selectByObj(T obj, String resultColumns, SQLConnection conn, Handler<AsyncResult<ResultSet>> handler, Boolean nullable) {
		SqlAndParams qp = selectByObj(obj, resultColumns);
		queryExecute(qp, conn, handler);
	}

	/**
	 * 插入一个对象包括属性值为null的值<br>
	 * 
	 * @param obj
	 * @return 返回:sql 或者 sql与params
	 */
	public SqlAndParams insertAll(T obj) {
		JsonArray params = null;
		StringBuilder tempColumn = null;
		StringBuilder tempValues = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (tempColumn == null) {
				tempColumn = new StringBuilder(pv.getName());
				tempValues = new StringBuilder("?");
				params = new JsonArray();
			} else {
				tempColumn.append("," + pv.getName());
				tempValues.append(",?");
			}
			if (pv.getValue() != null) {
				params.add(pv.getValue());
			} else {
				params.addNull();
			}
		}
		String sql = String.format("insert into %s (%s) values (%s) ", tableName(), tempColumn, tempValues);
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAllSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 插入一个对象包括属性值为null的值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void insertAll(T obj, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		insertAll(obj, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 插入一个对象包括属性值为null的值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void insertAll(T obj, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = insertAll(obj);
		updateExecute(qp, conn, handler);
	}

	/**
	 * 插入一个对象,指插入对象中值不为null的属性<br>
	 * 
	 * @param obj
	 *          对象
	 * @return 返回:sql 或 sql与params
	 */
	public SqlAndParams insertNonEmpty(T obj) {
		JsonArray params = null;
		StringBuilder tempColumn = null;
		StringBuilder tempValues = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getValue() != null) {
				if (tempColumn == null) {
					tempColumn = new StringBuilder(pv.getName());
					tempValues = new StringBuilder("?");
					params = new JsonArray();
				} else {
					tempColumn.append("," + pv.getName());
					tempValues.append(",?");
				}
				params.add(pv.getValue());
			}
		}
		String sql = String.format("insert into %s (%s) values (%s) ", tableName(), tempColumn, tempValues);
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertNonEmptySQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 插入一个对象,指插入对象中值不为null的属性
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void insertNonEmpty(T obj, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		insertNonEmpty(obj, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 插入一个对象,指插入对象中值不为null的属性
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void insertNonEmpty(T obj, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = insertNonEmpty(obj);
		updateExecute(qp, conn, handler);
	}
	/**
	 * 插入一个对象,如果该对象不存在就新建如果该对象已经存在就更新
	 * 
	 * @param obj
	 *          对象
	 * @return 返回:sql 或 sql与params
	 */
	public SqlAndParams replace(T obj) {
		JsonArray params = null;
		StringBuilder tempColumn = null;
		StringBuilder tempValues = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getValue() != null) {
				if (tempColumn == null) {
					tempColumn = new StringBuilder(pv.getName());
					tempValues = new StringBuilder("?");
					params = new JsonArray();
				} else {
					tempColumn.append("," + pv.getName());
					tempValues.append(",?");
				}
				params.add(pv.getValue());
			}
		}
		String sql = String.format("replace into %s (%s) values (%s) ", tableName(), tempColumn, tempValues);
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("replaceSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 插入一个对象,如果该对象不存在就新建如果该对象已经存在就更新
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果集受影响的行数
	 */
	public void replace(T obj, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		replace(obj, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}

	/**
	 * 插入一个对象,如果该对象不存在就新建如果该对象已经存在就更新
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          结果集
	 * @param nullable
	 *          没有什么作用,只是用于重载
	 */
	public void replace(T obj, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = replace(obj);
		updateExecute(qp, conn, handler);
	}
	/**
	 * 更新一个对象中所有的属性包括null值,条件为对象中的主键值
	 * 
	 * @param obj
	 * @return 返回:sql or sql与params, 如果对象中的id为null将会返回SQL:"there is no primary key
	 *         in your SQL statement"
	 */
	public SqlAndParams updateAllById(T obj) {
		if (primaryId() == null) {
			return new SqlAndParams(false, "there is no primary key in your SQL statement");
		}
		JsonArray params = null;
		StringBuilder tempColumn = null;
		Object tempIdValue = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getName().equals(primaryId())) {
				tempIdValue = pv.getValue();
				continue;
			}
			if (tempColumn == null) {
				params = new JsonArray();
				tempColumn = new StringBuilder(pv.getName() + " = ? ");
			} else {
				tempColumn.append(", " + pv.getName() + " = ? ");
			}
			if (pv.getValue() != null) {
				params.add(pv.getValue());
			} else {
				params.addNull();
			}
		}
		if (tempIdValue == null) {
			return new SqlAndParams(false, "there is no primary key in your SQL statement");
		}
		params.add(tempIdValue);
		String sql = String.format("update %s set %s where %s = ? ", tableName(), tempColumn, primaryId());
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAllByIdSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 更新一个对象中所有的属性包括null值,条件为对象中的主键值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void updateAllById(T obj, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateAllById(obj, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 更新一个对象中所有的属性包括null值,条件为对象中的主键值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateAllById(T obj, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = updateAllById(obj);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}

	/**
	 * 更新一个对象中所有的属性包括null值,条件为SqlAssist条件集<br>
	 * 
	 * @param obj
	 * @param assist
	 * @return 返回:sql or
	 *         sql与params如果SqlAssist对象或者对象的Condition为null将会返回SQL:"SqlAssist or
	 *         SqlAssist.condition is null"
	 */
	public SqlAndParams updateAllByAssist(T obj, SqlAssist assist) {
		if (assist == null || assist.getCondition() == null || assist.getCondition().size() < 1) {
			return new SqlAndParams(false, "SqlAssist or SqlAssist.condition is null");
		}
		JsonArray params = null;
		StringBuilder tempColumn = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (tempColumn == null) {
				params = new JsonArray();
				tempColumn = new StringBuilder(pv.getName() + " = ? ");
			} else {
				tempColumn.append(", " + pv.getName() + " = ? ");
			}
			if (pv.getValue() != null) {
				params.add(pv.getValue());
			} else {
				params.addNull();
			}
		}
		List<SqlWhereCondition<?>> where = assist.getCondition();
		StringBuilder whereStr = new StringBuilder(" where " + where.get(0).getRequire());
		if (where.get(0).getValue() != null) {
			params.add(where.get(0).getValue());
		}
		if (where.get(0).getValues() != null) {
			for (Object value : where.get(0).getValues()) {
				params.add(value);
			}
		}
		for (int i = 1; i < where.size(); i++) {
			whereStr.append(where.get(i).getRequire());
			if (where.get(i).getValue() != null) {
				params.add(where.get(i).getValue());
			}
			if (where.get(i).getValues() != null) {
				for (Object value : where.get(i).getValues()) {
					params.add(value);
				}
			}
		}
		String sql = String.format("update %s set %s %s", tableName(), tempColumn, whereStr == null ? "" : whereStr);
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAllByAssistSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 更新一个对象中所有的属性包括null值,条件为SqlAssist条件集<br>
	 * 
	 * @param obj
	 *          对象
	 * @param SqlAssist
	 *          sql帮助工具
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void updateAllByAssist(T obj, SqlAssist assist, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateAllByAssist(obj, assist, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 更新一个对象中所有的属性包括null值,条件为SqlAssist条件集<br>
	 * 
	 * @param obj
	 *          对象
	 * @param assist
	 *          sql帮助工具
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateAllByAssist(T obj, SqlAssist assist, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = updateAllByAssist(obj, assist);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}
	/**
	 * 更新一个对象中属性不为null值,条件为对象中的主键值
	 * 
	 * @param obj
	 *          对象
	 * @return 返回:sql or sql与params , 如果id为null或者没有要更新的数据将返回SQL:"there is no
	 *         primary key in your SQL statement"
	 */
	public SqlAndParams updateNonEmptyById(T obj) {
		if (primaryId() == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("there is no primary key in your SQL statement");
			}
			return new SqlAndParams(false, "there is no primary key in your SQL statement");
		}
		JsonArray params = null;
		StringBuilder tempColumn = null;
		Object tempIdValue = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getName().equals(primaryId())) {
				tempIdValue = pv.getValue();
				continue;
			}
			if (pv.getValue() != null) {
				if (tempColumn == null) {
					params = new JsonArray();
					tempColumn = new StringBuilder(pv.getName() + " = ? ");
				} else {
					tempColumn.append(", " + pv.getName() + " = ? ");
				}
				params.add(pv.getValue());
			}
		}
		if (tempColumn == null || tempIdValue == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("there is no set update value or no primary key in your SQL statement");
			}
			return new SqlAndParams(false, "there is no set update value or no primary key in your SQL statement");
		}
		params.add(tempIdValue);
		String sql = String.format("update %s set %s where %s = ? ", tableName(), tempColumn, primaryId());
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateNonEmptyByIdSQL : " + result.toString());
		}
		return result;
	}

	/**
	 * 更新一个对象中属性不为null值,条件为对象中的主键值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void updateNonEmptyById(T obj, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateNonEmptyById(obj, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 更新一个对象中属性不为null值,条件为对象中的主键值
	 * 
	 * @param obj
	 *          对象
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateNonEmptyById(T obj, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = updateNonEmptyById(obj);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}

	/**
	 * 将对象中属性值不为null的进行更新,条件为SqlAssist条件集
	 * 
	 * @param obj
	 *          对象
	 * @param assist
	 *          SqlAssist对象条件集
	 * @return 返回:sql or sql与params , 如果assist为null将会返回sql:"SqlAssist or
	 *         SqlAssist.condition is null"
	 */
	public SqlAndParams updateNonEmptyByAssist(T obj, SqlAssist assist) {
		if (assist == null || assist.getCondition() == null || assist.getCondition().size() < 1) {
			return new SqlAndParams(false, "SqlAssist or SqlAssist.condition is null");
		}
		JsonArray params = null;
		StringBuilder tempColumn = null;
		for (SqlPropertyValue<?> pv : propertyValue(obj)) {
			if (pv.getValue() != null) {
				if (tempColumn == null) {
					params = new JsonArray();
					tempColumn = new StringBuilder(pv.getName() + " = ? ");
				} else {
					tempColumn.append(", " + pv.getName() + " = ? ");
				}
				params.add(pv.getValue());
			}
		}
		if (tempColumn == null) {
			return new SqlAndParams(false, "The object has no value");
		}

		List<SqlWhereCondition<?>> where = assist.getCondition();
		StringBuilder whereStr = new StringBuilder(" where " + where.get(0).getRequire());
		if (where.get(0).getValue() != null) {
			params.add(where.get(0).getValue());
		}
		if (where.get(0).getValues() != null) {
			for (Object value : where.get(0).getValues()) {
				params.add(value);
			}
		}
		for (int i = 1; i < where.size(); i++) {
			whereStr.append(where.get(i).getRequire());
			if (where.get(i).getValue() != null) {
				params.add(where.get(i).getValue());
			}
			if (where.get(i).getValues() != null) {
				for (Object value : where.get(i).getValues()) {
					params.add(value);
				}
			}
		}
		String sql = String.format("update %s set %s %s", tableName(), tempColumn, whereStr);
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateNonEmptyByAssistSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 更新一个对象中属性不为null值,条件为SqlAssist对象条件集
	 * 
	 * @param obj
	 *          对象
	 * @param assist
	 *          数据库连接
	 * @param conn
	 *          sql帮助工具
	 * @param handler
	 *          返回操作结果
	 */
	public void updateNonEmptyByAssist(T obj, SqlAssist assist, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateNonEmptyByAssist(obj, assist, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 更新一个对象中属性不为null值,条件为SqlAssist对象条件集
	 * 
	 * @param obj
	 *          对象
	 * @param assist
	 *          sql帮助工具
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateNonEmptyByAssist(T obj, SqlAssist assist, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler,
			Boolean nullable) {
		SqlAndParams qp = updateNonEmptyByAssist(obj, assist);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}
	public <S> SqlAndParams updateSetNullById(S primaryValue, List<String> columns) {
		if (primaryId() == null) {
			return new SqlAndParams(false, "there is no primary key in your SQL statement");
		}

		if (columns == null || columns.size() == 0) {
			return new SqlAndParams(false, "Columns cannot be null or empty");
		}
		StringBuilder setStr = new StringBuilder();
		setStr.append(" " + columns.get(0) + " = null ");
		for (int i = 1; i < columns.size(); i++) {
			setStr.append(", " + columns.get(i) + " = null ");
		}
		String sql = String.format("update %s set %s where %s = ? ", tableName(), setStr.toString(), primaryId());
		JsonArray params = new JsonArray();
		params.add(primaryValue);
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSetNullById : " + result.toString());
		}
		return result;
	}
	/**
	 * 通过主键值设置指定的列为空
	 * 
	 * @param <S>
	 * @param primaryValue
	 *          主键
	 * @param columns
	 *          要设置为null的列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public <S> void updateSetNullById(S primaryValue, List<String> columns, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateSetNullById(primaryValue, columns, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 通过主键值设置指定的列为空
	 * 
	 * @param <S>
	 * @param primaryValue
	 *          主键
	 * @param columns
	 *          要设置为null的列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public <S> void updateSetNullById(S primaryValue, List<String> columns, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler,
			Boolean nullable) {
		SqlAndParams qp = updateSetNullById(primaryValue, columns);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}
	/**
	 * 
	 * 将指定的列设置为空,条件为SqlAssist条件集
	 * 
	 * @param assist
	 *          SqlAssist对象条件集
	 * @param columns
	 *          要设置为空的列
	 * @return 返回:sql or sql与params , 如果assist为null将会返回sql:"SqlAssist or
	 *         SqlAssist.condition is null"
	 * @return
	 */
	public SqlAndParams updateSetNullByAssist(SqlAssist assist, List<String> columns) {
		if (assist == null || assist.getCondition() == null || assist.getCondition().size() < 1) {
			return new SqlAndParams(false, "SqlAssist or SqlAssist.condition is null");
		}
		if (columns == null || columns.size() == 0) {
			return new SqlAndParams(false, "Columns cannot be null or empty");
		}
		StringBuilder setStr = new StringBuilder();
		setStr.append(" " + columns.get(0) + " = null ");
		for (int i = 1; i < columns.size(); i++) {
			setStr.append(", " + columns.get(i) + " = null ");
		}
		JsonArray params = new JsonArray();
		List<SqlWhereCondition<?>> where = assist.getCondition();
		StringBuilder whereStr = new StringBuilder(" where " + where.get(0).getRequire());
		if (where.get(0).getValue() != null) {
			params.add(where.get(0).getValue());
		}
		if (where.get(0).getValues() != null) {
			for (Object value : where.get(0).getValues()) {
				params.add(value);
			}
		}
		for (int i = 1; i < where.size(); i++) {
			whereStr.append(where.get(i).getRequire());
			if (where.get(i).getValue() != null) {
				params.add(where.get(i).getValue());
			}
			if (where.get(i).getValues() != null) {
				for (Object value : where.get(i).getValues()) {
					params.add(value);
				}
			}
		}
		String sql = String.format("update %s set %s %s", tableName(), setStr.toString(), whereStr);
		SqlAndParams result = new SqlAndParams(sql.toString(), params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSetNullByAssist : " + result.toString());
		}
		return result;
	}
	/**
	 * 通过Assist作为条件设置内容为空
	 * 
	 * @param assist
	 *          sql帮助工具
	 * @param columns
	 *          要设置为null的列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateSetNullByAssist(SqlAssist assist, List<String> columns, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		updateSetNullByAssist(assist, columns, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 通过Assist作为条件设置内容为空
	 * 
	 * @param assist
	 *          sql帮助工具
	 * @param columns
	 *          要设置为null的列
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void updateSetNullByAssist(SqlAssist assist, List<String> columns, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler,
			Boolean nullable) {
		SqlAndParams qp = updateSetNullByAssist(assist, columns);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}

	/**
	 * 通过主键值删除对应的数据行
	 * 
	 * @param primaryValue
	 *          id值
	 * @return 返回:sql or sql与params , 如果id为null或者没有要更新的数据将返回SQL:"there is no
	 *         primary key in your SQL statement"
	 */
	public <S> SqlAndParams deleteById(S primaryValue) {
		if (primaryId() == null) {
			return new SqlAndParams(false, "there is no primary key in your SQL statement");
		}
		String sql = String.format("delete from %s where %s = ? ", tableName(), primaryId());
		JsonArray params = new JsonArray();
		params.add(primaryValue);
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteByIdSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 通过主键值删除对应的数据行
	 * 
	 * @param obj
	 *          主键值
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public <S> void deleteById(S primaryValue, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		deleteById(primaryValue, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 通过主键值删除对应的数据行
	 * 
	 * @param obj
	 *          主键值
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public <S> void deleteById(S primaryValue, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = deleteById(primaryValue);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}

	/**
	 * 通过SqlAssist条件集删除对应的数据行
	 * 
	 * @param assist
	 * @return 返回:sql or sql与params , 如果assist为null将会返回sql: "SqlAssist or
	 *         SqlAssist.condition is null"
	 */
	public SqlAndParams deleteByAssist(SqlAssist assist) {
		if (assist == null || assist.getCondition() == null || assist.getCondition().size() < 1) {
			return new SqlAndParams(false, "SqlAssist or SqlAssist.condition is null");
		}
		List<SqlWhereCondition<?>> where = assist.getCondition();
		JsonArray params = new JsonArray();
		StringBuilder whereStr = new StringBuilder(" where " + where.get(0).getRequire());
		if (where.get(0).getValue() != null) {
			params.add(where.get(0).getValue());
		}
		if (where.get(0).getValues() != null) {
			for (Object value : where.get(0).getValues()) {
				params.add(value);
			}
		} ;
		for (int i = 1; i < where.size(); i++) {
			whereStr.append(where.get(i).getRequire());
			if (where.get(i).getValue() != null) {
				params.add(where.get(i).getValue());
			}
			if (where.get(i).getValues() != null) {
				for (Object value : where.get(i).getValues()) {
					params.add(value);
				}
			}
		}
		String sql = String.format("delete from %s %s", tableName(), whereStr);
		SqlAndParams result = new SqlAndParams(sql, params);
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteByAssistSQL : " + result.toString());
		}
		return result;
	}
	/**
	 * 通过SqlAssist条件集删除对应的数据行
	 * 
	 * @param assist
	 *          条件集
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 */
	public void deleteByAssist(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<Integer>> handler) {
		deleteByAssist(assist, conn, result -> {
			if (result.succeeded()) {
				int updated = result.result().getUpdated();
				handler.handle(Future.succeededFuture(updated));
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		}, null);
	}
	/**
	 * 通过SqlAssist条件集删除对应的数据行
	 * 
	 * @param assist
	 *          条件集
	 * @param conn
	 *          数据库连接
	 * @param handler
	 *          返回操作结果
	 * @param nullable
	 *          没有实际作用,用于做方法重载而已,可以传入null
	 */
	public void deleteByAssist(SqlAssist assist, SQLConnection conn, Handler<AsyncResult<UpdateResult>> handler, Boolean nullable) {
		SqlAndParams qp = deleteByAssist(assist);
		if (qp.succeeded()) {
			updateExecute(qp, conn, handler);
		} else {
			conn.close(close -> {
				if (close.succeeded()) {
					handler.handle(Future.failedFuture(qp.getSql()));
				} else {
					handler.handle(Future.failedFuture(close.cause()));
				}
			});
		}
	}

}
