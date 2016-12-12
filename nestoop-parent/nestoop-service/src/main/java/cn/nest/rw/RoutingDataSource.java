package cn.nest.rw;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by botter
 *
 * @Date 11/2/16.
 * @description
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceAspect.currentDataSource();
    }
}
