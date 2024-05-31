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
import com.ruoyi.common.core.domain.BaseEntity;
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
    
    private final static List<DocumentModel> documents = new ArrayList<DocumentModel>();
    {
        documents.add(new DocumentModel(1, "247-XW·2024-D10-0001", "新闻热线[2024]000001", "筑路千条 幸福万家——新疆“四好农村路”十年成果显著", "新疆地域广袤，农村公路是群众出行的重要选择。顾志峰介绍，2014年以来的十年间，新疆累计完成农村公路建设投资约1071亿元，累计新改建农村公路11.99万公里。截至2023年底，全疆农村公路总里程达15.6万公里，农村公路乡镇通三级及以上公路比例达到90.3%、较大人口规模自然村通硬化路比例达到91.5%，农村公路路网进一步完善，特别是南疆四地州农村路网结构得到根本性改善。"));
        documents.add(new DocumentModel(2, "247-XW·2024-D30-0002", "新闻热线[2024]000002", "网红账号被封，央媒：如此炫富毒瘤早就该拔了", "在社交平台上分享自己的生活日常，本来无可厚非。但无底线地展示物欲、宣扬拜金，取笑甚至嘲讽工薪者的烟火生活，就会遮蔽普通人的平凡质朴和坚韧奋斗，在无形中消解芸芸众生脚踏实地、自立自强的社会正气。对这种助长金钱至上、刺激公众焦虑，既污染网络生态，又撕裂社会和谐的炫富“毒瘤”，必须坚决拔除之。在国家有关部门的部署下，近日，多个网络平台开展“不良价值导向内容专项治理”行动，对“奢靡浪费”“炫富拜金”等问题从严打击，倡导理性、文明的消费观和价值观。"));
        documents.add(new DocumentModel(3, "CT01-XW·2024-Y-0003", "新闻热线[2024]000003", "重庆一夫妻被骗至缅甸，家属：两人已被解救，预计很快能回国", "5月25日，重庆一对夫妻在前往泰国后失联，疑被诈骗集团骗至缅甸的消息引发广泛关注。警方已对此事立案调查，而这对夫妻的亲属则每天生活在焦急和不安之中。亲属：家都瘫痪了，事情一经曝光，迅速登上了热搜，成为公众热议的话题。据了解，这对夫妻原计划是去泰国谈生意，但不幸的是，他们的泰国之行变成了一场噩梦。亲属李先生透露，4月14日，他们夫妻二人抵达泰国，不久后便疑似被人以10万元的价格卖到缅甸，目前被困在缅甸妙瓦底的一个电信诈骗园区。"));
        documents.add(new DocumentModel(4, "CT01-XW·2024-Y-0004", "新闻热线[2024]000004", "江滨社区联合派出所、金霞消防站开展电动自行车安全隐患夜查活动", "近日，长沙市开福区江滨社区联合派出所、金霞消防站深入居民小区、单位场所，以电动车自行车火灾防范为重点，开展消防安全夜查行动。此次夜查紧紧围绕老旧居民区、“三合一”场所、沿街门店、夜间经营使用场所等场所开展监督检查，重点检查电动自行车违规停放充电、堵塞疏散通道和安全出口，架空层违规作为电动自行车停放充电场所，电动自行车违规“进楼入户”“飞线充电”，电动自行车擅自改装等五大类问题。"));
        documents.add(new DocumentModel(5, "CT01-XW·2024-Y-0005", "新闻热线[2024]000005", "奋力建设“七个岳阳”，筑牢民生保障 奔向美好生活", "岳阳市委八届六次全会提出，坚持“1376”总体思路，着力推动高质量发展。“1”即始终牢记习近平总书记“守护好一江碧水”殷殷嘱托;“3”即在锚定“三高四新”美好蓝图中强化融入长江经济带、省域副中心、内陆地区改革开放高地三种意识;“7”即全面把握打造实力岳阳、富饶岳阳、美丽岳阳、开放岳阳、幸福岳阳、平安岳阳、新风岳阳“七个岳阳”目标任务;“6”即抓紧抓实“抓产业、促协调、优生态、扩开放、惠民生、防风险”六项重点工作。“1376”总体思路站位高远，契合实际，是岳阳深入学习贯彻落实习近平新时代中国特色社会主义思想，向着强国建设、民族复兴宏伟目标奋勇前进的行动方案、奋进号令。为全面展示岳阳锚定新坐标、攀登新高度的奋斗姿态，营造催人奋进的舆论氛围，从2月1日起，湖南日报社岳阳分社联动岳阳日报社、岳阳市广播电视台等主流媒体推出《奋力建设“七个岳阳”》系列报道。敬请关注。"));
        documents.add(new DocumentModel(6, "CT01-XW·2024-Y-0006", "新闻热线[2024]000006", "市场状况充满挑战！极星宣布裁员全球约15%的员工", "据路透社报道，极星周五以“充满挑战的市场状况”为由，宣布计划在全球范围内裁减约450个职位。此次裁员之际，许多人都对电动汽车需求降温表示担忧，而且极星预计汽车业务最早将在2025年开始实现收支平衡。极星发言人表示：“作为该商业计划的一部分，我们需要调整我们的业务和运营规模。” “这涉及减少外部支出，遗憾的是，还包括我们的员工数量。”该公司还表示，希望减少对沃尔沃及其母公司吉利外部融资的依赖，最近还表示希望削减成本并提高电动汽车的利润率。"));
        documents.add(new DocumentModel(7, "CT01-XW·2024-Y-0007", "新闻热线[2024]000007", "浙江隐秘富豪涉百亿非法集资案，部分资金流入新造车公司", "5月10日、11日，上海北广投资管理有限公司（下称“北广投”）非法集资案在上海黄浦区人民法院一审公开开庭审理，北广投实控人周敏、法定代表人朱江等30余名中高管被控非法吸收公众存款罪。根据财新报道，这一案件中，非法集资的资金有部分流入了两家新能源车企——爱驰汽车、万象汽车。同时，有多位投资人引述与经侦部门沟通时的说法称，该案事发时未兑付金额有130余亿元，其中去往广微控股45亿元、万象汽车63亿元、爱驰汽车15亿元。"));
        documents.add(new DocumentModel(8, "CT01-XW·2024-Y-0008", "新闻热线[2024]000008", "特斯拉宣布Model Y升级：搭载HW4.0硬件，售价仍25.89万元起", "2月1日，特斯拉官方宣布ModelY升级。外观上，新增ModelY专属色“快银车漆”，并采用烈焰红代替中国红、星空灰代替冷光银；性能上，ModelY全系配备全新一代自动辅助驾驶硬件(HW4.0)，通过搭载超远距离双目摄像头，ModelY的最远探测距离达424米。由此，特斯拉全系车型均配备了自动辅助驾驶硬件HW4.0。在售价方面，特斯拉中国官网显示，ModelY车型依然保持原价。ModelY后轮驱动版25.89万元起、ModelY长续航版29.99万元起、ModelY高性能版售价36.39万元起。"));
        documents.add(new DocumentModel(9, "CT01-XW·2024-D10-0009", "新闻热线[2024]000009", "华为手机归来，谁最受伤？", "低迷周期下的智能手机市场在2023年下半年迎来了华为的回归，这也给本就竞争激烈的市场环境带来了更大变数。1月29日，有消息称，华为已注册“星耀手机”品牌商标，定位中端手机市场，但上述消息并未获得华为方面确认。“目前星耀的相关信息我们看到了，但是没有获得产品信息以及启动线下铺货的通知。对于和其他品牌的二选一问题，听其他省份的经销商说过，但目前（华为渠道）这边也没有更多动作。”一位广东区域的华为核心经销商对记者说。但华为手机的反扑已经开始。在多家调研机构公布的2023年四季度智能手机出货数据中，华为手机的量正在明显上升，当季增幅在35%到47%之间。不过，从全年数据来看，并未登上前五榜单。"));
        documents.add(new DocumentModel(10, "CT01-XW·2024-D10-0010", "新闻热线[2024]000010", "疯狂裁员的硅谷大厂：除了AI，其它都是将就", "放眼望去，近期科技企业财报形势一片大好，裁员浪潮却仍在不断蔓延。国内职场动态看脉脉，那硅谷裁员情况就得看layoff.fyi了。数据显示，2024年，103家科技企业进行了裁员，28963位员工失去了饭碗。其中，电子支付公司PayPal大笔一挥，裁掉2500人，微软则在开年就裁掉1900人。回望2023年，谷歌、Meta、亚马逊、微软均为裁员重灾区，裁员人数在一万左右。具体而言，谷歌近日披露的财报指出，2023年谷歌解雇了12000多名员工，光是在遣散费和其他费用上就花费了21亿美元。而且裁员费用还在不断增加，2024年刚过去一个月，谷歌就已经花费了7亿美元用来裁员。"));
        documents.add(new DocumentModel(11, "CT01-XW·2024-D30-0011", "新闻热线[2024]000011", "国产手机品牌重新崛起背后：市场正在逐步恢复活力，竞争也愈发激烈", "2024年伊始，随着全球经济的逐渐复苏，手机消费市场也展现出勃勃生机。中国信通院最新数据显示，2023年中国市场手机出货量实现了6.5%的同比增长，其中5G手机增长势头更为强劲，占比高达82.8%。1月25日，国际数据公司（IDC）发布了最新手机季度跟踪报告，揭示了中国智能手机市场在2023年第四季度的出货量情况。报告显示，该季度中国智能手机市场出货量达到了约7363万台，同比增长1.2%。这是在连续十个季度同比下降后，中国智能手机市场首次实现反弹。这一积极信号表明，市场正在逐步恢复活力，各大品牌之间的竞争也愈发激烈。"));
        documents.add(new DocumentModel(12, "CT01-XW·2024-D30-0012", "新闻热线[2024]000012", "SpaceX将于1月31日向国际空间站发射天鹅号货运飞船", "1月29日消息，美国太空探索技术公司SpaceX计划于当地时间1月30日，利用“猎鹰9号”火箭从佛罗里达州肯尼迪航天中心发射诺斯罗普·格鲁曼公司的“天鹅号”货运飞船至国际空间站。此次任务是执行NG-20商业补给，将运送约8200多磅的物资、设备及科学实验器材。"));
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
     * 表格虚拟滚动
     */
    @GetMapping("/virtualScroll")
    public String virtualScroll()
    {
        return prefix + "/virtualScroll";
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
     * 全文索引
     */
    @GetMapping("/textSearch")
    public String textSearch()
    {
        return prefix + "/textSearch";
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
     * 查询全文索引数据
     */
    @PostMapping("/text/list")
    @ResponseBody
    public TableDataInfo textList(BaseEntity baseEntity)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<DocumentModel> documentList = new ArrayList<DocumentModel>(Arrays.asList(new DocumentModel[documents.size()]));
        Collections.copy(documentList, documents);
        // 查询条件过滤
        if (StringUtils.isNotEmpty(baseEntity.getSearchValue()))
        {
            documentList.clear();
            for (DocumentModel document : documents)
            {
                boolean indexFlag = false;
                if (document.getTitle().contains(baseEntity.getSearchValue()))
                {
                    indexFlag = true;
                    document.setTitle(document.getTitle().replace(baseEntity.getSearchValue(), "<font color=\"red\">" + baseEntity.getSearchValue() + "</font>"));
                }
                if (document.getContent().contains(baseEntity.getSearchValue()))
                {
                    indexFlag = true;
                    document.setContent(document.getContent().replace(baseEntity.getSearchValue(), "<font color=\"red\">" + baseEntity.getSearchValue() + "</font>"));
                }
                if (indexFlag)
                {
                    documentList.add(document);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize())
        {
            rspData.setRows(documentList);
            rspData.setTotal(documentList.size());
            return rspData;
        }
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > documentList.size())
        {
            pageSize = documentList.size();
        }
        rspData.setRows(documentList.subList(pageNum, pageSize));
        rspData.setTotal(documentList.size());
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
        Integer pageNum = (pageDomain.getPageNum() - 1) * pageDomain.getPageSize();
        Integer pageSize = pageDomain.getPageNum() * pageDomain.getPageSize();
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

class DocumentModel
{
    /** 编号 */
    private int tableId;

    /** 档号 */
    private String archiveNo;

    /** 文件编号 */
    private String docNo;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    public DocumentModel()
    {

    }

    public DocumentModel(int tableId, String archiveNo, String docNo, String title, String content)
    {
        this.tableId = tableId;
        this.archiveNo = archiveNo;
        this.docNo = docNo;
        this.title = title;
        this.content = content;
    }

    public int getTableId()
    {
        return tableId;
    }

    public String getArchiveNo()
    {
        return archiveNo;
    }

    public String getDocNo()
    {
        return docNo;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public void setArchiveNo(String archiveNo)
    {
        this.archiveNo = archiveNo;
    }

    public void setDocNo(String docNo)
    {
        this.docNo = docNo;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
