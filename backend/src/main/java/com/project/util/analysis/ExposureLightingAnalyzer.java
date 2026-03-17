package com.project.util.analysis;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExposureLightingAnalyzer {

    public ExposureResult analyze(Mat gray, Rect subjectRegion) {
        Rect boundedSubject = boundRect(gray, subjectRegion);
        Mat subject = new Mat(gray, boundedSubject);
        Mat background = gray.clone();
        Imgproc.rectangle(background, boundedSubject, new Scalar(0), -1);

        HistogramStats subjectStats = histogramStats(subject);
        HistogramStats backgroundStats = histogramStats(background);

        double shadowPercent = (subjectStats.shadowPercent * 0.7) + (backgroundStats.shadowPercent * 0.3);
        double highlightPercent = (subjectStats.highlightPercent * 0.7) + (backgroundStats.highlightPercent * 0.3);
        double midtonePercent = (subjectStats.midtonePercent * 0.7) + (backgroundStats.midtonePercent * 0.3);

        List<Integer> histogram = subjectStats.histogram;

        subject.release();
        background.release();

        String exposureState;
        if (highlightPercent > 15) {
            exposureState = "Overexposed";
        } else if (shadowPercent > 20) {
            exposureState = "Underexposed";
        } else {
            exposureState = "Balanced exposure";
        }

        double clippingPenalty = Math.min(100, highlightPercent * 3.5 + shadowPercent * 2.8);
        double midtoneScore = normalize(midtonePercent, 28, 68);
        double exposureScore = clamp((100 - clippingPenalty) * 0.7 + midtoneScore * 0.3);

        return new ExposureResult(
                shadowPercent,
                highlightPercent,
                midtonePercent,
                exposureState,
                exposureScore,
                histogram
        );
    }

    private HistogramStats histogramStats(Mat gray) {
        Mat hist = new Mat();
        Imgproc.calcHist(
                Collections.singletonList(gray),
                new MatOfInt(0),
                new Mat(),
                hist,
                new MatOfInt(256),
                new MatOfFloat(0, 256)
        );

        List<Integer> histogram = new ArrayList<>(256);
        double total = Math.max(1.0, gray.rows() * gray.cols());
        double shadow = 0;
        double highlight = 0;
        double midtone = 0;

        for (int i = 0; i < 256; i++) {
            int count = (int) hist.get(i, 0)[0];
            histogram.add(count);
            if (i <= 45) {
                shadow += count;
            } else if (i >= 210) {
                highlight += count;
            } else {
                midtone += count;
            }
        }
        hist.release();

        return new HistogramStats(
                percentage(shadow, total),
                percentage(highlight, total),
                percentage(midtone, total),
                histogram
        );
    }

    private Rect boundRect(Mat image, Rect rect) {
        int x = Math.max(0, rect.x);
        int y = Math.max(0, rect.y);
        int w = Math.max(1, Math.min(rect.width, image.cols() - x));
        int h = Math.max(1, Math.min(rect.height, image.rows() - y));
        return new Rect(x, y, w, h);
    }

    private double percentage(double value, double total) {
        if (total <= 0) {
            return 0;
        }
        return (value / total) * 100.0;
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

    public record ExposureResult(
            double shadowPercentage,
            double highlightPercentage,
            double midtonePercentage,
            String exposureState,
            double exposureScore,
            List<Integer> histogram
    ) {
    }

        private record HistogramStats(
            double shadowPercent,
            double highlightPercent,
            double midtonePercent,
            List<Integer> histogram
        ) {
        }
}
