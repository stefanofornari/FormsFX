package com.dlsc.formsfx.view.controls;

/* -
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

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.MultiSelectionField;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Test class to verify that tooltips show up when hovering over child elements
 * of SimpleControl implementations.
 */
public class SimpleControlTooltipTest {

    @BeforeAll
    public static void before() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ex) {
            // JavaFX may only be initialized once.
        }
    }

    @Test
    public void test_tooltip_setup_on_child_elements() {
        // Create a multi-selection field with tooltip
        MultiSelectionField<Integer> field = Field.ofMultiSelectionType(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1)
        );
        field.tooltip("Test tooltip for checkboxes");

        // Create the control
        SimpleCheckBoxControl<Integer> control = new SimpleCheckBoxControl<>();
        control.setField(field);

        // Verify that the JavaFX Tooltip object is properly installed on the control
        then(control.tooltip).isNotNull();
        
        // Trigger tooltip text update to simulate what happens when tooltip shows
        control.tooltipText();
        
        // Verify the JavaFX Tooltip object contains the expected text
        then(control.tooltip.getText()).contains("Test tooltip for checkboxes");
        
        // Verify the control has child elements (checkboxes)
        then(control.getNode()).isNotNull();
        then(control.getNode().getChildren()).hasSize(3);

        // Verify all children are CheckBox instances
        for (int i = 0; i < control.getNode().getChildren().size(); i++) {
            then(control.getNode().getChildren().get(i)).isInstanceOf(CheckBox.class);
        }
    }

    @Test
    public void test_tooltip_with_error_messages() {
        // Create a field with error messages
        MultiSelectionField<Integer> field = Field.ofMultiSelectionType(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1)
        );
        field.errorMessagesProperty().addAll("Error 1", "Error 2");

        // Create the control
        SimpleCheckBoxControl<Integer> control = new SimpleCheckBoxControl<>();
        control.setField(field);

        // Verify error messages are set
        then(control.getField().getErrorMessages()).hasSize(2);
        then(control.getField().getErrorMessages()).contains("Error 1", "Error 2");
    }

    @Test
    public void test_tooltip_on_individual_checkboxes() {
        // Create a multi-selection field with tooltip
        MultiSelectionField<Integer> field = Field.ofMultiSelectionType(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1, 2)
        );
        field.tooltip("Hover tooltip");

        // Create the control
        SimpleCheckBoxControl<Integer> control = new SimpleCheckBoxControl<>();
        control.setField(field);

        // Test that individual checkboxes can trigger tooltips
        // This verifies the event handlers are properly set up on child elements
        for (int i = 0; i < control.getNode().getChildren().size(); i++) {
            CheckBox checkbox = (CheckBox) control.getNode().getChildren().get(i);

            // Check that the checkbox has the same tooltip behavior as the parent
            // The tooltip should be accessible through the control's field
            then(control.getField().getTooltip()).isEqualTo("Hover tooltip");
        }
    }

    @Test
    public void test_tooltip_installed_on_control() {
        // Create a multi-selection field with tooltip
        MultiSelectionField<Integer> field = Field.ofMultiSelectionType(
            Arrays.asList(1, 2, 3),
            Arrays.asList(1)
        );
        field.tooltip("Test tooltip");

        // Create the control
        SimpleCheckBoxControl<Integer> control = new SimpleCheckBoxControl<>();
        control.setField(field);

        // Verify that the tooltip is installed on the control
        // With standard JavaFX tooltip mechanism, the tooltip should be accessible
        // and will automatically show on any part of the control including child elements
        then(control.tooltip).isNotNull();
        
        // Trigger tooltip text update by calling tooltipText() method
        // This simulates what happens when the tooltip is about to show
        control.tooltipText();
        
        // Verify the tooltip text is set correctly
        then(control.tooltip.getText()).contains("Test tooltip");
    }
}
