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
public class IntegerRangeValidatorTest {

    @Test
    public void betweenTest() {
        IntegerRangeValidator i = IntegerRangeValidator.between(10, 20, "test");

        then(i.validate(14).getResult()).isTrue();
        then(i.validate(21).getResult()).isFalse();
        then(i.validate(20).getResult()).isTrue();
        then(i.validate(10).getResult()).isTrue();

        thenThrownBy(() -> IntegerRangeValidator.between(20, 10, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        IntegerRangeValidator i2 = IntegerRangeValidator.between(10, 10, "test");
    }

    @Test
    public void atLeastTest() {
        IntegerRangeValidator i = IntegerRangeValidator.atLeast(10, "test");

        then(i.validate(14).getResult()).isTrue();
        then(i.validate(-139).getResult()).isFalse();
        then(i.validate(10).getResult()).isTrue();
        then(i.validate(9).getResult()).isFalse();
        then(i.validate(Integer.MAX_VALUE).getResult()).isTrue();
    }

    @Test
    public void upToTest() {
        IntegerRangeValidator i = IntegerRangeValidator.upTo(10, "test");

        then(i.validate(14).getResult()).isFalse();
        then(i.validate(21).getResult()).isFalse();
        then(i.validate(10).getResult()).isTrue();
        then(i.validate(11).getResult()).isFalse();
        then(i.validate(Integer.MIN_VALUE).getResult()).isTrue();
    }

    @Test
    public void exactlyTest() {
        IntegerRangeValidator i = IntegerRangeValidator.exactly(10, "test");

        then(i.validate(11).getResult()).isFalse();
        then(i.validate(9).getResult()).isFalse();
        then(i.validate(10).getResult()).isTrue();
        then(i.validate(Integer.MIN_VALUE).getResult()).isFalse();
    }

}
