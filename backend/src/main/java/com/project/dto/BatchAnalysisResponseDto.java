package com.project.dto;

import java.util.List;

public class BatchAnalysisResponseDto {

    private List<ImageAnalysisResponseDto> analyses;
    private Double averageQualityScore;
    private String bestImage;
    private String worstImage;
    private List<QualityDistributionPointDto> qualityDistribution;

    public List<ImageAnalysisResponseDto> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<ImageAnalysisResponseDto> analyses) {
        this.analyses = analyses;
    }

    public Double getAverageQualityScore() {
        return averageQualityScore;
    }

    public void setAverageQualityScore(Double averageQualityScore) {
        this.averageQualityScore = averageQualityScore;
    }

    public String getBestImage() {
        return bestImage;
    }

    public void setBestImage(String bestImage) {
        this.bestImage = bestImage;
    }

    public String getWorstImage() {
        return worstImage;
    }

    public void setWorstImage(String worstImage) {
        this.worstImage = worstImage;
    }

    public List<QualityDistributionPointDto> getQualityDistribution() {
        return qualityDistribution;
    }

    public void setQualityDistribution(List<QualityDistributionPointDto> qualityDistribution) {
        this.qualityDistribution = qualityDistribution;
    }
}
