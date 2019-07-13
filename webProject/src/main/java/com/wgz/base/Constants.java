package com.wgz.base;


/**
 * @Description:常量
 * @author: wenguozhang 
 * @date:   2019年5月21日 上午10:32:36  
 */
public class Constants {
	
	//任务状态
    public static final String TASK_STATUS_INIT = "INIT";
    public static final String TASK_STATUS_PROCESS = "PROCESS";
    public static final String TASK_STATUS_SUCCESS = "SUCCESS";
    public static final String TASK_STATUS_FAIL = "FAIL";
    
    //任务启停表示：0停用，1启用
    public static final int ENABLE_FLAG_0 = 0;
    public static final int ENABLE_FLAG_1 = 1;
    
    //获取数据方式：url 通过http请求，sql 访问数据库
    public static final String SOURCE_TYPE_URL = "url";
    public static final String SOURCE_TYPE_SQL = "sql";
    
    //字段非空标识：0 可为空，1 非空
    public static final int FIELD_NULL = 0;
    public static final int FIELD_NOT_NULL = 1;
    
    //增全量标识：0 增量，1 全量
    public static final int FULL_FLAG_0 = 0;
    public static final int FULL_FLAG_1 = 1;
    
    //接口编码
    public static final String INTERFACE_MECHANISM = "synOrg";
    public static final String INTERFACE_SPECIALITY = "synSpeciality";
    public static final String INTERFACE_CLASS = "synClass";
    public static final String INTERFACE_PERSON = "synPerson";
    public static final String INTERFACE_IDENTITY = "synIdentity";
    public static final String INTERFACE_STUDENT = "synStudent";
    public static final String INTERFACE_STUDENT_CHANGE = "synStudentChange";
    public static final String INTERFACE_STANDARD = "synStandard";
    public static final String INTERFACE_STANDARD_CHANGE = "synStandardChange";
    
    //一任务批次最大数据量
    public static final int TASK_MAX_ROWS = 2000;
    
    //删除标识 ：0 正常，1 删除
    public static final int DEL_FLAG_0 = 0;
    public static final int DEL_FLAG_1 = 1;
    
    //住宿异动类型 1转2退
    public static final String STUDENT_CHANGE_TYPE_1 = "1";
    public static final String STUDENT_CHANGE_TYPE_2 = "2";
    
    //初始化字符串
    public static final String INIT_STIRNG = "INIT";
    
    //学生类型
    public static final String STUDENT_TYPE = "0103";
}
