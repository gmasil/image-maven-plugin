/**
 * Image-Maven-Plugin
 * Copyright © 2020 gmasil.de
 *
 * This file is part of Image-Maven-Plugin.
 *
 * Image-Maven-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Image-Maven-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Image-Maven-Plugin. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.maven.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "image")
public class ImageMojo extends AbstractMojo {
	@Parameter(property = "background", required = true)
	protected String background;

	@Parameter(property = "title", required = true)
	protected Title title;

	@Parameter(property = "subtitle", required = false, defaultValue = "")
	protected Title subtitle = null;

	@Parameter(property = "target", required = true)
	protected String target;

	@Parameter(property = "overwrite", required = true, defaultValue = "true")
	protected boolean overwrite;

	@Override
	public void execute() throws MojoExecutionException {
		BufferedImage imgBackground;
		try {
			imgBackground = ImageIO.read(new File(background));
		} catch (IOException e) {
			getLog().error("Could not read image");
			throw new MojoExecutionException("Error while reading image " + background, e);
		}
		String format = getExtension(target);
		int type = imgBackground.getType();
		if (format.equalsIgnoreCase("bmp")) {
			type = BufferedImage.TYPE_INT_RGB;
		}
		BufferedImage image = new BufferedImage(imgBackground.getWidth(), imgBackground.getHeight(), type);
		Graphics graphics = image.getGraphics();
		graphics.drawImage(imgBackground, 0, 0, null);
		// draw title
		graphics.setColor(toColor(title.getColor()));
		graphics.setFont(new Font(title.getFont(), Font.PLAIN, title.getSize()));
		graphics.drawString(title.getText(), title.getX(), title.getY());
		if (subtitle != null) {
			// draw subtitle
			List<String> subtitles;
			subtitles = extractSubtitleLines();
			graphics.setColor(toColor(subtitle.getColor()));
			int offset = 0;
			for (String s : subtitles) {
				graphics.setFont(new Font(subtitle.getFont(), Font.PLAIN, subtitle.getSize()));
				graphics.drawString(s, subtitle.getX(), subtitle.getY() + offset);
				offset += subtitle.getSize() + 5;
			}
		}
		File targetFile = new File(target);
		if (targetFile.exists() && overwrite) {
			try {
				Files.delete(targetFile.toPath());
			} catch (IOException e) {
				getLog().error("Could not overwrite old file");
				throw new MojoExecutionException("Could not overwrite old file", e);
			}
		}
		if (!targetFile.exists() || overwrite) {
			try {
				targetFile.mkdirs();
				ImageIO.write(image, format, targetFile);
			} catch (IOException e) {
				getLog().error("Could not write image");
				throw new MojoExecutionException("Error while saving image", e);
			}
		}
	}

	protected List<String> extractSubtitleLines() {
		List<String> subtitles;
		if (subtitle.getText().contains("\\n")) {
			subtitles = Arrays.asList(subtitle.getText().split("\\\\n"));
		} else {
			subtitles = new LinkedList<>();
			subtitles.add(subtitle.getText());
		}
		return subtitles;
	}

	protected Color toColor(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	protected String getExtension(String filename) {
		int i = filename.lastIndexOf(".");
		if (i > 0) {
			return filename.substring(i + 1);
		}
		getLog().warn("Could not determine output format, using png");
		return "png";
	}
}
