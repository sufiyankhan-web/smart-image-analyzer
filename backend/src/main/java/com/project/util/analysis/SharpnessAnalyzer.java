package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class SharpnessAnalyzer {

    public SharpnessResult analyze(Mat gray, Rect subjectRegion) {
        Rect boundedSubject = boundRect(gray, subjectRegion);
        Mat subject = new Mat(gray, boundedSubject);
        Mat background = gray.clone();
        Imgproc.rectangle(background, boundedSubject, new org.opencv.core.Scalar(0), -1);

        double subjectLaplacian = calculateLaplacianVariance(subject);
        double backgroundLaplacian = calculateLaplacianVariance(background);
        double laplacianVariance = (subjectLaplacian * 0.7) + (backgroundLaplacian * 0.3);

        double subjectEdge = calculateEdgeDensity(subject);
        double backgroundEdge = calculateEdgeDensity(background);
        double edgeDensity = (subjectEdge * 0.7) + (backgroundEdge * 0.3);

        double subjectContrast = localContrast(subject);
        double backgroundContrast = localContrast(background);
        double contrastVariation = (subjectContrast * 0.7) + (backgroundContrast * 0.3);

        double laplacianScore = normalize(Math.log1p(laplacianVariance), Math.log1p(20), Math.log1p(3000));
        double edgeScore = normalize(edgeDensity, 0.02, 0.25);
        double contrastScore = normalize(contrastVariation, 8, 85);

        double sharpnessScore = clamp(0.5 * laplacianScore + 0.3 * edgeScore + 0.2 * contrastScore);

        subject.release();
        background.release();
        return new SharpnessResult(laplacianVariance, edgeDensity * 100.0, contrastVariation, sharpnessScore);
    }

    private double calculateLaplacianVariance(Mat gray) {
        Mat laplacian = new Mat();
        Imgproc.Laplacian(gray, laplacian, CvType.CV_64F);

        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(laplacian, mean, stddev);
        double variance = Math.pow(stddev.toArray()[0], 2);

        laplacian.release();
        mean.release();
        stddev.release();
        return variance;
    }

    private double calculateEdgeDensity(Mat gray) {
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 70, 180);
        double edgePixels = Core.countNonZero(edges);
        double totalPixels = gray.rows() * gray.cols();
        edges.release();
        return totalPixels == 0 ? 0 : edgePixels / totalPixels;
    }

    private double localContrast(Mat gray) {
        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(gray, mean, stddev);
        double contrast = stddev.toArray()[0];
        mean.release();
        stddev.release();
        return contrast;
    }

    private Rect boundRect(Mat image, Rect rect) {
        int x = Math.max(0, rect.x);
        int y = Math.max(0, rect.y);
        int w = Math.max(1, Math.min(rect.width, image.cols() - x));
        int h = Math.max(1, Math.min(rect.height, image.rows() - y));
        return new Rect(x, y, w, h);
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

    public record SharpnessResult(
            double laplacianVariance,
            double edgeDensityPercent,
            double localContrast,
            double sharpnessScore
    ) {
    }
}
