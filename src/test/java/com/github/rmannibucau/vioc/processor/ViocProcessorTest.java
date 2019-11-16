package com.github.rmannibucau.vioc.processor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Test;

public class ViocProcessorTest {
    @Test
    public void simple() {
        assertAbout(javaSource())
                .that(forResource("ViocProcessorTest/Simple.java"))
                .processedWith(new ViocProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(forResource("ViocProcessorTest/Simple$Vioc.java"));
    }
}
