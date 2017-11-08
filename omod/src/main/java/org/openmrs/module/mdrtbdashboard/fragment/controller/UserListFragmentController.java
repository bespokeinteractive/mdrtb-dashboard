package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.model.UserLocation;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.MdrtbUserWrapper;
import org.openmrs.module.mdrtbdashboard.util.NameModel;
import org.openmrs.module.mdrtbdashboard.util.ResultModelWrapper;
import org.openmrs.module.mdrtbdashboard.util.UserLocationModel;
import org.openmrs.module.mdrtbdashboard.util.UserRoleModel;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dennis Henry
 * Created on 4/1/2017.
 */
public class UserListFragmentController {
    public String get(FragmentModel model, UiUtils ui) {
        return null;
    }

    public SimpleObject addUserDetails(@BindParams("wrap") ResultModelWrapper wrapper,
                                       HttpServletRequest request)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Location> locations = new ArrayList<Location>();
        User user = new User();
        String gender = wrapper.getGender();
        String username = wrapper.getUsername();
        String password = wrapper.getPassword();

        for (Map.Entry<String, String[]> params : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
            if (StringUtils.contains(params.getKey(), "location.")) {
                Location location = Context.getLocationService().getLocation(Integer.parseInt(params.getKey().substring("location.".length())));
                locations.add(location);
            }
        }


        NameModel names = new NameModel(wrapper.getNames());
        PersonName pn = new PersonName();
        pn.setGivenName(names.getGivenName());
        pn.setFamilyName(names.getFamilyName());
        pn.setMiddleName(names.getOtherNames());

        Person person = new Person();
        person.setGender(gender);
        person.addName(pn);

        user.setPerson(person);
        user.setUsername(username);

        user.addRole(Context.getUserService().getRole("Authenticated"));

        Context.getUserService().saveUser(user, password);
        Context.getService(MdrtbService.class).setUserLocations(user, locations);

        return SimpleObject.create("status", "success", "message", "User details added successfully");
    }

    public SimpleObject updateUserDetails(@BindParams("wrap") ResultModelWrapper wrapper,
                                          HttpServletRequest request)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Location> locations = new ArrayList<Location>();
        List<Role> roles = new ArrayList<Role>();

        User user = wrapper.getUser();
        String gender = wrapper.getGender();
        String username = wrapper.getUsername();

        for (Map.Entry<String, String[]> params : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
            if (StringUtils.contains(params.getKey(), "location.")) {
                Location location = Context.getLocationService().getLocation(Integer.parseInt(params.getKey().substring("location.".length())));
                locations.add(location);
            }
        }

        for (Map.Entry<String, String[]> params : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
            if (StringUtils.contains(params.getKey(), "role.")) {
                Role role = Context.getUserService().getRole(params.getKey().substring("roles".length()));
                roles.add(role);
            }
        }

        NameModel names = new NameModel(wrapper.getNames());
        Person person = user.getPerson();
        person.setGender(gender);

        PersonName pn = person.getPersonName();
        pn.setGivenName(names.getGivenName());
        pn.setFamilyName(names.getFamilyName());
        pn.setMiddleName(names.getOtherNames());

        user.addName(pn);
        user.setPerson(person);
        user.setUsername(username);

        for(Role role: Context.getUserService().getAllRoles()){
            if (role.getRole().equals("Anonymous") || role.getRole().equals("Authenticated")){
                continue;
            }

           if (roles.contains(role)){
               user.addRole(role);
           }
           else{
               user.removeRole(role);
           }
       }


        Context.getUserService().saveUser(user, null);
        Context.getService(MdrtbService.class).setUserLocations(user, locations);



        return SimpleObject.create("status", "success", "message", "User details successfully updated!");
    }

    public  void setUserRoles(User user, List<Role> roles){


    }

    public SimpleObject changePasswordDetails(@BindParams("wrap") ResultModelWrapper wrapper,
                                              HttpServletRequest request)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String username = wrapper.getUsername();
        String password = wrapper.getPassword();
        User user = wrapper.getUser();
        user.setUsername(username);
        Context.getUserService().saveUser(user, password);

        return SimpleObject.create("status", "success", "message", "Password successfully changed!");
    }

    public SimpleObject getUserDetails(@RequestParam(value = "userId") User user,

                                       UiUtils ui) {
        SimpleObject users = SimpleObject.create("names", user.getPersonName().getFullName(), "gender", user.getPerson().getGender(), "systemId", user.getSystemId(), "username", user.getUsername());
        List<UserLocationModel> model = new ArrayList<UserLocationModel>();
        List<Location> locationList = Context.getLocationService().getAllLocations();
        List<Location> locations = getAuthenticatedUsersLocations(user);

        for (Location location : locationList) {
            UserLocationModel usl = new UserLocationModel();
            usl.setId(location.getLocationId());
            usl.setName(location.getName());

            if (locations.contains(location)) {
                usl.setChecked(true);
            }

            model.add(usl);
        }

        List<Role> roles = Context.getUserService().getAllRoles();
        List<UserRoleModel> rmodel = new ArrayList<UserRoleModel>();

        for (Role role : roles) {
            if (role.getRole().equals("Anonymous") || role.getRole().equals("Authenticated") || role.getRole().equals("System Developer") || role.getRole().equals("Privilege Level: Full") || role.getRole().equals("Provider")){
                continue;
            }

            UserRoleModel url = new UserRoleModel();
            url.setName(role.getName());

            if (user.hasRole(role.getRole())){
                url.setChecked(true);
            }

            rmodel.add(url);
        }

        return SimpleObject.create("user", users, "location", model,"role", rmodel);
    }

    public SimpleObject getAllLocations() {
        List<Location> locationList = Context.getLocationService().getAllLocations();
        List<UserLocationModel> model = new ArrayList<UserLocationModel>();

        for (Location location : locationList) {
            UserLocationModel usl = new UserLocationModel();
            usl.setId(location.getLocationId());
            usl.setName(location.getName());
            model.add(usl);
        }

        return SimpleObject.create("location", model);
    }

    public List<SimpleObject> getAuthenticatedUsers(UiUtils ui, HttpServletRequest request) {
        List<User> userList = Context.getUserService().getAllUsers();
        List<MdrtbUserWrapper> wrapperList = mdrtbUserWithDetails(userList);

        return SimpleObject.fromCollection(wrapperList, ui, "user.userId", "user.systemId", "user.username", "wrapperLocations", "wrapperNames");
    }

    public List<Location> getAuthenticatedUsersLocations(User user) {
        List<UserLocation> locales = Context.getService(MdrtbService.class).getUserLocations(user);
        List<Location> locations = new ArrayList<Location>();

        for (UserLocation locale : locales) {
            locations.add(locale.getLocation());
        }

        return locations;
    }

    private List<MdrtbUserWrapper> mdrtbUserWithDetails(List<User> users) {
        List<MdrtbUserWrapper> wrapper = new ArrayList<MdrtbUserWrapper>();
        for (User user : users) {
            MdrtbUserWrapper uw = new MdrtbUserWrapper(user);
            wrapper.add(uw);
        }
        return wrapper;
    }

    public SimpleObject getAllSysRoles() {
        List<Role> roles = Context.getUserService().getAllRoles();
        List<UserRoleModel> model = new ArrayList<UserRoleModel>();

        for (Role role : roles) {
            UserRoleModel usl = new UserRoleModel();
            usl.setId(role.getId());
            usl.setName(role.getName());
            model.add(usl);
        }

        return SimpleObject.create("role", model);
    }

}
