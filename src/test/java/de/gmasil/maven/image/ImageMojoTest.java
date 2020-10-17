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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.io.FileMatchers.aFileWithSize;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

class ImageMojoTest {
	@Test
	void test() {
		ImageMojo mojo = new ImageMojo();
		mojo.background = "src/test/resources/background.png";
		mojo.title = new Title("My Title");
		mojo.title.setX(20);
		mojo.title.setY(50);
		mojo.title.setSize(25);
		mojo.title.setFont("Ebrima");
		mojo.subtitle = new Title("My Subtitle\\nLine 2");
		mojo.subtitle.setX(20);
		mojo.subtitle.setY(70);
		mojo.subtitle.setSize(15);
		mojo.subtitle.setFont("Arial");
		mojo.target = "target/targetimage.bmp";
		mojo.overwrite = true;
		try {
			mojo.execute();
		} catch (MojoExecutionException e) {
			fail("Error while executing mojo", e);
		}
		assertThat("The target file does not exist", new File(mojo.target).exists());
		assertThat(new File(mojo.target), aFileWithSize(greaterThan(1L)));
	}

	@Test
	void testExtractSubtitleLinesSingle() {
		ImageMojo mojo = new ImageMojo();
		// escaped \n as it would be read from xml config
		mojo.subtitle = new Title("Only one Line");
		List<String> lines = mojo.extractSubtitleLines();
		assertThat(lines, hasSize(1));
	}

	@Test
	void testExtractSubtitleLinesMultiple() {
		ImageMojo mojo = new ImageMojo();
		// escaped \n as it would be read from xml config
		mojo.subtitle = new Title("Line1\\nLine2");
		List<String> lines = mojo.extractSubtitleLines();
		assertThat(lines, hasSize(2));
	}

	@Test
	void testGetExtensionReturnsPngOnFailure() {
		ImageMojo mojo = new ImageMojo();
		String extension = mojo.getExtension("testfile");
		assertThat(extension, is(equalTo("png")));
	}
}
