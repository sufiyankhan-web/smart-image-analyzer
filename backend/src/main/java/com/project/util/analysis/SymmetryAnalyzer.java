package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class SymmetryAnalyzer {

    public SymmetryResult analyze(Mat gray) {
        int width = gray.cols();
        int height = gray.rows();
        int halfWidth = width / 2;

        if (halfWidth < 2 || height < 2) {
            return new SymmetryResult(50, 0.5);
        }

        Mat left = new Mat(gray, new Rect(0, 0, halfWidth, height));
        Mat right = new Mat(gray, new Rect(width - halfWidth, 0, halfWidth, height));

        Mat rightFlipped = new Mat();
        Core.flip(right, rightFlipped, 1);

        if (left.size().width != rightFlipped.size().width || left.size().height != rightFlipped.size().height) {
            Imgproc.resize(rightFlipped, rightFlipped, new Size(left.cols(), left.rows()));
        }

        double ssim = computeSsim(left, rightFlipped);
        double score = clamp(ssim * 100.0);

        left.release();
        right.release();
        rightFlipped.release();

        return new SymmetryResult(score, ssim);
    }

    private double computeSsim(Mat first, Mat second) {
        Mat firstF = new Mat();
        Mat secondF = new Mat();
        first.convertTo(firstF, CvType.CV_32F);
        second.convertTo(secondF, CvType.CV_32F);

        MatOfDouble mean1 = new MatOfDouble();
        MatOfDouble std1 = new MatOfDouble();
        MatOfDouble mean2 = new MatOfDouble();
        MatOfDouble std2 = new MatOfDouble();
        Core.meanStdDev(firstF, mean1, std1);
        Core.meanStdDev(secondF, mean2, std2);

        double mu1 = mean1.toArray()[0];
        double mu2 = mean2.toArray()[0];
        double sigma1Sq = Math.pow(std1.toArray()[0], 2);
        double sigma2Sq = Math.pow(std2.toArray()[0], 2);

        Mat centered1 = new Mat();
        Mat centered2 = new Mat();
        Core.subtract(firstF, new org.opencv.core.Scalar(mu1), centered1);
        Core.subtract(secondF, new org.opencv.core.Scalar(mu2), centered2);

        Mat product = new Mat();
        Core.multiply(centered1, centered2, product);
        double sigma12 = Core.mean(product).val[0];

        double c1 = 6.5025;
        double c2 = 58.5225;

        double numerator = (2 * mu1 * mu2 + c1) * (2 * sigma12 + c2);
        double denominator = (mu1 * mu1 + mu2 * mu2 + c1) * (sigma1Sq + sigma2Sq + c2);
        double ssim = denominator == 0 ? 0 : numerator / denominator;

        firstF.release();
        secondF.release();
        mean1.release();
        std1.release();
        mean2.release();
        std2.release();
        centered1.release();
        centered2.release();
        product.release();

        return Math.max(0, Math.min(1, ssim));
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(100, value));
    }

    public record SymmetryResult(
            double symmetryScore,
            double ssim
    ) {
    }
}
