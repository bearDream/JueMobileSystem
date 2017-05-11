package com.beardream.model;

public class Method {
    private Integer methodId;

    private String methodName;

    private String methodContent;

    private Integer moduleId;

    private String actionkey;

    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getMethodContent() {
        return methodContent;
    }

    public void setMethodContent(String methodContent) {
        this.methodContent = methodContent == null ? null : methodContent.trim();
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getActionkey() {
        return actionkey;
    }

    public void setActionkey(String actionkey) {
        this.actionkey = actionkey == null ? null : actionkey.trim();
    }

    @Override
    public String toString() {
        return "Method{" +
                "methodId=" + methodId +
                ", methodName='" + methodName + '\'' +
                ", methodContent='" + methodContent + '\'' +
                ", moduleId=" + moduleId +
                ", actionkey='" + actionkey + '\'' +
                '}';
    }
}