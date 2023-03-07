package com.timedeal.common.service;

public interface CrudService<T> {

	public T save(T t);
	
	public T delete(T t);
}
