package com.cn.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import static com.cn.controller.BaseController.getResponse;

@Component
public class ActivitiUtil {
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 与流程定义和部署对象相关的Service
     */
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

    /**
     * 部署工作流
     * @param zipStream
     */
    public void deployWorkFlow(InputStream zipStream){
        repositoryService.createDeployment().addZipInputStream(new ZipInputStream(zipStream)).deploy();
    }

    /**
     * 根据流程实例id获取单个流程(ProcessDefinition) 对象
     * @param processInstanceId
     * @return
     */
    public ProcessDefinition getSingleResultProcessDefinition(String processInstanceId){
       return repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstanceId).singleResult();
    }
    /**
     * 获取所有流程
     */
    public List<ProcessDefinition> getProcessDefinitionList(){
        return repositoryService.createProcessDefinitionQuery().latestVersion().list();
    }

    /**
     * 查询流程定义
     */
    public void getProcessDefinition(){
        repositoryService.createProcessDefinitionQuery().deploymentId("");
        List<ProcessDefinition> list =
                repositoryService.createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件,where条件*/
//				.deploymentId(deploymentId)//使用部署对象ID查询
//				.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//				.processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//				.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//				.orderByProcessDefinitionVersion().desc()//按照版本的降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//				.singleResult();//返回惟一结果集
//				.count();//返回结果集数量
//			    .listPage(firstResult, maxResults);//分页查询
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID："+pd.getDeploymentId());
                System.out.println("#########################################################");
            }
        }
    }

    /**
     * 删除流程定义 ，不带级联的删除，只能删除没有启动的流程，如果流程启动，就会抛出异常
     * @param deploymentId 流程部署id
     */
    public void deleteProcessDefinition(String deploymentId){
        repositoryService.deleteDeployment(deploymentId);
    }

    /**
     *  删除流程定义
     *      级联删除,不管流程是否启动，都能可以删除
     * @param deploymentId 流程部署id
     * @param flag
     */
    public void deleteProcessDefinition(String deploymentId,boolean flag){
//        repositoryService.deleteDeployment(deploymentId, true);
        repositoryService.deleteDeployment(deploymentId,flag);
    }

    /**
     *  读取流程
     */
    public void viewPic(String deploymentId,String resourceName) throws IOException {
        //获取图片的输入流
        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        /// 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len = -1;
        if(resourceName.contains("bpmn")){
            getResponse().setHeader("Content-Type","text/xml");
        }
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            getResponse().getOutputStream().write(b, 0, len);
        }
        getResponse().getOutputStream().flush();
    }



    public static void main(String[] args) {
//        ActivitiUtil a  = new ActivitiUtil();
//        ProcessInstance processInstance = a.getProcessInstance("", 1, new HashMap());
//        processInstance.getId();

    }

}
