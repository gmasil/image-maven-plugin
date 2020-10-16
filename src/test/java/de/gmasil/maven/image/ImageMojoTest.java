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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.io.FileMatchers.aFileWithSize;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

class ImageMojoTest {
	@Test
	void test() {
		ImageMojo mojo = new ImageMojo();
		mojo.background = "src/test/resources/background.png";
		mojo.title = new Title("My Title", 20, 50, 25, "Ebrima");
		mojo.subtitle = new Title("My Subtitle\\nLine 2", 20, 70, 15, "Arial");
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
}
