/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import support.JarScan;
import support.StringAdapter;
import support.logic.Right;
import support.logic.RightStack;

/**
 *
 * @author Кот
 */
public class RightSeeker {

    public Map<String, Right> mapping = new HashMap();

    public RightStack createSpringRightsFromJar(Class startclass,String prefix) throws Exception {
        mapping = new HashMap();
        RightStack result = RightStack.getInstance(prefix);
        Collection string = JarScan.scanClasses(JarScan.getFilePathToClasses(startclass));

        for (Object cls : string) {
            Class cl = Class.forName(cls.toString());

            if (cl.isAnnotationPresent(org.springframework.stereotype.Controller.class)) {
                Object obj = cl.newInstance();
                Method[] methods = obj.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    String mappingName = "";
                    if (cl.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping rm = (RequestMapping) cl.getAnnotation(RequestMapping.class);
                        if (rm.value().length > 0) {
                            mappingName = rm.value()[0];
                        }
                    }
                    if (method.isAnnotationPresent(support.commons.Right.class)) {
                        support.commons.Right methAnn = method.getAnnotation(support.commons.Right.class);
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping rmmethod = method.getAnnotation(RequestMapping.class);
                            if (rmmethod.value().length > 0) {
                                mappingName += rmmethod.value()[0];
                            }

                        }

                        String action = method.getName();
                        if (StringAdapter.NotNull(methAnn.name())) {
                            action = methAnn.name();
                        }
                        Right rt = result.add(cl.getName(), action, cl.getName(), methAnn.description());
                        if (StringAdapter.NotNull(mappingName)) {
                            mapping.put(mappingName, rt);
                        }
                    }
                }

            }
        }
        return result;
    }

}
