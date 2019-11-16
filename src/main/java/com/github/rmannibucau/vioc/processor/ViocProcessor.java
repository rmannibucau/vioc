package com.github.rmannibucau.vioc.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.github.rmannibucau.vioc.api.Injected;

@SupportedAnnotationTypes({
        "com.github.rmannibucau.vioc.api.Injected"
})
public class ViocProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsWithInjections = roundEnv.getElementsAnnotatedWith(Injected.class);

        // for each class
        elementsWithInjections.stream()
                .map(Element::getEnclosingElement)
                .distinct()
                .forEach(clazz -> {
                    try {
                        final String simpleName = clazz.getSimpleName().toString();
                        final String name = simpleName + "$Vioc";
                        final JavaFileObject sourceFile = processingEnv.getFiler()
                                .createSourceFile(name, clazz);
                        try (final Writer writer = sourceFile.openWriter()) {
                            writer.write(
                                    "import com.github.rmannibucau.vioc.internal.Bean;\n" +
                                    "\n" +
                                    "public class " + name + " implements Bean<" + simpleName + "> {\n" +
                                    "    @Override\n" +
                                    "    public T create(final Container container) {\n" +
                                    "        return null;\n" + // todo
                                    "    }\n\n" +
                                    "    @Override\n" +
                                    "    public void destroy(final T instance) {\n" +
                                    "        // no-op\n" + // todo
                                    "    }\n" +
                                    "}\n");
                        }
                    } catch (final IOException e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), clazz);
                    }
                });
        return false;
    }
}
