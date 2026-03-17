package com.project.util.analysis;

import org.springframework.stereotype.Component;

@Component
public class WeightedScoreCalculator {

    public ScoringResult calculateFinalScore(double sharpnessScore,
                                             double exposureScore,
                                             double compositionScore,
                                             double faceScore,
                                             double symmetryScore,
                                             double noiseScore,
                                             String sceneType) {
        Weights weights = sceneWeights(sceneType);

        return clamp(
                sharpnessScore * weights.sharpness
                        + exposureScore * weights.exposure
                        + compositionScore * weights.composition
                        + faceScore * weights.face
                        + symmetryScore * weights.symmetry
                        + noiseScore * weights.noise
        );
    }

    private ScoringResult clamp(double rawScore) {
        return new ScoringResult(Math.max(0, Math.min(100, rawScore)));
    }

    private Weights sceneWeights(String sceneType) {
        if (sceneType == null) {
            return new Weights(0.25, 0.20, 0.20, 0.15, 0.10, 0.10);
        }
        return switch (sceneType.toLowerCase()) {
            case "portrait" -> new Weights(0.23, 0.20, 0.18, 0.22, 0.07, 0.10);
            case "landscape" -> new Weights(0.27, 0.24, 0.22, 0.05, 0.12, 0.10);
            case "architecture" -> new Weights(0.24, 0.18, 0.22, 0.06, 0.20, 0.10);
            default -> new Weights(0.25, 0.20, 0.20, 0.15, 0.10, 0.10);
        };
    }

    private record Weights(
            double sharpness,
            double exposure,
            double composition,
            double face,
            double symmetry,
            double noise
    ) {
    }

    public record ScoringResult(double finalScore) {
    }
}
