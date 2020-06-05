package io.space.automation.util.reflection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Reflections {

    public static <T extends Annotation> Optional<T> getAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if (Objects.nonNull(clazz.getAnnotation(annotationClass))) {
            return Optional.of(clazz.getAnnotation(annotationClass));
        }
        return Optional.empty();
    }

    public static <T extends Annotation> Optional<T> getAnnotation(Field field, Class<T> annotationClass) {
        if (Objects.nonNull(field.getAnnotation(annotationClass))) {
            return Optional.of(field.getAnnotation(annotationClass));
        }
        return Optional.empty();
    }

    public static List<Field> getFieldsWithAnnotation(Class<?> objectClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(objectClass.getDeclaredFields())
                .filter(field -> Objects.nonNull(field.getDeclaredAnnotation(annotationClass)))
                .collect(Collectors.toList());
    }

    private static Optional<Field> findField(String fieldName, Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst();
    }

    public static Object getFieldValue(String fieldName, Object instance) throws NoSuchFieldException {
        return findField(fieldName, instance.getClass())
                .map(field -> getFieldValue(field, instance))
                .orElseThrow(() -> new NoSuchFieldException("Cannot find field with name " + fieldName));
    }

    public static Object getFieldValue(Field field, Object instance) {
        if (field.canAccess(instance)) {
            try {
                return field.get(instance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field value. How did I get here?", e);
            }
        } else {
            try {
                field.setAccessible(true);
                return field.get(instance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field value", e);
            } finally {
                field.setAccessible(false);
            }
        }
    }

    public static void setFieldValue(String fieldName, Object instance, Object value) {
        findField(fieldName, instance.getClass())
                .ifPresent(field -> setFieldValue(field, instance, value));
    }

    public static void setFieldValue(Field field, Object instance, Object value) {
        if (!field.getType().isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Cannot set value " + value + " to field of type " + field.getType().getName());
        }
        if (field.canAccess(instance)) {
            try {
                field.set(value, instance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field value. How did I get here?", e);
            }
        } else {
            try {
                field.setAccessible(true);
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field value", e);
            } finally {
                field.setAccessible(false);
            }
        }
    }

    public static Object invokeMethod(Method method, Object o, Object... args) {
        try {
            return method.invoke(o, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not invoke method " + method.getName(), e);
        }
    }

    public static <T> T newInstance(Class<T> clazz, Object... constructorParams) {
        Class<?>[] paramTypes = new Class[constructorParams.length];
        for (int i = 0; i < constructorParams.length - 1; i++) {
            paramTypes[i] = constructorParams[i].getClass();
        }
        try {
            Constructor<T> declaredConstructor = clazz.getConstructor(paramTypes);
            return declaredConstructor.newInstance(constructorParams);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Cannot find such constructor", e);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Could not create new instance of " + clazz.getName(), e);
        }
    }
}
