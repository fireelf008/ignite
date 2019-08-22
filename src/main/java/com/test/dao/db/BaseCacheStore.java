package com.test.dao.db;

import com.test.utils.ApplicationContextUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public interface BaseCacheStore {

    DataSourceTransactionManager transactionManager = ApplicationContextUtils.getBean(DataSourceTransactionManager.class);
    DefaultTransactionDefinition def = ApplicationContextUtils.getBean(DefaultTransactionDefinition.class);
}
