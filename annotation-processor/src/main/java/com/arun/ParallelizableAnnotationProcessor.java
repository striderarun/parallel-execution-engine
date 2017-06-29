package com.arun;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DisplayTool;


/**
 * Annotation processor for Parallelizable annotation type
 *
 *
 */
@SupportedAnnotationTypes("com.arun.Parallelizable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParallelizableAnnotationProcessor extends AbstractProcessor {

	/**
	 * Default constructor.
	 */
	public ParallelizableAnnotationProcessor() {

		super();
	}

	/**
	 * Reads the Parallelizable annotation and constructs a TypeModel from it
	 *
	 * @param annotations
	 * @param roundEnv
	 * @return
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (annotations.isEmpty()) {
			return true;
		}
		try {
			Map<TypeModel, Set<String>> methodModel = new HashMap<>();
			for (Element e : roundEnv.getElementsAnnotatedWith(Parallelizable.class)) {
				ExecutableElement exeElement = (ExecutableElement) e;
				TypeElement classElement = (TypeElement) exeElement.getEnclosingElement();
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
				
				TypeModel model = new TypeModel();
				model.className = classElement.getSimpleName().toString() + "_";
				model.packageName = packageElement.getQualifiedName().toString();
				
				if (methodModel.get(model) != null) {
					Set<String> methods = methodModel.get(model);
					methods.add(exeElement.getSimpleName().toString());
				} else {
					Set<String> methods = new HashSet<>();
					methods.add(exeElement.getSimpleName().toString());
					methodModel.put(model, methods);
				}
			}

			Properties props = new Properties();
			URL url = this.getClass().getClassLoader().getResource("velocity.properties");
			props.load(url.openStream());

			VelocityEngine ve = new VelocityEngine(props);
			ve.init();

			for(Entry<TypeModel, Set<String>> entry: methodModel.entrySet()) {
				TypeModel model = entry.getKey();
				model.setMethods(new ArrayList<>(entry.getValue()));
				VelocityContext vc = new VelocityContext();
				vc.put("model", model);

				// adding DisplayTool from Velocity Tools library
				vc.put("display", new DisplayTool());

				Template vt = ve.getTemplate("beaninfo.vm");
				JavaFileObject jfo = processingEnv.getFiler().createSourceFile(model.getClassName());
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating source file: " + jfo.toUri());
				Writer writer = jfo.openWriter();
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"applying velocity template: " + vt.getName());
				vt.merge(vc, writer);
				writer.close();
			}
		} catch (ResourceNotFoundException rnfe) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, rnfe.getLocalizedMessage());
		} catch (ParseErrorException pee) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, pee.getLocalizedMessage());
		} catch (IOException ioe) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ioe.getLocalizedMessage());
		} catch (Exception e) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage());
		}

		return true;
	}
}
