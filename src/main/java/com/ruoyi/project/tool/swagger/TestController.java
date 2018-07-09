package com.ruoyi.project.tool.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 测试方法
 * 
 * @author ruoyi
 */
@Api("用户信息管理")
@RestController
@RequestMapping("/test/*")
public class TestController extends BaseController
{
    private final static List<Test> testList = new ArrayList<>();
    {
        testList.add(new Test("1", "admin", "admin123"));
        testList.add(new Test("2", "ry", "admin123"));
    }

    @ApiOperation("获取列表")
    @GetMapping("list")
    public List<Test> testList()
    {
        return testList;
    }

    @ApiOperation("新增用户")
    @PostMapping("save")
    public AjaxResult save(Test test)
    {
        return testList.add(test) ? success() : error();
    }

    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "Test", value = "单个用户信息", dataType = "Test")
    @PutMapping("update")
    public AjaxResult update(Test test)
    {
        return testList.remove(test) && testList.add(test) ? success() : error();
    }

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "Tests", value = "单个用户信息", dataType = "Test")
    @DeleteMapping("delete")
    public AjaxResult delete(Test test)
    {
        return testList.remove(test) ? success() : error();
    }
}

class Test
{
    private String userId;
    private String username;
    private String password;

    public Test()
    {

    }

    public Test(String userId, String username, String password)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Test test = (Test) o;

        return userId != null ? userId.equals(test.userId) : test.userId == null;
    }

    @Override
    public int hashCode()
    {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
