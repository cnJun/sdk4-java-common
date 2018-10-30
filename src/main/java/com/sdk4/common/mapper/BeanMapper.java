package com.sdk4.common.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现深度的BeanOfClasssA到BeanOfClassB复制
 */
public class BeanMapper {
    private static Mapper mapper = new DozerBeanMapper();

    /**
     * 简单的复制出新类型对象.
     *
     * @param source 源对象
     * @param destinationClass 目的对象类型
     * @param <S> 源对象类型
     * @param <D> 目的对象类型
     * @return 目的对象实例
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
     * 简单的复制出新对象ArrayList
     * 
     * @param sourceList 源对象列表
     * @param destinationClass 目的对象类型
     * @param <S> 源对象类型
     * @param <D> 目的对象类型
     * @return 目的对象实例列表
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
        List<D> destionationList = new ArrayList<D>();
        for (S source : sourceList) {
            if (source != null) {
                destionationList.add(mapper.map(source, destinationClass));
            }
        }
        return destionationList;
    }

}
