package com.linkjb.hcsbaihost.service.impl;
import com.linkjb.hcsbaihost.service.TestToolService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class TestToolServiceImpl implements TestToolService {
    @Tool(description = "推荐技术书籍")
    @Override
    public String msg1() {
        return "推荐【Head First Java】，这本书通过生动有趣的方式讲解Java的基础知识，非常适合编程新手。";
    }

    @Tool(description = "推荐值得看的电影")
    @Override
    public String msg2() {
        return "推荐《中国机长》，是一部根据真实事件改编的影片，讲述了2018年5月14日四川航空3U8633航班机组成功处置特情的真实故事。该片由刘伟强执导，于勇敢编剧，并由张涵予、欧豪、杜江、袁泉等领衔主演！";
    }

    @Tool(description = "查询天气")
    @Override
    public String msg3(@ToolParam(description = "需要查询天气的城市名称，例如：成都") String city) {
        return city + "，天气晴朗！";
    }
}
