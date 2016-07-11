/**
 * qccr.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.toowell.crm.biz.converter;

/**
 * 转换处理器
 * @author xiafei
 * @since $Revision:1.0.0, $Date: 2015年12月1日 下午5:08:27 $
 */
public interface ConvertHandler<O,T> {
    T process(O o);
}
