package com.test.ignite.config;

import lombok.Data;
import org.apache.ignite.configuration.WALMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class IgniteStorageProperties {

    @Value("${ignite.storage.enable}")
    private Boolean enable = false;

    private String path = IgniteConstants.STORAGE_PATH;

    private WALMode walMode = WALMode.FSYNC;

    private String walPath = IgniteConstants.WAL_PATH;

    private String walArchivePath = IgniteConstants.WAL_ARCHIVE_PATH;

    private Boolean walCompactionEnabled = true;

    private String regionName = IgniteConstants.STORAGE_REGION_NAME;
}
