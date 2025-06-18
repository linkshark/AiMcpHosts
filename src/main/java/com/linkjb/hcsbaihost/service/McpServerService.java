package com.linkjb.hcsbaihost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linkjb.hcsbaihost.entity.McpServer;
import com.linkjb.hcsbaihost.request.ServerListRequest;
import com.linkjb.hcsbaihost.vo.McpServerDetailVO;
import com.linkjb.hcsbaihost.vo.McpServerListVO;
import com.linkjb.hcsbaihost.vo.ResponseInfo;


import java.util.List;

/**
* @author shark
* @description 针对表【mcp_server】的数据库操作Service
* @createDate 2025-04-11 12:02:41
*/
public interface McpServerService extends IService<McpServer> {

    List<McpServerListVO> doList(ServerListRequest request);

    ResponseInfo<McpServerDetailVO> detail(String id, String secretKey);

    ResponseInfo<String> doSave(McpServerDetailVO detailVO);

    ResponseInfo<String> doDelete(String id,String secretKey);

    ResponseInfo<String> doValidate(String id, String secretKey);
}
