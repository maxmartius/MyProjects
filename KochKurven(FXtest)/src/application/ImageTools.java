package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class ImageTools {

	public static void exportImage(Canvas canvas) {
		exportImage(canvas, "picture");
	}

	public static void exportImage(Canvas canvas, String name) {
		WritableImage wImage = null;
		wImage = canvas.snapshot(null, wImage);
		String filename = "./savedImages/" + name + ".png";
		File file = new File(filename);
		RenderedImage rImage = SwingFXUtils.fromFXImage(wImage, null);
		try {
			ImageIO.write(rImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Canvas displayLines(Canvas canvas, List<Linie> linien, double scale) {
		double width = canvas.widthProperty().doubleValue();
		double height = canvas.heightProperty().doubleValue();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		for (Linie l : linien) {
			gc.strokeLine(
					(width / 2) + l.getUrsprungsPunkt().getX() * scale,
					(height / 2) - l.getUrsprungsPunkt().getY() * scale,
					(width / 2) + l.getEndPunkt().getX() * scale,
					(height / 2) - l.getEndPunkt().getY() * scale);
		}
		return canvas;
	}

	public static void clearCanvas(Canvas canvas) {
		double width = canvas.widthProperty().doubleValue();
		double height = canvas.heightProperty().doubleValue();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
	}
}
