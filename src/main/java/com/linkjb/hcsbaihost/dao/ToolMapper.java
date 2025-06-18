package com.linkjb.hcsbaihost.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linkjb.hcsbaihost.entity.Tool;
import org.apache.ibatis.annotations.Mapper;

/**
* @author shark
* @description 针对表【tool(工具信息)】的数据库操作Mapper
* @createDate 2025-04-11 12:02:41
* @Entity com.linkjb.hcsbaihost.entity.Tool
*/
@Mapper
public interface ToolMapper extends BaseMapper<Tool> {

}




