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

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class CustomValidatorTest {

    @Test
    public void customTest() {
        CustomValidator<String> c = CustomValidator.forPredicate(s -> s.length() % 2 == 0, "test");

        then(c.validate("abcd").getResult()).isTrue();
        then(c.validate("abc").getResult()).isFalse();
        then(c.validate("").getResult()).isTrue();
    }

}
