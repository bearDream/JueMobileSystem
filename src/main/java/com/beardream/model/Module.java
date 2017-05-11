package com.beardream.model;

public class Module {
    private Integer moduleId;

    private String moduleName;

    private String moduleContent;

    private String controllerkey;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(String moduleContent) {
        this.moduleContent = moduleContent == null ? null : moduleContent.trim();
    }

    public String getControllerkey() {
        return controllerkey;
    }

    public void setControllerkey(String controllerkey) {
        this.controllerkey = controllerkey == null ? null : controllerkey.trim();
    }
}