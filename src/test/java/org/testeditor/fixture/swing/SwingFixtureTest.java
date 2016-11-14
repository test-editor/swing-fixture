/**
 * Copyright (c) 2012, 2013 Signal Iduna Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p/>
 * Contributors:
 * Signal Iduna Corporation - initial API and implementation
 * akquinet AG
 */
/**
 *
 */
package org.testeditor.fixture.swing;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

//import de.akquinet.birthday.controller.EmployeeController;

public class SwingFixtureTest {

    String TEXTFIELD_NAME = "textFieldName";
    String TEXTFIELD_VORNAME = "textFieldVorname";
    String COMBOBOX_GJ_NAME = "comboBoxGeburtstagJahr";
    String COMBOBOX_GM_NAME = "comboBoxGeburtstagMonat";
    String COMBOBOX_GT_NAME = "comboBoxGeburtstagTag";
    String RADIOBUTTON_MALENAME = "radioButtonMale";
    String RADIOBUTTON_FEMALENAME = "radioButtonFemale";
    String CHECKBOX_HIDEGENDER_NAME = "checkBoxGeschlecht";
    String TABLE_NAME = "employeeTable";
    String BTN_NEW = "buttonNew";
    String BTN_HINZUFUEGEN = "buttonAdd";
    String BTN_LOESCHEN = "buttonDelete";
    String BTN_SPEICHERN = "buttonSave";

    private static SwingFixture swingFixture;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        swingFixture = new SwingFixture();
        swingFixture.startApplication("de.akquinet.birthday.controller.EmployeeController");
    }

    @After
    public void tearDown() {
        swingFixture.stopApplication();
    }


    @Test
    public void textTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        assertEquals("Mustermann", swingFixture.getTextFromTextField(TEXTFIELD_NAME));
        swingFixture.deleteTextField(TEXTFIELD_NAME);
        assertEquals("", swingFixture.getTextFromTextField(TEXTFIELD_NAME));
        assertEquals("Max", swingFixture.getTextFromTextField(TEXTFIELD_VORNAME));
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
    }


    @Test
    public void comboBoxTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GT_NAME, "20");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GM_NAME, "11");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GJ_NAME, "1978");
        assertEquals("20", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GT_NAME));
        assertEquals("11", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GM_NAME));
        assertEquals("1978", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GJ_NAME));
    }


    @Test
    public void checkBoxTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GT_NAME, "20");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GM_NAME, "11");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GJ_NAME, "1978");
        assertTrue(swingFixture.isCheckedCheckBox(CHECKBOX_HIDEGENDER_NAME));
        assertFalse(swingFixture.isElementEnabled(RADIOBUTTON_MALENAME));
        assertFalse(swingFixture.isElementEnabled(RADIOBUTTON_FEMALENAME));
        swingFixture.uncheckCheckBox(CHECKBOX_HIDEGENDER_NAME);
        assertFalse(swingFixture.isCheckedCheckBox(CHECKBOX_HIDEGENDER_NAME));
        assertTrue(swingFixture.isElementEnabled(RADIOBUTTON_MALENAME));
        assertTrue(swingFixture.isElementEnabled(RADIOBUTTON_FEMALENAME));
        swingFixture.checkCheckBox(CHECKBOX_HIDEGENDER_NAME);
        assertTrue(swingFixture.isCheckedCheckBox(CHECKBOX_HIDEGENDER_NAME));
        assertFalse(swingFixture.isElementEnabled(RADIOBUTTON_MALENAME));
        assertFalse(swingFixture.isElementEnabled(RADIOBUTTON_FEMALENAME));
        swingFixture.uncheckCheckBox(CHECKBOX_HIDEGENDER_NAME);
    }


    @Test
    public void radioButtonTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GT_NAME, "20");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GM_NAME, "11");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GJ_NAME, "1978");
        swingFixture.uncheckCheckBox(CHECKBOX_HIDEGENDER_NAME);
        assertFalse(swingFixture.isCheckedRadioButton(RADIOBUTTON_MALENAME));
        swingFixture.checkRadioButton(RADIOBUTTON_MALENAME);
        assertTrue(swingFixture.isCheckedRadioButton(RADIOBUTTON_MALENAME));
    }


    @Test
    public void buttonTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GT_NAME, "20");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GM_NAME, "11");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GJ_NAME, "1978");
        swingFixture.uncheckCheckBox(CHECKBOX_HIDEGENDER_NAME);
        swingFixture.checkRadioButton(RADIOBUTTON_MALENAME);
        swingFixture.clickButton(BTN_HINZUFUEGEN);
        assertEquals("", swingFixture.getTextFromTextField(TEXTFIELD_NAME));
    }

    public void tableTest() {
        swingFixture.insertIntoTextField(TEXTFIELD_NAME, "Mustermann");
        swingFixture.insertIntoTextField(TEXTFIELD_VORNAME, "Max");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GT_NAME, "20");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GM_NAME, "11");
        swingFixture.selectComboBoxItemByName(COMBOBOX_GJ_NAME, "1978");
        swingFixture.uncheckCheckBox(CHECKBOX_HIDEGENDER_NAME);
        swingFixture.checkRadioButton(RADIOBUTTON_MALENAME);
        swingFixture.clickButton(BTN_HINZUFUEGEN);
        swingFixture.selectTableRowById(TABLE_NAME, 2);
        assertEquals(2, swingFixture.getSelectedTableRowIndex(TABLE_NAME));
        assertEquals("Mustermann", swingFixture.getTextFromTextField(TEXTFIELD_NAME));
        assertEquals("Max", swingFixture.getTextFromTextField(TEXTFIELD_VORNAME));
        assertEquals("1978", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GJ_NAME));
        assertEquals("11", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GM_NAME));
        assertEquals("20", swingFixture.getSelectedComboBoxItemText(COMBOBOX_GT_NAME));
        assertFalse(swingFixture.isCheckedCheckBox(CHECKBOX_HIDEGENDER_NAME));
        assertTrue(swingFixture.isElementEnabled(RADIOBUTTON_MALENAME));
        assertTrue(swingFixture.isElementEnabled(RADIOBUTTON_FEMALENAME));
        assertTrue(swingFixture.isCheckedRadioButton(RADIOBUTTON_MALENAME));
    }

}
