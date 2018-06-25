package org.kaidzen.webscrap.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class IssuedLicense {

    @Id
    private Integer id;
    private String type;
    private String license;
    private Integer edrpo;
    private String theLicensee;
    private String adress;
    private LocalDate issueDate;
    private LocalDate validToDate;

    public IssuedLicense() {
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    @Override
    public String toString() {
        return new ToStringBuilder(IssuedLicense.class)
                .append("id", id)
                .append("type", type)
                .append("license", license)
                .append("edrpo", edrpo)
                .append("theLicensee", theLicensee)
                .append("adress", adress)
                .append("issueDate", issueDate)
                .append("validToDate", validToDate)
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
                .build();
    }
}
