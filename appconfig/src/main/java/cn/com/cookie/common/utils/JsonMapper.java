/*
 * FileName:    JsonMapper.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年9月6日 (Shicx) 1.0 Create
 */

package cn.com.cookie.common.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 */
public class JsonMapper extends ObjectMapper {

    /**
     * 
     */
    private static final long serialVersionUID = -8160920230372774139L;

    private static JsonMapper mapper;

    public JsonMapper() {
    }

    public JsonMapper(Include include) {
        // 设置输出时包含属性的风格,序列化时空值不显示=Include.NON_EMPTY
        if (include != null) {
            this.setSerializationInclusion(include);
        }
        // 允许单引号、允许不带引号的字段名称
        this.enableSimple();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString("");
            }
        });
    }

    /**
     * 创建JsonMapper单例.
     */
    public static JsonMapper getInstance() {
        if (mapper == null) {
            synchronized (JsonMapper.class) {
                if (mapper == null) {
                    // 创建采用默认json字符串输出格式的实体
                    mapper = new JsonMapper(null);
                }
            }
        }
        return mapper;
    }

    /**
     * 重写writeValueAsString方法，捕获抛出的异常
     * 
     * Object可以是POJO，也可以是Collection或数组。
     */
    public String writeValueAsString(Object object) {
        try {
            return super.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 重写readValue方法，捕获抛出的异常。反序列化POJO或简单Collection如List<String>。 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]",
     * 返回空集合。
     * 
     * 如需反序列化复杂Collection如List<MyBean>, 请使用readValue(String,JavaType)
     * 
     * @param jsonString
     *            不能为空，否则抛出异常
     * @param javaType
     */
    public <T> T readValue(String jsonString, Class<T> clazz) {
        try {
            return super.readValue(jsonString, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重写readValue方法，捕获抛出的异常。
     * 
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     * 
     * @param jsonString
     *            不能为空，否则抛出异常
     * @param javaType
     */
    public <T> T readValue(String jsonString, JavaType javaType) {
        try {
            return super.readValue(jsonString, javaType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造泛型的Collection Type如: ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class) HashMap
     * <String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType constructParametricType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 当JSON含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性.
     */
    public <T> T update(String jsonString, T object) {
        try {
            return this.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 允许单引号 允许不带引号的字段名称
     */
    public JsonMapper enableSimple() {
        this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    /**
     * ===========测试==============
     */
    public static void main(String[] args) {

    }

}
