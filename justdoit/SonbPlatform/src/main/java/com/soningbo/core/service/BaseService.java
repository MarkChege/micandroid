package com.soningbo.core.service;

import java.io.Serializable;
import java.util.List;

import com.soningbo.core.dao.Condition;
import com.soningbo.core.dao.OrderBy;
import com.soningbo.core.dao.Updater;
import com.soningbo.core.page.Pagination;

public interface BaseService<E,PK extends Serializable> {

	/**
	 * 通过ID查找对象
	 * 
	 * @param id
	 *            记录的ID
	 * @return 实体对象
	 */
	public E findById(PK id);

	public E load(PK id);

	/**
	 * 查找所有对象
	 * 
	 * @return 对象列表
	 */
	public List<E> findAll();

	public Pagination<E> findAll(int pageNo, int pageSize, OrderBy... orderBys);

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param eg
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询。默认false。
	 * @param conds
	 *            排序及is null。分别为OrderBy和String。
	 * @param exclude
	 *            排除属性
	 * @return
	 */
	public List<E> findByEgList(E eg, boolean anyWhere, Condition[] conds,
			String... exclude);

	public List<E> findByEgList(E eg, boolean anyWhere, String... exclude);

	public List<E> findByEgList(E eg, Condition[] conds, String... exclude);

	public List<E> findByEgList(E eg, boolean anyWhere, Condition[] conds,
			int firstResult, int maxResult, String... exclude);

	public List<E> findByEgList(E eg, boolean anyWhere, int firstResult,
			int maxResult, String... exclude);

	public List<E> findByEgList(E eg, Condition[] conds, int firstResult,
			int maxResult, String... exclude);

	public List<E> findByEgList(E eg, String... exclude);

	public Pagination<E> findByEg(E eg, boolean anyWhere, Condition[] conds,
			int pageNo, int pageSize, String... exclude);

	public Pagination<E> findByEg(E eg, boolean anyWhere, int pageNo,
			int pageSize, String... exclude);

	public Pagination<E> findByEg(E eg, Condition[] conds, int pageNo,
			int pageSize, String... exclude);

	public Pagination<E> findByEg(E eg, int pageNo, int pageSize,
			String... exclude);

	/**
	 * 根据Updater更新对象
	 * 
	 * @param updater
	 */
	public Object updateByUpdater(Updater updater);

	public Object updateDefault(Object entity);

	/**
	 * 保存对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 操作信息
	 */
	public E save(E entity);

	public Object update(Object o);

	public Object saveOrUpdate(Object o);

	public void delete(Object o);

	/**
	 * 根据ID删除记录
	 * 
	 * @param id
	 *            记录ID
	 */
	public E deleteById(PK id);

	/**
	 * 根据ID数组删除记录，当发生异常时，操作终止并回滚
	 * 
	 * @param ids
	 *            记录ID数组
	 * @return 删除的对象
	 */
	public List<E> deleteById(PK[] ids);

	/**
	 * 保存并刷新对象，避免many-to-one属性不完整
	 * 
	 * @param entity
	 */
	public E saveAndRefresh(E entity);
}
