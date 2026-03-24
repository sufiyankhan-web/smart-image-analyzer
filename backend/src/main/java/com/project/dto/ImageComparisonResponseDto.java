package com.project.dto;

public class ImageComparisonResponseDto {

    private ImageAnalysisResponseDto firstImage;
    private ImageAnalysisResponseDto secondImage;
    private String sharperImage;
    private String betterExposureImage;
    private String betterContrastImage;
    private String betterOverallImage;

    public ImageAnalysisResponseDto getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(ImageAnalysisResponseDto firstImage) {
        this.firstImage = firstImage;
    }

    public ImageAnalysisResponseDto getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(ImageAnalysisResponseDto secondImage) {
        this.secondImage = secondImage;
    }

    public String getSharperImage() {
        return sharperImage;
    }

    public void setSharperImage(String sharperImage) {
        this.sharperImage = sharperImage;
    }

    public String getBetterExposureImage() {
        return betterExposureImage;
    }

    public void setBetterExposureImage(String betterExposureImage) {
        this.betterExposureImage = betterExposureImage;
    }

    public String getBetterContrastImage() {
        return betterContrastImage;
    }

    public void setBetterContrastImage(String betterContrastImage) {
        this.betterContrastImage = betterContrastImage;
    }

    public String getBetterOverallImage() {
        return betterOverallImage;
    }

    public void setBetterOverallImage(String betterOverallImage) {
        this.betterOverallImage = betterOverallImage;
    }
}
