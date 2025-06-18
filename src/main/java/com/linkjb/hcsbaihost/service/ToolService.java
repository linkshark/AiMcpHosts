package com.linkjb.hcsbaihost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkjb.hcsbaihost.entity.Tool;
import com.linkjb.hcsbaihost.vo.ToolVO;


import java.util.List;

/**
* @author shark
* @description 针对表【tool(工具信息)】的数据库操作Service
* @createDate 2025-04-11 12:02:41
*/
public interface ToolService extends IService<Tool> {

    List<ToolVO> getByServerId(Long id);

    void removeByServerId(Long id);

    void doSave(List<ToolVO> tools, Long id);
}
