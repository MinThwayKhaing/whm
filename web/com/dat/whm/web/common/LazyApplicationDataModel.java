package com.dat.whm.web.common;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.util.CollectionUtils;

import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;

public class LazyApplicationDataModel extends LazyDataModel<User> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private IUserService userService;
	private ApplicationCriteria criteria;
	private List<User> datasource;

	public LazyApplicationDataModel(IUserService userService, ApplicationCriteria criteria) {
		this.userService = userService;
		this.criteria = criteria;
	}

	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		criteria.setOffset(first);
		criteria.setLimit(pageSize);

		// TODO Auto-generated method stub
//		ApplicationPagingData applicationPagingData = userService.getApplicationPagingData(criteria);
		ApplicationPagingData applicationPagingData = null;
		setRowCount(applicationPagingData.getCount());
		setPageSize(criteria.getLimit());
		datasource = applicationPagingData.getUserDTOList();
		return applicationPagingData.getUserDTOList();
	}

	@Override
	public Object getRowKey(User userDTO) {
		return userDTO.getId();
	}

	@Override
	public User getRowData(String rowKey) {
		if(!CollectionUtils.isEmpty(datasource)) {
			for (User userDTO : datasource) {
				if (userDTO.getId().toString().equals(rowKey)) {
					return userDTO;
				}
			}
		}
		return null;
	}
}