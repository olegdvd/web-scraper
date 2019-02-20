package org.kaidzen.webscrap.license.model;

public class FormFilterData {

    private String year;
    private String month;
    private String region;

    public FormFilterData(Builder builder) {
        this.year = builder.year;
        this.month = builder.month;
        this.region = builder.region;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getRegion() {
        return region;
    }

    public static class Builder {
        private String year;
        private String month;
        private String region;

        public Builder year(String year) {
            this.year = year;
            return this;
        }

        public Builder month(String month) {
            this.month = month;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public FormFilterData build() {
            return new FormFilterData(this);
        }
    }

    @Override
    public String toString() {
        return "filter:[region: " +getRegion()+ ", year: " +getYear()+ ", month: " +getMonth();
    }
}
