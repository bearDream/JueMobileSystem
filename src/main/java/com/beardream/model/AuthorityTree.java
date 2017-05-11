package com.beardream.model;

import java.util.List;

/**
 * Created by laxzh on 2017/5/7.
 */
public class AuthorityTree {


    private String controllerKey;

    private String moduleName;

    private List<Method> method;

    public AuthorityTree() {}

    public AuthorityTree(String controllerKey, String moduleName, List<Method> method) {
        this.controllerKey = controllerKey;
        this.moduleName = moduleName;
        this.method = method;
    }

    public String getControllerKey() {
        return controllerKey;
    }

    public void setControllerKey(String controllerKey) {
        this.controllerKey = controllerKey;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Method> getMethod() {
        return method;
    }

    public void setMethod(List<Method> method) {
        this.method = method;
    }
}
