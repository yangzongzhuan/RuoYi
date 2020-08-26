package com.ruoyi.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.CxSelect;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.json.JSONObject.JSONArray;
import com.ruoyi.common.utils.StringUtils;

/**
 * 表单相关
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/form")
public class DemoFormController
{
    private String prefix = "demo/form";

    private final static List<UserFormModel> users = new ArrayList<UserFormModel>();
    {
        users.add(new UserFormModel(1, "1000001", "测试1", "15888888888"));
        users.add(new UserFormModel(2, "1000002", "测试2", "15666666666"));
        users.add(new UserFormModel(3, "1000003", "测试3", "15666666666"));
        users.add(new UserFormModel(4, "1000004", "测试4", "15666666666"));
        users.add(new UserFormModel(5, "1000005", "测试5", "15666666666"));
    }

    /**
     * 按钮页
     */
    @GetMapping("/button")
    public String button()
    {
        return prefix + "/button";
    }

    /**
     * 下拉框
     */
    @GetMapping("/select")
    public String select()
    {
        return prefix + "/select";
    }

    /**
     * 时间轴
     */
    @GetMapping("/timeline")
    public String timeline()
    {
        return prefix + "/timeline";
    }

    /**
     * 表单校验
     */
    @GetMapping("/validate")
    public String validate()
    {
        return prefix + "/validate";
    }

    /**
     * 功能扩展（包含文件上传）
     */
    @GetMapping("/jasny")
    public String jasny()
    {
        return prefix + "/jasny";
    }

    /**
     * 拖动排序
     */
    @GetMapping("/sortable")
    public String sortable()
    {
        return prefix + "/sortable";
    }

    /**
     * 选项卡 & 面板
     */
    @GetMapping("/tabs_panels")
    public String tabs_panels()
    {
        return prefix + "/tabs_panels";
    }

    /**
     * 栅格
     */
    @GetMapping("/grid")
    public String grid()
    {
        return prefix + "/grid";
    }

    /**
     * 表单向导
     */
    @GetMapping("/wizard")
    public String wizard()
    {
        return prefix + "/wizard";
    }

    /**
     * 文件上传
     */
    @GetMapping("/upload")
    public String upload()
    {
        return prefix + "/upload";
    }

    /**
     * 日期和时间页
     */
    @GetMapping("/datetime")
    public String datetime()
    {
        return prefix + "/datetime";
    }

    /**
     * 左右互选组件
     */
    @GetMapping("/duallistbox")
    public String duallistbox()
    {
        return prefix + "/duallistbox";
    }

    /**
     * 基本表单
     */
    @GetMapping("/basic")
    public String basic()
    {
        return prefix + "/basic";
    }

    /**
     * 卡片列表
     */
    @GetMapping("/cards")
    public String cards()
    {
        return prefix + "/cards";
    }

    /**
     * summernote 富文本编辑器
     */
    @GetMapping("/summernote")
    public String summernote()
    {
        return prefix + "/summernote";
    }

    /**
     * 搜索自动补全
     */
    @GetMapping("/autocomplete")
    public String autocomplete()
    {
        return prefix + "/autocomplete";
    }

    /**
     * 多级联动下拉
     */
    @GetMapping("/cxselect")
    public String cxselect(ModelMap mmap)
    {
        CxSelect cxSelectTB = new CxSelect();
        cxSelectTB.setN("淘宝");
        cxSelectTB.setV("taobao");
        CxSelect cxSelectTm = new CxSelect();
        cxSelectTm.setN("天猫");
        cxSelectTm.setV("tm");
        CxSelect cxSelectJhs = new CxSelect();
        cxSelectJhs.setN("聚划算");
        cxSelectJhs.setV("jhs");
        List<CxSelect> tmList = new ArrayList<CxSelect>();
        tmList.add(cxSelectTm);
        tmList.add(cxSelectJhs);
        cxSelectTB.setS(tmList);

        CxSelect cxSelectJD = new CxSelect();
        cxSelectJD.setN("京东");
        cxSelectJD.setV("jd");
        CxSelect cxSelectCs = new CxSelect();
        cxSelectCs.setN("京东超市");
        cxSelectCs.setV("jdcs");
        CxSelect cxSelectSx = new CxSelect();
        cxSelectSx.setN("京东生鲜");
        cxSelectSx.setV("jdsx");
        List<CxSelect> jdList = new ArrayList<CxSelect>();
        jdList.add(cxSelectCs);
        jdList.add(cxSelectSx);
        cxSelectJD.setS(jdList);

        List<CxSelect> cxList = new ArrayList<CxSelect>();
        cxList.add(cxSelectTB);
        cxList.add(cxSelectJD);

        mmap.put("data", JSON.toJSON(cxList));
        return prefix + "/cxselect";
    }

    /**
     * 局部刷新
     */
    @GetMapping("/localrefresh")
    public String localRefresh(ModelMap mmap)
    {
    	JSONArray list = new JSONArray();
    	JSONObject item = new JSONObject();
    	item.put("name", "这条任务数据是由ModelMap传递到页面的，点击添加按钮后会将这条数据替换为新数据");
    	item.put("type", "默认");
    	item.put("date", "2020.06.10");
    	list.add(item);
    	mmap.put("tasks",list);
    	mmap.put("min",2);
    	mmap.put("max",10);
        return prefix + "/localrefresh";
    }
    
    /**
     * 局部刷新-添加任务
     * @param	fragment	页面中的模板名称
     * @param	taskName	任务名称
     */
    @PostMapping("/localrefresh/task")
    public String localRefreshTask(String fragment,String taskName,ModelMap mmap)
    {
    	JSONArray list = new JSONArray();
    	JSONObject item = new JSONObject();
    	item.put("name", StringUtils.defaultIfBlank(taskName, "通过电话销售过程中了解各盛市的设备仪器使用、采购情况及相关重要追踪人"));
    	item.put("type", "新增");
    	item.put("date", "2018.06.10");
    	list.add(item);
    	item = new JSONObject();
    	item.put("name", "提高自己电话营销技巧，灵活专业地与客户进行电话交流");
    	item.put("type", "新增");
    	item.put("date", "2018.06.12");
    	list.add(item);
    	mmap.put("tasks",list);
        return prefix + "/localrefresh::" + fragment;
    }
    
    /**
     * 模拟数据
     */
    @GetMapping("/cityData")
    @ResponseBody
    public String cityData()
    {
        String data = "[{\"n\":\"湖南省\",\"s\":[{\"n\":\"长沙市\",\"s\":[{\"n\":\"芙蓉区\"},{\"n\":\"天心区\"},{\"n\":\"岳麓区\"},{\"n\":\"开福区\"},{\"n\":\"雨花区\"},{\"n\":\"望城区\"},{\"n\":\"长沙县\"},{\"n\":\"宁乡县\"},{\"n\":\"浏阳市\"}]},{\"n\":\"株洲市\",\"s\":[{\"n\":\"荷塘区\"},{\"n\":\"芦淞区\"},{\"n\":\"石峰区\"},{\"n\":\"天元区\"},{\"n\":\"株洲县\"},{\"n\":\"攸县\"},{\"n\":\"茶陵县\"},{\"n\":\"炎陵县\"},{\"n\":\"醴陵市\"}]},{\"n\":\"湘潭市\",\"s\":[{\"n\":\"雨湖区\"},{\"n\":\"岳塘区\"},{\"n\":\"湘潭县\"},{\"n\":\"湘乡市\"},{\"n\":\"韶山市\"}]},{\"n\":\"衡阳市\",\"s\":[{\"n\":\"珠晖区\"},{\"n\":\"雁峰区\"},{\"n\":\"石鼓区\"},{\"n\":\"蒸湘区\"},{\"n\":\"南岳区\"},{\"n\":\"衡阳县\"},{\"n\":\"衡南县\"},{\"n\":\"衡山县\"},{\"n\":\"衡东县\"},{\"n\":\"祁东县\"},{\"n\":\"耒阳市\"},{\"n\":\"常宁市\"}]},{\"n\":\"邵阳市\",\"s\":[{\"n\":\"双清区\"},{\"n\":\"大祥区\"},{\"n\":\"北塔区\"},{\"n\":\"邵东县\"},{\"n\":\"新邵县\"},{\"n\":\"邵阳县\"},{\"n\":\"隆回县\"},{\"n\":\"洞口县\"},{\"n\":\"绥宁县\"},{\"n\":\"新宁县\"},{\"n\":\"城步苗族自治县\"},{\"n\":\"武冈市\"}]},{\"n\":\"岳阳市\",\"s\":[{\"n\":\"岳阳楼区\"},{\"n\":\"云溪区\"},{\"n\":\"君山区\"},{\"n\":\"岳阳县\"},{\"n\":\"华容县\"},{\"n\":\"湘阴县\"},{\"n\":\"平江县\"},{\"n\":\"汨罗市\"},{\"n\":\"临湘市\"}]},{\"n\":\"常德市\",\"s\":[{\"n\":\"武陵区\"},{\"n\":\"鼎城区\"},{\"n\":\"安乡县\"},{\"n\":\"汉寿县\"},{\"n\":\"澧县\"},{\"n\":\"临澧县\"},{\"n\":\"桃源县\"},{\"n\":\"石门县\"},{\"n\":\"津市市\"}]},{\"n\":\"张家界市\",\"s\":[{\"n\":\"永定区\"},{\"n\":\"武陵源区\"},{\"n\":\"慈利县\"},{\"n\":\"桑植县\"}]},{\"n\":\"益阳市\",\"s\":[{\"n\":\"资阳区\"},{\"n\":\"赫山区\"},{\"n\":\"南县\"},{\"n\":\"桃江县\"},{\"n\":\"安化县\"},{\"n\":\"沅江市\"}]},{\"n\":\"郴州市\",\"s\":[{\"n\":\"北湖区\"},{\"n\":\"苏仙区\"},{\"n\":\"桂阳县\"},{\"n\":\"宜章县\"},{\"n\":\"永兴县\"},{\"n\":\"嘉禾县\"},{\"n\":\"临武县\"},{\"n\":\"汝城县\"},{\"n\":\"桂东县\"},{\"n\":\"安仁县\"},{\"n\":\"资兴市\"}]},{\"n\":\"永州市\",\"s\":[{\"n\":\"零陵区\"},{\"n\":\"冷水滩区\"},{\"n\":\"祁阳县\"},{\"n\":\"东安县\"},{\"n\":\"双牌县\"},{\"n\":\"道县\"},{\"n\":\"江永县\"},{\"n\":\"宁远县\"},{\"n\":\"蓝山县\"},{\"n\":\"新田县\"},{\"n\":\"江华瑶族自治县\"}]},{\"n\":\"怀化市\",\"s\":[{\"n\":\"鹤城区\"},{\"n\":\"中方县\"},{\"n\":\"沅陵县\"},{\"n\":\"辰溪县\"},{\"n\":\"溆浦县\"},{\"n\":\"会同县\"},{\"n\":\"麻阳苗族自治县\"},{\"n\":\"新晃侗族自治县\"},{\"n\":\"芷江侗族自治县\"},{\"n\":\"靖州苗族侗族自治县\"},{\"n\":\"通道侗族自治县\"},{\"n\":\"洪江市\"}]},{\"n\":\"娄底市\",\"s\":[{\"n\":\"娄星区\"},{\"n\":\"双峰县\"},{\"n\":\"新化县\"},{\"n\":\"冷水江市\"},{\"n\":\"涟源市\"}]},{\"n\":\"湘西土家族苗族自治州\",\"s\":[{\"n\":\"吉首市\"},{\"n\":\"泸溪县\"},{\"n\":\"凤凰县\"},{\"n\":\"花垣县\"},{\"n\":\"保靖县\"},{\"n\":\"古丈县\"},{\"n\":\"永顺县\"},{\"n\":\"龙山县\"}]}]},{\"n\":\"广东省\",\"s\":[{\"n\":\"广州市\",\"s\":[{\"n\":\"荔湾区\"},{\"n\":\"越秀区\"},{\"n\":\"海珠区\"},{\"n\":\"天河区\"},{\"n\":\"白云区\"},{\"n\":\"黄埔区\"},{\"n\":\"番禺区\"},{\"n\":\"花都区\"},{\"n\":\"南沙区\"},{\"n\":\"萝岗区\"},{\"n\":\"增城市\"},{\"n\":\"从化市\"}]},{\"n\":\"韶关市\",\"s\":[{\"n\":\"武江区\"},{\"n\":\"浈江区\"},{\"n\":\"曲江区\"},{\"n\":\"始兴县\"},{\"n\":\"仁化县\"},{\"n\":\"翁源县\"},{\"n\":\"乳源瑶族自治县\"},{\"n\":\"新丰县\"},{\"n\":\"乐昌市\"},{\"n\":\"南雄市\"}]},{\"n\":\"深圳市\",\"s\":[{\"n\":\"罗湖区\"},{\"n\":\"福田区\"},{\"n\":\"南山区\"},{\"n\":\"宝安区\"},{\"n\":\"龙岗区\"},{\"n\":\"盐田区\"}]},{\"n\":\"珠海市\",\"s\":[{\"n\":\"香洲区\"},{\"n\":\"斗门区\"},{\"n\":\"金湾区\"}]},{\"n\":\"汕头市\",\"s\":[{\"n\":\"龙湖区\"},{\"n\":\"金平区\"},{\"n\":\"濠江区\"},{\"n\":\"潮阳区\"},{\"n\":\"潮南区\"},{\"n\":\"澄海区\"},{\"n\":\"南澳县\"}]},{\"n\":\"佛山市\",\"s\":[{\"n\":\"禅城区\"},{\"n\":\"南海区\"},{\"n\":\"顺德区\"},{\"n\":\"三水区\"},{\"n\":\"高明区\"}]},{\"n\":\"江门市\",\"s\":[{\"n\":\"蓬江区\"},{\"n\":\"江海区\"},{\"n\":\"新会区\"},{\"n\":\"台山市\"},{\"n\":\"开平市\"},{\"n\":\"鹤山市\"},{\"n\":\"恩平市\"}]},{\"n\":\"湛江市\",\"s\":[{\"n\":\"赤坎区\"},{\"n\":\"霞山区\"},{\"n\":\"坡头区\"},{\"n\":\"麻章区\"},{\"n\":\"遂溪县\"},{\"n\":\"徐闻县\"},{\"n\":\"廉江市\"},{\"n\":\"雷州市\"},{\"n\":\"吴川市\"}]},{\"n\":\"茂名市\",\"s\":[{\"n\":\"茂南区\"},{\"n\":\"茂港区\"},{\"n\":\"电白县\"},{\"n\":\"高州市\"},{\"n\":\"化州市\"},{\"n\":\"信宜市\"}]},{\"n\":\"肇庆市\",\"s\":[{\"n\":\"端州区\"},{\"n\":\"鼎湖区\"},{\"n\":\"广宁县\"},{\"n\":\"怀集县\"},{\"n\":\"封开县\"},{\"n\":\"德庆县\"},{\"n\":\"高要市\"},{\"n\":\"四会市\"}]},{\"n\":\"惠州市\",\"s\":[{\"n\":\"惠城区\"},{\"n\":\"惠阳区\"},{\"n\":\"博罗县\"},{\"n\":\"惠东县\"},{\"n\":\"龙门县\"}]},{\"n\":\"梅州市\",\"s\":[{\"n\":\"梅江区\"},{\"n\":\"梅县\"},{\"n\":\"大埔县\"},{\"n\":\"丰顺县\"},{\"n\":\"五华县\"},{\"n\":\"平远县\"},{\"n\":\"蕉岭县\"},{\"n\":\"兴宁市\"}]},{\"n\":\"汕尾市\",\"s\":[{\"n\":\"城区\"},{\"n\":\"海丰县\"},{\"n\":\"陆河县\"},{\"n\":\"陆丰市\"}]},{\"n\":\"河源市\",\"s\":[{\"n\":\"源城区\"},{\"n\":\"紫金县\"},{\"n\":\"龙川县\"},{\"n\":\"连平县\"},{\"n\":\"和平县\"},{\"n\":\"东源县\"}]},{\"n\":\"阳江市\",\"s\":[{\"n\":\"江城区\"},{\"n\":\"阳西县\"},{\"n\":\"阳东县\"},{\"n\":\"阳春市\"}]},{\"n\":\"清远市\",\"s\":[{\"n\":\"清城区\"},{\"n\":\"清新区\"},{\"n\":\"佛冈县\"},{\"n\":\"阳山县\"},{\"n\":\"连山壮族瑶族自治县\"},{\"n\":\"连南瑶族自治县\"},{\"n\":\"英德市\"},{\"n\":\"连州市\"}]},{\"n\":\"东莞市\"},{\"n\":\"中山市\"},{\"n\":\"潮州市\",\"s\":[{\"n\":\"湘桥区\"},{\"n\":\"潮安区\"},{\"n\":\"饶平县\"}]},{\"n\":\"揭阳市\",\"s\":[{\"n\":\"榕城区\"},{\"n\":\"揭东区\"},{\"n\":\"揭西县\"},{\"n\":\"惠来县\"},{\"n\":\"普宁市\"}]},{\"n\":\"云浮市\",\"s\":[{\"n\":\"云城区\"},{\"n\":\"新兴县\"},{\"n\":\"郁南县\"},{\"n\":\"云安县\"},{\"n\":\"罗定市\"}]}]}]";
        return data;
    }

    /**
     * 获取用户数据
     */
    @GetMapping("/userModel")
    @ResponseBody
    public AjaxResult userModel()
    {
        AjaxResult ajax = new AjaxResult();

        ajax.put("code", 200);
        ajax.put("value", users);
        return ajax;
    }

    /**
     * 获取数据集合
     */
    @GetMapping("/collection")
    @ResponseBody
    public AjaxResult collection()
    {
        String[] array = { "ruoyi 1", "ruoyi 2", "ruoyi 3", "ruoyi 4", "ruoyi 5" };
        AjaxResult ajax = new AjaxResult();
        ajax.put("value", array);
        return ajax;
    }
}

class UserFormModel
{
    /** 用户ID */
    private int userId;

    /** 用户编号 */
    private String userCode;

    /** 用户姓名 */
    private String userName;

    /** 用户手机 */
    private String userPhone;

    public UserFormModel()
    {

    }

    public UserFormModel(int userId, String userCode, String userName, String userPhone)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPhone = userPhone;
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

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

}
