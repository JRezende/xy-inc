package br.com.xy.dao;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CustomJdbcDaoSupport extends JdbcDaoSupport {

  @Autowired
  private DataSource dataSource;

  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

}
