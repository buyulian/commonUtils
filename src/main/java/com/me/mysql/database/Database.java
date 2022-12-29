package com.me.mysql.database;

import com.me.mysql.model.Table;

import java.util.List;

public interface Database {
    List<Table> getTableList();
}
