package edu.hnust.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.hnust.application.dto.PageBean;
import edu.hnust.application.dto.ServiceResponse;
import edu.hnust.application.exception.ServiceException;
import edu.hnust.application.orm.user.User;
import edu.hnust.application.service.IUserService;

@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private IUserService userService;
    
    private static final Logger log = Logger.getLogger(UserController.class);// 日志文件
    
    @RequestMapping("/index")
    @ResponseBody
    public ServiceResponse<User> index(Model model) {
        System.out.println("index");
        User user = new User();
        user.setId(1);
        user.setLoginName("aaa");
        user.setState(1);
        user.setUserName("测试用户123");
        user.setPassword("admin123");
        user.setLoginName("test123");
        user.setState(0);
        user.setCreateUser(1);
        user.setCreateDate("2017-04-13 16:55:00");
        user.setUpdateDate("2017-04-13 16:55:00");
        
        Integer id = -1;
        try {
            System.out.println(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        ServiceResponse<User> serviceResponse = new ServiceResponse<User>();
        serviceResponse.setResult(user);
        return serviceResponse;
    }
    
    /**
     * 修改密码
     *
     * @param user
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    public ServiceResponse<Map<String, Boolean>> modifyPassword(User user)
        throws Exception {
        ServiceResponse<Map<String, Boolean>> serviceResponse = new ServiceResponse<Map<String, Boolean>>();
        String MD5pwd = user.getPassword();
        user.setPassword(MD5pwd);
        Boolean resultTotal = userService.updateUser(user);
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        
        result.put("success", resultTotal);
        log.info("request: user/modifyPassword , user: " + user.toString());
        serviceResponse.setResult(result);
        return serviceResponse;
    }
    
    /**
     * @param page
     * @param rows
     * @param s_user
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public ServiceResponse<PageBean<User>> list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, User s_user)
        throws Exception {
        PageBean<User> pageBean = new PageBean<User>(Integer.parseInt(page), Integer.parseInt(rows));
        ServiceResponse<PageBean<User>> serviceResponse = new ServiceResponse<PageBean<User>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "%" + s_user.getUserName() + "%");
        map.put("start", pageBean.getCurrentPage());
        map.put("size", pageBean.getPageLimit());
        List<User> userList = userService.findUser(map);
        pageBean.setPageList(userList);
        Long total = userService.getTotalUser(map);
        pageBean.setTotalRows(total);
        serviceResponse.setResult(pageBean);
        log.info("request: user/list , map: " + map.toString());
        return serviceResponse;
    }
    
    /**
     * 添加或修改管理员
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public ServiceResponse<Boolean> save(User user)
        throws Exception {
        boolean resultTotal = false;
        user.setPassword(user.getPassword());
        if (user.getId() == null) {
            resultTotal = userService.addUser(user);
        } else {
            resultTotal = userService.updateUser(user);
        }
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        serviceResponse.setResult(resultTotal);
        log.info("request: user/save , user: " + user.toString());
        return serviceResponse;
    }
    
    /**
     * 删除管理员
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public ServiceResponse<Map<String, Object>> delete(@RequestParam(value = "ids") String ids)
        throws Exception {
        ServiceResponse<Map<String, Object>> serviceResponse = new ServiceResponse<>();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            userService.deleteUser(Integer.parseInt(idsStr[i]));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        log.info("request: user/delete , ids: " + ids);
        serviceResponse.setResult(result);
        return serviceResponse;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(User user) {
        ServiceResponse<User> serviceResponse = new ServiceResponse<User>();
        try {
            log.info("==============login==========####" + user.toString());
            user = userService.login(user);
            log.info("==============login-result==========####" + user);
            serviceResponse.setResult(user);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping(value = "/queryUser", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<List<User>> queryUser(@RequestBody Map<String, Object> map) {
        ServiceResponse<List<User>> serviceResponse = new ServiceResponse<>();
        try {
            log.info("queryUser:" + map);
            List<User> userList = userService.findUser(map);
            serviceResponse.setResult(userList);
            serviceResponse.setCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<Boolean> updateUser(@RequestBody User user) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        try {            
            Boolean result = userService.updateUser(user);
            serviceResponse.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping(value = "/getTotalUser", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<Long> getTotalUser(@RequestBody Map<String, Object> map) {
        ServiceResponse<Long> serviceResponse = new ServiceResponse<>();
        try {
            Long result = userService.getTotalUser(map);
            serviceResponse.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<Boolean> addUser(@RequestBody User user) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        try {
            Boolean result = userService.addUser(user);
            serviceResponse.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<Integer> deleteUser(@RequestBody String id) {
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            int result = userService.deleteUser(Integer.parseInt(id));
            serviceResponse.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping("/findUserById")
    @ResponseBody
    public ServiceResponse<User> findUserById(@RequestBody String id) {
        ServiceResponse<User> serviceResponse = new ServiceResponse<>();
        try {
            User user = userService.findById(Integer.parseInt(id));
            serviceResponse.setCode(200);
            serviceResponse.setResult(user);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
    }
    
    @RequestMapping("/findUsersByGroupId")
    @ResponseBody
    public ServiceResponse<List<User>> findUsersByGroupId(@RequestBody String id) {
        ServiceResponse<List<User>> serviceResponse = new ServiceResponse<>();
        try {
            List<User> users = userService.findUsersByGroupId(Integer.parseInt(id));
            serviceResponse.setResult(users);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;
        
    }
    
    @RequestMapping("/findUsersByGroupIdExcept")
    @ResponseBody
    public ServiceResponse<List<User>> findUsersByGroupIdExcept(@RequestBody String id) {
        ServiceResponse<List<User>> serviceResponse = new ServiceResponse<>();
        try {
            List<User> users = userService.findUsersByGroupIdExcept(Integer.parseInt(id));
            serviceResponse.setResult(users);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;        
    }
    
    @RequestMapping("/findUsersByFactoryId")
    @ResponseBody
    public ServiceResponse<List<User>> findUsersByFactoryId(@RequestBody String id) {
        ServiceResponse<List<User>> serviceResponse = new ServiceResponse<>();
        try {
            List<User> users = userService.findUsersByFactoryId(Integer.parseInt(id));
            serviceResponse.setResult(users);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;        
    }
    
    @RequestMapping("/findUsersByProviderId")
    @ResponseBody
    public ServiceResponse<List<User>> findUsersByProviderId(@RequestBody String id) {
        ServiceResponse<List<User>> serviceResponse = new ServiceResponse<>();
        try {
            List<User> users = userService.findUsersByProviderId(Integer.parseInt(id));
            serviceResponse.setResult(users);
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return serviceResponse;        
    }    
}