package mysql.database;

import mysql.model.Table;

import java.util.List;

public interface Database {
    List<Table> getTableList();
}
