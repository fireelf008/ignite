package com.test.ignite.config;

import java.io.File;

public interface IgniteConstants {

    String IGNITE_PATH = System.getProperty("user.dir") + File.separator + "ignite";
    String STORAGE_PATH = IGNITE_PATH + File.separator + "data";
    String STORAGE_REGION_NAME = "default_region_data";
    String WAL_PATH = IGNITE_PATH + File.separator + "wal";
    String WAL_ARCHIVE_PATH = IGNITE_PATH + File.separator + "wal";
}
