/*
 * generated by Xtext
 */
package fr.uns.i3s.rainbow.captah.dsl.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import fr.uns.i3s.rainbow.captah.dsl.ui.internal.CaPtahActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class CaPtahExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return CaPtahActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return CaPtahActivator.getInstance().getInjector(CaPtahActivator.FR_UNS_I3S_RAINBOW_CAPTAH_DSL_CAPTAH);
	}
	
}
