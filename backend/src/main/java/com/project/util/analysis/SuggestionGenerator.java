package com.project.util.analysis;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuggestionGenerator {

    public List<String> generate(SharpnessAnalyzer.SharpnessResult sharpness,
                                 ExposureLightingAnalyzer.ExposureResult exposure,
                                 CompositionAnalyzer.CompositionResult composition,
                                 GoldenRatioAnalyzer.GoldenRatioResult goldenRatio,
                                 SymmetryAnalyzer.SymmetryResult symmetry,
                                 FaceQualityAnalyzer.FaceResult face,
                                 NoiseAnalyzer.NoiseResult noise,
                                 String sceneType) {
        List<String> suggestions = new ArrayList<>();

        if (sharpness.sharpnessScore() < 45) {
            suggestions.add("Image appears slightly blurry. Improve focus stability or increase shutter speed.");
        }
        if (exposure.highlightPercentage() > 15) {
            suggestions.add("Highlights are overexposed. Reduce exposure or use faster shutter speed.");
        }
        if (exposure.shadowPercentage() > 20) {
            suggestions.add("Shadows are too deep. Increase fill light or expose slightly brighter.");
        }
        if (composition.ruleOfThirdsScore() < 60) {
            suggestions.add("Subject is centered instead of following rule-of-thirds. Reframe near intersection points.");
        }
        if (goldenRatio.goldenRatioScore() < 55) {
            suggestions.add("Golden ratio alignment is weak. Try placing subject near 0.382/0.618 frame zones.");
        }
        if (symmetry.symmetryScore() < 45) {
            suggestions.add("Scene symmetry is low. If symmetry is intended, align camera and subject axis.");
        }
        if (face.faceDetected()) {
            if (face.faceScore() < 60) {
                suggestions.add("Face quality can be improved with better focus and softer front lighting.");
            }
            if (face.faceLightingBalance() > 22) {
                suggestions.add("Face lighting is uneven. Balance key and fill light across the face.");
            }
        }
        if (noise.noiseScore() < 55) {
            suggestions.add("Noise is high. Lower ISO or improve scene lighting for cleaner detail.");
        }

        if (suggestions.isEmpty()) {
            suggestions.add("Image quality is strong. Apply minor color grading for final polish.");
        }

        if ("portrait".equalsIgnoreCase(sceneType)) {
            suggestions.add("Scene detected as portrait. Prioritize eye-focus sharpness and face lighting consistency.");
        } else if ("landscape".equalsIgnoreCase(sceneType)) {
            suggestions.add("Scene detected as landscape. Keep horizon level and protect highlight details in sky regions.");
        } else if ("architecture".equalsIgnoreCase(sceneType)) {
            suggestions.add("Scene detected as architecture. Align vertical lines and leverage symmetry for stronger framing.");
        }

        return suggestions;
    }
}
