package com.project.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.project.exception.InvalidImageException;
import com.project.util.analysis.CompositionAnalyzer;
import com.project.util.analysis.ExposureLightingAnalyzer;
import com.project.util.analysis.FaceQualityAnalyzer;
import com.project.util.analysis.GoldenRatioAnalyzer;
import com.project.util.analysis.NoiseAnalyzer;
import com.project.util.analysis.SceneAwarenessAnalyzer;
import com.project.util.analysis.SharpnessAnalyzer;
import com.project.util.analysis.SuggestionGenerator;
import com.project.util.analysis.SubjectDetectionAnalyzer;
import com.project.util.analysis.SymmetryAnalyzer;
import com.project.util.analysis.WeightedScoreCalculator;
import org.opencv.core.Rect;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.List;

@Component
public class ImageQualityAnalyzer {

    static {
        nu.pattern.OpenCV.loadLocally();
    }

    private final SharpnessAnalyzer sharpnessAnalyzer;
    private final ExposureLightingAnalyzer exposureLightingAnalyzer;
    private final SubjectDetectionAnalyzer subjectDetectionAnalyzer;
    private final CompositionAnalyzer compositionAnalyzer;
    private final GoldenRatioAnalyzer goldenRatioAnalyzer;
    private final SymmetryAnalyzer symmetryAnalyzer;
    private final FaceQualityAnalyzer faceQualityAnalyzer;
    private final NoiseAnalyzer noiseAnalyzer;
    private final SceneAwarenessAnalyzer sceneAwarenessAnalyzer;
    private final WeightedScoreCalculator weightedScoreCalculator;
    private final SuggestionGenerator suggestionGenerator;

    public ImageQualityAnalyzer(SharpnessAnalyzer sharpnessAnalyzer,
                                ExposureLightingAnalyzer exposureLightingAnalyzer,
                                SubjectDetectionAnalyzer subjectDetectionAnalyzer,
                                CompositionAnalyzer compositionAnalyzer,
                                GoldenRatioAnalyzer goldenRatioAnalyzer,
                                SymmetryAnalyzer symmetryAnalyzer,
                                FaceQualityAnalyzer faceQualityAnalyzer,
                                NoiseAnalyzer noiseAnalyzer,
                                SceneAwarenessAnalyzer sceneAwarenessAnalyzer,
                                WeightedScoreCalculator weightedScoreCalculator,
                                SuggestionGenerator suggestionGenerator) {
        this.sharpnessAnalyzer = sharpnessAnalyzer;
        this.exposureLightingAnalyzer = exposureLightingAnalyzer;
        this.subjectDetectionAnalyzer = subjectDetectionAnalyzer;
        this.compositionAnalyzer = compositionAnalyzer;
        this.goldenRatioAnalyzer = goldenRatioAnalyzer;
        this.symmetryAnalyzer = symmetryAnalyzer;
        this.faceQualityAnalyzer = faceQualityAnalyzer;
        this.noiseAnalyzer = noiseAnalyzer;
        this.sceneAwarenessAnalyzer = sceneAwarenessAnalyzer;
        this.weightedScoreCalculator = weightedScoreCalculator;
        this.suggestionGenerator = suggestionGenerator;
    }

    public AnalysisResult analyze(byte[] imageBytes) {
        Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);
        if (image.empty()) {
            throw new InvalidImageException("Invalid or unsupported image file.");
        }

        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        SubjectDetectionAnalyzer.SubjectResult subject = subjectDetectionAnalyzer.detect(gray);
        Rect subjectRegion = subject.subjectRegion();

        SharpnessAnalyzer.SharpnessResult sharpness = sharpnessAnalyzer.analyze(gray, subjectRegion);
        ExposureLightingAnalyzer.ExposureResult exposure = exposureLightingAnalyzer.analyze(gray, subjectRegion);
        CompositionAnalyzer.CompositionResult composition = compositionAnalyzer.analyze(gray, subject);
        GoldenRatioAnalyzer.GoldenRatioResult goldenRatio = goldenRatioAnalyzer.analyze(
                gray.cols(),
                gray.rows(),
                composition.subjectCentroidX(),
                composition.subjectCentroidY()
        );
        SymmetryAnalyzer.SymmetryResult symmetry = symmetryAnalyzer.analyze(gray);
        FaceQualityAnalyzer.FaceResult face = faceQualityAnalyzer.analyze(gray);
        NoiseAnalyzer.NoiseResult noise = noiseAnalyzer.analyze(gray);
        SceneAwarenessAnalyzer.SceneResult scene = sceneAwarenessAnalyzer.detect(gray, face.faceDetected(), symmetry.symmetryScore());
        ExifResult exif = extractExif(imageBytes);

