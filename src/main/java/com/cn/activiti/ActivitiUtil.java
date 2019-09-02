package com.cn.activiti;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActivitiUtil {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FormService formService;

    /**
     *  启动流程
     * @param key  部署该流程定义的id
     * @param id    关联实体类的主键
     * @param variables 参数
     * @return
     */
    public ProcessInstance getProcessInstance(String key, Integer id, Map variables) {
        return runtimeService.startProcessInstanceByKey(key,id.toString(), variables);
    }

    public static void main(String[] args) {
//        ActivitiUtil a  = new ActivitiUtil();
//        ProcessInstance processInstance = a.getProcessInstance("", 1, new HashMap());
//        processInstance.getId();

    }

}
