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

import java.util.regex.PatternSyntaxException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class RegexValidatorTest {

    @Test
    public void regexTest() {
        RegexValidator r = RegexValidator.forPattern("[a-z]*", "test");

        then(r.validate("abc").getResult()).isTrue();
        then(r.validate("aeihafpiaheypfhapfhpa").getResult()).isTrue();
        then(r.validate("Ajj").getResult()).isFalse();
        then(r.validate("").getResult()).isTrue();
        then(r.validate("hlhlhL").getResult()).isFalse();

        Throwable thrown = catchThrowable(() -> RegexValidator.forPattern("a[aa[", "test"));
        then(thrown).isInstanceOf(PatternSyntaxException.class);
    }

    @Test
    public void emailTest() {
        RegexValidator r = RegexValidator.forEmail("test");

        then(r.validate("test@test.com").getResult()).isTrue();
        then(r.validate("test@test").getResult()).isFalse();
        then(r.validate("test.com@test@").getResult()).isFalse();
        then(r.validate("test+ea@test.com").getResult()).isTrue();
    }

    @Test
    public void urlTest() {
        RegexValidator r = RegexValidator.forURL("test");

        then(r.validate("http://test.com").getResult()).isTrue();
        then(r.validate("http:/test.com").getResult()).isFalse();
        then(r.validate("www.test.com").getResult()).isTrue();
        then(r.validate("https://www.test.com").getResult()).isTrue();
    }

    @Test
    public void alphaNumericTest() {
        RegexValidator r= RegexValidator.forAlphaNumeric("test");

        then(r.validate("afaehofh3r1ohr1o3hro1h3A13OIHho").getResult()).isTrue();
        then(r.validate("aefih 391ur fj ").getResult()).isFalse();
        then(r.validate("¢æ±#").getResult()).isFalse();
    }

}
