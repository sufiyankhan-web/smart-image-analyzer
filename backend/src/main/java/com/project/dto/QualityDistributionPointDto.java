package com.project.dto;

public class QualityDistributionPointDto {

    private String range;
    private Integer count;

    public QualityDistributionPointDto() {
    }

    public QualityDistributionPointDto(String range, Integer count) {
        this.range = range;
        this.count = count;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
