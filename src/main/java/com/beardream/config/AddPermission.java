package com.beardream.config;

import com.beardream.Utils.ClassSearcher;
import com.beardream.Utils.TextUtil;
import com.beardream.dao.MethodMapper;
import com.beardream.dao.ModuleMapper;
import com.beardream.ioc.PermissionMethod;
import com.beardream.ioc.PermissionModule;
import com.beardream.model.Module;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by soft01 on 2017/5/7.
 */
@Component
public class AddPermission {

    private List<Class<? extends Controller>> excludeClasses = Lists.newArrayList();
    private boolean includeAllJarsInLib;
    private List<String> includeJars = Lists.newArrayList();
    private String suffix = "Controller";


    public AddPermission addExcludeClasses(Class<? extends Controller>... clazzes) {
        if (clazzes != null) {
            for (Class<? extends Controller> clazz : clazzes) {
                excludeClasses.add(clazz);
            }
        }
        return this;
    }

    public AddPermission addExcludeClasses(List<Class<? extends Controller>> clazzes) {
        excludeClasses.addAll(clazzes);
        return this;
    }

    public AddPermission addJars(String... jars) {
        if (jars != null) {
            for (String jar : jars) {
                includeJars.add(jar);
            }
        }
        return this;
    }

    public AddPermission includeAllJarsInLib(boolean includeAllJarsInLib) {
        this.includeAllJarsInLib = includeAllJarsInLib;
        return this;
    }

    public AddPermission suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    //扫描controller包下面的所有方法
    public void scanner(ModuleMapper moduleMap, MethodMapper methodMapper){
        moduleMap.truncate();
        methodMapper.truncate();
        List<Class<? extends RestController>> controllerClasses = ClassSearcher.of(RestController.class)
                .includeAllJarsInLib(includeAllJarsInLib).injars(includeJars).search();
        PermissionModule permissionController = null;
        for (Class controller : controllerClasses) {
            if (excludeClasses.contains(controller)) {
                continue;
            }
            permissionController = (PermissionModule) controller.getAnnotation(PermissionModule.class);
            if (permissionController != null) {
                Module module = new Module();
                module.setControllerkey(controllerKey(controller));
                module.setModuleName(permissionController.text());
                moduleMap.insertSelective(module);
                Method[] methods = controller.getMethods();
                for (Method method : methods) {
                    /*
                     * 根据注解类型返回方法的指定类型注解
                     */
                    PermissionMethod permissionMethod = method.getAnnotation(PermissionMethod.class);
                    if (permissionMethod != null) {
                        com.beardream.model.Method m = new com.beardream.model.Method();
                        m.setMethodName(permissionMethod.text());
                        m.setActionkey(controllerKey(controller) + methodKey(method.getName()));
                        m.setModuleId(module.getModuleId());
                        methodMapper.insertSelective(m);
                    }
                }
            }
        }
    }

    private String controllerKey(Class<Controller> clazz) {
        Preconditions.checkArgument(clazz.getSimpleName().endsWith(suffix),
                clazz.getName() + " is not annotated with @ControllerBind and not end with " + suffix);
        if (clazz.getPackage().getName().startsWith("com.beardream")) {
            String controllerKey = "/" + TextUtil.firstCharToLowerCase(clazz.getSimpleName());
            controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
            return controllerKey;
        }
        String controllerKey = "/" + TextUtil.firstCharToLowerCase(clazz.getSimpleName());
        controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
        return controllerKey;
    }

    private String methodKey(String methodname) {
        String controllerKey = "/" + TextUtil.firstCharToLowerCase(methodname);
        return controllerKey;
    }
}
