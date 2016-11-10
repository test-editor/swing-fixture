/*******************************************************************************
 * Copyright (c) 2012, 2013 Signal Iduna Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Signal Iduna Corporation - initial API and implementation
 * akquinet AG
 *******************************************************************************/
package org.testeditor.fixture.swing;

import fitnesse.junit.JUnitHelper;

public class FitNesseTest {
	public static void main(String[] args) {
		System.setProperty("APPLICATION_WORK", "/Users/delph/.testeditor");
		JUnitHelper jUnitHelper = new JUnitHelper("/Users/delph/.testeditor/DemoSwingTests",
				System.getProperty("java.io.tmpdir"));
		jUnitHelper.setPort(8064);
		try {
			jUnitHelper.assertTestPasses("DemoSwingTests.StartStopSuite.AnlegenTest");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
