package org.pathref;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SpringParser {
    public static <T> T setProperty(Class<T> clazz, String path, String value) {

        T objectToInitialize = null;
        try {
            objectToInitialize = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        SpelParserConfiguration config = new SpelParserConfiguration(true,true);   // auto create objects if null
        ExpressionParser parser = new SpelExpressionParser(config);
        StandardEvaluationContext modelContext = new StandardEvaluationContext(objectToInitialize);

        parser.parseExpression(path).setValue(modelContext, value);

        return objectToInitialize;
    }

    public static <T> T getProperty(Object object, String path, Class<T> type) {

        SpelParserConfiguration config = new SpelParserConfiguration(true,true);   // auto create objects if null
        ExpressionParser parser = new SpelExpressionParser(config);
        StandardEvaluationContext modelContext = new StandardEvaluationContext(object);

        return type.cast(parser.parseExpression(path).getValue(modelContext));
    }
}
