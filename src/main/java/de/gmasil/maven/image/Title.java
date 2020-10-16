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

import org.apache.maven.plugins.annotations.Parameter;

public class Title {
	@Parameter(property = "text", required = true)
	private String text;

	@Parameter(property = "x", required = false, defaultValue = "20")
	private int x = 20;

	@Parameter(property = "y", required = false, defaultValue = "20")
	private int y = 20;

	@Parameter(property = "size", required = false, defaultValue = "20")
	private int size = 20;

	@Parameter(property = "font", required = false, defaultValue = "Arial")
	private String font = "Arial";

	public Title() {
	}

	public Title(String text, int x, int y, int size, String font) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.size = size;
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
}
