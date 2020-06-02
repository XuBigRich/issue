package top.piao888.issue.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.piao888.issue.domain.Deal;

@Mapper
public interface DealMapper {
    void insert(Deal deal);
    Deal getDealByiid(Integer iid);
}
