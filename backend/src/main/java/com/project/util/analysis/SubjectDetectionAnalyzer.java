package com.project.util.analysis;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class SubjectDetectionAnalyzer {

    public SubjectResult detect(Mat gray) {
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 60, 170);

        int rows = gray.rows();
        int cols = gray.cols();
        int gridRows = 3;
        int gridCols = 3;

        double bestDensity = -1;
        Rect bestRegion = new Rect(0, 0, cols, rows);

        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridCols; c++) {
                int x = c * cols / gridCols;
                int y = r * rows / gridRows;
                int w = (c == gridCols - 1) ? cols - x : cols / gridCols;
                int h = (r == gridRows - 1) ? rows - y : rows / gridRows;
                Rect tile = new Rect(x, y, w, h);

                Mat tileEdges = new Mat(edges, tile);
                double edgePixels = Core.countNonZero(tileEdges);
                double density = edgePixels / Math.max(1.0, (double) (w * h));
                tileEdges.release();

                if (density > bestDensity) {
                    bestDensity = density;
                    bestRegion = tile;
                }
            }
        }

        edges.release();

        double centroidX = bestRegion.x + bestRegion.width / 2.0;
        double centroidY = bestRegion.y + bestRegion.height / 2.0;

        return new SubjectResult(bestRegion, centroidX, centroidY, bestDensity * 100.0);
    }

    public record SubjectResult(
            Rect subjectRegion,
            double centroidX,
            double centroidY,
            double edgeDensityPercent
    ) {
    }
}
