/**
 * Copyright (c) 2012 - 2015 Signal Iduna Corporation and others.
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

import org.apache.log4j.Logger;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.BasicJTableCellReader;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.*;
import org.fest.swing.launcher.ApplicationLauncher;
import org.testeditor.fixture.core.interaction.Fixture;
import org.testeditor.fixture.core.interaction.FixtureMethod;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Fixture for communication via socket with swing agent.
 */
public class SwingFixture implements Fixture {
    private static final Logger LOGGER = Logger.getLogger(SwingFixture.class);
    private static Thread thread;
    private Robot robot;
    private FrameFixture window;

    /**
     * @param path
     */
    @FixtureMethod
    public boolean startApplication(final String path) {
        startApplicationThread(path, null, Thread.currentThread().getContextClassLoader());
        return true;
    }

    /**
     * @param path
     * @param args2
     * @param cl
     */
    @FixtureMethod
    public void startApplicationThread(final String path, final String[] args2, ClassLoader cl) {
        thread = new Thread() {

            @Override
            public void run() {
                ApplicationLauncher.application(path).start();
            }
        };
        cl = Thread.currentThread().getContextClassLoader();
        thread.setContextClassLoader(cl);
        thread.start();

        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return frame.isActive() && frame.isFocused();
            }
        }).using(robot);
    }

    /**
     * Stops running AUT.
     */
    @FixtureMethod
    public boolean stopApplication() {
        robot.cleanUp();
        return true;
    }

    /**
     * Search and return the Component with the element list key.
     *
     * @param locator Identifier of the Component
     * @return Found component
     */
    protected Component findComponent(String locator) {
        Component result = null;
        try {
            ComponentFinder finder = window.robot.finder();
            Component component = finder.findByName(locator);
            if (component != null) {
                result = component;
            } else {
                LOGGER.error("The Component with the name " + locator + " could not be found.");
            }
        } catch (Exception e) {
            LOGGER.error("find Component Error: " + e);
        }
        return result;
    }

    /**
     * Insert the Text into a JTextField.
     *
     * @param text    The Text to fill the TextField
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean insertIntoTextField(String locator, String text) {
        try {
            JTextComponentFixture textField = window.textBox(locator);
            textField.enterText(text);
            return true;
        } catch (Exception e) {
            LOGGER.error("insert text into a textField Error: " + e);
            return false;
        }
    }

    /**
     * delete the text form the textField.
     *
     * @param locator Key of the Component in element list
     */
    @FixtureMethod
    public boolean deleteTextField(String locator) {
        try {
            JTextComponentFixture textField = window.textBox(locator);
            textField.deleteText();
            return true;
        } catch (Exception e) {
            LOGGER.error("delete text from textField Error: " + e);
            return false;
        }
    }

    /**
     * Get the Text from the JTextField.
     *
     * @param locator Identifier of the Component
     * @return
     */
    @FixtureMethod
    public String getTextFromTextField(String locator) {
        String result = null;
        try {
            JTextComponentFixture textField = window.textBox(locator);
            result = textField.text();
        } catch (Exception e) {
            LOGGER.error("get text from textField Error: " + e);
        }
        return result;
    }

    /**
     * Click the button with the name.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean clickButton(String locator) {
        try {
            JButtonFixture button = window.button(locator);
            button.click();
            return true;
        } catch (Exception e) {
            LOGGER.error("click Button Error: " + e);
            return false;
        }
    }

    /**
     * select the item by the text of the comboBox item.
     *
     * @param locator Identifier of the Component
     * @param item    name of the item
     */
    @FixtureMethod
    public boolean selectComboBoxItemByName(String locator, String item) {
        try {
            JComboBoxFixture comboBoxFixture = window.comboBox(locator);
            comboBoxFixture.selectItem(item);
            return true;
        } catch (ComponentLookupException e) {
            LOGGER.error("No or more then one comboBox found Error: " + e);
            return false;
        } catch (Exception e) {
            LOGGER.error("could not select the item in comboBox Error: " + e);
            return false;
        }
    }

    /**
     * select the item by the index of the comboBox item.
     *
     * @param locator Identifier of the Component
     * @param index   index of the item
     */
    @FixtureMethod
    public boolean selectComboBoxItemById(String locator, int index) {
        try {
            JComboBoxFixture comboBoxFixture = window.comboBox(locator);
            comboBoxFixture.selectItem(index);
            return true;
        } catch (ComponentLookupException e) {
            LOGGER.error("No or more then one comboBox found Error: " + e);
            return false;
        } catch (Exception e) {
            LOGGER.error("could not select the item in comboBox Error: " + e);
            return false;
        }
    }

    /**
     * clears the selection from the ComboBox.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean clearSelectionComboBox(String locator) {
        try {
            JComboBoxFixture comboBox = window.comboBox(locator);
            comboBox.clearSelection();
            return true;
        } catch (ComponentLookupException e) {
            LOGGER.error("No or more then one comboBox found Error: " + e);
            return false;
        } catch (Exception e) {
            LOGGER.error("could not clear the selection in comboBox Error: " + e);
            return false;
        }
    }

    /**
     * Returns the text of the selected item in the ComboBox.
     *
     * @param locator Identifier of the Component
     * @return text of the selected Item
     */
    @FixtureMethod
    public String getSelectedComboBoxItemText(String locator) {
        String result = null;
        try {
            JComboBoxFixture comboBox = window.comboBox(locator);
            result = comboBox.valueAt(comboBox.target.getSelectedIndex());
        } catch (ComponentLookupException e) {
            LOGGER.error("No or more then one comboBox found Error: " + e);
        } catch (Exception e) {
            LOGGER.error("could get the text of the selected item Error: " + e);
        }
        return result;
    }

    /**
     * Returns the id of the selected item in the ComboBox.
     *
     * @param locator Identifier of the Component
     * @return id of the selected Item
     */
    @FixtureMethod
    public int getSelectedComboBoxItemId(String locator) {
        int result = -2;
        try {
            JComboBoxFixture comboBox = window.comboBox(locator);
            result = comboBox.target.getSelectedIndex();
        } catch (ComponentLookupException e) {
            LOGGER.error("No or more then one comboBox found Error: " + e);
        } catch (Exception e) {
            LOGGER.error("could get the id of the selected item Error: " + e);
        }
        return result;
    }

    /**
     * Check the radioButton.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean checkRadioButton(String locator) {
        LOGGER.debug("locator: " + locator);
        try {
            JRadioButtonFixture radioButton = window.radioButton(locator);
            radioButton.check();
            return true;
        } catch (Exception e) {
            LOGGER.error("could not check the radioButton Error: " + e);
            return false;
        }
    }

    /**
     * Uncheck Radiobutton
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean uncheckRadioButton(String locator) {
        LOGGER.debug("locator: " + locator);
        try {
            JRadioButtonFixture radioButton = window.radioButton(locator);
            radioButton.uncheck();
            return true;
        } catch (Exception e) {
            LOGGER.error("could not check the radioButton Error: " + e);
            return false;
        }
    }

    /**
     * Check the radioButton.
     *
     * @param locator Identifier of the Component
     * @return boolean State of the radioButton
     */
    @FixtureMethod
    public boolean isCheckedRadioButton(String locator) {
        boolean result = false;
        try {
            JRadioButtonFixture radioButton = window.radioButton(locator);
            result = radioButton.target.isSelected();
        } catch (Exception e) {
            LOGGER.error("could not get the State of the radioButton Error: " + e);
        }
        return result;
    }

    /**
     * check the checkBox.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean checkCheckBox(String locator) {
        try {
            JCheckBoxFixture checkBox = window.checkBox(locator);
            checkBox.check();
            return true;
        } catch (Exception e) {
            LOGGER.error("could not check the checkBox Error: " + e);
            return false;
        }
    }

    /**
     * uncheck the checkBox.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean uncheckCheckBox(String locator) {
        try {
            JCheckBoxFixture checkBox = window.checkBox(locator);
            checkBox.uncheck();
            return true;
        } catch (Exception e) {
            LOGGER.error("could not uncheck the checkBox Error: " + e);
            return false;
        }
    }

    /**
     * returns the state of the checkBox.
     *
     * @param locator Identifier of the Component
     * @return boolean state of the checkBox
     */
    @FixtureMethod
    public boolean isCheckedCheckBox(String locator) {
        boolean result = false;
        try {
            JCheckBoxFixture checkBox = window.checkBox(locator);
            result = checkBox.target.isSelected();
        } catch (Exception e) {
            LOGGER.error("could get the State of the checkBox Error: " + e);
        }
        return result;
    }

    /**
     * select the Row with the index from the table.
     *
     * @param locator Identifier of the Component
     * @param Id      index from the Row
     */
    @FixtureMethod
    public boolean selectTableRowById(String locator, int Id) {
        try {
            JTableFixture table = window.table(locator);
            if (Id <= table.rowCount()) {
                table.selectRows(Id);
                return true;
            } else {
                LOGGER.error("Id was not in range from the tabel row count.");
                return false;
            }

        } catch (Exception e) {
            LOGGER.error("could not select the Row from the tabel Error: " + e);
            return false;
        }
    }

    /**
     * Compares table entry with given value
     *
     * @param locator Identifier of the Component
     * @param value
     * @param column  Identifier of the Component
     * @return if true or false
     */
    @FixtureMethod
    public boolean checkTableCellValue(String locator, String value, String column) {
        int colLocator = Integer.parseInt(column);
        String content = null;
        try {
            JTableFixture table = window.table(locator);

            BasicJTableCellReader cellReader = new BasicJTableCellReader();
            content = cellReader.valueAt(table.target, (table.rowCount() - 1), colLocator);

        } catch (Exception e) {
            LOGGER.error("could not select the Row from the tabel Error: " + e);
            return false;
        }
        return (value.equals(content));
    }

    /**
     * Double click the row of the table.
     *
     * @param locator Identifier of the Component
     * @param Id      index from the Row
     */
    @FixtureMethod
    public boolean doubleClickTableRowById(String locator, int Id) {
        try {
            JTableFixture table = window.table(locator);
            selectTableRowById(locator, Id);
            table.doubleClick();
            return true;
        } catch (Exception e) {
            LOGGER.error("could not double click the tabel Error: " + e);
            return false;
        }
    }

    /**
     * returns the index from the selected row from the table.
     *
     * @param locator Identifier of the Component
     * @return int Index of the selection
     */
    @FixtureMethod
    public int getSelectedTableRowIndex(String locator) {
        int result = -2;
        try {
            JTableFixture table = window.table(locator);
            result = table.target.getSelectedRow();
        } catch (Exception e) {
            LOGGER.error("could not get the selected index the tabel Error: " + e);
        }
        return result;
    }

    /**
     * double click the component with the name.
     *
     * @param locator Identifier of the Component
     */
    @FixtureMethod
    public boolean doubleClickComponent(String locator) {
        try {
            JComponent component = (JComponent) findComponent(locator);
            window.robot.doubleClick(component);
            return true;
        } catch (Exception e) {
            LOGGER.error("could not doubleClick the component Error: " + e);
            return false;
        }
    }

    /**
     * wait milli seconds
     *
     * @param milliSeconds milliseconds to wait
     */
    @FixtureMethod
    public boolean waitMilliSeconds(int milliSeconds) {
        try {
            if (milliSeconds > 0) {
                window.wait(milliSeconds);

            }
            return true;
        } catch (Exception e) {
            LOGGER.error("could not wait/timeout: " + e);
            return false;
        }
    }

    /**
     * returns true when element is enabled, false otherwise.
     *
     * @param locator Identifier of the Component
     * @return
     */
    @FixtureMethod
    public boolean isElementEnabled(String locator) {
        boolean result = false;
        try {
            JComponent component = (JComponent) findComponent(locator);
            result = component.isEnabled();
        } catch (Exception e) {
            LOGGER.error("could not find out state, Error: " + e);
        }
        return result;
    }

    /**
     * returns true when the compared texts are identical
     *
     * @param locator Identifier of the Component
     * @param text    text to compare
     * @return boolean Status of consent
     */
    @FixtureMethod
    public boolean checkIfTextEquals(String locator, String text) {
        return (text.equals(getTextFromTextField(locator)));
    }

    /**
     * returns true when the compared texts are not identical
     *
     * @param locator Identifier of the Component
     * @param text    text to compare
     * @return boolean Status of non-compliance
     */
    @FixtureMethod
    public boolean checkIfTextNotEquals(String locator, String text) {
        return !(text.equals(getTextFromTextField(locator)));
    }

    /**
     * returns true when the compared texts are identical
     *
     * @param locator Identifier of the Component
     * @param text    text to compare
     * @return boolean Status of consent
     */
    @FixtureMethod
    public boolean checkIfSelectedItemIs(String locator, String text) {
        LOGGER.debug("ComboBox: " + getSelectedComboBoxItemText(locator));
        LOGGER.debug("Vorgegebener Text: " + text);
        return (text.equals(getSelectedComboBoxItemText(locator)));
    }

    /**
     * returns true when the compared texts are not identical
     *
     * @param locator Identifier of the Component
     * @param text    text to compare
     * @return boolean Status of non-compliance
     */
    @FixtureMethod
    public boolean checkIfSelectedItemIsNot(String locator, String text) {
        return !(text.equals(getSelectedComboBoxItemText(locator)));
    }

    @Override
    public String getTestName() {
        return null;
    }

    @Override
    public void postInvoke(Method arg0, Object arg1, Object... arg2) throws InvocationTargetException,
        IllegalAccessException {
    }

    @Override
    public void preInvoke(Method arg0, Object arg1, Object... arg2) throws InvocationTargetException,
        IllegalAccessException {
    }

    @Override
    public void setTestName(String arg0) {

    }
}
