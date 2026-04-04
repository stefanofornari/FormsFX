package com.dlsc.formsfx.model.validators;

/*-
 * ========================LICENSE_START=================================
 * FormsFX
 * %%
 * Copyright (C) 2017 DLSC Software & Consulting
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class StringLengthValidatorTest {

    @Test
    public void betweenTest() {
        StringLengthValidator s = StringLengthValidator.between(10, 20, "test");

        then(s.validate("abcdefghijklmno").getResult()).isTrue();
        then(s.validate("abcde").getResult()).isFalse();
        then(s.validate("                ").getResult()).isTrue();
        then(s.validate("æ¢¢äöä1ö3ä±æ#¢æ±“{").getResult()).isTrue();

        thenThrownBy(() -> StringLengthValidator.between(-10, 2, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        StringLengthValidator s3 = StringLengthValidator.between(0, 0, "test");

        thenThrownBy(() -> StringLengthValidator.between(10, 1, "test"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void atLeastTest() {
        StringLengthValidator s = StringLengthValidator.atLeast(5, "test");

        then(s.validate("gosjrgohgsr").getResult()).isTrue();
        then(s.validate(" ").getResult()).isFalse();
        then(s.validate("ae").getResult()).isFalse();
        then(s.validate("¶æ¢¶ππ§±#").getResult()).isTrue();

        thenThrownBy(() -> StringLengthValidator.atLeast(-10, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        StringLengthValidator s3 = StringLengthValidator.atLeast(0, "test");
    }

    @Test
    public void upToTest() {
        StringLengthValidator s = StringLengthValidator.upTo(5, "test");

        then(s.validate("gosjrgohgsr").getResult()).isFalse();
        then(s.validate(" ").getResult()).isTrue();
        then(s.validate("ae").getResult()).isTrue();
        then(s.validate("¶æ¢¶ππ§±#").getResult()).isFalse();
    }

    @Test
    public void exactlyTest() {
        StringLengthValidator s = StringLengthValidator.exactly(3, "test");

        then(s.validate("gfyf").getResult()).isFalse();
        then(s.validate("   ").getResult()).isTrue();
        then(s.validate("aee").getResult()).isTrue();
        then(s.validate("ee").getResult()).isFalse();
    }

}
