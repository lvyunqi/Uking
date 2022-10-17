package com.uking.shiro;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * 不创建shiro内部的session
 * @author mryunqi
 */
public class ShiroDefaultSubjectFactory extends DefaultSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建shiro内部的session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
