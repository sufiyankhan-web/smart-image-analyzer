package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.stereotype.Component;

@Component
public class SceneAwarenessAnalyzer {

    public SceneResult detect(Mat gray, boolean faceDetected, double symmetryScore) {
        if (faceDetected) {
            return new SceneResult("portrait");
        }

        double horizontalEdgeStrength = horizontalEdgeStrength(gray);
        if (horizontalEdgeStrength > 24) {
            return new SceneResult("landscape");
        }

        if (symmetryScore > 65) {
            return new SceneResult("architecture");
        }

        return new SceneResult("general");
    }

    private double horizontalEdgeStrength(Mat gray) {
        Mat gradY = new Mat();
        org.opencv.imgproc.Imgproc.Sobel(gray, gradY, CvType.CV_32F, 0, 1, 3);
        Core.absdiff(gradY, Mat.zeros(gradY.size(), gradY.type()), gradY);
        double value = Core.mean(gradY).val[0];
        gradY.release();
        return value;
    }

    public record SceneResult(String sceneType) {
    }
}
