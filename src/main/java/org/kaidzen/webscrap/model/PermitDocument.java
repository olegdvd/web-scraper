package org.kaidzen.webscrap.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kaidzen.webscrap.util.Md5Calculator;
import org.kaidzen.webscrap.util.StandardTimeClock;

import java.sql.Timestamp;

public class PermitDocument {

    private String documentId;
    private String region;
    private String documentType;
    private String subject;
    private short category;
    private String customer;
    private String techSupervision;
    private String designer;
    private String supervision;
    private String contractor;
    private String landInfo;
    private short month;
    private short year;
    private Timestamp timestamp;
    private String md5;

    PermitDocument(Builder builder) {
        this.documentId = builder.documentId;
        this.region = builder.region;
        this.documentType = builder.documentType;
        this.subject = builder.subject;
        this.category = builder.category;
        this.customer = builder.customer;
        this.techSupervision = builder.techSupervision;
        this.designer = builder.designer;
        this.supervision = builder.supervision;
        this.contractor = builder.contractor;
        this.landInfo = builder.landInfo;
        this.month = builder.month;
        this.year = builder.year;
        this.timestamp = builder.timestamp;
        this.md5 = Md5Calculator.calculateMd5(
                documentId,
                region,
                documentType,
                subject,
                Short.toString(category),
                customer,
                techSupervision,
                designer,
                supervision,
                contractor,
                landInfo,
                Short.toString(month),
                Short.toString(year)
        );
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(PermitDocument.class)
        .append(documentId)
                .append(region)
                .append(documentType)
                .append(subject)
                .append(Short.toString(category))
                .append(customer)
                .append(techSupervision)
                .append(designer)
                .append(supervision)
                .append(contractor)
                .append(landInfo)
                .append(Short.toString(month))
                .append(Short.toString(year))
                .build();
    }

    public static class Builder {
        private final StandardTimeClock clock;
        private String documentId;
        private String region;
        private String documentType;
        private String subject;
        private short category;
        private String customer;
        private String techSupervision;
        private String designer;
        private String supervision;
        private String contractor;
        private String landInfo;
        private short month;
        private short year;
        private Timestamp timestamp;

        public Builder(StandardTimeClock clock) {
            this.clock = clock;
        }

        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder category(short category) {
            this.category = category;
            return this;
        }

        public Builder customer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder techSupervision(String techSupervision) {
            this.techSupervision = techSupervision;
            return this;
        }

        public Builder designer(String designer) {
            this.designer = designer;
            return this;
        }

        public Builder supervision(String supervision) {
            this.supervision = supervision;
            return this;
        }

        public Builder contractor(String contractor) {
            this.contractor = contractor;
            return this;
        }

        public Builder landInfo(String landInfo) {
            this.landInfo = landInfo;
            return this;
        }

        public Builder month(short month) {
            this.month = month;
            return this;
        }

        public Builder year(short year) {
            this.year = year;
            return this;
        }

        public Builder timestamp() {
            this.timestamp = clock.createTimestamp();
            return this;
        }

        public PermitDocument build() {
            return new PermitDocument(this);
        }
    }
}
