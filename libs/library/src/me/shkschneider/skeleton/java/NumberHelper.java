/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.java;

import java.util.Random;

@SuppressWarnings("unused")
public class NumberHelper {

    public static Integer random(final Integer min, final Integer max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static Integer random() {
        return new Random().nextInt();
    }

    public static Boolean zero(final Integer integer) {
        return (integer == null || integer == 0);
    }

}
