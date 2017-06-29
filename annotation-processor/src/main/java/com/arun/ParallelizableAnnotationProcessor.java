package com.arun;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.VariableElement;
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
			Map<TypeModel, Set<MethodModel>> classModel = new HashMap<>();
			for (Element e : roundEnv.getElementsAnnotatedWith(Parallelizable.class)) {
				ExecutableElement exeElement = (ExecutableElement) e;
				TypeElement classElement = (TypeElement) exeElement.getEnclosingElement();
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

				MethodModel methodModel = new MethodModel();
				methodModel.setName(exeElement.getSimpleName().toString());
				String returnType = exeElement.getReturnType().toString();
				if (returnType.contains("<")) {
					returnType = returnType.substring(0, returnType.indexOf("<"));
				}
				returnType = String.format("%s.class", returnType);
				methodModel.setReturnTypeString(returnType);
				List<String> argTypeNames = new ArrayList<>();
				List<String> argNames = new ArrayList<>();
				List<String> typeParamsList = new ArrayList<>();
				for(VariableElement variableElement : ((ExecutableElement) e).getParameters()) {
					typeParamsList.add(String.format("%s %s", variableElement.asType().toString(), variableElement.getSimpleName().toString()));
					argNames.add(variableElement.getSimpleName().toString());
					if (variableElement.asType().toString().contains("<")) {
						String argType = variableElement.asType().toString();
						argType = argType.substring(0, argType.indexOf("<"));
						argTypeNames.add(String.format("%s.class", argType));
					} else {
						argTypeNames.add(String.format("%s.class", variableElement.asType().toString()));
					}
				}
				methodModel.setArgNames(argNames);
				methodModel.setArgTypeNames(argTypeNames);
				methodModel.setTypeParamsSignature(String.join(",", typeParamsList));

				TypeModel model = new TypeModel();
				model.className = classElement.getSimpleName().toString() + "_";
				model.packageName = packageElement.getQualifiedName().toString();

				if (classModel.get(model) != null) {
					Set<MethodModel> methods = classModel.get(model);
					methods.add(methodModel);
				} else {
					Set<MethodModel> methods = new HashSet<>();
					methods.add(methodModel);
					classModel.put(model, methods);
				}
			}

			Properties props = new Properties();
			URL url = this.getClass().getClassLoader().getResource("velocity.properties");
			props.load(url.openStream());

			VelocityEngine ve = new VelocityEngine(props);
			ve.init();

			for(Entry<TypeModel, Set<MethodModel>> entry: classModel.entrySet()) {
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
