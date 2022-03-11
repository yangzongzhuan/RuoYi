package com.ruoyi.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 表格相关
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/table")
public class DemoTableController extends BaseController
{
    private String prefix = "demo/table";

    private final static List<UserTableModel> users = new ArrayList<UserTableModel>();
    {
        users.add(new UserTableModel(1, "1000001", "测试1", "0", "15888888888", "ry@qq.com", 150.0, "0"));
        users.add(new UserTableModel(2, "1000002", "测试2", "1", "15666666666", "ry@qq.com", 180.0, "1"));
        users.add(new UserTableModel(3, "1000003", "测试3", "0", "15666666666", "ry@qq.com", 110.0, "1"));
        users.add(new UserTableModel(4, "1000004", "测试4", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.add(new UserTableModel(5, "1000005", "测试5", "0", "15666666666", "ry@qq.com", 140.0, "1"));
        users.add(new UserTableModel(6, "1000006", "测试6", "1", "15666666666", "ry@qq.com", 330.0, "1"));
        users.add(new UserTableModel(7, "1000007", "测试7", "0", "15666666666", "ry@qq.com", 160.0, "1"));
        users.add(new UserTableModel(8, "1000008", "测试8", "1", "15666666666", "ry@qq.com", 170.0, "1"));
        users.add(new UserTableModel(9, "1000009", "测试9", "0", "15666666666", "ry@qq.com", 180.0, "1"));
        users.add(new UserTableModel(10, "1000010", "测试10", "0", "15666666666", "ry@qq.com", 210.0, "1"));
        users.add(new UserTableModel(11, "1000011", "测试11", "1", "15666666666", "ry@qq.com", 110.0, "1"));
        users.add(new UserTableModel(12, "1000012", "测试12", "0", "15666666666", "ry@qq.com", 120.0, "1"));
        users.add(new UserTableModel(13, "1000013", "测试13", "1", "15666666666", "ry@qq.com", 380.0, "1"));
        users.add(new UserTableModel(14, "1000014", "测试14", "0", "15666666666", "ry@qq.com", 280.0, "1"));
        users.add(new UserTableModel(15, "1000015", "测试15", "0", "15666666666", "ry@qq.com", 570.0, "1"));
        users.add(new UserTableModel(16, "1000016", "测试16", "1", "15666666666", "ry@qq.com", 260.0, "1"));
        users.add(new UserTableModel(17, "1000017", "测试17", "1", "15666666666", "ry@qq.com", 210.0, "1"));
        users.add(new UserTableModel(18, "1000018", "测试18", "1", "15666666666", "ry@qq.com", 340.0, "1"));
        users.add(new UserTableModel(19, "1000019", "测试19", "1", "15666666666", "ry@qq.com", 160.0, "1"));
        users.add(new UserTableModel(20, "1000020", "测试20", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.add(new UserTableModel(21, "1000021", "测试21", "1", "15666666666", "ry@qq.com", 120.0, "1"));
        users.add(new UserTableModel(22, "1000022", "测试22", "1", "15666666666", "ry@qq.com", 130.0, "1"));
        users.add(new UserTableModel(23, "1000023", "测试23", "1", "15666666666", "ry@qq.com", 490.0, "1"));
        users.add(new UserTableModel(24, "1000024", "测试24", "1", "15666666666", "ry@qq.com", 570.0, "1"));
        users.add(new UserTableModel(25, "1000025", "测试25", "1", "15666666666", "ry@qq.com", 250.0, "1"));
        users.add(new UserTableModel(26, "1000026", "测试26", "1", "15666666666", "ry@qq.com", 250.0, "1"));
    }

    private final static List<AreaModel> areas = new ArrayList<AreaModel>();
    {
        areas.add(new AreaModel(1, 0, "广东省", "440000", "GDS", "GuangDongSheng", 1));
        areas.add(new AreaModel(2, 0, "湖南省", "430000", "HNS", "HuNanSheng", 1));
        areas.add(new AreaModel(3, 0, "河南省", "410000", "HNS", "HeNanSheng", 0));
        areas.add(new AreaModel(4, 0, "湖北省", "420000", "HBS", "HuBeiSheng", 0));
        areas.add(new AreaModel(5, 0, "辽宁省", "210000", "LNS", "LiaoNingSheng", 0));
        areas.add(new AreaModel(6, 0, "山东省", "370000", "SDS", "ShanDongSheng", 0));
        areas.add(new AreaModel(7, 0, "陕西省", "610000", "SXS", "ShanXiSheng", 0));
        areas.add(new AreaModel(8, 0, "贵州省", "520000", "GZS", "GuiZhouSheng", 0));
        areas.add(new AreaModel(9,  0, "上海市", "310000", "SHS", "ShangHaiShi", 0));
        areas.add(new AreaModel(10, 0, "重庆市", "500000", "CQS", "ChongQingShi", 0));
        areas.add(new AreaModel(11, 0, "若依省", "666666", "YYS", "RuoYiSheng", 0));
        areas.add(new AreaModel(12, 0, "安徽省", "340000", "AHS", "AnHuiSheng", 0));
        areas.add(new AreaModel(13, 0, "福建省", "350000", "FJS", "FuJianSheng", 0));
        areas.add(new AreaModel(14, 0, "海南省", "460000", "HNS", "HaiNanSheng", 0));
        areas.add(new AreaModel(15, 0, "江苏省", "320000", "JSS", "JiangSuSheng", 0));
        areas.add(new AreaModel(16, 0, "青海省", "630000", "QHS", "QingHaiSheng", 0));
        areas.add(new AreaModel(17, 0, "广西壮族自治区", "450000", "GXZZZZQ", "GuangXiZhuangZuZiZhiQu", 0));
        areas.add(new AreaModel(18, 0, "宁夏回族自治区", "640000", "NXHZZZQ", "NingXiaHuiZuZiZhiQu", 0));
        areas.add(new AreaModel(19, 0, "内蒙古自治区", "150000", "NMGZZQ", "NeiMengGuZiZhiQu", 0));
        areas.add(new AreaModel(20, 0, "新疆维吾尔自治区", "650000", "XJWWEZZQ", "XinJiangWeiWuErZiZhiQu", 0));
        areas.add(new AreaModel(21, 0, "江西省", "360000", "JXS", "JiangXiSheng", 0));
        areas.add(new AreaModel(22, 0, "浙江省", "330000", "ZJS", "ZheJiangSheng", 0));
        areas.add(new AreaModel(23, 0, "河北省", "130000", "HBS", "HeBeiSheng", 0));
        areas.add(new AreaModel(24, 0, "天津市", "120000", "TJS", "TianJinShi", 0));
        areas.add(new AreaModel(25, 0, "山西省", "140000", "SXS", "ShanXiSheng", 0));
        areas.add(new AreaModel(26, 0, "台湾省", "710000", "TWS", "TaiWanSheng", 0));
        areas.add(new AreaModel(27, 0, "甘肃省", "620000", "GSS", "GanSuSheng", 0));
        areas.add(new AreaModel(28, 0, "四川省", "510000", "SCS", "SiChuanSheng", 0));
        areas.add(new AreaModel(29, 0, "云南省", "530000", "YNS", "YunNanSheng", 0));
        areas.add(new AreaModel(30, 0, "北京市", "110000", "BJS", "BeiJingShi", 0));
        areas.add(new AreaModel(31, 0, "香港特别行政区", "810000", "XGTBXZQ", "XiangGangTeBieXingZhengQu", 0));
        areas.add(new AreaModel(32, 0, "澳门特别行政区", "820000", "AMTBXZQ", "AoMenTeBieXingZhengQu", 0));
        
        areas.add(new AreaModel(100, 1, "深圳市", "440300", "SZS", "ShenZhenShi", 1));
        areas.add(new AreaModel(101, 1, "广州市", "440100", "GZS", "GuangZhouShi", 0));
        areas.add(new AreaModel(102, 1, "东莞市", "441900", "DGS", "DongGuanShi", 0));
        areas.add(new AreaModel(103, 2, "长沙市", "410005", "CSS", "ChangShaShi", 1));
        areas.add(new AreaModel(104, 2, "岳阳市", "414000", "YYS", "YueYangShi", 0));
        
        areas.add(new AreaModel(1000, 100, "龙岗区", "518172", "LGQ", "LongGangQu", 0));
        areas.add(new AreaModel(1001, 100, "南山区", "518051", "NSQ", "NanShanQu", 0));
        areas.add(new AreaModel(1002, 100, "宝安区", "518101", "BAQ", "BaoAnQu", 0));
        areas.add(new AreaModel(1003, 100, "福田区", "518081", "FTQ", "FuTianQu", 0));
        areas.add(new AreaModel(1004, 103, "天心区", "410004", "TXQ", "TianXinQu", 0));
        areas.add(new AreaModel(1005, 103, "开福区", "410008", "KFQ", "KaiFuQu", 0));
        areas.add(new AreaModel(1006, 103, "芙蓉区", "410011", "FRQ", "FuRongQu", 0));
        areas.add(new AreaModel(1007, 103, "雨花区", "410011", "YHQ", "YuHuaQu", 0));
    }

    private final static List<UserTableColumn> columns = new ArrayList<UserTableColumn>();
    {
        columns.add(new UserTableColumn("用户ID", "userId"));
        columns.add(new UserTableColumn("用户编号", "userCode"));
        columns.add(new UserTableColumn("用户姓名", "userName"));
        columns.add(new UserTableColumn("用户手机", "userPhone"));
        columns.add(new UserTableColumn("用户邮箱", "userEmail"));
        columns.add(new UserTableColumn("用户状态", "status"));
    }

    /**
     * 搜索相关
     */
    @GetMapping("/search")
    public String search()
    {
        return prefix + "/search";
    }

    /**
     * 数据汇总
     */
    @GetMapping("/footer")
    public String footer()
    {
        return prefix + "/footer";
    }

    /**
     * 组合表头
     */
    @GetMapping("/groupHeader")
    public String groupHeader()
    {
        return prefix + "/groupHeader";
    }

    /**
     * 表格导出
     */
    @GetMapping("/export")
    public String export()
    {
        return prefix + "/export";
    }

    /**
     * 表格导出选择列
     */
    @GetMapping("/exportSelected")
    public String exportSelected()
    {
        return prefix + "/exportSelected";
    }

    /**
     * 导出数据
     */
    @PostMapping("/exportData")
    @ResponseBody
    public AjaxResult exportSelected(UserTableModel userModel, String userIds)
    {
        List<UserTableModel> userList = new ArrayList<UserTableModel>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);

        // 条件过滤
        if (StringUtils.isNotEmpty(userIds))
        {
            userList.clear();
            for (Long userId : Convert.toLongArray(userIds))
            {
                for (UserTableModel user : users)
                {
                    if (user.getUserId() == userId)
                    {
                        userList.add(user);
                    }
                }
            }
        }
        ExcelUtil<UserTableModel> util = new ExcelUtil<UserTableModel>(UserTableModel.class);
        return util.exportExcel(userList, "用户数据");
    }

    /**
     * 翻页记住选择
     */
    @GetMapping("/remember")
    public String remember()
    {
        return prefix + "/remember";
    }

    /**
     * 跳转至指定页
     */
    @GetMapping("/pageGo")
    public String pageGo()
    {
        return prefix + "/pageGo";
    }

    /**
     * 自定义查询参数
     */
    @GetMapping("/params")
    public String params()
    {
        return prefix + "/params";
    }

    /**
     * 多表格
     */
    @GetMapping("/multi")
    public String multi()
    {
        return prefix + "/multi";
    }

    /**
     * 点击按钮加载表格
     */
    @GetMapping("/button")
    public String button()
    {
        return prefix + "/button";
    }

    /**
     * 直接加载表格数据
     */
    @GetMapping("/data")
    public String data(ModelMap mmap)
    {
        mmap.put("users", users);
        return prefix + "/data";
    }

    /**
     * 表格冻结列
     */
    @GetMapping("/fixedColumns")
    public String fixedColumns()
    {
        return prefix + "/fixedColumns";
    }

    /**
     * 自定义触发事件
     */
    @GetMapping("/event")
    public String event()
    {
        return prefix + "/event";
    }

    /**
     * 表格细节视图
     */
    @GetMapping("/detail")
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 表格父子视图
     */
    @GetMapping("/child")
    public String child()
    {
        return prefix + "/child";
    }

    /**
     * 表格图片预览
     */
    @GetMapping("/image")
    public String image()
    {
        return prefix + "/image";
    }

    /**
     * 动态增删改查
     */
    @GetMapping("/curd")
    public String curd()
    {
        return prefix + "/curd";
    }

    /**
     * 表格行拖拽操作
     */
    @GetMapping("/reorderRows")
    public String reorderRows()
    {
        return prefix + "/reorderRows";
    }

    /**
     * 表格列拖拽操作
     */
    @GetMapping("/reorderColumns")
    public String reorderColumns()
    {
        return prefix + "/reorderColumns";
    }

    /**
     * 表格列宽拖动
     */
    @GetMapping("/resizable")
    public String resizable()
    {
        return prefix + "/resizable";
    }

    /**
     * 表格行内编辑操作
     */
    @GetMapping("/editable")
    public String editable()
    {
        return prefix + "/editable";
    }

    /**
     * 主子表提交
     */
    @GetMapping("/subdata")
    public String subdata()
    {
        return prefix + "/subdata";
    }

    /**
     * 表格自动刷新
     */
    @GetMapping("/refresh")
    public String refresh()
    {
        return prefix + "/refresh";
    }

    /**
     * 表格打印配置
     */
    @GetMapping("/print")
    public String print()
    {
        return prefix + "/print";
    }

    /**
     * 表格标题格式化
     */
    @GetMapping("/headerStyle")
    public String headerStyle()
    {
        return prefix + "/headerStyle";
    }

    /**
     * 表格动态列
     */
    @GetMapping("/dynamicColumns")
    public String dynamicColumns()
    {
        return prefix + "/dynamicColumns";
    }

    /**
     * 自定义视图分页
     */
    @GetMapping("/customView")
    public String customView()
    {
        return prefix + "/customView";
    }

    /**
     * 异步加载表格树
     */
    @GetMapping("/asynTree")
    public String asynTree()
    {
        return prefix + "/asynTree";
    }

    /**
     * 表格其他操作
     */
    @GetMapping("/other")
    public String other()
    {
        return prefix + "/other";
    }

    /**
     * 动态获取列
     */
    @PostMapping("/ajaxColumns")
    @ResponseBody
    public AjaxResult ajaxColumns(UserTableColumn userColumn)
    {
        List<UserTableColumn> columnList = new ArrayList<UserTableColumn>(Arrays.asList(new UserTableColumn[columns.size()]));
        Collections.copy(columnList, columns);
        if (userColumn != null && "userBalance".equals(userColumn.getField()))
        {
            columnList.add(new UserTableColumn("用户余额", "userBalance"));
        }
        return AjaxResult.success(columnList);
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserTableModel userModel)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<UserTableModel> userList = new ArrayList<UserTableModel>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);
        // 查询条件过滤
        if (StringUtils.isNotEmpty(userModel.getUserName()))
        {
            userList.clear();
            for (UserTableModel user : users)
            {
                if (user.getUserName().equals(userModel.getUserName()))
                {
                    userList.add(user);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize())
        {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size())
        {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }

    /**
     * 查询树表数据
     */
    @PostMapping("/tree/list")
    @ResponseBody
    public TableDataInfo treeList(AreaModel areaModel)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<AreaModel> areaList = new ArrayList<AreaModel>(Arrays.asList(new AreaModel[areas.size()]));
        // 默认查询条件 parentId 0
        Collections.copy(areaList, areas);
        areaList.clear();
        if (StringUtils.isNotEmpty(areaModel.getAreaName()))
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId() == 0 && area.getAreaName().equals(areaModel.getAreaName()))
                {
                    areaList.add(area);
                }
            }
        }
        else
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId() == 0)
                {
                    areaList.add(area);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > areaList.size())
        {
            pageSize = areaList.size();
        }
        rspData.setRows(areaList.subList(pageNum, pageSize));
        rspData.setTotal(areaList.size());
        return rspData;
    }

    /**
     * 查询树表子节点数据
     */
    @PostMapping("/tree/listChild")
    @ResponseBody
    public List<AreaModel> listChild(AreaModel areaModel)
    {
        List<AreaModel> areaList = new ArrayList<AreaModel>(Arrays.asList(new AreaModel[areas.size()]));
        // 查询条件 parentId
        Collections.copy(areaList, areas);
        areaList.clear();
        if (StringUtils.isNotEmpty(areaModel.getAreaName()))
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId().intValue() == areaModel.getParentId().intValue() && area.getAreaName().equals(areaModel.getAreaName()))
                {
                    areaList.add(area);
                }
            }
        }
        else
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId().intValue() == areaModel.getParentId().intValue())
                {
                    areaList.add(area);
                }
            }
        }
        return areaList;
    }
}

