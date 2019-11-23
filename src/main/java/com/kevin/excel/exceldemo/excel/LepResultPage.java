package com.kevin.excel.exceldemo.excel;

import lombok.Data;

import java.util.*;

/**
 * @author Jinyugai
 * @description:
 * @date: Create in 16:49 2019/11/23
 * @modified By:
 */
@Data
public class LepResultPage<T> implements List<T> {

    /**
     * 默认分页为20条数据
     */
    public static final int DEFAULT_ROW_SIZE = 20;

    /**
     * 默认为第一页
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 当前页码
     */
    private int pageNo = DEFAULT_PAGE_NUM;
    /**
     * 页面大小
     */

    private int pageSize = DEFAULT_ROW_SIZE;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 返回的结果集
     */
    private List<T> results = new ArrayList<>();

    public LepResultPage() {
    }

    /**
     * 构造一个Page对象
     *
     * @param pageNo   当前页码
     * @param pageSize 页面大小
     */
    public LepResultPage(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public LepResultPage(int pageNo, int pageSize, int totalCount, List<T> results) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.results = results;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
    }

    public static LepResultPage getDefaultPage(BasePageVo basePageVo) {
        LepResultPage page =  new LepResultPage<>();
        page.setPageNo(basePageVo.getPageNo());
        page.setPageSize(basePageVo.getPageSize());
        return page;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", results=" + results +
                '}';
    }

    @Override
    public int size() {
        return results.size();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return results.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }

    @Override
    public Object[] toArray() {
        return results.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return results.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return results.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return results.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return results.contains(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return results.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return results.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return results.remove(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return results.retainAll(c);
    }

    @Override
    public void clear() {
        results.clear();
    }

    @Override
    public T get(int index) {
        return results.get(index);
    }

    @Override
    public T set(int index, T element) {
        return results.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        results.add(index, element);
    }

    @Override
    public T remove(int index) {
        return results.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return results.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return results.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return results.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return results.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return results.subList(fromIndex, toIndex);
    }

}
