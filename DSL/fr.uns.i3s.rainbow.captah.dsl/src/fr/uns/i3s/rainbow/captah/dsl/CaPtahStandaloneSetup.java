
package fr.uns.i3s.rainbow.captah.dsl;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResourceSet;

import captah.CaptahFactory;
import captah.impl.CaptahPackageImpl;

import com.google.inject.Injector;
/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class CaPtahStandaloneSetup extends CaPtahStandaloneSetupGenerated{

	public final static String OUTPUT_PATH = "ptah.xmi";
	public final static String INPUT_PATH = "test.ptah";
	
	public static void generate() {
	
		URI uri = null;
		try {
			// GENERATION FILE
			File file = new File(INPUT_PATH);

			// GETTING RESOURCE
			Injector injector = new CaPtahStandaloneSetup().createInjectorAndDoEMFRegistration();
			XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
			uri = URI.createFileURI(file.getAbsolutePath());

			// LOADED RESOURCE
			Resource resource = resourceSet.getResource(uri, true);

			// Creating the xmi file
			EcoreUtil.resolveAll(resource);

			Resource xmiResource = resourceSet.createResource(URI.createURI(OUTPUT_PATH));
			xmiResource.getContents().add(resource.getContents().get(0));
			try {
				
				xmiResource.save(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (RuntimeException exception) {
			System.out.println("Problem loading " + uri);
			exception.printStackTrace();
		}
		System.out.println("saved XMI");
	}
	
	public Injector createInjectorAndDoEMFRegistration() {
		CaptahPackageImpl.init();
		org.eclipse.xtext.common.TerminalsStandaloneSetup.doSetup();

		// register default ePackages
		if (!Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.containsKey("ecore"))
			Resource.Factory.Registry.INSTANCE
					.getExtensionToFactoryMap()
					.put("ecore",
							new org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl());
		if (!Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
				.containsKey("xmi"))
			Resource.Factory.Registry.INSTANCE
					.getExtensionToFactoryMap()
					.put("xmi",
							new org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl());
		if (!EPackage.Registry.INSTANCE
				.containsKey(org.eclipse.xtext.XtextPackage.eNS_URI))
			EPackage.Registry.INSTANCE.put(
					org.eclipse.xtext.XtextPackage.eNS_URI,
					org.eclipse.xtext.XtextPackage.eINSTANCE);

		Injector injector = createInjector();
		register(injector);
		return injector;
	}
	
	public void register(Injector injector) {

		if (!EPackage.Registry.INSTANCE.containsKey("TargetmodelFactory")) {
			EPackage.Registry.INSTANCE.put("TargetmodelFactory", CaptahFactory.eINSTANCE);
		}

		// Copied from NatisStandaloneSetupGenerated:
		org.eclipse.xtext.resource.IResourceFactory resourceFactory = injector
				.getInstance(org.eclipse.xtext.resource.IResourceFactory.class);
		org.eclipse.xtext.resource.IResourceServiceProvider serviceProvider = injector
				.getInstance(org.eclipse.xtext.resource.IResourceServiceProvider.class);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ptah", resourceFactory);
		org.eclipse.xtext.resource.IResourceServiceProvider.Registry.INSTANCE
				.getExtensionToFactoryMap().put("ptah", serviceProvider);

	}
	
	public static void doSetup() {
		new CaPtahStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

