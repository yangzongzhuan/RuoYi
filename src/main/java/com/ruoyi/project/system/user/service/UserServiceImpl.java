package com.ruoyi.project.system.user.service;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.ExcelImportUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.shiro.service.PasswordService;
import com.ruoyi.project.system.dept.domain.Dept;
import com.ruoyi.project.system.dept.service.IDeptService;
import com.ruoyi.project.system.post.domain.Post;
import com.ruoyi.project.system.post.mapper.PostMapper;
import com.ruoyi.project.system.role.domain.Role;
import com.ruoyi.project.system.role.mapper.RoleMapper;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.domain.UserPost;
import com.ruoyi.project.system.user.domain.UserRole;
import com.ruoyi.project.system.user.mapper.UserMapper;
import com.ruoyi.project.system.user.mapper.UserPostMapper;
import com.ruoyi.project.system.user.mapper.UserRoleMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 用户 业务层处理
 * 
 * @author ruoyi
 */
@Service("userService")
public class UserServiceImpl implements IUserService
{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private IDeptService deptService;


    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * 
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int batchDeleteUser(Long[] ids)
    {
        userRoleMapper.deleteUserRole(ids);
        userPostMapper.deleteUserPost(ids);
        return userMapper.batchDeleteUser(ids);
    }

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int saveUser(User user)
    {
        int count = 0;
        Long userId = user.getUserId();
        if (StringUtils.isNotNull(userId))
        {
            user.setUpdateBy(ShiroUtils.getLoginName());
            // 修改用户信息
            count = updateUser(user);
            // 删除用户与角色关联
            userRoleMapper.deleteUserRoleByUserId(userId);
            // 新增用户与角色管理
            insertUserRole(user);
            // 删除用户与岗位关联
            userPostMapper.deleteUserPostByUserId(userId);
            // 新增用户与岗位管理
            insertUserPost(user);

        }
        else
        {
            user.randomSalt();
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
            user.setCreateBy(ShiroUtils.getLoginName());
            // 新增用户信息
            count = userMapper.insertUser(user);
            // 新增用户岗位关联
            insertUserPost(user);
            // 新增用户与角色管理
            insertUserRole(user);
        }
        return count;
    }

