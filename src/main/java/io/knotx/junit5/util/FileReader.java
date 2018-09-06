/*
 * Copyright (C) 2018 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.junit5.util;

import com.google.common.io.Resources;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public interface FileReader {

  /**
   * Read contents of resource and return as string. Ported from
   * io.knotx.junit.util.FileReader.readText(String) and fixed.
   *
   * @param path resource path
   * @return resource contents
   * @throws IOException resource can not be read
   */
  static String readText(String path) throws IOException {
    return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
  }

  /**
   * Same as {@linkplain #readText(String)}, but throws unchecked exception instead
   *
   * @param path resource path
   * @return resource contents
   * @throws IllegalArgumentException on IOException
   */
  static String readTextSafe(String path) {
    try {
      return readText(path);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not load text from [" + path + "]");
    }
  }
}
