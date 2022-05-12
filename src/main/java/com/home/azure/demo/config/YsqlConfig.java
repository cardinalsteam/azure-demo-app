package com.home.azure.demo.config;

//@Configuration
//@EnableYsqlRepositories
public class YsqlConfig  {

    /*@Bean
    DataSource dataSource() {

        String hostName = "20.121.116.153";
        String port = "5433";

        Properties poolProperties = new Properties();
        poolProperties.setProperty("dataSourceClassName", "com.yugabyte.ysql.YBClusterAwareDataSource");
        poolProperties.setProperty("dataSource.serverName", hostName);
        poolProperties.setProperty("dataSource.portNumber", port);
        poolProperties.setProperty("dataSource.user", "yugabyte");
        poolProperties.setProperty("dataSource.password", "Hackathon22!");
        poolProperties.setProperty("dataSource.loadBalance", "true");
        poolProperties.setProperty("dataSource.additionalEndpoints",
                "20.62.193.199:5433,20.119.92.88:5433");

        HikariConfig hikariConfig = new HikariConfig(poolProperties);
        DataSource ybClusterAwareDataSource = new HikariDataSource(hikariConfig);
        return ybClusterAwareDataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    TransactionManager transactionManager(DataSource dataSource) {
        return new YugabyteTransactionManager(dataSource);
    }
*/

}