class UserTableColumn
{
    /** 表头 */
    private String title;
    /** 字段 */
    private String field;

    public UserTableColumn()
    {

    }

    public UserTableColumn(String title, String field)
    {
        this.title = title;
        this.field = field;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }
}

class UserTableModel
{
    /** 用户ID */
    private int userId;

    /** 用户编号 */
    @Excel(name = "用户编号", cellType = ColumnType.NUMERIC)
    private String userCode;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 用户性别 */
    private String userSex;

    /** 用户手机 */
    @Excel(name = "用户手机")
    private String userPhone;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String userEmail;

    /** 用户余额 */
    @Excel(name = "用户余额", cellType = ColumnType.NUMERIC)
    private double userBalance;

    /** 用户状态（0正常 1停用） */
    private String status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public UserTableModel()
    {

    }

    public UserTableModel(int userId, String userCode, String userName, String userSex, String userPhone,
            String userEmail, double userBalance, String status)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtils.getNowDate();
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex(String userSex)
    {
        this.userSex = userSex;
    }

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public double getUserBalance()
    {
        return userBalance;
    }

    public void setUserBalance(double userBalance)
    {
        this.userBalance = userBalance;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
class AreaModel
{
    /** 编号 */
    private Long id;

    /** 父编号 */
    private Long parentId;

    /** 区域名称 */
    private String areaName;

    /** 区域代码 */
    private String areaCode;

    /** 名称首字母 */
    private String simplePy;

    /** 名称全拼 */
    private String pinYin;

    /** 是否有子节点（0无 1有） */
    private Integer isTreeLeaf = 1;

    public AreaModel()
    {

    }

    public AreaModel(int id, int parentId, String areaName, String areaCode, String simplePy, String pinYin, Integer isTreeLeaf)
    {
        this.id = Long.valueOf(id);
        this.parentId = Long.valueOf(parentId);
        this.areaName = areaName;
        this.areaCode = areaCode;
        this.simplePy = simplePy;
        this.pinYin = pinYin;
        this.isTreeLeaf = isTreeLeaf;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getSimplePy()
    {
        return simplePy;
    }

    public void setSimplePy(String simplePy)
    {
        this.simplePy = simplePy;
    }

    public String getPinYin()
    {
        return pinYin;
    }

    public void setPinYin(String pinYin)
    {
        this.pinYin = pinYin;
    }

    public Integer getIsTreeLeaf()
    {
        return isTreeLeaf;
    }

    public void setIsTreeLeaf(Integer isTreeLeaf)
    {
        this.isTreeLeaf = isTreeLeaf;
    }
}
