package org.apache.camel.quarkus.update;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.AddImport;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.MethodMatcher;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JContainer;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.openrewrite.Tree.randomId;

public class CamelAPIsRecipe extends Recipe {
    private static final MethodMatcher MATCHER_CONTEXT_GET_ENDPOINT_MAP =
            new MethodMatcher("org.apache.camel.CamelContext getEndpointMap()");

    @Override
    public String getDisplayName() {
        return "Camel API changes.";
    }

    @Override
    public String getDescription() {
        return "Camel API changes";
    }

    @Override
    protected TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<>() {

            @Override
            public J.FieldAccess visitFieldAccess(J.FieldAccess fieldAccess, ExecutionContext executionContext) {
                J.FieldAccess fa =  super.visitFieldAccess(fieldAccess, executionContext);
                //The org.apache.camel.ExchangePattern has removed InOptionalOut.
                if("InOptionalOut".equals(fieldAccess.getSimpleName()) && fieldAccess.getType().isAssignableFrom(Pattern.compile("org.apache.camel.ExchangePattern"))) {
                    return fa.withName(new J.Identifier(UUID.randomUUID(), fa.getPrefix(), Markers.EMPTY, "/* " + fa.getSimpleName() + " has been removed */", fa.getType(), null));
                }


                return fa;
            }

            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation method, ExecutionContext executionContext) {
                J.MethodInvocation mi = super.visitMethodInvocation(method, executionContext);

                // context.getExtension(ExtendedCamelContext.class).getComponentNameResolver() -> PluginHelper.getComponentNameResolver(context)
                if (MATCHER_CONTEXT_GET_ENDPOINT_MAP.matches(mi)) {
                    return mi.withName(new J.Identifier(UUID.randomUUID(), mi.getPrefix(), Markers.EMPTY,
                            "/* " + mi.getSimpleName() + " has been removed, consider getEndpointRegistry() instead */", mi.getType(), null));
                }

                return mi;
            }

//            @Override
//            public J.MethodDeclaration visitMethodDeclaration(J.MethodDeclaration method, ExecutionContext executionContext) {
//                J.MethodDeclaration md = super.visitMethodDeclaration(method, executionContext);
//
//                for (J.Annotation annotation : md.getLeadingAnnotations()) {
//                    if (annotation.getType().toString().equals("org.apache.camel.FallbackConverter")) {
//                        J.Identifier newAnnotationIdentifier =  new J.Identifier(randomId(), annotation.getPrefix(), Markers.EMPTY, "Converter",
//                                JavaType.ShallowClass.build("java.lang.Object"), null);
//                        JContainer<Expression> args = JContainer.build(
//                                Space.EMPTY,
//                                Collections.singletonList(new JRightPadded(new J.Empty(randomId(), Space.format("fallback = true"), Markers.EMPTY), Space.EMPTY, Markers.EMPTY)),
//                                Markers.EMPTY);
//                        J.Annotation newAnnotation = RecipesUtil.createAnnotation(annotation, "Converter", "fallback = true");
//                        List<J.Annotation> annotations = new LinkedList<>(md.getLeadingAnnotations());
//                        annotations.remove(annotation);
//                        annotations.add(newAnnotation);
//
//                        maybeAddImport("org.apache.camel.Converter", null, false);
//                        maybeRemoveImport("org.apache.camel.FallbackConverter");
//
//                        return md.withLeadingAnnotations(annotations);
//                    }
//                }
//
//                return md;
//            }

//            @Override
//            public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl, ExecutionContext executionContext) {
//                J.ClassDeclaration cd = super.visitClassDeclaration(classDecl, executionContext);
//
//                for (J.Annotation annotation : cd.getLeadingAnnotations()) {
//                    if (annotation.getType().toString().equals("org.apache.camel.FallbackConverter")) {
//                        J.Identifier newAnnotationIdentifier =  new J.Identifier(randomId(), annotation.getPrefix(), Markers.EMPTY, "Converter",
//                                JavaType.ShallowClass.build("java.lang.Object"), null);
//                        JContainer<Expression> args = JContainer.build(
//                                Space.EMPTY,
//                                Collections.singletonList(new JRightPadded(new J.Empty(randomId(), Space.format("fallback = true"), Markers.EMPTY), Space.EMPTY, Markers.EMPTY)),
//                                Markers.EMPTY);
//                        J.Annotation newAnnotation = RecipesUtil.createAnnotation(annotation, "Converter", "fallback = true");
//                        List<J.Annotation> annotations = new LinkedList<>(cd.getLeadingAnnotations());
//                        annotations.remove(annotation);
//                        annotations.add(newAnnotation);
//
//                        maybeAddImport("org.apache.camel.Converter", null, false);
//                        maybeRemoveImport("org.apache.camel.FallbackConverter");
//
//                        return cd.withLeadingAnnotations(annotations);
//                    }
//                }
//
//                return cd;
//            }

            @Override
            public J.Annotation visitAnnotation(J.Annotation annotation, ExecutionContext executionContext) {
                J.Annotation a = super.visitAnnotation(annotation, executionContext);

                if (a.getType().toString().equals("org.apache.camel.FallbackConverter")) {
                    maybeAddImport("org.apache.camel.Converter", null, false);
                    maybeRemoveImport("org.apache.camel.FallbackConverter");

                    return RecipesUtil.createAnnotation(annotation, "Converter", null, "fallback = true");
                }
                else if (a.getType().toString().equals("org.apache.camel.EndpointInject")) {
                   Optional<String> originalValue = RecipesUtil.getValueOfArgs(a.getArguments(), "uri");
                   if(originalValue.isPresent()) {
                       return RecipesUtil.createAnnotation(annotation, "EndpointInject", s -> s.startsWith("uri="), originalValue.get());
                   }
                }
                else if (a.getType().toString().equals("org.apache.camel.Produce")) {
                   Optional<String> originalValue = RecipesUtil.getValueOfArgs(a.getArguments(), "uri");
                   if(originalValue.isPresent()) {
                       return RecipesUtil.createAnnotation(annotation, "Produce", s -> s.startsWith("uri="), originalValue.get());
                   }
                }
                else if (a.getType().toString().equals("org.apache.camel.Consume")) {
                   Optional<String> originalValue = RecipesUtil.getValueOfArgs(a.getArguments(), "uri");
                   if(originalValue.isPresent()) {
                       return RecipesUtil.createAnnotation(annotation, "Consume", s -> s.startsWith("uri="), originalValue.get());
                   }
                }

                return a;

            }
        };
    }
}

