package service.departmentAdminService;

import mapper.TbApplicationMapper;
import mapper.TbEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.TbApplication;
import pojo.TbApplicationExample;
import pojo.TbEmployee;
import pojo.TbEmployeeExample;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2017/3/25.
 */
@Service
public class DepartmentAdminService {

    @Autowired
    private TbEmployeeMapper tbEmployeeMapper;

    @Autowired
    private TbApplicationMapper tbApplicationMapper;

    public List<TbEmployee>  queryEmployee(TbEmployee tbEmployee)
    {
        TbEmployeeExample employeeExample=new TbEmployeeExample();
        TbEmployeeExample.Criteria criteria=employeeExample.createCriteria();
        criteria.andDepartmentidEqualTo(tbEmployee.getDepartmentid());
        List<TbEmployee> employeeList=tbEmployeeMapper.selectByExample(employeeExample);
        for(TbEmployee tmp:employeeList)
        {
            //System.out.println(tmp);
            tmp.setPassword(null);
        }
        return employeeList;
    }
    public List<TbApplication> queryApplication(TbEmployee tbEmployee)
    {
        List<TbEmployee> employeeList=queryEmployee(tbEmployee);
        TbApplicationExample applicationExample=new TbApplicationExample();
        TbApplicationExample.Criteria criteria=applicationExample.createCriteria();
        List<String>employIdList=new LinkedList<String>();
        for(TbEmployee tmp:employeeList)
        {
            //System.out.println(tmp)
            employIdList.add(tmp.getEmployeeid());
        }
        criteria.andEmployeeidIn(employIdList);
        criteria.andStatusEqualTo(false);
        List<TbApplication> applications=tbApplicationMapper.selectByExample(applicationExample);
        return applications;
    }
}
