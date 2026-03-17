package com.project.util.analysis;

import org.springframework.stereotype.Component;

@Component
public class GoldenRatioAnalyzer {

    public GoldenRatioResult analyze(double imageWidth, double imageHeight, double subjectCentroidX, double subjectCentroidY) {
        double[] xLines = {imageWidth * 0.382, imageWidth * 0.618};
        double[] yLines = {imageHeight * 0.382, imageHeight * 0.618};

        double minDistance = Double.MAX_VALUE;
        for (double x : xLines) {
            for (double y : yLines) {
                minDistance = Math.min(minDistance, Math.hypot(subjectCentroidX - x, subjectCentroidY - y));
            }
        }

        double maxDistance = Math.hypot(imageWidth, imageHeight);
        double score = clamp(100.0 - ((minDistance / maxDistance) * 250.0));
        String feedback = score >= 70
                ? "Subject aligns well with golden ratio"
                : "Golden ratio alignment can be improved";
        return new GoldenRatioResult(score, feedback);
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(100, value));
    }

    public record GoldenRatioResult(
            double goldenRatioScore,
            String feedback
    ) {
    }
}
