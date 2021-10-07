package com.example.demo.repository;

import com.example.demo.model.CoinInfo;
import com.example.demo.model.CoinStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

//bu sınıfı injection yapmadan nasıl kullandık teorisine bak
//resultset sınıfına bak
@Repository
@Scope("prototype")
@Order(value = 2)
public class CoinRepository implements ICrudRepository {

    private static final String CREATE_TABLE = "create table coininfo (\n" +
            "    coin_id bigserial primary key,\n" +
            "    title varchar(128) not null,\n" +
            "    symbol varchar(128) not null,\n" +
            "    price double precision not null,\n" +
            "    average double precision,\n" +
            "    time time\n" +
            ")";
    private static final String SAVE_SQL = "insert into coininfo (title, symbol, price, average) values (:title, :symbol, :price, :average)";  // bölümleri kontrol et
    private static final String COUNT_SQL = "select count(*) from coininfo";
    private static final String FIND_ALL_SQL = "select * from coininfo";
    private static final String UPDATE_SQL = "update coininfo set price = :price where symbol = :symbol";
    private static final String AVERAGE = "update coininfo set average = :average where symbol = :symbol";
    //private static final String TIMER = "update coininfo set time = :localTime where symbol = :symbol";
    private final NamedParameterJdbcTemplate m_jdbcTemplate;

    @Value("${spring.datasource.url}") //send another class
    public String DB_URL;

    @Autowired
    @Qualifier("getIs")
    public CoinStore coininfos;

    public CoinRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        m_jdbcTemplate = jdbcTemplate;
        executeCommand("CREATE DATABASE postgres", "Database created");
        executeCommand(CREATE_TABLE, "Table created");
    }

    private void executeCommand(String command, String message)
    {
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
            Statement stmt = conn.createStatement();
        ) {
            String sql = command;
            stmt.executeUpdate(sql);
            System.out.println(message);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createDataBase()
    {
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
            Statement stmt = conn.createStatement();
        ) {
            String sql1 = "CREATE DATABASE postgres";
            String sql2 = "create table coininfo (\n" +
                    "    coin_id bigserial primary key,\n" +
                    "    title varchar(128) not null,\n" +
                    "    symbol varchar(128) not null,\n" +
                    "    price double precision not null,\n" +
                    "    average double precision,\n" +
                    "    time time\n" +
                    ")";
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <S extends CoinInfo> S updateAvg(S coin) {
        m_jdbcTemplate.update(AVERAGE, new BeanPropertySqlParameterSource(coin));
        return coin;
    }

    public <S extends CoinInfo> S update(S coin) {
        m_jdbcTemplate.update(UPDATE_SQL, new BeanPropertySqlParameterSource(coin));
        return coin;
    }

    //add new row
    @Override
    public <S extends CoinInfo> S save(S coin) {
        var keyHolder = new GeneratedKeyHolder();
        var parameterSource = new BeanPropertySqlParameterSource(coin);

        m_jdbcTemplate.update(SAVE_SQL, parameterSource, keyHolder, new String[]{"coin_id"}); //get infos with reflection and update
        coin.setId(keyHolder.getKey().longValue());

        return coin;
    }

    private static void fillCounts(ResultSet resultSet, ArrayList<Long> counts) throws SQLException {
        do {
            counts.add(resultSet.getLong(1));
        } while (resultSet.next());
    }


    private void fillCoins(ResultSet resultSet, CoinInfo[] coins) throws SQLException {
        do {
            var id = resultSet.getLong("coin_id");
            var title = resultSet.getString("title");
            var symbol = resultSet.getString("symbol");
            var price = resultSet.getLong("price");


        } while (resultSet.next());
    }

    @Override
    public ArrayList<CoinInfo> findAllAsList() {
        m_jdbcTemplate.query(FIND_ALL_SQL, (ResultSet rs) -> fillCoins(rs, coininfos.getCoinInfo()));
        return new ArrayList<>(Arrays.asList(coininfos.getCoinInfo()));
    }

    @Override
    public Iterable<CoinInfo> findAll() {

        m_jdbcTemplate.query(FIND_ALL_SQL, (ResultSet rs) -> fillCoins(rs, coininfos.getCoinInfo()));
        return Arrays.asList(coininfos.getCoinInfo().clone());
    }


    @Override
    public <S extends CoinInfo> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<CoinInfo> findById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Iterable<CoinInfo> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    //not needed
    @Override
    public long count() {
        var counts = new ArrayList<Long>();

        m_jdbcTemplate.query(COUNT_SQL, (ResultSet rs) -> fillCounts(rs, counts));

        return counts.get(0);
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(CoinInfo entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends CoinInfo> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
