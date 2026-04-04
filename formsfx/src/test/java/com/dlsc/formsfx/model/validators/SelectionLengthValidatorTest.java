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

import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class SelectionLengthValidatorTest {

    @Test
    public void betweenTest() {
        SelectionLengthValidator<Integer> s = SelectionLengthValidator.between(1, 3, "test");

        then(s.validate(FXCollections.observableArrayList(1, 2, 3)).getResult()).isTrue();
        then(s.validate(FXCollections.observableArrayList()).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList(1, 2, 3, 4, 5)).getResult()).isFalse();

        thenThrownBy(() -> SelectionLengthValidator.between(-10, 2, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        SelectionLengthValidator s3 = SelectionLengthValidator.between(0, 0, "test");

        thenThrownBy(() -> SelectionLengthValidator.between(10, 1, "test"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void atLeastTest() {
        SelectionLengthValidator<Integer> s = SelectionLengthValidator.atLeast(2, "test");

        then(s.validate(FXCollections.observableArrayList(1, 4)).getResult()).isTrue();
        then(s.validate(FXCollections.observableArrayList(1)).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList()).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList(1, 2, 3)).getResult()).isTrue();

        thenThrownBy(() -> SelectionLengthValidator.atLeast(-10, "test"))
            .isInstanceOf(IllegalArgumentException.class);

        SelectionLengthValidator s3 = SelectionLengthValidator.atLeast(0, "test");
    }

    @Test
    public void upToTest() {
        SelectionLengthValidator<Integer> s = SelectionLengthValidator.upTo(2, "test");

        then(s.validate(FXCollections.observableArrayList(3, 5, 1)).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList(1, 2)).getResult()).isTrue();
        then(s.validate(FXCollections.observableArrayList()).getResult()).isTrue();
        then(s.validate(FXCollections.observableArrayList(1, 2, 3, 5, 14)).getResult()).isFalse();
    }

    @Test
    public void exactlyTest() {
        SelectionLengthValidator<Integer> s = SelectionLengthValidator.exactly(2, "test");

        then(s.validate(FXCollections.observableArrayList(1, 2, 3)).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList(1, 2)).getResult()).isTrue();
        then(s.validate(FXCollections.observableArrayList()).getResult()).isFalse();
        then(s.validate(FXCollections.observableArrayList(1)).getResult()).isFalse();
    }

}
