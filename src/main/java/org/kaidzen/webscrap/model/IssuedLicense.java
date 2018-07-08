package org.kaidzen.webscrap.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kaidzen.webscrap.util.Md5Calculator;
import org.kaidzen.webscrap.util.StandardTimeClock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "issuedLicenses")
public class IssuedLicense {

    @Id
    @Column(name = "licenseId")
    private Integer licenseId;
    private String type;
    private String license;
    private Integer edrpo;
    private String theLicensee;
    private String address;
    private LocalDate issueDate;
    private LocalDate validToDate;
    private Timestamp timestamp;
    private String md5Value;

    IssuedLicense(Builder builder) {
        this.licenseId = builder.id;
        this.type = builder.type;
        this.license = builder.license;
        this.edrpo = builder.edrpo;
        this.theLicensee = builder.theLicensee;
        this.address = builder.address;
        this.issueDate = builder.issueDate;
        this.validToDate = builder.validToDate;
        this.timestamp = builder.timestamp;
        this.md5Value = Md5Calculator.calculateMd5(
                licenseId.toString(),
                type,
                license,
                edrpo.toString(),
                theLicensee,
                address,
                issueDate.toString(),
                validToDate.toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(IssuedLicense.class)
                .append("licenseId", licenseId)
                .append("type", type)
                .append("license", license)
                .append("edrpo", edrpo)
                .append("theLicensee", theLicensee)
                .append("address", address)
                .append("issueDate", issueDate)
                .append("validToDate", validToDate)
                .append("timestamp", timestamp)
                .append("md5Value", md5Value)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IssuedLicense)) return false;
        IssuedLicense generic = (IssuedLicense) obj;
        return new EqualsBuilder()
                .append(this.licenseId, generic.licenseId)
                .append(this.license, generic.license)
                .append(this.edrpo, generic.edrpo)
                .append(this.issueDate, generic.issueDate)
                .append(this.validToDate, generic.validToDate)
                .append(this.md5Value, generic.md5Value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(licenseId)
                .append(license)
                .append(edrpo)
                .append(issueDate)
                .append(validToDate)
                .append(md5Value)
                .build();
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public String getType() {
        return type;
    }

    public String getLicense() {
        return license;
    }

    public Integer getEdrpo() {
        return edrpo;
    }

    public String getTheLicensee() {
        return theLicensee;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getValidToDate() {
        return validToDate;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public static class Builder {
        private final StandardTimeClock clock;
        private Integer id;
        private String type;
        private String license;
        private Integer edrpo;
        private String theLicensee;
        private String address;
        private LocalDate issueDate;
        private LocalDate validToDate;
        private Timestamp timestamp;

        public Builder(StandardTimeClock clock) {
            this.clock = clock;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder license(String license) {
            this.license = license;
            return this;
        }

        public Builder edrpo(Integer edrpo) {
            this.edrpo = edrpo;
            return this;
        }

        public Builder theLicensee(String theLicensee) {
            this.theLicensee = theLicensee;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder issueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder validToDate(LocalDate validToDate) {
            this.validToDate = validToDate;
            return this;
        }

        public Builder timestamp() {
            this.timestamp = clock.createTimestamp();
            return this;
        }

        public IssuedLicense build() {
            return new IssuedLicense(this);
        }
    }
}