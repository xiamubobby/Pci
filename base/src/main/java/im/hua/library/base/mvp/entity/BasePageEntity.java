package im.hua.library.base.mvp.entity;

import java.util.List;

/**
 * Created by hua on 16/5/9.
 */
public class BasePageEntity<T> extends BaseEntity {

    /**
     * totalElements : 1
     * last : true
     * totalPages : 1
     * size : 20
     * number : 0
     * sort : null
     * first : true
     * numberOfElements : 1
     */

    private RetValuesEntity<T> ret_values;

    public RetValuesEntity<T> getRet_values() {
        return ret_values;
    }

    public void setRet_values(RetValuesEntity<T> ret_values) {
        this.ret_values = ret_values;
    }

    public static class RetValuesEntity<T> {
        /**
         * 数据库总条数
         */
        private int totalElements;
        /**
         * 是否是最后一页
         */
        private boolean last;
        /**
         * 总共的页数
         */
        private int totalPages;
        /**
         * 一页的大小
         */
        private int size;
        /**
         * 当前为第几页
         */
        private int number;
        private Object sort;
        /**
         * 是否为第一页
         */
        private boolean first;
        /**
         * 当前页返回的条数
         */
        private int numberOfElements;
        private List<T> content;

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }
    }
}
