package com.project.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ImageAnalysisResponseDto {

    private Long id;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Double blurVariance;
    private Double brightness;
    private Double contrast;
    private Double overexposurePercent;
    private Double underexposurePercent;
    private Double finalScore;
    private Double sharpnessScore;
    private Double exposureScore;
    private Double compositionScore;
    private Double ruleOfThirdsAlignmentScore;
    private String compositionFeedback;
    private String subjectPlacement;
    private Double highlightClippingPercent;
    private Double shadowClippingPercent;
    private String exposureBalance;
    private Double goldenRatioScore;
    private Double symmetryScore;
    private Double faceScore;
    private Double noiseScore;
    private String sceneType;
    private Integer imageWidth;
    private Integer imageHeight;
    private Double subjectCentroidX;
    private Double subjectCentroidY;
    private Integer subjectRegionX;
    private Integer subjectRegionY;
    private Integer subjectRegionWidth;
    private Integer subjectRegionHeight;
    private Double symmetryAxisX;
    private Boolean faceDetected;
    private Integer faceX;
    private Integer faceY;
    private Integer faceWidth;
    private Integer faceHeight;
    private List<Integer> histogram;
    private Integer iso;
    private String aperture;
    private String shutterSpeed;
    private String focalLength;
    private String cameraModel;
    private LocalDateTime uploadedAt;
    private List<String> suggestions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Double getBlurVariance() {
        return blurVariance;
    }

    public void setBlurVariance(Double blurVariance) {
        this.blurVariance = blurVariance;
    }

    public Double getBrightness() {
        return brightness;
    }

    public void setBrightness(Double brightness) {
        this.brightness = brightness;
    }

    public Double getContrast() {
        return contrast;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }

    public Double getOverexposurePercent() {
        return overexposurePercent;
    }

    public void setOverexposurePercent(Double overexposurePercent) {
        this.overexposurePercent = overexposurePercent;
    }

    public Double getUnderexposurePercent() {
        return underexposurePercent;
    }

    public void setUnderexposurePercent(Double underexposurePercent) {
        this.underexposurePercent = underexposurePercent;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Double getSharpnessScore() {
        return sharpnessScore;
    }

    public void setSharpnessScore(Double sharpnessScore) {
        this.sharpnessScore = sharpnessScore;
    }

    public Double getExposureScore() {
        return exposureScore;
    }

    public void setExposureScore(Double exposureScore) {
        this.exposureScore = exposureScore;
    }

    public Double getCompositionScore() {
        return compositionScore;
    }

    public void setCompositionScore(Double compositionScore) {
        this.compositionScore = compositionScore;
    }

    public Double getRuleOfThirdsAlignmentScore() {
        return ruleOfThirdsAlignmentScore;
    }

    public void setRuleOfThirdsAlignmentScore(Double ruleOfThirdsAlignmentScore) {
        this.ruleOfThirdsAlignmentScore = ruleOfThirdsAlignmentScore;
    }

    public String getCompositionFeedback() {
        return compositionFeedback;
    }

    public void setCompositionFeedback(String compositionFeedback) {
        this.compositionFeedback = compositionFeedback;
    }

    public String getSubjectPlacement() {
        return subjectPlacement;
    }

    public void setSubjectPlacement(String subjectPlacement) {
        this.subjectPlacement = subjectPlacement;
    }

    public Double getHighlightClippingPercent() {
        return highlightClippingPercent;
    }

    public void setHighlightClippingPercent(Double highlightClippingPercent) {
        this.highlightClippingPercent = highlightClippingPercent;
    }

    public Double getShadowClippingPercent() {
        return shadowClippingPercent;
    }

    public void setShadowClippingPercent(Double shadowClippingPercent) {
        this.shadowClippingPercent = shadowClippingPercent;
    }

    public String getExposureBalance() {
        return exposureBalance;
    }

    public void setExposureBalance(String exposureBalance) {
        this.exposureBalance = exposureBalance;
    }

    public Double getGoldenRatioScore() {
        return goldenRatioScore;
    }

    public void setGoldenRatioScore(Double goldenRatioScore) {
        this.goldenRatioScore = goldenRatioScore;
    }

    public Double getSymmetryScore() {
        return symmetryScore;
    }

    public void setSymmetryScore(Double symmetryScore) {
        this.symmetryScore = symmetryScore;
    }

    public Double getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Double faceScore) {
        this.faceScore = faceScore;
    }

    public Double getNoiseScore() {
        return noiseScore;
    }

    public void setNoiseScore(Double noiseScore) {
        this.noiseScore = noiseScore;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Double getSubjectCentroidX() {
        return subjectCentroidX;
    }

    public void setSubjectCentroidX(Double subjectCentroidX) {
        this.subjectCentroidX = subjectCentroidX;
    }

    public Double getSubjectCentroidY() {
        return subjectCentroidY;
    }

    public void setSubjectCentroidY(Double subjectCentroidY) {
        this.subjectCentroidY = subjectCentroidY;
    }

    public Integer getSubjectRegionX() {
        return subjectRegionX;
    }

    public void setSubjectRegionX(Integer subjectRegionX) {
        this.subjectRegionX = subjectRegionX;
    }

    public Integer getSubjectRegionY() {
        return subjectRegionY;
    }

    public void setSubjectRegionY(Integer subjectRegionY) {
        this.subjectRegionY = subjectRegionY;
    }

    public Integer getSubjectRegionWidth() {
        return subjectRegionWidth;
    }

    public void setSubjectRegionWidth(Integer subjectRegionWidth) {
        this.subjectRegionWidth = subjectRegionWidth;
    }

    public Integer getSubjectRegionHeight() {
        return subjectRegionHeight;
    }

    public void setSubjectRegionHeight(Integer subjectRegionHeight) {
        this.subjectRegionHeight = subjectRegionHeight;
    }

    public Double getSymmetryAxisX() {
        return symmetryAxisX;
    }

    public void setSymmetryAxisX(Double symmetryAxisX) {
        this.symmetryAxisX = symmetryAxisX;
    }

    public Boolean getFaceDetected() {
        return faceDetected;
    }

    public void setFaceDetected(Boolean faceDetected) {
        this.faceDetected = faceDetected;
    }

    public Integer getFaceX() {
        return faceX;
    }

    public void setFaceX(Integer faceX) {
        this.faceX = faceX;
    }

    public Integer getFaceY() {
        return faceY;
    }

    public void setFaceY(Integer faceY) {
        this.faceY = faceY;
    }

    public Integer getFaceWidth() {
        return faceWidth;
    }

    public void setFaceWidth(Integer faceWidth) {
        this.faceWidth = faceWidth;
    }

    public Integer getFaceHeight() {
        return faceHeight;
    }

    public void setFaceHeight(Integer faceHeight) {
        this.faceHeight = faceHeight;
    }

    public List<Integer> getHistogram() {
        return histogram;
    }

    public void setHistogram(List<Integer> histogram) {
        this.histogram = histogram;
    }

    public Integer getIso() {
        return iso;
    }

    public void setIso(Integer iso) {
        this.iso = iso;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
