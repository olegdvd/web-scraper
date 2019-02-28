package org.kaidzen.webscrap.document.dao;

import org.kaidzen.webscrap.document.model.PermitDocument;
import org.kaidzen.webscrap.document.util.StandardTimeClock;
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
public class PermitDocumentDao implements GeneralDao<PermitDocument> {

    private static final Logger LOG = LoggerFactory.getLogger(PermitDocumentDao.class);

    private static final String TABLE = " permitdocuments";
    private static final String SELECT = "SELECT ";
    private static final String INSERT = "INSERT INTO " + TABLE;
    private static final String DELETE = "DELETE ";
    private static final String VALUES = " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FROM = " FROM" + TABLE;
    private static final String WHERE = " WHERE documentId=?";
    private static final String WHERE_IDS = " WHERE documentId IN ";
    private static final String COLUMN_STR =
            "documentId, region, documentType, subject, category, customer, techSupervision," +
                    "designer, supervision, contractor, landInfo, month, year, timestamp, md5";
    private final JdbcTemplate jdbcTemplate;
    private final StandardTimeClock clock;

    public PermitDocumentDao(JdbcTemplate jdbcTemplate, StandardTimeClock clock) {
        this.jdbcTemplate = jdbcTemplate;
        this.clock = clock;
    }

    @Override
    public List<PermitDocument> getAll() {
        String sql = SELECT + COLUMN_STR + FROM;
        return jdbcTemplate.query(sql, new PermitDocumentRowMapper());
    }

    @Override
    public PermitDocument getById(Integer documentId) {
        String sql = SELECT + COLUMN_STR + FROM + WHERE;
        return jdbcTemplate.queryForObject(sql, new PermitDocumentRowMapper());
    }

    @Override
    public void add(PermitDocument document) {
        String sql = INSERT + " (" + COLUMN_STR + ")" + VALUES;
        jdbcTemplate.update(sql,
                document.getDocumentId(),
                document.getRegion(),
                document.getDocumentType(),
                document.getSubject(),
                document.getCategory(),
                document.getCustomer(),
                document.getTechSupervision(),
                document.getDesigner(),
                document.getSupervision(),
                document.getContractor(),
                document.getLandInfo(),
                document.getMonth(),
                document.getYear(),
                document.getTimestamp(),
                document.getMd5());
    }

    @Override
    public int addAll(List<PermitDocument> permitDocuments) {
        String sql = INSERT + " (" + COLUMN_STR + ")" + VALUES;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    PermitDocument document = permitDocuments.get(i);
                    ps.setString(1, document.getDocumentId());
                    ps.setString(2, document.getRegion());
                    ps.setString(3, document.getDocumentType());
                    ps.setString(4, document.getSubject());
                    ps.setShort(5, document.getCategory());
                    ps.setString(6, document.getCustomer());
                    ps.setString(7, document.getTechSupervision());
                    ps.setString(8, document.getDesigner());
                    ps.setString(9, document.getSupervision());
                    ps.setString(10, document.getContractor());
                    ps.setString(11, document.getLandInfo());
                    ps.setShort(12, document.getMonth());
                    ps.setShort(13, document.getYear());
                    ps.setObject(14, document.getTimestamp());
                    ps.setString(15, document.getMd5());
                }

                @Override
                public int getBatchSize() {
                    return permitDocuments.size();
                }
            });
            return permitDocuments.size();
        } catch (DuplicateKeyException e) {
            LOG.info("Found duplicates in base, start to check one by one...");
            this.addAllNonDuplicated(permitDocuments);
        }
        return 0;
    }

    @Override
    public void addAllNonDuplicated(List<PermitDocument> permitDocuments) {
        String iDs = permitDocuments.stream()
                .map(PermitDocument::getDocumentId)
                .map(this::wrapAsSqlParam)
                .collect(joining(", "));
        String sql = SELECT + COLUMN_STR + FROM + WHERE_IDS + String.format("(%s)", iDs);
        List<PermitDocument> permitDocumentList = jdbcTemplate.query(sql, new PermitDocumentRowMapper());
        Map<String, String> currentDocumentsPair = permitDocumentList.stream()
                .collect(toMap(PermitDocument::getDocumentId, PermitDocument::getMd5));
        List<PermitDocument> unsavedLicenses = permitDocuments.stream()
                .filter(getPermitDocumentPredicate(currentDocumentsPair))
                .filter(license -> !currentDocumentsPair.containsValue(license.getMd5()))
                .collect(toList());
        if (!unsavedLicenses.isEmpty()) addAll(unsavedLicenses);
    }

    private StringBuilder wrapAsSqlParam(String id) {
        return new StringBuilder("'").append(id).append("'");
    }

    private Predicate<PermitDocument> getPermitDocumentPredicate(Map<String, String> presentDocumentsIds) {
        return permitDocument -> !presentDocumentsIds.containsKey(permitDocument.getDocumentId());
    }

    @Override
    public void update(PermitDocument permitDocument) {
        //not now
    }

    @Override
    public void delete(Integer documentId) {
        String sql = DELETE + WHERE;
        jdbcTemplate.update(sql, documentId);
    }

    @Override
    public boolean isWithIdExists(Integer documentId) {
        String sql = SELECT + "COUNT(*)" + FROM + WHERE;
        Integer countDocumentId = jdbcTemplate.queryForObject(sql, Integer.class, documentId);
        return countDocumentId >= 0;
    }

    class PermitDocumentRowMapper implements RowMapper<PermitDocument> {
        @Override
        public PermitDocument mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PermitDocument.Builder(clock)
                    .documentId(rs.getString("documentId"))
                    .region(rs.getString("region"))
                    .documentType(rs.getString("documentType"))
                    .subject(rs.getString("subject"))
                    .category(rs.getShort("category"))
                    .customer(rs.getString("customer"))
                    .techSupervision(rs.getString("techSupervision"))
                    .designer(rs.getString("designer"))
                    .supervision(rs.getString("supervision"))
                    .contractor(rs.getString("contractor"))
                    .landInfo(rs.getString("landInfo"))
                    .month(rs.getShort("month"))
                    .year(rs.getShort("year"))
                    .timestamp()
                    .build();
//            documentId
//            region
//            documentType
//            subject
//            category
//            customer
//            techSupervision
//            designer
//            supervision
//            contractor
//            landInfo
//            month
//            year
//            timestamp
//            md5
        }
    }
}
