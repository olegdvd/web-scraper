package org.kaidzen.webscrap.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kaidzen.webscrap.util.Md5Calculator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class IssuedLicense {

    @Id
    private Integer id;
    private String type;
    private String license;
    private Integer edrpo;
    private String theLicensee;
    private String address;
    private LocalDate issueDate;
    private LocalDate validToDate;
    private Timestamp timestamp;
    private String md5Value;

    public IssuedLicense(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.license = builder.license;
        this.edrpo = builder.edrpo;
        this.theLicensee = builder.theLicensee;
        this.address = builder.address;
        this.issueDate = builder.issueDate;
        this.validToDate = builder.validToDate;
        this.timestamp = builder.timestamp;
        this.md5Value = Md5Calculator.calculateMd5(
                id.toString(),
                type,
                license,
                edrpo.toString(),
                theLicensee,
                address,
                issueDate.toString(),
                validToDate.toString());
    }

    public static class Builder {
        private Integer id;
        private String type;
        private String license;
        private Integer edrpo;
        private String theLicensee;
        private String address;
        private LocalDate issueDate;
        private LocalDate validToDate;
        private Timestamp timestamp;

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

        public Builder timestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public IssuedLicense build() {
            return new IssuedLicense(this);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(IssuedLicense.class)
                .append("id", id)
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
                .append(this.id, generic.id)
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
                .append(id)
                .append(license)
                .append(edrpo)
                .append(issueDate)
                .append(validToDate)
                .append(md5Value)
                .build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getEdrpo() {
        return edrpo;
    }

    public void setEdrpo(Integer edrpo) {
        this.edrpo = edrpo;
    }

    public String getTheLicensee() {
        return theLicensee;
    }

    public void setTheLicensee(String theLicensee) {
        this.theLicensee = theLicensee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(LocalDate validToDate) {
        this.validToDate = validToDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }
}
