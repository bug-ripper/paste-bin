package com.denisbondd111.hashservice.repository;

import com.denisbondd111.hashservice.entity.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class HashRepository implements CrudRepository<Hash, Long>{

    private JdbcTemplate jdbcTemplate;

    public static final String FIND_BY_ID_SQL = "select * from hash where id = ?";
    public static final String FIND_ALL_SQL = "select * from hash";
    public static final String SAVE_SQL = "insert into hash (id, hash) values (?, ?)";
    public static final String UPDATE_SQL = "update hash set hash = ?, is_used = ? where id = ?";
    public static final String DELETE_SQL = "delete from hash where id = ?";
    public static final String FIND_NEXT_ID_SQL = "select max(id) as id from hash";
    public static final String FIND_FIRST_UNUSED_HASH_SQL = """
    WITH selected AS (
            SELECT id, hash FROM hash
                    WHERE is_used = FALSE
                    ORDER BY id
                    LIMIT 1
                    FOR UPDATE SKIP LOCKED
    )
    UPDATE hash
    SET is_used = TRUE
    WHERE id IN (SELECT id FROM selected)
    RETURNING hash;
""";
    public static final String FIND_UNUSED_HASHES_SQL = "SELECT * FROM hash WHERE is_used = FALSE ORDER BY id LIMIT ?";
    public static final String FIND_NUMBER_OF_UNUSED_HASHES_SQL = "SELECT count(id) as number FROM hash WHERE hash.is_used = FALSE";


    private final RowMapper<Hash> hashRowMapper = (rs, rowNum) -> {
        Hash hash = new Hash(
                rs.getLong("id"),
                rs.getString("hash"),
                rs.getBoolean("is_used"),
                rs.getTimestamp("created_at"));
        return hash;
    };

    @Autowired
    public HashRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Hash> findById(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, hashRowMapper, id)).or(() -> Optional.empty());
    }

    @Override
    public List<Hash> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, hashRowMapper);
    }

    @Override
    public void save(Hash entity) {
        jdbcTemplate.update(SAVE_SQL, entity.getId(), entity.getHash());
    }

    @Override
    public void saveAll(List<Hash> hashes) {
        jdbcTemplate.batchUpdate(SAVE_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, hashes.get(i).getId());
                ps.setString(2, hashes.get(i).getHash());
            }

            @Override
            public int getBatchSize() {
                return hashes.size();
            }
        });
    }

    @Override
    public void update(Hash entity) {
        jdbcTemplate.update(UPDATE_SQL, entity.getHash(), entity.getUsed(), entity.getId());
    }

    public void updateAll(List<Hash> hashes) {
        jdbcTemplate.batchUpdate(UPDATE_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, hashes.get(i).getHash());
                ps.setBoolean(2, hashes.get(i).getUsed());
                ps.setLong(3, hashes.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return hashes.size();
            }
        });
    }


    @Override
    public void delete(Hash entity) {
        jdbcTemplate.update(DELETE_SQL, entity.getId());
    }

    public Long getNextId(){
        return jdbcTemplate.queryForObject(FIND_NEXT_ID_SQL, (rs, rowNum) -> {
            return rs.getLong("id")+1;
        });
    }

    public String getFirstNotUsedHash(){
        return jdbcTemplate.queryForObject(FIND_FIRST_UNUSED_HASH_SQL, (rs, rowNum) -> {
            return rs.getString("hash");
        });
    }

    public List<Hash> getUnusedHashes(int n){
        return jdbcTemplate.query(FIND_UNUSED_HASHES_SQL, hashRowMapper, n);
    }

    public Long getNumberOfUnusedHashes(){
        return jdbcTemplate.queryForObject(FIND_NUMBER_OF_UNUSED_HASHES_SQL, (rs, rowNum) -> {
            return rs.getLong("number");
        });
    }
}
