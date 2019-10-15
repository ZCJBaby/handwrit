package com.handwrit.manage.utils.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public abstract interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {

}