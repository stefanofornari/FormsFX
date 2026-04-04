package com.dlsc.formsfx.model.structure;

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

import com.dlsc.formsfx.model.util.ResourceBundleService;
import com.dlsc.formsfx.model.validators.RegexValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public class FormTest {

    private ResourceBundleService service;
    private ResourceBundle rbDE = ResourceBundle.getBundle("testbundle", new Locale("de", "CH"));
    private ResourceBundle rbEN = ResourceBundle.getBundle("testbundle", new Locale("en", "UK"));

    @BeforeEach
    public void beforeSuite() {
        service = new ResourceBundleService(rbEN);
    }

    @Test
    public void generateFormTest() {
        Form f = Form.of(
                Group.of(
                        Field.ofDoubleType(2.0)
                ),
                Section.of(
                        Field.ofStringType("test"),
                        Field.ofBooleanType(false)
                ),
                Section.of(
                        Field.ofIntegerType(1),
                        Field.ofDoubleType(1.0)
                ),
                Section.of(
                        Field.ofPasswordType("")
                )
        );

        then(f.getElements().size()).isEqualTo(6);
        then(f.getGroups().size()).isEqualTo(4);
    }

    @Test
    public void translationTest() {
        Form f = Form.of(
                Section.of(
                        Field.ofStringType("test")
                                .label("string_label")
                ).title("section_1_title")
        ).title("form_title")
                .i18n(service);

        then(f.getTitle()).isEqualTo("Test Form");

        service.changeLocale(rbDE);

        then(f.getTitle()).isEqualTo("Testformular");
        then(((Section) f.getGroups().get(0)).getTitle()).isEqualTo("Erste Gruppe");
    }

    @Test
    public void changeTest() {
        StringField s = Field.ofStringType("test");
        DoubleField d = Field.ofDoubleType(2.0);
        Section sec = Section.of(s, d);

        Form f = Form.of(sec);

        s.userInputProperty().setValue("testttt");
        d.userInputProperty().setValue("3.0");

        then(s.hasChanged()).isTrue();
        then(sec.hasChanged()).isTrue();
        then(f.hasChanged()).isTrue();

        s.reset();

        then(s.hasChanged()).isFalse();
        then(sec.hasChanged()).isTrue();
        then(f.hasChanged()).isTrue();

        d.persist();

        then(s.hasChanged()).isFalse();
        then(sec.hasChanged()).isFalse();
        then(f.hasChanged()).isFalse();
    }

    @Test
    public void validationTest() {
        StringField s = Field.ofStringType("Testing")
                .validate(
                        StringLengthValidator.atLeast(5, "String not long enough."),
                        RegexValidator.forPattern("[a-z ]*", "String is not all lowercase.")
                );
        Section sec = Section.of(s);

        Form f = Form.of(sec);

        then(s.isValid()).isFalse();
        then(sec.isValid()).isFalse();
        then(f.isValid()).isFalse();
        then(s.getErrorMessages().size()).isEqualTo(1);

        s.userInputProperty().setValue("Test");

        then(s.isValid()).isFalse();
        then(sec.isValid()).isFalse();
        then(f.isValid()).isFalse();
        then(s.getErrorMessages().size()).isEqualTo(2);

        s.userInputProperty().setValue("Testing this");

        then(s.isValid()).isFalse();
        then(sec.isValid()).isFalse();
        then(f.isValid()).isFalse();
        then(s.getErrorMessages().size()).isEqualTo(1);

        s.userInputProperty().setValue("testing this properly");

        then(s.isValid()).isTrue();
        then(sec.isValid()).isTrue();
        then(f.isValid()).isTrue();
        then(s.getErrorMessages()).isEmpty();
    }

    @Test
    public void persistableTest() {
        StringField s = Field.ofStringType("test");
        DoubleField d = Field.ofDoubleType(2.0);
        IntegerField i = Field.ofIntegerType(10);

        Form f = Form.of(Group.of(i), Section.of(s), Section.of(d));

        s.userInputProperty().setValue("testttt");

        then(s.hasChanged()).isTrue();
        then(f.hasChanged()).isTrue();
        then(f.isPersistable()).isTrue();

        s.reset();

        then(s.hasChanged()).isFalse();
        then(f.hasChanged()).isFalse();
        then(f.isPersistable()).isFalse();

        i.userInputProperty().setValue("testttt");

        then(i.hasChanged()).isTrue();
        then(f.hasChanged()).isTrue();
        then(f.isPersistable()).isFalse();

        f.persist();

        then(i.hasChanged()).isTrue();
        then(f.hasChanged()).isTrue();
        then(f.isPersistable()).isFalse();

        i.userInputProperty().setValue("50");

        then(f.isPersistable()).isTrue();

        f.reset();

        then(i.hasChanged()).isFalse();
        then(f.hasChanged()).isFalse();
        then(f.isPersistable()).isFalse();

        i.userInputProperty().setValue("50");
        f.persist();

        then(i.hasChanged()).isFalse();
        then(f.hasChanged()).isFalse();
        then(f.isPersistable()).isFalse();
    }

}
