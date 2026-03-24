package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FaceQualityAnalyzer {

    static {
        nu.pattern.OpenCV.loadLocally();
    }

    private final CascadeClassifier cascadeClassifier;

    public FaceQualityAnalyzer(@Value("${app.face.cascade-path:}") String configuredCascadePath) {
        this.cascadeClassifier = new CascadeClassifier();
        String cascadeFileName = "haarcascade_frontalface_default.xml";
        String haarDirectory = System.getProperty("opencv.data.haarcascades", "");
        String systemPath = (haarDirectory == null || haarDirectory.isBlank())
                ? ""
                : Path.of(haarDirectory, cascadeFileName).toString();

        boolean loaded = false;
        if (isValidPath(configuredCascadePath)) {
            loaded = cascadeClassifier.load(configuredCascadePath);
        }
        if (!loaded && isValidPath(systemPath)) {
            loaded = cascadeClassifier.load(systemPath);
        }

        if (!loaded) {
            cascadeClassifier.empty();
        }
    }

    private boolean isValidPath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return false;
        }
        try {
            return Files.exists(Path.of(filePath));
        } catch (Exception ignored) {
            return false;
        }
    }

    public FaceResult analyze(Mat gray) {
        if (cascadeClassifier.empty()) {
            return new FaceResult(false, 60, 0, 0, 0, 0, null);
        }

        MatOfRect faces = new MatOfRect();
        cascadeClassifier.detectMultiScale(gray, faces);
        Rect[] foundFaces = faces.toArray();
        faces.release();

        if (foundFaces.length == 0) {
            return new FaceResult(false, 60, 0, 0, 0, 0, null);
        }

        Rect face = foundFaces[0];
        Mat faceRegion = new Mat(gray, face);

        double brightness = Core.mean(faceRegion).val[0];
        double sharpness = calculateSharpness(faceRegion);
        double lightingBalance = calculateLightingBalance(faceRegion);

        double brightnessScore = clamp(100 - (Math.abs(brightness - 128) / 128.0) * 100);
        double sharpnessScore = clamp(normalize(Math.log1p(sharpness), Math.log1p(10), Math.log1p(1200)));
        double balanceScore = clamp(100 - (lightingBalance * 2.8));

        double faceScore = clamp(0.35 * brightnessScore + 0.40 * sharpnessScore + 0.25 * balanceScore);

        faceRegion.release();
        return new FaceResult(true, faceScore, brightness, sharpness, lightingBalance, balanceScore, face);
    }

    private double calculateSharpness(Mat region) {
        Mat laplacian = new Mat();
        Imgproc.Laplacian(region, laplacian, org.opencv.core.CvType.CV_64F);
        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(laplacian, mean, stddev);
        double variance = Math.pow(stddev.toArray()[0], 2);
        laplacian.release();
        mean.release();
        stddev.release();
        return variance;
    }

    private double calculateLightingBalance(Mat faceRegion) {
        int width = faceRegion.cols();
        int height = faceRegion.rows();
        if (width < 2 || height < 2) {
            return 0;
        }
        Mat left = faceRegion.colRange(0, width / 2);
        Mat right = faceRegion.colRange(width / 2, width);

        double leftMean = Core.mean(left).val[0];
        double rightMean = Core.mean(right).val[0];

        left.release();
        right.release();
        return Math.abs(leftMean - rightMean);
    }

    private double normalize(double value, double min, double max) {
        if (value <= min) {
            return 0;
        }
        if (value >= max) {
            return 100;
        }
        return ((value - min) / (max - min)) * 100.0;
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(100, value));
    }

    public record FaceResult(
            boolean faceDetected,
            double faceScore,
            double faceBrightness,
            double faceSharpness,
            double faceLightingBalance,
            double faceLightingBalanceScore,
            Rect faceRect
    ) {
    }
}
