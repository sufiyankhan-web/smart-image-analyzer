package com.project.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image_analysis")
public class ImageAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @Lob
    @Column(nullable = false)
    private byte[] imageBytes;

    @Column(nullable = false)
    private Double blurVariance;

    @Column(nullable = false)
    private Double brightness;

    @Column(nullable = false)
    private Double contrast;

    @Column(nullable = false)
    private Double overexposurePercent;

    @Column(nullable = false)
    private Double underexposurePercent;

    @Column(nullable = false)
    private Double finalScore;

    @Column
    private Double sharpnessScore;

    @Column
    private Double exposureScore;

    @Column(nullable = false)
    private Double compositionScore;

    @Column
    private Double goldenRatioScore;

    @Column
    private Double symmetryScore;

    @Column
    private Double faceScore;

    @Column
    private Double noiseScore;

    @Column
    private String sceneType;

    @Column
    private Integer imageWidth;

    @Column
    private Integer imageHeight;

    @Column
    private Double subjectCentroidX;

    @Column
    private Double subjectCentroidY;

    @Column
    private Integer subjectRegionX;

    @Column
    private Integer subjectRegionY;

    @Column
    private Integer subjectRegionWidth;

    @Column
    private Integer subjectRegionHeight;

    @Column
    private Double symmetryAxisX;

    @Column
    private Boolean faceDetected;

    @Column
    private Integer faceX;

    @Column
    private Integer faceY;

    @Column
    private Integer faceWidth;

    @Column
    private Integer faceHeight;

    @Column(nullable = false)
    private Double ruleOfThirdsAlignmentScore;

    @Column(nullable = false)
    private String compositionFeedback;

    @Column(nullable = false)
    private String subjectPlacement;

    @Column(nullable = false)
    private Double highlightClippingPercent;

    @Column(nullable = false)
    private Double shadowClippingPercent;

    @Column(nullable = false)
    private String exposureBalance;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String histogram;

    private Integer iso;

    private String aperture;

    private String shutterSpeed;

    private String focalLength;

    private String cameraModel;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "image_analysis_suggestions", joinColumns = @JoinColumn(name = "image_analysis_id"))
    @Column(name = "suggestion", nullable = false)
    private List<String> suggestions = new ArrayList<>();

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

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
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

    public String getHistogram() {
        return histogram;
    }

    public void setHistogram(String histogram) {
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
