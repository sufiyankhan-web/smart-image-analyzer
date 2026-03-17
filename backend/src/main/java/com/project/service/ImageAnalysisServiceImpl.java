package com.project.service;

import com.project.dto.ImageAnalysisResponseDto;
import com.project.dto.ImageComparisonResponseDto;
import com.project.dto.BatchAnalysisResponseDto;
import com.project.dto.QualityDistributionPointDto;
import com.project.dto.UploadResponseDto;
import com.project.exception.InvalidImageException;
import com.project.exception.ResourceNotFoundException;
import com.project.model.ImageAnalysis;
import com.project.repository.ImageAnalysisRepository;
import com.project.util.ImageQualityAnalyzer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {

    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024;

    private final ImageAnalysisRepository repository;
    private final ImageQualityAnalyzer analyzer;

    public ImageAnalysisServiceImpl(ImageAnalysisRepository repository, ImageQualityAnalyzer analyzer) {
        this.repository = repository;
        this.analyzer = analyzer;
    }

    @Override
    public UploadResponseDto uploadAndAnalyze(MultipartFile file) {
        validateFile(file);

        byte[] bytes = readBytes(file);

        ImageQualityAnalyzer.AnalysisResult result = analyzer.analyze(bytes);

        ImageAnalysis entity = new ImageAnalysis();
        entity.setFileName(file.getOriginalFilename());
        entity.setContentType(file.getContentType());
        entity.setFileSize(file.getSize());
        entity.setImageBytes(bytes);
        entity.setBlurVariance(result.blurVariance());
        entity.setBrightness(result.brightness());
        entity.setContrast(result.contrast());
        entity.setOverexposurePercent(result.overexposurePercent());
        entity.setUnderexposurePercent(result.underexposurePercent());
        entity.setSharpnessScore(result.sharpnessScore());
        entity.setExposureScore(result.exposureScore());
        entity.setCompositionScore(result.compositionScore());
        entity.setGoldenRatioScore(result.goldenRatioScore());
        entity.setSymmetryScore(result.symmetryScore());
        entity.setFaceScore(result.faceScore());
        entity.setNoiseScore(result.noiseScore());
        entity.setSceneType(result.sceneType());
        entity.setImageWidth(result.imageWidth());
        entity.setImageHeight(result.imageHeight());
        entity.setSubjectCentroidX(result.subjectCentroidX());
        entity.setSubjectCentroidY(result.subjectCentroidY());
        entity.setSubjectRegionX(result.subjectRegionX());
        entity.setSubjectRegionY(result.subjectRegionY());
        entity.setSubjectRegionWidth(result.subjectRegionWidth());
        entity.setSubjectRegionHeight(result.subjectRegionHeight());
        entity.setSymmetryAxisX(result.symmetryAxisX());
        entity.setFaceDetected(result.faceDetected());
        entity.setFaceX(result.faceX());
        entity.setFaceY(result.faceY());
        entity.setFaceWidth(result.faceWidth());
        entity.setFaceHeight(result.faceHeight());
        entity.setRuleOfThirdsAlignmentScore(result.ruleOfThirdsAlignmentScore());
        entity.setCompositionFeedback(result.compositionFeedback());
        entity.setSubjectPlacement(result.subjectPlacement());
        entity.setHighlightClippingPercent(result.highlightClippingPercent());
        entity.setShadowClippingPercent(result.shadowClippingPercent());
        entity.setExposureBalance(result.exposureBalance());
        entity.setHistogram(joinHistogram(result.histogram()));
        entity.setIso(result.iso());
        entity.setAperture(result.aperture());
        entity.setShutterSpeed(result.shutterSpeed());
        entity.setFocalLength(result.focalLength());
        entity.setCameraModel(result.cameraModel());
        entity.setFinalScore(result.finalScore());
        entity.setSuggestions(result.suggestions());
        entity.setUploadedAt(LocalDateTime.now());

        ImageAnalysis saved = repository.save(entity);
        return new UploadResponseDto("Image analyzed successfully.", toDto(saved));
    }

    @Override
    public ImageComparisonResponseDto compare(MultipartFile firstFile, MultipartFile secondFile) {
        validateFile(firstFile);
        validateFile(secondFile);

        ImageAnalysisResponseDto first = toDto(firstFile.getOriginalFilename(), analyzer.analyze(readBytes(firstFile)));
        ImageAnalysisResponseDto second = toDto(secondFile.getOriginalFilename(), analyzer.analyze(readBytes(secondFile)));

        ImageComparisonResponseDto result = new ImageComparisonResponseDto();
        result.setFirstImage(first);
        result.setSecondImage(second);
        result.setSharperImage(first.getSharpnessScore() >= second.getSharpnessScore() ? first.getFileName() : second.getFileName());

        double firstExposureHealth = first.getExposureScore();
        double secondExposureHealth = second.getExposureScore();
        result.setBetterExposureImage(firstExposureHealth >= secondExposureHealth ? first.getFileName() : second.getFileName());

        result.setBetterContrastImage(first.getContrast() >= second.getContrast() ? first.getFileName() : second.getFileName());
        result.setBetterOverallImage(first.getFinalScore() >= second.getFinalScore() ? first.getFileName() : second.getFileName());
        return result;
    }

    @Override
    public BatchAnalysisResponseDto analyzeBatch(List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new InvalidImageException("Please upload at least one image.");
        }

        List<ImageAnalysisResponseDto> analyses = new ArrayList<>();
        for (MultipartFile file : files) {
            validateFile(file);
            analyses.add(toDto(file.getOriginalFilename(), analyzer.analyze(readBytes(file))));
        }

        analyses.sort(Comparator.comparing(ImageAnalysisResponseDto::getFinalScore).reversed());

        double averageScore = analyses.stream()
                .mapToDouble(ImageAnalysisResponseDto::getFinalScore)
                .average()
                .orElse(0);

        BatchAnalysisResponseDto response = new BatchAnalysisResponseDto();
        response.setAnalyses(analyses);
        response.setAverageQualityScore(round(averageScore));
        response.setBestImage(analyses.get(0).getFileName());
        response.setWorstImage(analyses.get(analyses.size() - 1).getFileName());
        response.setQualityDistribution(buildDistribution(analyses));
        return response;
    }

    @Override
    public ImageAnalysisResponseDto getById(Long id) {
        Long safeId = Objects.requireNonNull(id, "id must not be null");
        ImageAnalysis analysis = repository.findById(safeId)
            .orElseThrow(() -> new ResourceNotFoundException("Image analysis not found for id: " + safeId));
        return toDto(analysis);
    }

    @Override
    public List<ImageAnalysisResponseDto> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(ImageAnalysis::getUploadedAt).reversed())
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        Long safeId = Objects.requireNonNull(id, "id must not be null");
        if (!repository.existsById(safeId)) {
            throw new ResourceNotFoundException("Image analysis not found for id: " + safeId);
        }
        repository.deleteById(safeId);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidImageException("Please upload a non-empty image file.");
        }
        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new InvalidImageException("Image exceeds max size of 10MB.");
        }
        String contentType = file.getContentType();
        if (contentType != null && !contentType.toLowerCase().startsWith("image/")) {
            throw new InvalidImageException("Only image files are supported.");
        }
    }

    private ImageAnalysisResponseDto toDto(ImageAnalysis analysis) {
        ImageAnalysisResponseDto dto = new ImageAnalysisResponseDto();
        dto.setId(analysis.getId());
        dto.setFileName(analysis.getFileName());
        dto.setContentType(analysis.getContentType());
        dto.setFileSize(analysis.getFileSize());
        dto.setBlurVariance(roundNullable(analysis.getBlurVariance()));
        dto.setBrightness(roundNullable(analysis.getBrightness()));
        dto.setContrast(roundNullable(analysis.getContrast()));
        dto.setOverexposurePercent(roundNullable(analysis.getOverexposurePercent()));
        dto.setUnderexposurePercent(roundNullable(analysis.getUnderexposurePercent()));
        dto.setSharpnessScore(roundNullable(analysis.getSharpnessScore()));
        dto.setExposureScore(roundNullable(analysis.getExposureScore()));
        dto.setCompositionScore(roundNullable(analysis.getCompositionScore()));
        dto.setGoldenRatioScore(roundNullable(analysis.getGoldenRatioScore()));
        dto.setSymmetryScore(roundNullable(analysis.getSymmetryScore()));
        dto.setFaceScore(roundNullable(analysis.getFaceScore()));
        dto.setNoiseScore(roundNullable(analysis.getNoiseScore()));
        dto.setSceneType(analysis.getSceneType());
        dto.setImageWidth(analysis.getImageWidth());
        dto.setImageHeight(analysis.getImageHeight());
        dto.setSubjectCentroidX(roundNullable(analysis.getSubjectCentroidX()));
        dto.setSubjectCentroidY(roundNullable(analysis.getSubjectCentroidY()));
        dto.setSubjectRegionX(analysis.getSubjectRegionX());
        dto.setSubjectRegionY(analysis.getSubjectRegionY());
        dto.setSubjectRegionWidth(analysis.getSubjectRegionWidth());
        dto.setSubjectRegionHeight(analysis.getSubjectRegionHeight());
        dto.setSymmetryAxisX(roundNullable(analysis.getSymmetryAxisX()));
        dto.setFaceDetected(analysis.getFaceDetected());
        dto.setFaceX(analysis.getFaceX());
        dto.setFaceY(analysis.getFaceY());
        dto.setFaceWidth(analysis.getFaceWidth());
        dto.setFaceHeight(analysis.getFaceHeight());
        dto.setRuleOfThirdsAlignmentScore(roundNullable(analysis.getRuleOfThirdsAlignmentScore()));
        dto.setCompositionFeedback(analysis.getCompositionFeedback());
        dto.setSubjectPlacement(analysis.getSubjectPlacement());
        dto.setHighlightClippingPercent(roundNullable(analysis.getHighlightClippingPercent()));
        dto.setShadowClippingPercent(roundNullable(analysis.getShadowClippingPercent()));
        dto.setExposureBalance(analysis.getExposureBalance());
        dto.setHistogram(parseHistogram(analysis.getHistogram()));
        dto.setIso(analysis.getIso());
        dto.setAperture(analysis.getAperture());
        dto.setShutterSpeed(analysis.getShutterSpeed());
        dto.setFocalLength(analysis.getFocalLength());
        dto.setCameraModel(analysis.getCameraModel());
        dto.setFinalScore(roundNullable(analysis.getFinalScore()));
        dto.setUploadedAt(analysis.getUploadedAt());
        dto.setSuggestions(analysis.getSuggestions());
        return dto;
    }

    private ImageAnalysisResponseDto toDto(String fileName, ImageQualityAnalyzer.AnalysisResult result) {
        ImageAnalysisResponseDto dto = new ImageAnalysisResponseDto();
        dto.setFileName(fileName);
        dto.setBlurVariance(round(result.blurVariance()));
        dto.setBrightness(round(result.brightness()));
        dto.setContrast(round(result.contrast()));
        dto.setOverexposurePercent(round(result.overexposurePercent()));
        dto.setUnderexposurePercent(round(result.underexposurePercent()));
        dto.setSharpnessScore(round(result.sharpnessScore()));
        dto.setExposureScore(round(result.exposureScore()));
        dto.setCompositionScore(round(result.compositionScore()));
        dto.setGoldenRatioScore(round(result.goldenRatioScore()));
        dto.setSymmetryScore(round(result.symmetryScore()));
        dto.setFaceScore(round(result.faceScore()));
        dto.setNoiseScore(round(result.noiseScore()));
        dto.setSceneType(result.sceneType());
        dto.setImageWidth(result.imageWidth());
        dto.setImageHeight(result.imageHeight());
        dto.setSubjectCentroidX(round(result.subjectCentroidX()));
        dto.setSubjectCentroidY(round(result.subjectCentroidY()));
        dto.setSubjectRegionX(result.subjectRegionX());
        dto.setSubjectRegionY(result.subjectRegionY());
        dto.setSubjectRegionWidth(result.subjectRegionWidth());
        dto.setSubjectRegionHeight(result.subjectRegionHeight());
        dto.setSymmetryAxisX(round(result.symmetryAxisX()));
        dto.setFaceDetected(result.faceDetected());
        dto.setFaceX(result.faceX());
        dto.setFaceY(result.faceY());
        dto.setFaceWidth(result.faceWidth());
        dto.setFaceHeight(result.faceHeight());
        dto.setRuleOfThirdsAlignmentScore(round(result.ruleOfThirdsAlignmentScore()));
        dto.setCompositionFeedback(result.compositionFeedback());
        dto.setSubjectPlacement(result.subjectPlacement());
        dto.setHighlightClippingPercent(round(result.highlightClippingPercent()));
        dto.setShadowClippingPercent(round(result.shadowClippingPercent()));
        dto.setExposureBalance(result.exposureBalance());
        dto.setHistogram(result.histogram());
        dto.setIso(result.iso());
        dto.setAperture(result.aperture());
        dto.setShutterSpeed(result.shutterSpeed());
        dto.setFocalLength(result.focalLength());
        dto.setCameraModel(result.cameraModel());
        dto.setFinalScore(round(result.finalScore()));
        dto.setSuggestions(result.suggestions());
        dto.setUploadedAt(LocalDateTime.now());
        return dto;
    }

    private List<QualityDistributionPointDto> buildDistribution(List<ImageAnalysisResponseDto> analyses) {
        int[] buckets = new int[5];
        for (ImageAnalysisResponseDto analysis : analyses) {
            double score = analysis.getFinalScore();
            int idx;
            if (score < 20) {
                idx = 0;
            } else if (score < 40) {
                idx = 1;
            } else if (score < 60) {
                idx = 2;
            } else if (score < 80) {
                idx = 3;
            } else {
                idx = 4;
            }
            buckets[idx]++;
        }

        return List.of(
                new QualityDistributionPointDto("0-20", buckets[0]),
                new QualityDistributionPointDto("20-40", buckets[1]),
                new QualityDistributionPointDto("40-60", buckets[2]),
                new QualityDistributionPointDto("60-80", buckets[3]),
                new QualityDistributionPointDto("80-100", buckets[4])
        );
    }

    private byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException ex) {
            throw new InvalidImageException("Unable to read uploaded file.");
        }
    }

    private String joinHistogram(List<Integer> histogram) {
        return histogram == null ? "" : histogram.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).orElse("");
    }

    private List<Integer> parseHistogram(String histogram) {
        if (histogram == null || histogram.isBlank()) {
            return List.of();
        }
        return java.util.Arrays.stream(histogram.split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private double roundNullable(Double value) {
        return value == null ? 0 : round(value);
    }
}
