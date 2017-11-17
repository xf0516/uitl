

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Generate {
    public final Logger LOGGER = LoggerFactory.getLogger(Generate.class);
    private int listSize = 3;
    private int intMax = 100;
    private Map<String,List> valueMap=new HashMap<>();
    private List<String> stringAll = ImmutableList.of("洗车", "保养", "汽车美容", "维修");
    private ImmutableSet basicType = ImmutableSet.of("int", "double", "float", "long","boolean");
    public void addValues(String fieldName,List<Object> values){
       valueMap.put(fieldName,values);
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public void setIntMax(int intMax) {
        this.intMax = intMax;
    }

    /*
    * 生成实体入口
    */
    public Object treeClass(Class<?> clazz){
        return treeClass(clazz,null);
    }
    /**
     * 递归方法,返回class实例
     *
     * @param clazz
     * @return
     */
    public Object treeClass(Class<?> clazz,String fieldName) {
        if (isBasicType(clazz)) {
            return newBasicInstance(clazz,fieldName);
        }
        Object instance = newInstance(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            if(isSkip(field)){
                continue;
            }
            Type type = field.getGenericType();
            //普通对象
            Object value = null;
            if (Class.class.isInstance(type)) {
                value = treeClass(field.getType(),field.getName());
                //List泛型对象
            } else if (ParameterizedType.class.isInstance(type)) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type pt = parameterizedType.getActualTypeArguments()[0];
                List list = new ArrayList();
                for (int i = 0; i < listSize; i++) {
                    list.add(treeClass((Class) pt,field.getName()));
                }
                value = list;
            } else {
                LOGGER.warn("未能解析的对象{}", type);
            }
            setValue(field,instance,value);

        }
        return instance;
    }

    private boolean isSkip(Field field){
        return field.getName().equals("serialVersionUID");
    }
    private void setValue(Field field,Object instance,Object value){
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private boolean isBasicType(Class clazz) {
        String simpleName = clazz.getName();
        return simpleName.startsWith("java") || basicType.contains(simpleName);
    }


    private Object newInstance(Class<?> empty) {
        try {
            return empty.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    Object newBasicInstance(Class<?> empty,String fileName) {

        String className = empty.getName();
        Object value=null;
        List<Object> values=valueMap.get(String.valueOf(fileName));
        if(CollectionUtils.isNotEmpty(values)){
            return values.get(new Random().nextInt(values.size()));
        }
        if (String.class.isAssignableFrom(empty)) {
            value= new String(generateString());
        } else if (Integer.class.isAssignableFrom(empty) || className.equals("int")) {
            value= new Integer(generateInt());
        } else if (Double.class.isAssignableFrom(empty) || className.equals("double")) {
            value= new Double(generateInt()/10.0);
        } else if (Float.class.isAssignableFrom(empty) || className.equals("float")) {
            value= new Float(generateInt()/10.0);
        } else if (Long.class.isAssignableFrom(empty) || className.equals("long")) {
            value= new Long(generateInt());
        }else if(Date.class.isAssignableFrom(empty)){
            value=new Date();
        }else if (Boolean.class.isAssignableFrom(empty)||className.equals("boolean")){
            value=true;
        } else{
            LOGGER.warn("未能生成基本数据对象,{}", empty);
        }

        return value;
    }

    public String generateString() {
        return stringAll.get(new Random().nextInt(stringAll.size()));
    }

    public int generateInt() {
        return new Random().nextInt(intMax);
    }






}
