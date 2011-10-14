package service.component.extender;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.MemberValue;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class ComponentByteCodeAnnotationScanner implements BundleTrackerCustomizer {

	final BundleTracker bundleTracker;
	
	final BundleContext bundleContext;

	public ComponentByteCodeAnnotationScanner(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		this.bundleTracker = new BundleTracker(bundleContext, Bundle.STARTING, this);
	}
	
	@Override
	public Object addingBundle(Bundle bundle, BundleEvent event) {
		String serviceComponentClasses = (String) bundle.getHeaders().get("Service-Component-Classes");
		if(serviceComponentClasses != null){
			
			// TODO : Add regex for component classes parsing
			//        The demo code only works for one component class in MANIFEST.MF 
			// TODO: Add loop for classes
						
			System.out.println("Found a Bundle with a Service Component Classes : " + serviceComponentClasses);
			String fileName = "/" +serviceComponentClasses.replace('.', '/') + ".class";
			
			// Scan the component class via byte code reading (javassist library is used)
			// more details about javassist see http://www.jboss.org/javassist/
			URL componentClassUrl = bundle.getResource(fileName);
			try {
				InputStream componentClassInputStream = componentClassUrl.openStream();
				DataInputStream dstream = new DataInputStream(componentClassInputStream);
				ClassFile cf =  new ClassFile(dstream);
				
				// DS Component name is the class name
				String componentName = cf.getName();
				System.out.println("DS Component Name is : " + componentName);
				
				// DS component properties read component annotation 
				AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
				Annotation[] annotations = annotationsAttribute.getAnnotations();
				for (Annotation annotation : annotations) {
					System.out.println(annotation);
					if(annotation.getTypeName().endsWith("service.component.annotations.Component")){
						MemberValue propertiesValue = annotation.getMemberValue("properties");
						final List<String> properties = new ArrayList<String>();
						propertiesValue.accept(new PropertiesMemberValueVisitor(properties));
						System.out.println("The DS component has the following properties:");
						for (String property : properties) {
							System.out.println(property);
						}
					}
				}
				
				// DS find activate Method
				List<MethodInfo> methods = cf.getMethods();
				for (MethodInfo methodInfo : methods) {
					AnnotationsAttribute methodAnnotationAttribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
					if(methodAnnotationAttribute != null){
						Annotation[] methodAnnotations = methodAnnotationAttribute.getAnnotations();
						if(methodAnnotations != null){
							for (Annotation methodAnnotation : methodAnnotations) {
								if(methodAnnotation.getTypeName().equals("service.component.annotations.Activate")){
									System.out.println("DS activate Method name is : " + methodInfo.getName());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error Bytecode Annotation parsing.");
				e.printStackTrace();
			}
			
			return bundle;
		}
		return null;
	}

	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
	}
	
	public void close() {
		bundleTracker.close();
	}

	public void open() {
		bundleTracker.open();
	}

}
