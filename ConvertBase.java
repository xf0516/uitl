/**
 * qccr.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.toowell.crm.biz.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 
 * @author wqi
 * @version $Id: ConvertBase.java, v 0.1 2015年8月24日 下午7:15:47 wqi Exp $
 */
public class ConvertBase {

    private static Logger logger = LoggerFactory.getLogger(ConvertBase.class);

    /**
     * @description:   Do to Ro
     * @author: wqi
     * @return
     * @date: 2015年8月24日 上午11:18:33
     */
    public static <T> T beanConvert(Object source, Class<T> target) {
        /*
        T targetObj;
        if (source == null) {
            return null;
        }
        try {
            targetObj = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties( source,targetObj);
            return targetObj;
        } catch (InstantiationException e) {
            logger.error("转换出错", e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e.getMessage());
        } 
        return null;*/
        return apacheBeanConvert(source, target);
    }

    @SuppressWarnings({ "rawtypes" })
    public static <T> T beanConvert(Object source, Class<T> target,
                                    ConvertPostHandler convertPostHandler) {
        /*
        T targetObj;
        if (source == null) {
            return null;
        }
        try {
            targetObj = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties( source,targetObj);
            if (null != convertPostHandler) {
                convertPostHandler.postProcess(source, targetObj);
            }
            return targetObj;
        } catch (InstantiationException e) {
            logger.error("转换出错", e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e.getMessage());
        } 
        return null;*/
        return apacheBeanConvert(source, target, convertPostHandler);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T beanConvert(Object source, Class<T> target,
                                    ConvertPostHandler convertPostHandler,
                                    String... ignoreProperties) {
        T targetObj = null;
        try {
            targetObj = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetObj, ignoreProperties);
            if (null != convertPostHandler) {
                convertPostHandler.postProcess(source, targetObj);
            }
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        return targetObj;
    }

    public static <T> T beanConvert(Object source, Class<T> target, String... ignoreProperties) {
        T targetObj = null;
        try {
            targetObj = target.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, targetObj, ignoreProperties);
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        return targetObj;
    }

    public static <T> List<T> batchBeanConvert(List<?> sources, Class<T> target) {
        /*
        if (sources == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Object object : sources) {
            T t = beanConvert(object, target);
            list.add(t);
        }
        return list;*/
        return apacheBatchBeanConvert(sources, target);
    }

    /**
     * 增加转化的后置处理器
     * @param sources
     * @param target
     * @param convertPostHandler
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> List<T> batchBeanConvert(List<?> sources, Class<T> target,
                                               ConvertPostHandler convertPostHandler) {
        if (sources == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Object object : sources) {
            T t = beanConvert(object, target);
            if (null != convertPostHandler) {
                convertPostHandler.postProcess(object, t);
            }
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> apacheBatchBeanConvert(List<?> sources, Class<T> target) {
        return apacheBatchBeanConvert(sources, target, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> List<T> apacheBatchBeanConvert(List<?> sources, Class<T> target,
                                                     ConvertPostHandler convertPostHandler) {
        if (sources == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Object object : sources) {
            T t = apacheBeanConvert(object, target);
            if (null != convertPostHandler) {
                convertPostHandler.postProcess(object, t);
            }
            list.add(t);
        }
        return list;
    }

    public static <T> T apacheBeanConvert(Object source, Class<T> target) {
        T targetObj = null;
        if (source == null) {
            return null;
        }
        try {
            targetObj = target.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(targetObj, source);
        } catch (InvocationTargetException e) {
            logger.error("转换出错", e);
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        return targetObj;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T apacheBeanConvert(Object source, Class<T> target,
                                          ConvertPostHandler convertPostHandler) {
        T targetObj = null;
        if (source == null) {
            return null;
        }
        try {
            targetObj = target.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(targetObj, source);
            if (null != convertPostHandler) {
                convertPostHandler.postProcess(source, targetObj);
            }
        } catch (InvocationTargetException e) {
            logger.error("转换出错", e);
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        return targetObj;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T customBeanConvert(Object source, ConvertHandler convertHandler) {
        if (null == source) {
            return null;
        }
        if (null == convertHandler) {
            return (T) source;
        }
        return (T)convertHandler.process(source);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> customBatchBeanConvert(List<?> sources,
                                                     ConvertHandler convertHandler) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        if (null == convertHandler) {
            return (List<T>) sources;
        }
        List<T> list = Lists.newArrayList();
        for (Object o : sources) {
            T t = customBeanConvert(o, convertHandler);
            if (null != t) {
                list.add(t);
            }
        }
        return list;
    }
}
