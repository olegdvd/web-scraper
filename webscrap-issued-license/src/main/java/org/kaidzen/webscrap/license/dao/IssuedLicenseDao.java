package org.kaidzen.webscrap.license.dao;

import org.kaidzen.webscrap.license.model.IssuedLicense;
import org.kaidzen.webscrap.license.util.MapperUtil;
import org.kaidzen.webscrap.license.util.StandardTimeClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

@Repository
public class IssuedLicenseDao implements GeneralDao<IssuedLicense> {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseDao.class);

    private static final String TABLE = " issuedLicenses_aug";
    private static final String SELECT = "SELECT ";
    private static final String INSERT = "INSERT INTO " + TABLE;
    private static final String DELETE = "DELETE ";
    private static final String VALUES = " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FROM = " FROM" + TABLE;
    private static final String WHERE = " WHERE licenseId=?";
    private static final String WHERE_IDS = " WHERE licenseId IN ";
    private static final String COLUMN_STR =
            "licenseId, type, license, edrpo, theLicensee, address, issueDate, validToDate, timestamp, md5";
    private final JdbcTemplate jdbcTemplate;
    private final StandardTimeClock clock;

    public IssuedLicenseDao(JdbcTemplate jdbcTemplate, StandardTimeClock clock) {
        this.jdbcTemplate = jdbcTemplate;
        this.clock = clock;
    }

    @Override
    public List<IssuedLicense> getAll() {
        String sql = SELECT + COLUMN_STR + FROM;
        return jdbcTemplate.query(sql, new IssuedLicenseRowMapper());
    }

    @Override
    public IssuedLicense getById(Integer licenseId) {
        String sql = SELECT + COLUMN_STR + FROM + WHERE;
        return jdbcTemplate.queryForObject(sql, new IssuedLicenseRowMapper());
    }

    @Override
    public void add(IssuedLicense license) {
        String sql = INSERT + " (" + COLUMN_STR + ")" + VALUES;
        jdbcTemplate.update(sql,
                license.getLicenseId(),
                license.getType(),
                license.getLicense(),
                license.getEdrpo(),
                license.getTheLicensee(),
                license.getAddress(),
                license.getIssueDate(),
                license.getValidToDate(),
                license.getTimestamp(),
                license.getMd5());
    }

    @Override
    public int addAll(List<IssuedLicense> licenses) {
        String sql = INSERT + " (" + COLUMN_STR + ")" + VALUES;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    IssuedLicense license = licenses.get(i);
                    ps.setInt(1, license.getLicenseId());
                    ps.setString(2, license.getType());
                    ps.setString(3, license.getLicense());
                    ps.setString(4, license.getEdrpo());
                    ps.setString(5, license.getTheLicensee());
                    ps.setString(6, license.getAddress());
                    ps.setObject(7, license.getIssueDate());
                    ps.setObject(8, license.getValidToDate());
                    ps.setObject(9, license.getTimestamp());
                    ps.setString(10, license.getMd5());
                }

                @Override
                public int getBatchSize() {
                    return licenses.size();
                }
            });
            return licenses.size();
        } catch (DuplicateKeyException e) {
            LOG.info("Found duplicates in base, start to check one by one...");
            this.addAllNonDuplicated(licenses);
        }
        return 0;
    }

    @Override
    public void addAllNonDuplicated(List<IssuedLicense> licenses) {
        String iDs = licenses.stream()
                .map(license -> license.getLicenseId().toString())
                .collect(joining(", "));
        String sql = SELECT + COLUMN_STR + FROM + WHERE_IDS + String.format("(%s)", iDs);
        List<IssuedLicense> presentLicenses = jdbcTemplate.query(sql, new IssuedLicenseRowMapper());
        Map<Integer, String> currentLicensesPair = presentLicenses.stream()
                .collect(toMap(IssuedLicense::getLicenseId, IssuedLicense::getMd5));
        List<IssuedLicense> unsavedLicenses = licenses.stream()
                .filter(getIssuedLicensePredicate(currentLicensesPair))
                .filter(license -> !currentLicensesPair.containsValue(license.getMd5()))
                .collect(toList());
        if (!unsavedLicenses.isEmpty()) addAll(unsavedLicenses);
    }

    private Predicate<IssuedLicense> getIssuedLicensePredicate(Map<Integer, String> presentLicensesIds) {
        return license -> !presentLicensesIds.containsKey(license.getLicenseId());
    }

    @Override
    public void update(IssuedLicense license) {
        //not now
    }

    @Override
    public void delete(Integer licenseId) {
        String sql = DELETE + WHERE;
        jdbcTemplate.update(sql, licenseId);
    }

    @Override
    public boolean isWithIdExists(Integer licenseId) {
        String sql = SELECT + "COUNT(*)" + FROM + WHERE;
        Integer countLicenseId = jdbcTemplate.queryForObject(sql, Integer.class, licenseId);
        return countLicenseId >= 0;
    }

    class IssuedLicenseRowMapper implements RowMapper<IssuedLicense> {
        @Override
        public IssuedLicense mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new IssuedLicense.Builder(clock)
                    .id(rs.getInt("licenseId"))
                    .type(rs.getString("type"))
                    .license(rs.getString("license"))
                    .edrpo(rs.getString("edrpo"))
                    .theLicensee(rs.getString("theLicensee"))
                    .address(rs.getString("address"))
                    .issueDate(MapperUtil.getDateOrMax(rs.getDate("issueDate")))
                    .validToDate(MapperUtil.getDateOrMax(rs.getDate("validToDate")))
                    .timestamp()
                    .build();
        }
    }
}
