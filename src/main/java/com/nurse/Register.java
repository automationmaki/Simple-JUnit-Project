package com.nurse;

import com.nurse.Inject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Register {

    Map<String, Object> register = new HashMap<String, Object>();
    Map<Field, Object> fieldToInject = new HashMap<>();


    public Object get (String name) {
        Object something = register.get(name);
        Objects.requireNonNull(something);
        return something;
    }

    void add(String name, Object something) {
        if (register.containsKey(name)){
            throw new RuntimeException();
        }
        for (Field field: something.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Inject.class)){
                fieldToInject.put(field, something);

            }
        }
        register.put(name, something);

    }

    public void add(Object something) {
        add(something.getClass().getName(), something);
    }

    public <T> T  get(Class<T> type) {
        return (T) get(type.getName());
    }


    void inject() {
        for (Field field: fieldToInject.keySet()){
            Object something = fieldToInject.get(field);
            Object injection = this.get(field.getType());
            field.setAccessible(true);
            try {
                field.set(something, injection);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
