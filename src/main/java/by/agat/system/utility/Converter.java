package by.agat.system.utility;

import by.agat.system.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    public static DocumentDTO convertFromDocumentToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setBank(document.getBank());
        documentDTO.setName(document.getName());
        documentDTO.setPath(document.getPath());
        documentDTO.setTypeDoc(document.getType().getType());
        documentDTO.setUuid(document.getUuid());
        documentDTO.setStatus(document.getStatuses().get(document.getStatuses().size() - 1).getStatus().getStatus());
        return documentDTO;
    }

    public static UserDTO convertFromUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setCodeBank(user.getCodeBank());
        userDTO.setDateRegistration(user.getDateRegistration());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setId(user.getUser_id());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRoles().get(0));
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public static User convertFromUserSaveModelToUser(UserSaveModel userSaveModel) {
        User user = new User();
        user.setCodeBank(userSaveModel.getCodeBank());
        user.setDateRegistration(new Date());
        if(userSaveModel.isEnabled()) {
            user.setEnabled(new Byte("1"));
        } else {
            user.setEnabled(new Byte("0"));
        }

        user.setFirstname(userSaveModel.getFirstname());
        user.setSurname(userSaveModel.getSurname());
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(userSaveModel.getRole());
        roles.add(role);
        user.setRoles(roles);
        user.setUsername(userSaveModel.getUsername());
        return user;
    }
}
