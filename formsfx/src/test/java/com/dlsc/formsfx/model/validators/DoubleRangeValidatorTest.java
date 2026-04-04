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
public class DoubleRangeValidatorTest {

    @Test
    public void betweenTest() {
        DoubleRangeValidator i = DoubleRangeValidator.between(3.5, 12.1351, "test");

        then(i.validate(11.5).getResult()).isTrue();
        then(i.validate(3.50000001).getResult()).isTrue();
        then(i.validate(12.13511).getResult()).isFalse();
        then(i.validate(3.4999999).getResult()).isFalse();

        thenThrownBy(() -> DoubleRangeValidator.between(10.0, 5.3, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        DoubleRangeValidator i2 = DoubleRangeValidator.between(5.5, 5.5, "test");
    }

    @Test
    public void atLeastTest() {
        DoubleRangeValidator i = DoubleRangeValidator.atLeast(5.12351, "test");

        then(i.validate(6234.1).getResult()).isTrue();
        then(i.validate(1.31).getResult()).isFalse();
        then(i.validate(5.12351).getResult()).isTrue();
        then(i.validate(5.1235).getResult()).isFalse();
        then(i.validate(Double.MAX_VALUE).getResult()).isTrue();
    }

    @Test
    public void upToTest() {
        DoubleRangeValidator i = DoubleRangeValidator.upTo(3.14, "test");

        then(i.validate(-1.14).getResult()).isFalse();
        then(i.validate(5.13).getResult()).isFalse();
        then(i.validate(3.14).getResult()).isTrue();
        then(i.validate(3.141).getResult()).isFalse();
        then(i.validate(Double.MIN_VALUE).getResult()).isTrue();
    }

    @Test
    public void exactlyTest() {
        DoubleRangeValidator i = DoubleRangeValidator.exactly(3.14, "test");

        then(i.validate(-3.4).getResult()).isFalse();
        then(i.validate(3.145).getResult()).isFalse();
        then(i.validate(3.14).getResult()).isTrue();
        then(i.validate(3.0).getResult()).isFalse();
        then(i.validate(Double.MIN_VALUE).getResult()).isFalse();
    }

}
