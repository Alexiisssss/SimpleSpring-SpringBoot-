package edu.spring.dao;

import edu.spring.domain.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository

public class PersonDaoJdbc implements PersonDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcOperations jdbcOperations;

    public PersonDaoJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count (*) from  persons", Integer.class);
    }

    @Override
    public void insert(Person person) {
        jdbcTemplate.update("insert into persons (id, `name`) values (?, ?)", person.getId(), person.getName());
    }

    @Override
    public Person getById(int id) {
        Map<String, Object> idParams = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select * from persons where id = :id", idParams, new PersonsMapper());
    }


    @Override
    public void deleteById(int id) {
        Map<String, Object> delParams = Collections.singletonMap("id", id);
        jdbcOperations.update("delete from persons where id = :id", delParams);
    }


    @Override
    public List<Person> getAll() {
        return jdbcTemplate.query("select * from persons", new PersonsMapper());
    }


    private static class PersonsMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Person(id, name);
        }
    }
}
