package com.huanghehua.www.common;

import java.util.Objects;

/**
 * 分页业务封装类.<br/>
 * 当物理分页时，即通过数据库完成分页时，需要录入页面大小、当前页码和记录总数。<br/>
 * 当逻辑分页时，即通过程序内存完成分页时，只需要录入页面大小、当前页码。但是需要注意，应该在业务方法中和自行计算出记录总数。<br/>
 * 当前页面的第一条记录的索引 和 获取当前页面的记录数量 只能通过上述三个条件来得到，而不能自行通过setter设置。<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/25
 */
public class PageAbility {
    /**
     * 页面承载的最大记录条数
     */
    private Integer maxPageSize;
    /**
     * 当前页面实际承载的记录条数
     */
    private Integer currentPageSize;
    /**
     * 当前页码
     */
    private Integer currentPageNumber;

    /**
     * 查询的记录总数
     */
    private Integer recordCount;

    /**
     * 本页的第一条记录的索引，（记录最开始的索引为0）
     */
    private Integer fromIndex;

    public PageAbility() {
    }

    public PageAbility(Integer maxPageSize, Integer currentPageNumber) {
        this.maxPageSize = maxPageSize;
        this.currentPageNumber = currentPageNumber;
    }

    public PageAbility(Integer maxPageSize, Integer currentPageNumber, Integer recordCount) {
        this.maxPageSize = maxPageSize;
        this.currentPageNumber = currentPageNumber;
        this.recordCount = recordCount;
    }

    /**
     * 设置当前页码，用于跳转页面。<br/>
     *
     * @param currentPageNumber 当前页码
     */
    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    /**
     * 得到当前页面记录条数，用于限制页面展示记录的数量。<br/>
     * <p/>
     * 若页面最大记录数量 * 当前页面 <= 总记录数量，那么返回页面最大记录数量，
     * 否则返回刚好的记录数量。<br/>
     *
     * @return {@link Integer}
     */
    public Integer getCurrentPageSize() {
        Integer cnt = this.maxPageSize * this.currentPageNumber;
        // 当记录总数远超时，返回页面可展示的最大数量
        if (cnt <= this.recordCount) {
            return this.maxPageSize;
        } else {
            int res = cnt - this.recordCount;
            // 当跳转页面远超时
            if (res > this.maxPageSize) {
                return 0;
            }
            else {
                return this.maxPageSize - res;
            }
        }

    }

    /**
     * 获取本页第一条记录的索引，用于过滤先前的记录。<br/>
     *
     * @return {@link Integer}
     */
    public Integer getFromIndex() {
        return this.maxPageSize * (this.currentPageNumber - 1);
    }


    @Override
    public String toString() {
        return "PageBO{" +
                "maxPageSize=" + maxPageSize +
                ", currentPage=" + currentPageSize +
                ", currentPageNumber=" + currentPageNumber +
                ", recordCount=" + recordCount +
                ", fromIndex=" + fromIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageAbility pageAbility = (PageAbility) o;
        return Objects.equals(maxPageSize, pageAbility.maxPageSize) && Objects.equals(currentPageSize, pageAbility.currentPageSize) && Objects.equals(currentPageNumber, pageAbility.currentPageNumber) && Objects.equals(recordCount, pageAbility.recordCount) && Objects.equals(fromIndex, pageAbility.fromIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPageSize, currentPageSize, currentPageNumber, recordCount, fromIndex);
    }


    public Integer getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(Integer maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }


}