        WeightedScoreCalculator.ScoringResult scoring = weightedScoreCalculator.calculateFinalScore(
                sharpness.sharpnessScore(),
                exposure.exposureScore(),
                composition.compositionScore(),
                face.faceScore(),
                symmetry.symmetryScore(),
            noise.noiseScore(),
            scene.sceneType()
        );

        List<String> suggestions = suggestionGenerator.generate(
                sharpness,
                exposure,
                composition,
                goldenRatio,
                symmetry,
                face,
                noise,
                scene.sceneType()
        );

            Rect faceRect = face.faceRect();
            double symmetryAxisX = gray.cols() / 2.0;

        image.release();
        gray.release();

        return new AnalysisResult(
                sharpness.laplacianVariance(),
                exposure.midtonePercentage(),
                sharpness.localContrast(),
                exposure.highlightPercentage(),
                exposure.shadowPercentage(),
                sharpness.sharpnessScore(),
                exposure.exposureScore(),
                composition.compositionScore(),
                composition.ruleOfThirdsScore(),
                composition.feedback(),
                composition.subjectPlacement(),
                exposure.highlightPercentage(),
                exposure.shadowPercentage(),
                exposure.exposureState(),
                exposure.histogram(),
                goldenRatio.goldenRatioScore(),
                symmetry.symmetryScore(),
                face.faceScore(),
                noise.noiseScore(),
                scene.sceneType(),
                gray.cols(),
                gray.rows(),
                composition.subjectCentroidX(),
                composition.subjectCentroidY(),
                subjectRegion.x,
                subjectRegion.y,
                subjectRegion.width,
                subjectRegion.height,
                symmetryAxisX,
                face.faceDetected(),
                faceRect == null ? 0 : faceRect.x,
                faceRect == null ? 0 : faceRect.y,
                faceRect == null ? 0 : faceRect.width,
                faceRect == null ? 0 : faceRect.height,
                exif.iso(),
                exif.aperture(),
                exif.shutterSpeed(),
                exif.focalLength(),
                exif.cameraModel(),
                scoring.finalScore(),
                suggestions
        );
    }

    private ExifResult extractExif(byte[] imageBytes) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageBytes));
            ExifSubIFDDirectory sub = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            ExifIFD0Directory ifd0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            Integer iso = sub != null ? sub.getInteger(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT) : null;
            String aperture = sub != null ? sub.getDescription(ExifSubIFDDirectory.TAG_FNUMBER) : null;
            String shutter = sub != null ? sub.getDescription(ExifSubIFDDirectory.TAG_EXPOSURE_TIME) : null;
            String focal = sub != null ? sub.getDescription(ExifSubIFDDirectory.TAG_FOCAL_LENGTH) : null;
            String camera = ifd0 != null ? ifd0.getDescription(ExifIFD0Directory.TAG_MODEL) : null;

            return new ExifResult(iso, aperture, shutter, focal, camera);
        } catch (Exception ignored) {
            return new ExifResult(null, null, null, null, null);
        }
    }

    public record AnalysisResult(
            double blurVariance,
            double brightness,
            double contrast,
            double overexposurePercent,
            double underexposurePercent,
            double sharpnessScore,
            double exposureScore,
            double compositionScore,
            double ruleOfThirdsAlignmentScore,
            String compositionFeedback,
            String subjectPlacement,
            double highlightClippingPercent,
            double shadowClippingPercent,
            String exposureBalance,
            List<Integer> histogram,
            double goldenRatioScore,
            double symmetryScore,
            double faceScore,
            double noiseScore,
            String sceneType,
            int imageWidth,
            int imageHeight,
            double subjectCentroidX,
            double subjectCentroidY,
            int subjectRegionX,
            int subjectRegionY,
            int subjectRegionWidth,
            int subjectRegionHeight,
            double symmetryAxisX,
            boolean faceDetected,
            int faceX,
            int faceY,
            int faceWidth,
            int faceHeight,
            Integer iso,
            String aperture,
            String shutterSpeed,
            String focalLength,
            String cameraModel,
            double finalScore,
            List<String> suggestions
    ) {
    }

    private record ExifResult(
            Integer iso,
            String aperture,
            String shutterSpeed,
            String focalLength,
            String cameraModel
    ) {
    }
}
