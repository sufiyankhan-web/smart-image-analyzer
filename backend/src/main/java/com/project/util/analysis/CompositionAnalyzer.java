package com.project.util.analysis;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.springframework.stereotype.Component;

@Component
public class CompositionAnalyzer {

    public CompositionResult analyze(Mat gray, SubjectDetectionAnalyzer.SubjectResult subject) {
        double width = gray.cols();
        double height = gray.rows();
        if (subject == null || subject.subjectRegion() == null) {
            return new CompositionResult(
                    55,
                    50,
                    "No dominant subject detected",
                    "Balanced composition",
                    width / 2.0,
                    height / 2.0,
                    new Rect(0, 0, (int) width, (int) height)
            );
        }

        Rect region = subject.subjectRegion();
        double centroidX = subject.centroidX();
        double centroidY = subject.centroidY();

        double[] thirdsX = {width / 3.0, 2.0 * width / 3.0};
        double[] thirdsY = {height / 3.0, 2.0 * height / 3.0};

        double minDistance = Double.MAX_VALUE;
        for (double tx : thirdsX) {
            for (double ty : thirdsY) {
                minDistance = Math.min(minDistance, Math.hypot(centroidX - tx, centroidY - ty));
            }
        }

        double maxDistance = Math.hypot(width, height);
        double ruleOfThirdsScore = clamp(100.0 - ((minDistance / maxDistance) * 220.0));

        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double centerDistance = Math.hypot(centroidX - centerX, centroidY - centerY);
        String subjectPlacement = centerDistance < (Math.min(width, height) * 0.08)
                ? "Subject centered"
                : "Subject off-center";

        String feedback = ruleOfThirdsScore >= 72
                ? "Balanced composition"
                : "Better alignment with rule-of-thirds recommended";

        double compositionScore = clamp(ruleOfThirdsScore * 0.85 + ("Subject off-center".equals(subjectPlacement) ? 15 : 5));

        return new CompositionResult(
                compositionScore,
                ruleOfThirdsScore,
                subjectPlacement,
                feedback,
                centroidX,
            centroidY,
            region
        );
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(100, value));
    }

    public record CompositionResult(
            double compositionScore,
            double ruleOfThirdsScore,
            String subjectPlacement,
            String feedback,
            double subjectCentroidX,
            double subjectCentroidY,
            Rect subjectRegion
    ) {
    }
}
