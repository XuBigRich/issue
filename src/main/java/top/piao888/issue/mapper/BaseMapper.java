package top.piao888.issue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.piao888.issue.domain.Base;
import top.piao888.issue.domain.Deal;

import java.util.List;

@Mapper
@Component
public interface BaseMapper {
    Base selectByid(Integer bid);
    List<Base> getAll();
}