    /**
     * 根据Execl 批量保存用户
     *  1.  使用HSSFWorkbook 打开或者创建 “Excel对象”
     *  2.  用HSSFWorkbook返回对象或者创建sheet对象
     *  3.  用sheet返回行对象，用行对象得到Cell对象
     *  4.  对Cell对象进行读写
     * @param myFile
     * @return
     */
    @Override
    public int batchImportUsers(MultipartFile myFile) {
        //Excel工作簿
        Workbook workbook=null;
        //获取文件名
        String filename=myFile.getOriginalFilename();
        log.info("【ExeclfileName】{}",filename);
         //根据文件名判断文件是2003版本还是2007版本
        if(ExcelImportUtils.isExcel2003(filename)){
            try {
                workbook=new HSSFWorkbook(myFile.getInputStream());//2003版本
            }catch (IOException e){
              log.error("获取Excel2003流错误"+e.getMessage());
            }
        }else  if(ExcelImportUtils.isExcel2007(filename)){
            try {
                workbook=new XSSFWorkbook(myFile.getInputStream());//2007以上版本
            }catch (IOException e){
                log.error("获取Excel2007以上版本流错误"+e.getMessage());
            }
        }else{

            throw new UserException("1000",new Object[]{"文件不是Excel格式"});
        }
        //得到第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet.getLastRowNum();
        log.info("【rows】{}",totalRows);
        //新建用户list
        List<User> users=new ArrayList<User>();

        List<Dept> depts;
        List<Role> roles;
        List<Post> posts;

        //如果行数为空
        /**
         * getPhysicalNumberOfRows
         *
         *     获取有记录的行数，即：最后有数据的行是第n行，前面有m行是空行没数据，则返回n-m；
         */
        if((totalRows==0)&&(sheet.getPhysicalNumberOfRows()==0)){
            throw new UserException("1001",new Object[]{"数据为空 请填写数据"});
        }else{
            //获取全部部门信息
              depts=deptService.selectDeptAll();
            //获取全部角色信息
              roles=roleMapper.selectRolesAll();
            //获取全部岗位信息
              posts=postMapper.selectPostAll();
        }

       for(int i=1;i<=totalRows;i++){
           Row row = sheet.getRow(i);
           if(row!=null){
               User user=new User();
               //登录名（用户名）
               String userName=ExcelImportUtils.getCellValue(row.getCell(0));
               if(userName.isEmpty()){
                   continue;
               }else{
                   //判断用户名是否唯一
                 if(checkLoginNameUnique(userName).equals(UserConstants.USER_NAME_UNIQUE)){
                     user.setLoginName(userName);
                 }else {
                     log.error("【rows】{}行用户名已经存在",i+1);
                     continue;
                 }
               }
               //姓名
               String userRealName=ExcelImportUtils.getCellValue(row.getCell(1));
               user.setUserName(userRealName);
               //性别
               String  userSex=ExcelImportUtils.getCellValue(row.getCell(2));
               if(StringUtils.isNotEmpty(userSex)){
                   if(userSex.equals("男")){
                       user.setSex("0");
                   }else if(userSex.equals("女")){
                       user.setSex("1");
                   }else {
                       user.setSex("2");
                   }
               }
               //密码
               String passWord=ExcelImportUtils.getCellValue(row.getCell(3));
               user.randomSalt();
               user.setPassword(passwordService.encryptPassword(userName, passWord, user.getSalt()));
               //部门
               String dept=ExcelImportUtils.getCellValue(row.getCell(4));
               if(StringUtils.isNotEmpty(dept)){
                  for (int k=0;k<depts.size();k++){
                       if(dept.equals(depts.get(k).getDeptName())){
                           user.setDeptId(depts.get(k).getDeptId());
                           break;
                       }
                  }
               }
               user.setCreateBy(ShiroUtils.getLoginName());
               //角色--多个角色以","分割
               String userRolesExcel=ExcelImportUtils.getCellValue(row.getCell(5));
               if(StringUtils.isNotEmpty(userRolesExcel)){
                   //Set可以去掉重复的值，
                   Set<Long> sets=new HashSet<Long>();
                   //判断是否有"," 号
                   if(userRolesExcel.contains(",")){
                       List<String> results= Arrays.asList(userRolesExcel.split(","));
                       for(String s:results){
                           for(int l=0;l<roles.size();l++){
                               if(s.equals(roles.get(l).getRoleName())){
                                   sets.add(roles.get(l).getRoleId());
                                     break;
                               }
                           }
                       }

                   }else {
                      for(int j=0;j<roles.size();j++){
                          if(userRolesExcel.equals(roles.get(j).getRoleName())){
                              sets.add(roles.get(j).getRoleId());
                                break;
                          }
                      }

                   }
                   for(Long longTes:sets){
                       log.info("username={},longTes={}",userName,longTes);
                   }
                   user.setRoleIds((Long[]) sets.toArray(new Long[sets.size()]));
               }

               //岗位--多个岗位以","分割
               String userPostExcel=ExcelImportUtils.getCellValue(row.getCell(6));
               if(StringUtils.isNotEmpty(userPostExcel)){
                   //去掉重复的值，
                   Set<Long> setPosts=new HashSet<Long>();
                   //判断是否有"," 号
                   if(userPostExcel.contains(",")){
                       List<String> resultsp= Arrays.asList(userPostExcel.split(","));
                       for(String p:resultsp){
                           for(int h=0;h<posts.size();h++){
                               if(p.equals(posts.get(h).getPostName())){
                                   setPosts.add(posts.get(h).getPostId());
                                   break;
                               }
                           }
                       }

                   }else {
                       for(int m=0;m<posts.size();m++){
                           if(userPostExcel.equals(posts.get(m).getPostName())){
                               setPosts.add(posts.get(m).getPostId());
                               break;
                           }
                       }

                   }

                   for(Long longTest:setPosts){
                       log.info("username={},longTest={}",userName,longTest);
                   }
                   user.setPostIds((Long[]) setPosts.toArray(new Long[setPosts.size()]));
               }

               //手机号
               String phoneNumber=ExcelImportUtils.getCellValue(row.getCell(7));
               if(StringUtils.isNotEmpty(phoneNumber)){
                   //验证是否是手机号
                   if(phoneNumber.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN)){
                       user.setPhonenumber(phoneNumber);
                   }
               }
               //邮箱
               String userEmail=ExcelImportUtils.getCellValue(row.getCell(8));
               if(StringUtils.isNotEmpty(userEmail)){
                   //验证是否是邮箱
                   if(userEmail.matches(UserConstants.EMAIL_PATTERN)){
                       user.setEmail(userEmail);
                   }
               }
            users.add(user);
           }
       }
       //实际添加行数
       int realRow=0;
        //如果添加的列表不为空
       if(users.size()>0){
           //批量插入用户
            realRow=userMapper.batchAddUser(users);
       }
       System.out.println(realRow);
        if(realRow>0){
            //用户和角色关联
            List<UserRole> userRoles=new ArrayList<UserRole>();
            //用户和岗位关联
            List<UserPost> userPosts=new ArrayList<UserPost>();
          for(User test:users){
              //添加用户-角色关联表
              for(int q=0;q<test.getRoleIds().length;q++){
                    UserRole userRole=new UserRole();
                    userRole.setUserId(test.getUserId());
                    userRole.setRoleId(test.getRoleIds()[q]);
                    userRoles.add(userRole);

              }

              for(int r=0;r<test.getPostIds().length;r++){
                  UserPost userPost=new UserPost();
                  userPost.setUserId(test.getUserId());
                  userPost.setPostId(test.getPostIds()[r]);
                  userPosts.add(userPost);
              }

          }
            //批量添加用户-角色关联数据
            userRoleMapper.batchUserRole(userRoles);

          //批量添加用户-岗位关联数据
            userPostMapper.batchUserPost(userPosts);
;
        }

        return  realRow;
    }

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return updateUser(user);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds())
        {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0)
        {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        for (Long postId : user.getPostIds())
        {
            UserPost up = new UserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0)
        {
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName)
    {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0)
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        if (user.getUserId() == null)
        {
            user.setUserId(-1L);
        }
        Long userId = user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getUserId())
                && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        if (user.getUserId() == null)
        {
            user.setUserId(-1L);
        }
        Long userId = user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getUserId())
                && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }
}
