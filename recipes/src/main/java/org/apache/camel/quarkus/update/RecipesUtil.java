package org.apache.camel.quarkus.update;

import org.openrewrite.java.tree.Comment;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JContainer;
import org.openrewrite.java.tree.JRightPadded;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.TextComment;
import org.openrewrite.marker.Markers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.openrewrite.Tree.randomId;

public class RecipesUtil {

    static J.Annotation createAnnotation(J.Annotation annotation, String name, Function<String, Boolean> argMatcher, String args) {

        LinkedList<Expression> originalArguments = annotation.getArguments() == null ? new LinkedList<>() : new LinkedList(annotation.getArguments());

        String newArgName = args.replaceAll("=.*", "").trim();

        //remove argument with the same name as the new one
        if(argMatcher == null) {
            originalArguments.add(new J.Empty(randomId(), Space.format(args), Markers.EMPTY));
        } else {
            for (ListIterator<Expression> iter = originalArguments.listIterator(); iter.hasNext(); ) {
                Expression expr = iter.next();
                if (argMatcher.apply(expr.toString().replaceAll("\\s", ""))) {
                    iter.set(new J.Empty(randomId(), Space.format(args), Markers.EMPTY));
                }
            }
        }

        //construct arguments for the new annotation
        List<JRightPadded<Expression>> newArgs = new LinkedList<>();
        for(Expression e: originalArguments) {
            newArgs.add(new JRightPadded(e, Space.EMPTY, Markers.EMPTY));
        }

        J.Identifier newAnnotationIdentifier =  new J.Identifier(randomId(), annotation.getPrefix(), Markers.EMPTY, name,
                JavaType.ShallowClass.build("java.lang.Object"), null);
        JContainer<Expression> arguments = JContainer.build(
                Space.EMPTY,
                newArgs,
                Markers.EMPTY);
        return new J.Annotation(UUID.randomUUID(), annotation.getPrefix(), Markers.EMPTY,
                newAnnotationIdentifier, arguments);
    }

    static Optional<String> getValueOfArgs(List<Expression> expressions, String parameter) {
       return expressions.stream()
               .filter(e -> e.toString().replaceAll("\\s", "").startsWith(parameter + "="))
               .map(e -> e.toString().replaceAll("\\s", "").replaceFirst(parameter + "=", ""))
               .findFirst();

//       ;
//            if (trimmedName.equals(parameter + "=")) {
//                return trimmedName.replaceFirst(parameter + "=", "");
//            }
//        }        for(Expression expression : expressions) {
//            String trimmedName = expression.toString().replaceAll("\\s", "");
//            if (trimmedName.equals(parameter + "=")) {
//                return trimmedName.replaceFirst(parameter + "=", "");
//            }
//        }
//
//        return null;
    }

    static Comment createComment(String text) {
        return new TextComment(false, text, null, Markers.EMPTY);
    }
}
