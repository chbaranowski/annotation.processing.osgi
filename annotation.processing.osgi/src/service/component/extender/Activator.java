package service.component.extender;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	ComponentByteCodeAnnotationScanner scanner;
	
	@Override
	public void start(BundleContext context) throws Exception {
		scanner = new ComponentByteCodeAnnotationScanner(context);
		scanner.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		scanner.close();
		scanner = null;
	}

}
