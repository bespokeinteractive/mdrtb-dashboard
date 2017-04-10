package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Dennis Henry
 * Created On 4/1/2017
 */

public class UsersPageController {
    public String get(@RequestParam(value = "userId", required = false) Integer userId,
                      PageModel model,
                      UiUtils ui){
        if (userId == null){
            model.addAttribute("userId", 0);
        }
        else {
            User user = Context.getUserService().getUser(userId);

            model.addAttribute("user", user);
            model.addAttribute("userId", userId);
        }

        return null;
    }
}
