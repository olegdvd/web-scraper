package org.kaidzen.webscrap.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kaidzen.webscrap.util.Md5Calculator;
import org.kaidzen.webscrap.util.StandardTimeClock;

import java.sql.Timestamp;

public class PermitDocument implements ScrappedModel{

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

    public String getDocumentId() {
        return documentId;
    }

    public String getRegion() {
        return region;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getSubject() {
        return subject;
    }

    public short getCategory() {
        return category;
    }

    public String getCustomer() {
        return customer;
    }

    public String getTechSupervision() {
        return techSupervision;
    }

    public String getDesigner() {
        return designer;
    }

    public String getSupervision() {
        return supervision;
    }

    public String getContractor() {
        return contractor;
    }

    public String getLandInfo() {
        return landInfo;
    }

    public short getMonth() {
        return month;
    }

    public short getYear() {
        return year;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMd5() {
        return md5;
    }

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
    public String toCsv() {
        return String.join(", ",
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
                Short.toString(year),
                timestamp.toString(),
                md5);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(documentId)
                .append(region)
                .append(documentType)
                .append(subject)
                .append(category)
                .append(customer)
                .append(techSupervision)
                .append(designer)
                .append(supervision)
                .append(contractor)
                .append(landInfo)
                .append(month)
                .append(year)
                .append(timestamp)
                .append(md5)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PermitDocument)) return false;
        PermitDocument other = (PermitDocument) obj;
        return new EqualsBuilder()
                .append(this.documentId, other.documentId)
                .append(this.region, other.region)
                .append(this.documentType, other.documentType)
                .append(this.subject, other.subject)
                .append(this.category, other.category)
                .append(this.customer, other.customer)
                .append(this.techSupervision, other.techSupervision)
                .append(this.designer, other.designer)
                .append(this.supervision, other.supervision)
                .append(this.contractor, other.contractor)
                .append(this.landInfo, other.landInfo)
                .append(this.month, other.month)
                .append(this.year, other.year)
                .append(this.md5, other.md5)
                .build();
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
                .append(timestamp)
                .append(md5)
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
