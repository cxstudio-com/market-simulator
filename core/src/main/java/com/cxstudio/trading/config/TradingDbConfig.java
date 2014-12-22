package com.cxstudio.trading.config;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;
import org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.common.base.Joiner;

@Configuration
@PropertySources({
    @PropertySource("classpath:database.properties")
})
@MapperScan("com.cxstudio.trading.persistence.db.mapper")
public class TradingDbConfig {
	private static final boolean TEST_ON_BORROW = true;
    private static final String VALIDATION_QUERY = "select 1";
    private static final String JDBC_INTERCEPTORS = Joiner.on(";").join(
        Arrays.asList(ConnectionState.class.getCanonicalName(), StatementFinalizer.class.getCanonicalName())
    );

    @Value("${marketsimulator.datasource.username}") private String driverClassName;
    @Value("${marketsimulator.datasource.url}") private String url;
    @Value("${marketsimulator.datasource.username}")private String username;
    @Value("${marketsimulator.datasource.password}")private String password;
    @Value("${marketsimulator.datasource.pool.initialSize}") private int initialSize;
    @Value("${marketsimulator.datasource.pool.minIdle}") private int minIdle;
    @Value("${marketsimulator.datasource.pool.maxIdle}") private int maxIdle;
    @Value("${marketsimulator.datasource.pool.maxActive}") private int maxActive;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        PoolProperties properties = new PoolProperties();
        properties.setDriverClassName(driverClassName);
        properties.setUrl(url);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setInitialSize(initialSize);
        properties.setMinIdle(minIdle);
        properties.setMaxIdle(maxIdle);
        properties.setMaxActive(maxActive);
        properties.setJdbcInterceptors(JDBC_INTERCEPTORS);
        properties.setTestOnBorrow(TEST_ON_BORROW);
        properties.setValidationQuery(VALIDATION_QUERY);
        return new DataSource(properties);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
      SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
      sqlSessionFactory.setDataSource(dataSource());
      return sqlSessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
