package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class NoiseAnalyzer {

    public NoiseResult analyze(Mat gray) {
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(3, 3), 0);

        Mat residual = new Mat();
        Core.absdiff(gray, blurred, residual);

        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(residual, mean, stddev);

        double noiseLevel = stddev.toArray()[0];
        double noiseScore = clamp(100.0 - normalize(noiseLevel, 2.5, 28.0));

        blurred.release();
        residual.release();
        mean.release();
        stddev.release();

        return new NoiseResult(noiseLevel, noiseScore);
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

    public record NoiseResult(
            double noiseLevel,
            double noiseScore
    ) {
    }
}
