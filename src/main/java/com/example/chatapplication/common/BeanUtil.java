package com.example.chatapplication.common;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;

public class BeanUtil {
    private static final PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
    private static final ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();

    public BeanUtil() {
    }

    public static void copyProperties(Object orig, Object dest) {
        copyProperties(orig, dest, false);
    }

    public static void copyProperties(Object orig, Object dest, boolean ignoreNull) {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        } else if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        } else {
            try {
                int i;
                String name;
                Object value;
                if (orig instanceof DynaBean) {
                    DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();

                    for(i = 0; i < origDescriptors.length; ++i) {
                        name = origDescriptors[i].getName();
                        if (propertyUtilsBean.isReadable(orig, name) && propertyUtilsBean.isWriteable(dest, name)) {
                            value = ((DynaBean)orig).get(name);
                            if (value != null || !ignoreNull) {
                                copyProperty(dest, name, value);
                            }
                        }
                    }
                } else if (orig instanceof Map) {
                    Map<String, Object> propMap = (Map)orig;
                    Iterator var12 = propMap.entrySet().iterator();

                    while(true) {
//                        Object value;
//                        String name;
                        do {
                            Map.Entry entry;
                            do {
                                if (!var12.hasNext()) {
                                    return;
                                }

                                entry = (Map.Entry)var12.next();
                                name = (String)entry.getKey();
                            } while(!propertyUtilsBean.isWriteable(dest, name));

                            value = entry.getValue();
                        } while(value == null && ignoreNull);

                        copyProperty(dest, name, value);
                    }
                } else {
                    PropertyDescriptor[] origDescriptors = propertyUtilsBean.getPropertyDescriptors(orig);

                    for(i = 0; i < origDescriptors.length; ++i) {
                        name = origDescriptors[i].getName();
                        if (!"class".equals(name) && propertyUtilsBean.isReadable(orig, name) && propertyUtilsBean.isWriteable(dest, name)) {
                            try {
                                value = propertyUtilsBean.getSimpleProperty(orig, name);
                                if (value != null || !ignoreNull) {
                                    copyProperty(dest, name, value);
                                }
                            } catch (NoSuchMethodException var8) {
                            }
                        }
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException var9) {
                var9.printStackTrace();
            }

        }
    }

    public static void copyProperty(Object bean, String name, Object value) {
        try {
            Object target = bean;
            Resolver resolver = propertyUtilsBean.getResolver();

            while(resolver.hasNested(name)) {
                try {
                    target = propertyUtilsBean.getProperty(target, resolver.next(name));
                    name = resolver.remove(name);
                } catch (InvocationTargetException | IllegalAccessException var14) {
                    var14.printStackTrace();
                } catch (NoSuchMethodException var15) {
                    return;
                }
            }

            String propName = resolver.getProperty(name);
            Class<?> type = null;
            int index = resolver.getIndex(name);
            String key = resolver.getKey(name);
            if (target instanceof DynaBean) {
                DynaClass dynaClass = ((DynaBean)target).getDynaClass();
                DynaProperty dynaProperty = dynaClass.getDynaProperty(propName);
                if (dynaProperty == null) {
                    return;
                }

                type = dynaPropertyType(dynaProperty, value);
            } else {
                PropertyDescriptor descriptor = null;

                try {
                    descriptor = propertyUtilsBean.getPropertyDescriptor(target, name);
                    if (descriptor == null) {
                        return;
                    }
                } catch (NoSuchMethodException var16) {
                    return;
                }

                type = descriptor.getPropertyType();
                if (type == null) {
                    return;
                }
            }

            if (index >= 0) {
                value = convertForCopy(value, type.getComponentType());

                try {
                    propertyUtilsBean.setIndexedProperty(target, propName, index, value);
                } catch (NoSuchMethodException var13) {
                    throw new InvocationTargetException(var13, "Cannot set " + propName);
                }
            } else if (key != null) {
                try {
                    propertyUtilsBean.setMappedProperty(target, propName, key, value);
                } catch (NoSuchMethodException var12) {
                    throw new InvocationTargetException(var12, "Cannot set " + propName);
                }
            } else {
                value = convertForCopy(value, type);

                try {
                    propertyUtilsBean.setSimpleProperty(target, propName, value);
                } catch (NoSuchMethodException var11) {
                    throw new InvocationTargetException(var11, "Cannot set " + propName);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException var17) {
            var17.printStackTrace();
        }

    }

    private static Class<?> dynaPropertyType(DynaProperty dynaProperty, Object value) {
        if (!dynaProperty.isMapped()) {
            return dynaProperty.getType();
        } else {
            return value == null ? String.class : value.getClass();
        }
    }

    private static Object convertForCopy(Object value, Class<?> type) {
        return value != null ? convert(value, type) : value;
    }

    protected static Object convert(Object value, Class<?> type) {
        Converter converter = convertUtilsBean.lookup(type);
        return converter != null ? converter.convert(type, value) : value;
    }

    public static <T> List<T> convertIter(Iterator<T> iterator) {
        List<T> list = new ArrayList();

        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
