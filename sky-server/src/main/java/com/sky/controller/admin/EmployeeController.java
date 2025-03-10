package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeedDTO
     * @return
     */
    @PostMapping("/addemploy")
    public Result<String> addEmploy(@RequestBody EmployeeDTO employeedDTO){
        employeeService.addEmploy(employeedDTO);
        return Result.success("新增成功");
    }

    /**
     * 员工分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> page(@RequestBody EmployeePageQueryDTO employeeQueryDTO){
        log.info("员工分页查询,{}", employeeQueryDTO);
        PageResult result =  employeeService.pagequery(employeeQueryDTO);
        return Result.success(result);
    }

    // TODO 员工状态修改,更改员工条件的时候,直接创建新的employee对象,然后对要修改的属性进行赋值,最后用动态sql判断哪些值不为null,不为null的值进行修改
    /**
     * 启用禁用员工账号,传过来的有id和status
     */

    // TODO 编辑员工,跟员工状态修改差不多,直接创建新的employee对象,然后对要修改的属性进行赋值,最后用动态sql判断哪些值不为null,不为null的值进行修改
    /**
     * 编辑员工
     */


}
