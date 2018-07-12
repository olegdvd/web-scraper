package org.kaidzen.webscrap.dao;

import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.util.MapperUtil;
import org.kaidzen.webscrap.util.StandardTimeClock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class IssuedLicenseDao implements GeneralDao<IssuedLicense> {

    private static final String TABLE = " issuedLicenses";
    private static final String SELECT = "SELECT ";
    private static final String INSERT = "INSERT INTO "+TABLE;
    private static final String DELETE = "DELETE ";
    private static final String VALUES = " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FROM = " FROM"+TABLE;
    private static final String WHERE = " WHERE licenseId=?";
    private static final String COLUMN_STR =
            "licenseId, type, license, edrpo, theLicensee, address, issueDate, validToDate, timestamp";
    private final JdbcTemplate jdbcTemplate;
    private final StandardTimeClock clock;

    public IssuedLicenseDao(JdbcTemplate jdbcTemplate, StandardTimeClock clock) {
        this.jdbcTemplate = jdbcTemplate;
        this.clock = clock;
    }

    @Override
    public List<IssuedLicense> getAllLicensees() {
        String sql = SELECT + COLUMN_STR + FROM;
        return jdbcTemplate.query(sql, new IssuedLicenseRowMapper());
    }

    @Override
    public IssuedLicense getLicenseById(Integer licenseId) {
        String sql = SELECT + COLUMN_STR + FROM + WHERE;
        return jdbcTemplate.queryForObject(sql, new IssuedLicenseRowMapper());
    }

    @Override
    public void addLicense(IssuedLicense license) {
        String sql = INSERT + " ("+COLUMN_STR+ ")" +VALUES;
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
                license.getMd5Value());
    }

    @Override
    public void updateLicense(IssuedLicense license) {
        //not now
    }

    @Override
    public void deleteLicense(Integer licenseId) {
        String sql = DELETE + WHERE;
        jdbcTemplate.update(sql, licenseId);
    }

    class IssuedLicenseRowMapper implements RowMapper<IssuedLicense> {
        @Override
        public IssuedLicense mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new IssuedLicense.Builder(clock)
                    .id(rs.getInt("licenseId"))
                    .type(rs.getString("type"))
                    .license(rs.getString("license"))
                    .edrpo(rs.getLong("edrpo"))
                    .theLicensee(rs.getString("theLicensee"))
                    .address(rs.getString("address"))
                    .issueDate(MapperUtil.getDateOrNow(rs.getDate("issueDate")))
                    .validToDate(MapperUtil.getDateOrNow(rs.getDate("validToDate")))
                    .timestamp()
                    .build();
        }
    }

    @Override
    public boolean isLicenseIdExists(Integer licenseId) {
        String sql = SELECT + "COUNT(*)" + FROM + WHERE;
        Integer licenseIds = jdbcTemplate.queryForObject(sql, Integer.class, licenseId);
        return licenseIds >= 0;
    }
}