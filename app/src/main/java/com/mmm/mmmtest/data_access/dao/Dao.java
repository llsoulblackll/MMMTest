package com.mmm.mmmtest.data_access.dao;

import com.mmm.mmmtest.data_access.DbHelperFactory;

import java.util.List;

interface Dao<T> {
    boolean insert(T toInsert);
    boolean update(T toUpdate);
    boolean delete(Object id);
    T find(Object id);
    List<T> findAll();
    boolean close();
    DbHelperFactory.DbHelper getDbHelper();
}
